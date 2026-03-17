package com.ChalkerCharles.morecolorful.client.renderer.item;

import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.client.texture.DynamicTextureAtlas;
import com.ChalkerCharles.morecolorful.client.texture.PapercuttingTextureManager;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.PapercuttingStencil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class PapercuttingRenderer extends DynamicItemRenderer {
    public static final PapercuttingRenderer INSTANCE = new PapercuttingRenderer();

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Item item = pStack.getItem();
        Block block = Block.byItem(item);
        if (block instanceof PapercuttingBlock) {
            PapercuttingStencil stencil = pStack.getOrDefault(ModDataComponents.PAPERCUTTING_STENCIL, PapercuttingStencil.DEFAULT);
            pPoseStack.pushPose();
            pPoseStack.translate(0.5F, 0.0F, 0.5F);
            pPoseStack.scale(-0.0625F, 0.0625F, 0.0625F);
            PoseStack.Pose pose = pPoseStack.last();
            DynamicTextureAtlas.Sprite sprite = PapercuttingTextureManager.get(block, stencil);
            VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(pBuffer, PapercuttingTextureManager.RENDER_TYPE, false, pStack.hasFoil());
            buildPapercuttingModel(consumer, pose, sprite, pPackedLight);
            pPoseStack.popPose();
        }
    }

    private static void buildPapercuttingModel(VertexConsumer consumer, PoseStack.Pose pose, DynamicTextureAtlas.Sprite sprite, int pPackedLight) {
        float u0 = sprite.u0();
        float v0 = sprite.v0();
        float u1 = sprite.u1();
        float v1 = sprite.v1();

        vertex(pose, consumer, 8, 16, u0, v0, 1, pPackedLight);
        vertex(pose, consumer, 8, 0, u0, v1, 1, pPackedLight);
        vertex(pose, consumer, -8, 0, u1, v1, 1, pPackedLight);
        vertex(pose, consumer, -8, 16, u1, v0, 1, pPackedLight);

        vertex(pose, consumer, 8, 16, u0, v0, -1, pPackedLight);
        vertex(pose, consumer, -8, 16, u1, v0, -1, pPackedLight);
        vertex(pose, consumer, -8, 0, u1, v1, -1, pPackedLight);
        vertex(pose, consumer, 8, 0, u0, v1, -1, pPackedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int x, int y, float u, float v, int normalZ, int packedLight) {
        consumer.addVertex(pose, x, y, 0)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 0, normalZ);
    }

    public static void renderOnScreen(ItemStack stack, GuiGraphics guiGraphics, int x, int y) {
        Item item = stack.getItem();
        Block block = Block.byItem(item);
        if (block instanceof PapercuttingBlock) {
            PapercuttingStencil stencil = stack.getOrDefault(ModDataComponents.PAPERCUTTING_STENCIL, PapercuttingStencil.DEFAULT);
            DynamicTextureAtlas.Sprite sprite = PapercuttingTextureManager.get(block, stencil);
            int x1 = x + 64, y1 = y + 64;
            float u0 = sprite.u0(), v0 = sprite.v0(), u1 = sprite.u1(), v1 = sprite.v1();
            RenderSystem.setShaderTexture(0, Atlases.PAPERCUTTING_SHEET);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            Matrix4f matrix4f = guiGraphics.pose().last().pose();
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.addVertex(matrix4f, x, y, 0).setUv(u0, v0);
            bufferbuilder.addVertex(matrix4f, x, y1, 0).setUv(u0, v1);
            bufferbuilder.addVertex(matrix4f, x1, y1, 0).setUv(u1, v1);
            bufferbuilder.addVertex(matrix4f, x1, y, 0).setUv(u1, v0);
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        }
    }
}
