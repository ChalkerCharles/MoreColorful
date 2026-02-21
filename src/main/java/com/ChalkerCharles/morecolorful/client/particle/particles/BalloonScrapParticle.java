package com.ChalkerCharles.morecolorful.client.particle.particles;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.BalloonParticleOption;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BalloonScrapParticle extends TextureSheetParticle implements WindSensitive {
    private static final TextureAtlasSprite[][] SPRITES;
    private float rotSpeed;
    private final float spinAcceleration;

    protected BalloonScrapParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, Balloon.Variant variant) {
        super(level, x, y, z, xd, yd, zd);
        this.gravity = 0.9F;
        this.sprite = Util.getRandom(SPRITES[variant.getIndex()], this.random);
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -45.0 : 45.0);
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -10.0 : 10.0);
        this.roll = this.oRoll = (float) Math.toRadians(this.random.nextInt(90));
    }

    @Override
    public void tick() {
        this.oRoll = this.roll;
        super.tick();
        if (!this.removed) {
            this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
            this.roll = this.roll + this.rotSpeed / 20.0F;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static Particle create(BalloonParticleOption type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        return new BalloonScrapParticle(level, x, y, z, xd, yd, zd, type.variant());
    }

    static {
        TextureAtlas atlas = Minecraft.getInstance().particleEngine.textureAtlas;
        Balloon.Variant[] values = Balloon.Variant.values();
        int size = values.length;
        TextureAtlasSprite[][] atlasSprites = new TextureAtlasSprite[size][];
        for (int j = 0; j < size; j++) {
            Balloon.Variant variant = values[j];
            TextureAtlasSprite[] sprites = new TextureAtlasSprite[4];
            for (int i = 0; i < 4; i++) {
                String path = variant.isSpecial()
                        ? "particle/balloon_" + variant.getName() + '_' + i
                        : "colored/balloon/balloon_" + i + '_' + variant.getName();
                sprites[i] = atlas.getSprite(MoreColorful.location(path));
            }
            atlasSprites[j] = sprites;
        }
        SPRITES = atlasSprites;
    }
}
