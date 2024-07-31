package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
    @Shadow
    @Final
    public ModelPart rightArm;
    @Shadow
    @Final
    public ModelPart leftArm;

    @Unique
    protected void moreColorful$setupKeyboardAnimation(T pLivingEntity) {
        if (Minecraft.getInstance().screen instanceof PlayingScreen pScreen && (PlayingScreen.pType == InstrumentsType.PIANO || (PlayingScreen.pType.ordinal() >= 9 && PlayingScreen.pType.ordinal() <= 10))) {
            boolean flag = pLivingEntity.isPassenger();
            this.rightArm.xRot = flag ? -1.326451F : -1.221731F;
            this.leftArm.xRot = flag ? -1.326451F : -1.221731F;
            float angle = (float) -(Math.PI / 12);
            float f = pScreen.pTick;
            float f1 = f % 6 >= 3 ? - (f % 6) + 4.5F : (f % 6) - 1.5F;
            float f2 = f % 12 >= 6 ? - (f % 12) + 9 : (f % 12) - 3;
            if (PlayingScreen.isPressing) {
                this.rightArm.xRot = Mth.rotLerp(f1 / 8, this.rightArm.xRot, angle - 1.221731F);
                this.rightArm.yRot = Mth.rotLerp(f2 / 4, 0.0F, angle);
                this.rightArm.zRot = Mth.rotLerp(f2 / 4, 0.0F, angle);
                this.leftArm.xRot = Mth.rotLerp(f1 / 8, this.leftArm.xRot, -angle - 1.221731F);
                this.leftArm.yRot = Mth.rotLerp(f2 / 4, 0.0F, -angle);
                this.leftArm.zRot = Mth.rotLerp(f2 / 4, 0.0F, -angle);
            }
        }
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "setupAnim", at = @At("TAIL"))
    public void moreColorful$setupAnim(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        this.moreColorful$setupKeyboardAnimation(pEntity);
    }
}
