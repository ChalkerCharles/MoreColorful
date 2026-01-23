package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FluidState;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public abstract class ParticleMixin implements IParticleExtension {
    @Shadow
    @Final
    protected ClientLevel level;
    @Shadow
    protected double x;
    @Shadow
    protected double y;
    @Shadow
    protected double z;
    @Shadow
    protected double xd;
    @Shadow
    protected double yd;
    @Shadow
    protected double zd;
    @Shadow
    protected boolean stoppedByCollision;
    @Shadow
    protected boolean onGround;
    @Shadow
    @Final
    protected RandomSource random;

    @Shadow
    public abstract void remove();

    @Override
    public void moreColorful$applyWind() {
        if (!WeatherUtils.isWindSensitive(this) || this.stoppedByCollision) return;
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, x, y, z);
        if (wind != null) {
            double windX = wind.x * 0.04 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windY = wind.y * 0.04 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = wind.z * 0.04 * WeatherUtils.getRandomSpeedMultiplier(random);
            this.xd = Mth.clamp(xd + windX * 0.02, Math.min(xd, windX), Math.max(xd, windX));
            this.yd += windY * 0.02;
            this.zd = Mth.clamp(zd + windZ * 0.02, Math.min(zd, windZ), Math.max(zd, windZ));
        }
    }

    @Inject(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/Particle;stoppedByCollision:Z", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void move(CallbackInfo ci) {
        this.moreColorful$whenStoppedByCollision();
    }

    @Override
    public void moreColorful$floatOnFluid() {
        if (!this.stoppedByCollision) {
            FluidState state = LevelSavedData.getFluidState(this.level, Mth.floor(this.x), Mth.floor(this.y), Mth.floor(this.z));
            if (!state.isEmpty()) {
                if (state.is(FluidTags.LAVA)) {
                    this.remove();
                    return;
                }
                float height = state.getOwnHeight();
                if (Mth.frac(this.y) < height) {
                    this.stoppedByCollision = true;
                    this.onGround = true;
                    this.y = Mth.floor(this.y) + height + 0.01 + this.random.nextDouble() * 0.01;
                }
            }
        }
    }

    @Override
    public void moreColorful$groundFacingCameraMode(Quaternionf quaternion, Camera camera, float partialTick) {
        float angle = camera.getPosition().y < this.y ? Mth.HALF_PI : -Mth.HALF_PI;
        quaternion.rotateX(angle);
    }
}
