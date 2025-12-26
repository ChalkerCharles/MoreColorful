package com.ChalkerCharles.morecolorful.client.particle.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class WindParticle extends TextureSheetParticle {
    private final double a, c, d;
    private float rot;
    private float length = 0.5F;
    private float oLength = 0.5F;
    private final boolean isVertical;

    protected WindParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.pickSprite(spriteSet);
        this.setParticleSpeed(xSpeed, ySpeed, zSpeed);
        this.isVertical = xSpeed == 0 && zSpeed == 0;
        if (this.isVertical) {
            this.a = 0;
            this.c = 1;
            this.d = -z;
        } else {
            this.a = -zSpeed;
            this.c = xSpeed;
            this.d = zSpeed * x - xSpeed * z;
            this.rot = (float) -Mth.atan2(zSpeed, xSpeed);
        }
        this.setAlpha(0.4F);
        this.quadSize = 0.5F;
    }

    private void setLifetime(int lower, int upper) {
        this.lifetime = this.random.nextInt(lower, upper);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.oLength = this.length;
        if (this.removed) return;
        if (this.age++ < this.lifetime) {
            this.move(this.xd, this.yd, this.zd);
            if (this.age < 4) {
                this.length += 0.5F;
            }
            if (this.age >= this.lifetime - 4 && this.alpha > 0.01F) {
                this.alpha -= 0.1F;
            }
        } else {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        Quaternionf quaternion = new Quaternionf();
        if (this.isVertical) {
            quaternion.rotateZ(Mth.HALF_PI);
        } else {
            quaternion.rotateY(this.rot);
        }
        this.renderRotatedQuad(buffer, renderInfo, quaternion, partialTicks);
    }

    @Override
    protected void renderRotatedQuad(VertexConsumer buffer, Camera camera, Quaternionf quaternion, float partialTicks) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
        this.renderRotatedQuad(buffer, quaternion, f, f1, f2, partialTicks, camera);
    }

    private void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks, Camera camera) {
        float l = Mth.lerp(partialTicks, this.oLength, this.length);
        float w = this.quadSize;
        float f1 = this.getU0();
        float f2 = this.getU1();
        float f3 = this.getV0();
        float f4 = this.getV1();
        int i = this.getLightColor(partialTicks);
        if (this.isBackFace(camera)) {
            this.renderVertex(buffer, quaternion, x, y, z, l, -w, f2, f4, i);
            this.renderVertex(buffer, quaternion, x, y, z, -l, -w, f1, f4, i);
            this.renderVertex(buffer, quaternion, x, y, z, -l, w, f1, f3, i);
            this.renderVertex(buffer, quaternion, x, y, z, l, w, f2, f3, i);
        } else {
            this.renderVertex(buffer, quaternion, x, y, z, l, -w, f2, f4, i);
            this.renderVertex(buffer, quaternion, x, y, z, l, w, f2, f3, i);
            this.renderVertex(buffer, quaternion, x, y, z, -l, w, f1, f3, i);
            this.renderVertex(buffer, quaternion, x, y, z, -l, -w, f1, f4, i);
        }
    }

    private boolean isBackFace(Camera camera) {
       Vec3 pos = camera.getPosition();
       double delta = this.a * pos.x + this.c * pos.z + this.d;
        return delta < 0;
    }

    private void renderVertex(
            VertexConsumer buffer,
            Quaternionf quaternion,
            float x,
            float y,
            float z,
            float xOffset,
            float yOffset,
            float u,
            float v,
            int packedLight) {
        Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0F).rotate(quaternion).add(x, y, z);
        buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z())
                .setUv(u, v)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(packedLight);
    }

    @Override
    public void move(double pX, double pY, double pZ) {
        double d0 = pX;
        double d1 = pY;
        double d2 = pZ;
        if ((pX != 0.0 || pY != 0.0 || pZ != 0.0) && pX * pX + pY * pY + pZ * pZ < 10000.0) {
            Vec3 vec3 = Entity.collideBoundingBox(null, new Vec3(pX, pY, pZ), this.getBoundingBox(), this.level, List.of());
            pX = vec3.x;
            pY = vec3.y;
            pZ = vec3.z;
        }
        if (pX != 0.0 || pY != 0.0 || pZ != 0.0) {
            this.setBoundingBox(this.getBoundingBox().move(pX, pY, pZ));
            this.setLocationFromBoundingbox();
        }
        if (d0 != pX || d1 != pY || d2 != pZ) {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class GlobalProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public GlobalProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            WindParticle particle = new WindParticle(level, x, y, z, dx, dy, dz, this.sprites);
            particle.setLifetime(24, 40);
            return particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FanProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FanProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            WindParticle particle = new WindParticle(level, x, y, z, dx, dy, dz, this.sprites);
            particle.setLifetime(12, 20);
            return particle;
        }
    }
}
