package com.ChalkerCharles.morecolorful.util;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
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
}
