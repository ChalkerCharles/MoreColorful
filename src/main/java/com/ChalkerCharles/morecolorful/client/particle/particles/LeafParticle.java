package com.ChalkerCharles.morecolorful.client.particle.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class LeafParticle extends TextureSheetParticle {
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;
    public LeafParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSpriteSet) {
        super(pLevel, pX, pY, pZ);
        this.setSprite(pSpriteSet.get(this.random.nextInt(8), 8));
        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 300;
        this.gravity = 7.5E-4F;
        float f = this.random.nextBoolean() ? 0.1F : 0.125F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
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
            float f = (float)(300 - this.lifetime);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = Math.cos(Math.toRadians(this.particleRandom * 60.0F)) * 2.0 * Math.pow(f1, 1.25);
            double d1 = Math.sin(Math.toRadians(this.particleRandom * 60.0F)) * 2.0 * Math.pow(f1, 1.25);
            this.xd += d0 * 0.0025F;
            this.zd += d1 * 0.0025F;
            this.yd = this.yd - (double)this.gravity;
            this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll = this.roll + this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd = this.xd * (double)this.friction;
                this.yd = this.yd * (double)this.friction;
                this.zd = this.zd * (double)this.friction;
            }
        }
    }
}
