package com.ChalkerCharles.morecolorful.client.particle.particles;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.Predicates;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

@OnlyIn(Dist.CLIENT)
public class LeafParticle extends TextureSheetParticle implements WindSensitive, IParticleExtension {
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
            float f = (float)(300 - this.lifetime);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = f1 * Math.cos(f1 * Math.toRadians(1000.0F + this.particleRandom * 3000.0F)) * 10.0;
            double d1 = f1 * Math.sin(f1 * Math.toRadians(1000.0F + this.particleRandom * 3000.0F)) * 10.0;
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

    @Override
    public void moreColorful$applyWind() {
        WeatherUtils.canApplyWind(level, this.getPos()).thenAccept(Predicates.ifTrueThen(() -> {
            Vector2f globalWind = LevelSavedData.getGlobalWindSpeed(this.level);
            double windX = globalWind.x() * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = globalWind.y() * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
            this.xd = Mth.clamp(xd + windX * 0.02, Math.min(xd, windX), Math.max(xd, windX));
            this.zd = Mth.clamp(zd + windZ * 0.02, Math.min(zd, windZ), Math.max(zd, windZ));
        }));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new LeafParticle(pLevel, pX, pY, pZ, sprites);
        }
    }
}
