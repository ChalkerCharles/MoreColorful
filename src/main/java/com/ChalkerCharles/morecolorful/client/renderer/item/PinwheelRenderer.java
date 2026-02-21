package com.ChalkerCharles.morecolorful.client.renderer.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelColor;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelContext;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.Material;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unchecked")
public class PinwheelRenderer extends DynamicItemRenderer {
    private static final Material STICK_TEXTURE = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("item/pinwheel_stick"));
    public static final Material[][] TEXTURES = new Material[16][];
    private static List<BakedQuad> stickQuads;
    private static final List<BakedQuad>[] BAKED_QUADS_CACHE = new List[256];
    public static final PinwheelRenderer INSTANCE = new PinwheelRenderer();

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        stickQuads = bakeLayer(STICK_TEXTURE, 0);
        Arrays.fill(BAKED_QUADS_CACHE, null);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        List<DyeColor> colors = pStack.getOrDefault(ModDataComponents.PINWHEEL_COLOR, PinwheelColor.DEFAULT).colors();
        int frame = pStack.getOrDefault(ModDataComponents.PINWHEEL_CONTEXT, PinwheelContext.DEFAULT).lerpFrame(RenderUtils.partialTick);
        List<List<BakedQuad>> list = List.of(
                stickQuads,
                getBakedLayer(colors, 0, frame),
                getBakedLayer(colors, 1, frame),
                getBakedLayer(colors, 2, frame),
                getBakedLayer(colors, 3, frame)
        );
        VertexConsumer consumer = getItemFoilBuffer(pBuffer, Sheets.translucentCullBlockSheet(), pStack.hasFoil());
        renderItemLayers(pPoseStack, consumer, list, pPackedLight, pPackedOverlay);
    }

    private static List<BakedQuad> getBakedLayer(List<DyeColor> colors, int index, int frame) {
        DyeColor color = colors.get(index);
        int i = color.getId();
        if (i > 15) i = 0;
        int j = (frame + (index << 2)) & 15;
        int k = (j << 4) + i;
        List<BakedQuad> quads = BAKED_QUADS_CACHE[k];
        if (quads == null) {
            quads = bakeLayer(TEXTURES[j][i], index + 1);
            BAKED_QUADS_CACHE[k] = quads;
        }
        return quads;
    }

    private static String colorNameFix(DyeColor color) {
        return color.getId() > 15 ? DyeColor.WHITE.getName() : color.getName();
    }

    static {
        DyeColor[] colors = DyeColor.values();
        int size = colors.length;
        for (int i = 0; i < 16; i++) {
            Material[] materials = new Material[size];
            for (int j = 0; j < size; j++) {
                DyeColor color = colors[j];
                materials[j] = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("colored/pinwheel/" + i + '_' + colorNameFix(color)));
            }
            TEXTURES[i] = materials;
        }
    }
}
