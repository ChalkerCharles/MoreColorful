package com.ChalkerCharles.morecolorful.client.particle.particles;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class SmokeBombParticles {
    @OnlyIn(Dist.CLIENT)
    private static class Smoke extends TextureSheetParticle implements WindSensitive {
        protected Smoke(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            super(pLevel, pX, pY, pZ);
            this.scale(3.0F);
            this.setSize(0.25F, 0.25F);
            this.lifetime = this.random.nextInt(50) + 280;
            this.quadSize *= 2;
            this.gravity = 1.5E-6F;
            this.xd = pXSpeed;
            this.yd = pYSpeed + (double)(this.random.nextFloat() / 500.0F);
            this.zd = pZSpeed;
        }

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
                this.xd = this.xd + (this.random.nextFloat() / 5000.0F * (this.random.nextBoolean() ? 1 : -1));
                this.zd = this.zd + (this.random.nextFloat() / 5000.0F * (this.random.nextBoolean() ? 1 : -1));
                this.yd = this.yd - this.gravity;
                this.move(this.xd, this.yd, this.zd);
                if (this.age >= this.lifetime - 60 && this.alpha > 0.01F) {
                    this.alpha -= 0.015F;
                }
            } else {
                this.remove();
            }
            if (Config.wavyParticles && this.age >= 20) {
                Vector3f vec = WeatherUtils.getEffectiveWindSpeedAt(level, x, y, z);
                if (vec != null) {
                    float wind = vec.length();
                    float j = Math.max(0.25F, wind * 0.1F);
                    this.alpha = Math.max(0.01F, alpha - (float) Math.tanh(wind * 0.5) * 0.015F * j);
                }
            }
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public record SmokeProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            Smoke smoke = new Smoke(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            smoke.pickSprite(this.sprites);
            return smoke;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static class Flash extends TextureSheetParticle {
        protected Flash(ClientLevel pLevel, double pX, double pY, double pZ) {
            super(pLevel, pX, pY, pZ);
            this.quadSize = 2;
            this.gravity = 0;
            this.alpha = 0.0F;
        }

        @Override
        protected void setAlpha(float pAlpha) {
            super.setAlpha(pAlpha);
        }

        private void setQuadSize(float f) {
            this.quadSize += f;
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }
    }

    public static TextureSheetParticle createFlash(SimpleParticleType ignore, ClientLevel level, double x, double y, double z, double ignore1, double ignore2, double ignore3) {
        return new Flash(level, x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Starter extends NoRenderParticle {
        private final ParticleEngine engine;
        private final Flash flash;

        public Starter(ClientLevel pLevel, double pX, double pY, double pZ, ParticleEngine engine, int lifetime, int color) {
            super(pLevel, pX, pY, pZ);
            this.engine = engine;
            this.age = lifetime;
            this.setColor(
                    FastColor.ARGB32.red(color) / 255.0F,
                    FastColor.ARGB32.green(color) / 255.0F,
                    FastColor.ARGB32.blue(color) / 255.0F
            );
            this.flash = (Flash) this.engine.createParticle(ModParticles.SMOKE_BOMB_FLASH.get(), pX, pY, pZ, 0, 0, 0);
            if (this.flash != null) {
                this.flash.setLifetime(600 - lifetime);
                this.flash.setColor(this.rCol, this.gCol, this.bCol);
            }
        }

        @Override
        public void tick() {
            this.flash.setPos(this.x, this.y, this.z);
            if (this.age > 600) {
                this.remove();
            } else {
                if (this.age <= 200) {
                    this.flash.setAlpha(this.age / 200.0F);
                    this.flash.setQuadSize(0.02F);
                }
                int fadeTicks = 600 - this.age;
                if (fadeTicks < 200) {
                    this.flash.setAlpha(fadeTicks / 200.0F);
                    this.flash.setQuadSize(-0.02F);
                } else {
                    double dx = this.random.nextGaussian() * 0.01;
                    double dy = this.random.nextDouble() * 0.05;
                    double dz = this.random.nextGaussian() * 0.01;
                    Particle smoke = this.engine.createParticle(ModParticles.SMOKE_BOMB.get(), this.x, this.y, this.z, dx, dy, dz);
                    if (smoke != null) {
                        smoke.setColor(this.rCol, this.gCol, this.bCol);
                    }
                }
            }
            this.age++;
        }
    }
}
