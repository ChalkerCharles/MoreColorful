package com.ChalkerCharles.morecolorful.mixin.mixins.client.model;

import com.ChalkerCharles.morecolorful.client.model.ArmPoseExtension;
import com.ChalkerCharles.morecolorful.common.attachment.InstrumentData;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {
    @Shadow
    @Final
    public ModelPart rightArm;
    @Shadow
    @Final
    public ModelPart leftArm;
    @Shadow
    public HumanoidModel.ArmPose rightArmPose;
    @Shadow
    public HumanoidModel.ArmPose leftArmPose;

    @Shadow
    protected abstract void poseLeftArm(T pLivingEntity);
    @Shadow
    protected abstract void poseRightArm(T pLivingEntity);

    @Unique
    private void moreColorful$setupKeyboardAnimation(T pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            InstrumentData data = InstrumentData.get(player);
            InstrumentsType type = data.type;
            BlockPos pos = data.pos;
            boolean isOpen = data.isOpen;
            if (isOpen && type.isKeyBoard()) {
                double deltaY = (pos.getY() - player.getY());
                if (type == InstrumentsType.PIANO_LOW || type == InstrumentsType.PIANO_HIGH) {
                    BlockState state = player.level().getBlockState(pos);
                    if ((state.is(ModBlocks.GRAND_PIANO) && state.getValue(ModBlockStateProperties.GRAND_PIANO_PART).isUpper())
                            || (state.is(ModBlocks.UPRIGHT_PIANO) && state.getValue(ModBlockStateProperties.UPRIGHT_PIANO_PART).isUpper())) {
                        deltaY -= 1;
                    }
                }
                this.rightArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                this.leftArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                float angle = (float) -(Math.PI / 12);
                float f = data.tick;
                float f1 = f % 6 >= 3 ? -(f % 6) + 4.5F : (f % 6) - 1.5F;
                float f2 = f % 12 >= 6 ? -(f % 12) + 9 : (f % 12) - 3;
                if (data.isPlaying) {
                    this.rightArm.xRot = Mth.rotLerp(f1 / 8, this.rightArm.xRot, angle + this.rightArm.xRot);
                    this.rightArm.yRot = Mth.rotLerp(f2 / 4, 0.0F, angle);
                    this.rightArm.zRot = Mth.rotLerp(f2 / 4, 0.0F, angle);
                    this.leftArm.xRot = Mth.rotLerp(f1 / 8, this.leftArm.xRot, -angle + this.leftArm.xRot);
                    this.leftArm.yRot = Mth.rotLerp(f2 / 4, 0.0F, -angle);
                    this.leftArm.zRot = Mth.rotLerp(f2 / 4, 0.0F, -angle);
                }
            }
        }
    }
    @Unique
    private void moreColorful$setupGuzhengAnimation(T pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            InstrumentData data = InstrumentData.get(player);
            InstrumentsType type = data.type;
            BlockPos pos = data.pos;
            boolean isOpen = data.isOpen;
            if (isOpen && type == InstrumentsType.GUZHENG) {
                double deltaY = (pos.getY() - player.getY());
                this.rightArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                this.leftArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                float angle = (float) -(Math.PI / 6);
                float f = data.tick;
                float f1 = f % 12 >= 6 ? -(f % 12) + 9F : (f % 12) - 3F;
                float f2 = f % 12 >= 6 ? -(f % 12) + 9 : (f % 12) - 3;
                if (data.isPlaying) {
                    this.rightArm.xRot = Mth.rotLerp(f1 / 32, this.rightArm.xRot, angle + this.rightArm.xRot);
                    this.rightArm.yRot = Mth.rotLerp(f2 / 8, 0.0F, angle);
                    this.rightArm.zRot = Mth.rotLerp(f2 / 8, 0.0F, angle);
                    this.leftArm.xRot = Mth.rotLerp(f1 / 32, this.leftArm.xRot, -angle + this.leftArm.xRot);
                    this.leftArm.yRot = Mth.rotLerp(f2 / 8, 0.0F, -angle);
                    this.leftArm.zRot = Mth.rotLerp(f2 / 8, 0.0F, -angle);
                }
            }
        }
    }

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/LivingEntity;FFF)V", at = @At("TAIL"))
    private void prepareMobModel(T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, CallbackInfo ci) {
        if (entity instanceof Player) return;
        if (UmbrellaItem.isOpen(ItemUtils.getRightHandItem(entity))) {
            this.rightArmPose = ArmPoseExtension.UMBRELLA;
        }
        if (UmbrellaItem.isOpen(ItemUtils.getLeftHandItem(entity))) {
            this.leftArmPose = ArmPoseExtension.UMBRELLA;
        }
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;poseRightArm(Lnet/minecraft/world/entity/LivingEntity;)V", ordinal = 0))
    private void setUpAnim$poseLeftArm(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        if (UmbrellaItem.isHolding(pEntity)) {
            this.poseLeftArm(pEntity);
        }
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;poseLeftArm(Lnet/minecraft/world/entity/LivingEntity;)V", ordinal = 0))
    private void setUpAnim$poseRightArm(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        if (UmbrellaItem.isHolding(pEntity)) {
            this.poseRightArm(pEntity);
        }
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        this.moreColorful$setupKeyboardAnimation(pEntity);
        this.moreColorful$setupGuzhengAnimation(pEntity);
    }
}
