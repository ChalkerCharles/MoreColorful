package com.ChalkerCharles.morecolorful.client.particle.particles;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor.ARGB32;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfettiParticle extends TextureSheetParticle implements WindSensitive, IParticleExtension {
    private static final int[] COLORS = new int[] {
            0xdb352c, 0xfb9320, 0xffd426, 0x99e42e, 0x14d9cc,
            0x3299e3, 0x4149df, 0x9940e3, 0xdb65cf, 0xfa82a8
    };
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    protected ConfettiParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
        super(pLevel, pX, pY, pZ);
        this.pickSprite(spriteSet);
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 300;
        this.gravity = this.random.nextFloat() * 0.01F + 0.0015F;
        float f = this.random.nextFloat() * 0.2F + 0.05F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        int color = COLORS[this.random.nextInt(COLORS.length)];
        this.setColor(ARGB32.red(color) / 255.0F, ARGB32.green(color) / 255.0F, ARGB32.blue(color) / 255.0F);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float f = (float) (300 - this.lifetime);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = f1 * Math.cos(f1 * Math.toRadians(this.particleRandom * 60.0F)) * 2.0;
            double d1 = f1 * Math.sin(f1 * Math.toRadians(this.particleRandom * 60.0F)) * 2.0;
            this.xd += d0 * 0.0025F;
            this.zd += d1 * 0.0025F;
            this.yd = this.yd - (double) this.gravity;
            this.oRoll = this.roll;
            if (!this.onGround && !this.stoppedByCollision) {
                this.rotSpeed = this.rotSpeed + this.spinAcceleration / 10.0F;
                this.roll = this.roll + this.rotSpeed / 15.0F;
            }
            this.move(this.xd, this.yd, this.zd);
            if (Config.confettiOnGround) {
                this.moreColorful$floatOnFluid();
            }
            if (!Config.confettiOnGround && (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0))) {
                this.remove();
            }

            if (!this.removed) {
                this.xd = this.xd * (double) this.friction;
                this.yd = this.yd * (double) this.friction;
                this.zd = this.zd * (double) this.friction;
            }
        }
    }

    @Override
    public void moreColorful$whenStoppedByCollision() {
        this.y += 0.0001 + this.particleRandom * 0.01;
    }

    @Override
    public FacingCameraMode getFacingCameraMode() {
        if (Config.confettiOnGround && (this.onGround || this.stoppedByCollision)) {
            return this::moreColorful$groundFacingCameraMode;
        }
        return super.getFacingCameraMode();
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            ConfettiParticle particle = new ConfettiParticle(pLevel, pX, pY, pZ, this.sprites);
            particle.setParticleSpeed(pXSpeed, pYSpeed, pZSpeed);
            return particle;
        }
    }
}
