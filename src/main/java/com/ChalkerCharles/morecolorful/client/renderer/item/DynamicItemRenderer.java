package com.ChalkerCharles.morecolorful.client.renderer.item;

import com.ChalkerCharles.morecolorful.client.renderer.ItemGlintVertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.geometry.UnbakedGeometryHelper;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class DynamicItemRenderer extends BlockEntityWithoutLevelRenderer {
    public DynamicItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public static VertexConsumer getItemFoilBuffer(MultiBufferSource buffer, RenderType renderType, boolean glint) {
        return glint ? new ItemGlintVertexConsumer(buffer.getBuffer(RenderType.glint()), buffer.getBuffer(renderType)) : buffer.getBuffer(renderType);
    }

    public static void renderItemLayers(PoseStack poseStack, VertexConsumer buffer, List<List<BakedQuad>> list, int combinedLight, int combinedOverlay) {
        PoseStack.Pose pose = poseStack.last();
        if (buffer instanceof ItemGlintVertexConsumer consumer) {
            for (int i = 0, k = list.size(); i < k; i++) {
                List<BakedQuad> quads = list.get(i);
                for (int j = 0, l = quads.size(); j < l; j++) {
                    BakedQuad quad = quads.get(j);
                    consumer.setRenderGlint(i == 0 || j > 1);
                    consumer.putBulkData(pose, quad, 1, 1, 1, 1, combinedLight, combinedOverlay, true);
                }
            }
        } else {
            for (List<BakedQuad> quads : list) {
                for (BakedQuad quad : quads) {
                    buffer.putBulkData(pose, quad, 1, 1, 1, 1, combinedLight, combinedOverlay, true);
                }
            }
        }
    }

    public static List<BakedQuad> bakeLayer(Material material, int layer) {
        TextureAtlasSprite sprite = material.sprite();
        List<BlockElement> unbaked = UnbakedGeometryHelper.createUnbakedItemElements(layer, sprite);
        return UnbakedGeometryHelper.bakeElements(unbaked, m -> sprite, BlockModelRotation.X0_Y0);
    }
}
