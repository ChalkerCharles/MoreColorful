package com.ChalkerCharles.morecolorful.client.particle.particles;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LeafParticle extends TextureSheetParticle implements WindSensitive, IParticleExtension {
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    public LeafParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSpriteSet) {
        super(pLevel, pX, pY, pZ);
        this.setSprite(pSpriteSet.get(this.random.nextInt(12), 12));
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 300;
        this.gravity = 2.1E-4F;
        float f = this.random.nextBoolean() ? 0.1F : 0.125F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        this.yd = -0.021F;
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
            double d0 = f1 * Math.cos(f1 * Math.toRadians(1000.0F + this.particleRandom * 3000.0F)) * 10.0;
            double d1 = f1 * Math.sin(f1 * Math.toRadians(1000.0F + this.particleRandom * 3000.0F)) * 10.0;
            this.xd += d0 * 0.0025F;
            this.zd += d1 * 0.0025F;
            this.yd = this.yd - (double) this.gravity;
            this.oRoll = this.roll;
            if (!this.onGround && !this.stoppedByCollision) {
                this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
                this.roll = this.roll + this.rotSpeed / 20.0F;
            }
            this.move(this.xd, this.yd, this.zd);
            if (Config.leavesOnGround) {
                this.moreColorful$floatOnFluid();
            }
            if (!Config.leavesOnGround && (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0))) {
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
        if (Config.leavesOnGround && (this.onGround || this.stoppedByCollision)) {
            return this::moreColorful$groundFacingCameraMode;
        }
        return super.getFacingCameraMode();
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new LeafParticle(pLevel, pX, pY, pZ, sprites);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public record TintedProvider(SpriteSet sprites) implements ParticleProvider<ColorParticleOption> {
        @Override
        public Particle createParticle(ColorParticleOption pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            LeafParticle particle = new LeafParticle(pLevel, pX, pY, pZ, sprites);
            particle.setColor(pType.getRed(), pType.getGreen(), pType.getBlue());
            return particle;
        }
    }
}
