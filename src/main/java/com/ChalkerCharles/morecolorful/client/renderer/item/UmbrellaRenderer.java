package com.ChalkerCharles.morecolorful.client.renderer.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.UmbrellaColor;
import com.ChalkerCharles.morecolorful.util.Colour;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.*;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.*;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unchecked")
public class UmbrellaRenderer extends DynamicItemRenderer {
    public static final ModelResourceLocation UMBRELLA_FRAME_MODEL = ModelResourceLocation.standalone(MoreColorful.location("item/umbrella_frame"));
    public static final ModelResourceLocation UMBRELLA_FRAME_MIRRORED_MODEL = ModelResourceLocation.standalone(MoreColorful.location("item/umbrella_frame_mirrored"));
    public static final ModelResourceLocation UMBRELLA_OVERLAY_MODEL = ModelResourceLocation.standalone(MoreColorful.location("item/umbrella_overlay"));
    private static final String[] PREFIXES = new String[] {"open_0_", "open_1_", "open_2_", "closed_0_", "closed_1_", "closed_2_"};
    private static final Material UMBRELLA_HANDLE_TEXTURE = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("item/umbrella_handle"));
    private static final Material OPEN_UMBRELLA_HANDLE_TEXTURE = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("item/umbrella_handle_open"));
    private static final Material[][] ITEM_TEXTURES = new Material[6][];
    private static final Material[] BLOCK_TEXTURES;
    private static BakedModel frameModel;
    private static BakedModel frameMirroredModel;
    private static BakedModel overlayModel;
    private static List<BakedQuad> handleQuads;
    private static List<BakedQuad> openHandleQuads;
    private static final List<BakedQuad>[] BAKED_QUADS_CACHE = new List[102];
    public static final UmbrellaRenderer INSTANCE = new UmbrellaRenderer();

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        ModelManager manager = Minecraft.getInstance().getModelManager();
        frameModel = manager.getModel(UMBRELLA_FRAME_MODEL);
        frameMirroredModel = manager.getModel(UMBRELLA_FRAME_MIRRORED_MODEL);
        overlayModel = manager.getModel(UMBRELLA_OVERLAY_MODEL);
        handleQuads = bakeLayer(UMBRELLA_HANDLE_TEXTURE, 0);
        openHandleQuads = bakeLayer(OPEN_UMBRELLA_HANDLE_TEXTURE, 0);
        Arrays.fill(BAKED_QUADS_CACHE, null);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        UmbrellaColor umbrellaColor = pStack.getOrDefault(ModDataComponents.UMBRELLA_COLOR, UmbrellaColor.DEFAULT);
        List<Colour> colors = umbrellaColor.colors();
        boolean open = pStack.has(ModDataComponents.OPEN);
        if (open && shouldRender3D(pDisplayContext)) {
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            BakedModel model = getFrameModel(pDisplayContext);
            VertexConsumer frameConsumer = ItemRenderer.getFoilBufferDirect(pBuffer, Sheets.cutoutBlockSheet(), false, pStack.hasFoil());
            renderer.renderModelLists(model, pStack, pPackedLight, pPackedOverlay, pPoseStack, frameConsumer);
            if (pStack.hasFoil()) {
                VertexConsumer overlayConsumer = ItemRenderer.getFoilBufferDirect(pBuffer, Sheets.translucentCullBlockSheet(), false, true);
                renderer.renderModelLists(overlayModel, pStack, pPackedLight, pPackedOverlay, pPoseStack, overlayConsumer);
            }
            pPoseStack.pushPose();
            pPoseStack.scale(0.0625F, 0.0625F, 0.0625F);
            PoseStack.Pose pose = pPoseStack.last();
            for (int i = 0; i < 8; i++) {
                Colour color = colors.get(i);
                Material material = BLOCK_TEXTURES[color.getIndex()];
                VertexConsumer canopyConsumer = material.buffer(pBuffer, RenderType::entityCutout, false);
                buildCanopyModel(pose, canopyConsumer, pPackedLight, i);
            }
            pPoseStack.popPose();
        } else {
            List<List<BakedQuad>> list = List.of(
                    open ? openHandleQuads : handleQuads,
                    getBakedLayer(colors, 0, open),
                    getBakedLayer(colors, 1, open),
                    getBakedLayer(colors, 2, open)
            );
            VertexConsumer consumer = getItemFoilBuffer(pBuffer, Sheets.translucentCullBlockSheet(), pStack.hasFoil());
            renderItemLayers(pPoseStack, consumer, list, pPackedLight, pPackedOverlay);
        }
    }

    private static boolean shouldRender3D(ItemDisplayContext context) {
        return context.firstPerson()
                || context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                || context == ItemDisplayContext.HEAD;
    }

    private static BakedModel getFrameModel(ItemDisplayContext context) {
        boolean mirror = context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
        return mirror ? frameMirroredModel : frameModel;
    }

    private static void buildCanopyModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight, int index) {
        switch (index) {
            case 0 -> {
                face(pose, consumer, -4, 24, -4, 12, 12, 0.5F, 0.5F, 1.0F, 1.0F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, -4, 16, -4, 12, -4, 0.5F, 0.5F, 0.0F, 0.75F, packedLight, 0, 0, -1, 1);
                face(pose, consumer, -4, 16, -4, -4, 12, 0.0F, 0.5F, 0.5F, 0.75F, packedLight, -1, 0, 0, 1);
            }
            case 1 -> {
                face(pose, consumer, 0, 24, -4, 16, 12, 0.5F, 0.0F, 0.0F, 0.5F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, 0, 16, -4, 16, -4, 0.0F, 0.75F, 0.5F, 1.0F, packedLight, 0, 0, -1, 1);
            }
            case 2 -> {
                face(pose, consumer, 4, 24, -4, 20, 12, 1.0F, 0.5F, 0.5F, 1.0F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, 20, 16, -4, 20, 12, 0.5F, 0.5F, 0.0F, 0.75F, packedLight, 1, 0, 0, 2);
                face(pose, consumer, 4, 16, -4, 20, -4, 0.0F, 0.5F, 0.5F, 0.75F, packedLight, 0, 0, -1, 1);
            }
            case 3 -> {
                face(pose, consumer, -4, 24, 0, 12, 16, 0.5F, 0.5F, 1.0F, 0.0F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, -4, 16, 0, -4, 16, 0.0F, 0.75F, 0.5F, 1.0F, packedLight, -1, 0, 0, 1);
            }
            case 4 -> {
                face(pose, consumer, 4, 24, 0, 20, 16, 1.0F, 0.5F, 0.5F, 0.0F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, 20, 16, 0, 20, 16, 0.0F, 0.75F, 0.5F, 1.0F, packedLight, 1, 0, 0, 2);
            }
            case 5 -> {
                face(pose, consumer, -4, 24, 4, 12, 20, 0.5F, 1.0F, 1.0F, 0.5F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, -4, 16, 4, -4, 20, 0.5F, 0.5F, 0.0F, 0.75F, packedLight, -1, 0, 0, 1);
                face(pose, consumer, -4, 16, 20, 12, 20, 0.0F, 0.5F, 0.5F, 0.75F, packedLight, 0, 0, 1, 2);
            }
            case 6 -> {
                face(pose, consumer, 0, 24, 4, 16, 20, 0.5F, 0.5F, 0.0F, 0.0F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, 0, 16, 20, 16, 20, 0.0F, 0.75F, 0.5F, 1.0F, packedLight, 0, 0, 1, 2);
            }
            case 7 -> {
                face(pose, consumer, 4, 24, 4, 20, 20, 1.0F, 1.0F, 0.5F, 0.5F, packedLight, 0, 1, 0, 0);
                face(pose, consumer, 4, 16, 20, 20, 20, 0.5F, 0.5F, 0.0F, 0.75F, packedLight, 0, 0, 1, 2);
                face(pose, consumer, 20, 16, 4, 20, 20, 0.0F, 0.5F, 0.5F, 0.75F, packedLight, 1, 0, 0, 2);
            }
        }
    }

    private static void face(PoseStack.Pose pose, VertexConsumer consumer, int x0, int y0, int z0, int x1, int z1,
                             float u0, float v0, float u1, float v1, int packedLight, int nx, int ny, int nz, int facing) {
        switch (facing) {
            case 0 -> { // up
                vertex(pose, consumer, x0, 24, z0, u0, v0, packedLight, nx, ny, nz);
                vertex(pose, consumer, x0, 24, z1, u0, v1, packedLight, nx, ny, nz);
                vertex(pose, consumer, x1, 24, z1, u1, v1, packedLight, nx, ny, nz);
                vertex(pose, consumer, x1, 24, z0, u1, v0, packedLight, nx, ny, nz);
                vertex(pose, consumer, x0, 24, z0, u0, v0, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x1, 24, z0, u1, v0, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x1, 24, z1, u1, v1, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x0, 24, z1, u0, v1, packedLight, -nx, -ny, -nz);
            }
            case 1 -> { // north, west
                vertex(pose, consumer, x1, 24, z0, u0, v0, packedLight, nx, ny, nz);
                vertex(pose, consumer, x1, y0, z0, u0, v1, packedLight, nx, ny, nz);
                vertex(pose, consumer, x0, y0, z1, u1, v1, packedLight, nx, ny, nz);
                vertex(pose, consumer, x0, 24, z1, u1, v0, packedLight, nx, ny, nz);
                vertex(pose, consumer, x1, 24, z0, u0, v0, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x0, 24, z1, u1, v0, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x0, y0, z1, u1, v1, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x1, y0, z0, u0, v1, packedLight, -nx, -ny, -nz);
            }
            case 2 -> { // south, east
                vertex(pose, consumer, x0, 24, z1, u0, v0, packedLight, nx, ny, nz);
                vertex(pose, consumer, x0, y0, z1, u0, v1, packedLight, nx, ny, nz);
                vertex(pose, consumer, x1, y0, z0, u1, v1, packedLight, nx, ny, nz);
                vertex(pose, consumer, x1, 24, z0, u1, v0, packedLight, nx, ny, nz);
                vertex(pose, consumer, x0, 24, z1, u0, v0, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x1, 24, z0, u1, v0, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x1, y0, z0, u1, v1, packedLight, -nx, -ny, -nz);
                vertex(pose, consumer, x0, y0, z1, u0, v1, packedLight, -nx, -ny, -nz);
            }
        }
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float z, float u, float v, int packedLight, int nx, int ny, int nz) {
        consumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, nx, ny, nz);
    }

    private static List<BakedQuad> getBakedLayer(List<Colour> colors, int index, boolean open) {
        int i = index + (open ? 0 : 3);
        Colour color = colors.get(index);
        int j = color.getIndex();
        if (j > 16) j = 0;
        int k = i * 17 + j;
        List<BakedQuad> quads = BAKED_QUADS_CACHE[k];
        if (quads == null) {
            quads = bakeLayer(ITEM_TEXTURES[i][j], index + 1);
            BAKED_QUADS_CACHE[k] = quads;
        }
        return quads;
    }

    private static String colorNameFix(Colour color) {
        return color.getIndex() > 16 ? "default" : color.getName();
    }

    static {
        Colour[] colors = Colour.values();
        int size = colors.length;
        for (int i = 0; i < 6; i++) {
            Material[] materials = new Material[size];
            for (int j = 0; j < size; j++) {
                Colour color = colors[j];
                materials[j] = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("colored/umbrella_item/" + PREFIXES[i] + colorNameFix(color)));
            }
            ITEM_TEXTURES[i] = materials;
        }
        Material[] materials = new Material[size];
        for (int i = 0; i < size; i++) {
            Colour color = colors[i];
            materials[i] = new Material(Atlases.BLOCK_SHEET, MoreColorful.location("colored/umbrella_block/canopy_" + colorNameFix(color)));
        }
        BLOCK_TEXTURES = materials;
    }
}
