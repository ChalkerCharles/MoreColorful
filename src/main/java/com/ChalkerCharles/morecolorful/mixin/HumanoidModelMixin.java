package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
        if (pLivingEntity instanceof Player) {
            PlayingScreenPacket data = pLivingEntity.getData(ModDataAttachments.PLAYING_SCREEN_DATA);
            InstrumentsType pType = data.pType();
            BlockPos pos = data.pos();
            Entity pPlayer = pLivingEntity.level().getEntity(data.id());
            boolean isOpen = data.isOpen();
            if (isOpen && pType.getType() == InstrumentsType.Type.KEYBOARD && pPlayer != null) {
                double deltaY = (pos.getY() - pPlayer.getY());
                if (pType == InstrumentsType.PIANO_LOW || pType == InstrumentsType.PIANO_HIGH) {
                    if ((pPlayer.level().getBlockState(pos).is(ModBlocks.GRAND_PIANO) && pPlayer.level().getBlockState(pos).getValue(ModBlockStateProperties.GRAND_PIANO_PART).ordinal() > 2)
                            || (pPlayer.level().getBlockState(pos).is(ModBlocks.UPRIGHT_PIANO) && pPlayer.level().getBlockState(pos).getValue(ModBlockStateProperties.UPRIGHT_PIANO_PART).ordinal() > 1)) {
                        deltaY -= 1;
                    }
                }
                this.rightArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                this.leftArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                float angle = (float) -(Math.PI / 12);
                float f = pPlayer.getData(ModDataAttachments.PLAYING_SCREEN_TICK);
                float f1 = f % 6 >= 3 ? -(f % 6) + 4.5F : (f % 6) - 1.5F;
                float f2 = f % 12 >= 6 ? -(f % 12) + 9 : (f % 12) - 3;
                if (pPlayer.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)) {
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
    protected void moreColorful$setupGuzhengAnimation(T pLivingEntity) {
        if (pLivingEntity instanceof Player) {
            PlayingScreenPacket data = pLivingEntity.getData(ModDataAttachments.PLAYING_SCREEN_DATA);
            InstrumentsType pType = data.pType();
            BlockPos pos = data.pos();
            Entity pPlayer = pLivingEntity.level().getEntity(data.id());
            boolean isOpen = data.isOpen();
            if (isOpen && pType == InstrumentsType.GUZHENG && pPlayer != null) {
                double deltaY = (pos.getY() - pPlayer.getY());
                this.rightArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                this.leftArm.xRot = (float) (-1.221731F - Math.tanh(deltaY));
                float angle = (float) -(Math.PI / 6);
                float f = pPlayer.getData(ModDataAttachments.PLAYING_SCREEN_TICK);
                float f1 = f % 12 >= 6 ? -(f % 12) + 9F : (f % 12) - 3F;
                float f2 = f % 12 >= 6 ? -(f % 12) + 9 : (f % 12) - 3;
                if (pPlayer.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)) {
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

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void moreColorful$setupAnim(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        this.moreColorful$setupKeyboardAnimation(pEntity);
        this.moreColorful$setupGuzhengAnimation(pEntity);
    }
}
