package com.ChalkerCharles.morecolorful.client.renderer.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.KiteColor;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unchecked")
public class KiteRenderer extends DynamicItemRenderer {
    private static final String[] PREFIXES = new String[]{"0_", "1_", "2_", "3_", "tail_", "bow_"};
    private static final Material ROPE_TEXTURE = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("item/kite_rope"));
    private static final Material[][] TEXTURES = new Material[6][];
    private static List<BakedQuad> ropeQuads;
    private static final List<BakedQuad>[] BAKED_QUADS_CACHE = new List[96];
    public static final KiteRenderer INSTANCE = new KiteRenderer();

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        ropeQuads = bakeLayer(ROPE_TEXTURE, 0);
        Arrays.fill(BAKED_QUADS_CACHE, null);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        List<DyeColor> colors = pStack.getOrDefault(ModDataComponents.KITE_COLOR, KiteColor.DEFAULT).colors();
        List<List<BakedQuad>> list = new ArrayList<>(6);
        list.add(ropeQuads);
        list.add(getBakedLayer(colors, 0));
        list.add(getBakedLayer(colors, 1));
        list.add(getBakedLayer(colors, 2));
        list.add(getBakedLayer(colors, 3));
        list.add(getBakedLayer(colors, 4));
        DyeColor bowColor = pStack.get(ModDataComponents.RIBBON);
        if (bowColor != null) {
            list.add(getBakedLayer(bowColor, 5));
        }
        VertexConsumer consumer = getItemFoilBuffer(pBuffer, Sheets.translucentCullBlockSheet(), pStack.hasFoil());
        renderItemLayers(pPoseStack, consumer, list, pPackedLight, pPackedOverlay);
    }

    private static List<BakedQuad> getBakedLayer(List<DyeColor> colors, int index) {
        return getBakedLayer(colors.get(index), index);
    }

    private static List<BakedQuad> getBakedLayer(DyeColor color, int index) {
        int i = color.getId();
        if (i > 15) i = 0;
        int j = (index << 4) + i;
        List<BakedQuad> quads = BAKED_QUADS_CACHE[j];
        if (quads == null) {
            quads = bakeLayer(TEXTURES[index][i], index + 1);
            BAKED_QUADS_CACHE[j] = quads;
        }
        return quads;
    }

    private static String colorNameFix(DyeColor color) {
        return color.getId() > 15 ? DyeColor.WHITE.getName() : color.getName();
    }

    static {
        DyeColor[] colors = DyeColor.values();
        int size = colors.length;
        for (int i = 0; i < 6; i++) {
            Material[] materials = new Material[size];
            for (int j = 0; j < size; j++) {
                DyeColor color = colors[j];
                materials[j] = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("colored/kite_item/" + PREFIXES[i] + colorNameFix(color)));
            }
            TEXTURES[i] = materials;
        }
    }
}
