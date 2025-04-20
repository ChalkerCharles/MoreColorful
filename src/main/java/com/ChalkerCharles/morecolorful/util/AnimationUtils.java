package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.client.renderer.block.CymbalRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class AnimationUtils {
    public static void animateGuitarPlaying(ModelPart pRightArm, ModelPart pLeftArm, LivingEntity pLivingEntity, boolean pRightHanded) {
        ModelPart modelPart = pRightHanded ? pLeftArm : pRightArm;
        float angle = (float) -(Math.PI / 12);
        float f = pLivingEntity.getTicksUsingItem();
        float f1 = f % 10 >= 5 ? - (f % 10) + 5 : (f % 10) - 5;
        modelPart.yRot = Mth.rotLerp(f1 / 3, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
        modelPart.zRot = Mth.rotLerp(f1 / 3, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
    }
    public static void animateViolinPlaying(ModelPart pRightArm, ModelPart pLeftArm, LivingEntity pLivingEntity, boolean pRightHanded) {
        ModelPart modelPart = pRightHanded ? pLeftArm : pRightArm;
        float angle = (float) -(Math.PI / 16);
        float f = pLivingEntity.getTicksUsingItem();
        float f1 = f % 20 >= 10 ? - (f % 20) + 10 : (f % 20) - 10;
        modelPart.yRot = Mth.rotLerp(f1 / 5, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
        modelPart.zRot = Mth.rotLerp(f1 / 5, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
    }
    public static void animateCelloPlaying(ModelPart pRightArm, ModelPart pLeftArm, LivingEntity pLivingEntity, boolean pRightHanded) {
        ModelPart modelPart = pRightHanded ? pLeftArm : pRightArm;
        float angle = (float) -(Math.PI / 16);
        float f = pLivingEntity.getTicksUsingItem();
        float f1 = f % 20 >= 10 ? - (f % 20) + 10 : (f % 20) - 10;
        modelPart.yRot = Mth.rotLerp(f1 / 4, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
        modelPart.zRot = Mth.rotLerp(f1 / 8, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
        modelPart.z = Mth.lerp(f1 / 4, 0.0F, 1.0F);
    }
    public static void animatePipaPlaying(ModelPart pRightArm, ModelPart pLeftArm, LivingEntity pLivingEntity, boolean pRightHanded) {
        ModelPart modelPart = pRightHanded ? pLeftArm : pRightArm;
        float angle = (float) -(Math.PI / 12);
        float f = pLivingEntity.getTicksUsingItem();
        float f1 = f % 16 >= 8 ? - (f % 16) + 8 : (f % 16) - 8;
        modelPart.xRot = Mth.rotLerp(f1 / 4, modelPart.xRot, -angle + modelPart.xRot);
        modelPart.yRot = Mth.rotLerp(f1 / 4, modelPart.yRot, angle * (float)(pRightHanded ? 1 : -1) + modelPart.yRot);
        modelPart.zRot = Mth.rotLerp(f1 / 8, modelPart.zRot, angle * (float)(pRightHanded ? 1 : -1) + modelPart.zRot);
    }
    public static void animateErhuPlaying(ModelPart pRightArm, ModelPart pLeftArm, LivingEntity pLivingEntity, boolean pRightHanded) {
        ModelPart modelPart = pRightHanded ? pLeftArm : pRightArm;
        float angle = (float) -(Math.PI / 16);
        float f = pLivingEntity.getTicksUsingItem();
        float f1 = f % 20 >= 10 ? - (f % 20) + 10 : (f % 20) - 10;
        modelPart.yRot = Mth.rotLerp(f1 / 8, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
        modelPart.zRot = Mth.rotLerp(f1 / 8, 0.0F, angle * (float)(pRightHanded ? 1 : -1));
        modelPart.z = Mth.lerp(f1 / 8, 0.0F, 1.0F);
    }
    public static void animateCymbalShaking(
            float ticks,
            float ticksAfterStop,
            boolean shaking,
            ModelPart part,
            float pPartialTick,
            PoseStack pPoseStack,
            MultiBufferSource pBufferSource,
            int pPackedLight,
            int pPackedOverlay) {
        float f = ticks + pPartialTick;
        float f0 = ticksAfterStop + pPartialTick;
        float f1 = 0.0F;
        float f2 = 0.0F;
        if (shaking) {
            float f3 = Mth.sin(f / (float) Math.PI) / (4.0F + f0 / 2.0F);
            float f4 = Mth.cos(f / (float) Math.PI) / (4.0F + f0 / 2.0F);
            f1 = f3;
            f2 = f4;
        }
        part.xRot = f1;
        part.zRot = f2;
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.entitySolid(CymbalRenderer.CYMBAL_TEXTURE));
        part.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
    }
    public static <T extends BlockEntity & CymbalUtils> void animateCymbalShakingWithOffset(
            T blockEntity,
            float ticks,
            float ticksAfterStop,
            boolean shaking,
            ModelPart part,
            float pPartialTick,
            PoseStack pPoseStack,
            MultiBufferSource pBufferSource,
            int pPackedLight,
            int pPackedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        float rot = blockState.getValue(HorizontalDirectionalBlock.FACING).getOpposite().toYRot();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0.5F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-rot));
        pPoseStack.translate(-0.5F, -0.5F, -0.5F);
        animateCymbalShaking(ticks, ticksAfterStop, shaking, part, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
        pPoseStack.popPose();
    }
}
