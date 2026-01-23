package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CherryParticle.class)
public abstract class CherryParticleMixin extends TextureSheetParticle implements WindSensitive, IParticleExtension {
    @Shadow
    @Final
    private float particleRandom;

    private CherryParticleMixin(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    @WrapWithCondition(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/CherryParticle;roll:F", opcode = Opcodes.PUTFIELD))
    private boolean tick$0(CherryParticle instance, float value) {
        return !this.onGround && !this.stoppedByCollision;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/CherryParticle;move(DDD)V", shift = At.Shift.AFTER))
    private void tick$1(CallbackInfo ci) {
        if (Config.leavesOnGround) {
            this.moreColorful$floatOnFluid();
        }
    }

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/CherryParticle;remove()V", ordinal = 1))
    private boolean tick$remove(CherryParticle instance) {
        return !Config.leavesOnGround;
    }

    @Override
    public void moreColorful$whenStoppedByCollision() {
        this.y += 0.0001 + this.particleRandom * 0.01;
    }

    @Override
    public SingleQuadParticle.FacingCameraMode getFacingCameraMode() {
        if (Config.leavesOnGround && (this.onGround || this.stoppedByCollision)) {
            return this::moreColorful$groundFacingCameraMode;
        }
        return super.getFacingCameraMode();
    }
}
