package com.ChalkerCharles.morecolorful.client.particle.particles;

import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.util.FastColor.ARGB32;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SparklerParticles {
    @OnlyIn(Dist.CLIENT)
    private static class Sparkle extends TextureSheetParticle implements WindSensitive {
        private final ParticleEngine engine = Minecraft.getInstance().particleEngine;
        private final SpriteSet sprites;
        private final boolean trail;
        private final int color;

        protected Sparkle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet sprites, int color) {
            super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            this.friction = 0.91F;
            this.gravity = 0.1F;
            this.sprites = sprites;
            this.quadSize *= 0.75F;
            this.lifetime = 48 + this.random.nextInt(12);
            this.setSpriteFromAge(sprites);
            this.alpha = 0.99F;
            this.trail = true;
            this.color = color;
        }

        private Sparkle(Sparkle sparkle) {
            super(sparkle.level, sparkle.x, sparkle.y, sparkle.z);
            this.setParticleSpeed(0, 0, 0);
            this.friction = sparkle.friction;
            this.gravity = 0.05F;
            this.sprites = sparkle.sprites;
            this.quadSize = sparkle.quadSize * 0.75F;
            this.lifetime = sparkle.lifetime;
            this.age = (int) (this.lifetime * 0.75F);
            this.setSpriteFromAge(sprites);
            this.alpha = 0.9F;
            this.color = -1;
            this.trail = false;
            this.setColor(sparkle.rCol, sparkle.gCol, sparkle.bCol);
        }

        @Override
        public void tick() {
            super.tick();
            this.setSpriteFromAge(this.sprites);
            if (this.trail && this.age <= 24) {
                float f = (float)this.age / 24;
                int i = ARGB32.lerp(f, -1, this.color);
                this.setColor(ARGB32.red(i) / 255.0F, ARGB32.green(i) / 255.0F, ARGB32.blue(i) / 255.0F);
            }
            if (this.trail && this.age < this.lifetime / 2 && (this.age + this.lifetime) % 2 == 0) {
                Sparkle sparkle = new Sparkle(this);
                this.engine.add(sparkle);
            }
        }

        @Override
        public boolean moreColorful$isWindSensitive() {
            return this.trail;
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }

        @Override
        public int getLightColor(float pPartialTick) {
            return 15728880;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public record SparkleProvider(SpriteSet sprites) implements ParticleProvider<ColorParticleOption> {
        @Override
        public Particle createParticle(ColorParticleOption pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            int color = ARGB32.colorFromFloat(1, pType.getRed(), pType.getGreen(), pType.getBlue());
            return new Sparkle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites, color);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static class Glitter extends TextureSheetParticle implements WindSensitive {
        private final int color;

        protected Glitter(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, int color) {
            super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            this.quadSize = this.random.nextFloat() * 0.05F + 0.01F;
            this.gravity = 0.9F;
            this.lifetime = (int)(Math.random() * 5.0) + 25;
            this.color = color;
        }

        @Override
        public void tick() {
            super.tick();
            if (!this.removed) {
                float f = (float)this.age / this.lifetime;
                int i = ARGB32.lerp(f, -1, this.color);
                this.setColor(ARGB32.red(i) / 255.0F, ARGB32.green(i) / 255.0F, ARGB32.blue(i) / 255.0F);
            }
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
        }

        @Override
        public int getLightColor(float pPartialTick) {
            return 15728880;
        }
    }

    public static TextureSheetParticle createGlitter(ColorParticleOption option, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        int color = ARGB32.colorFromFloat(1, option.getRed(), option.getGreen(), option.getBlue());
        return new Glitter(level, x, y, z, xd, yd, zd, color);
    }
}
