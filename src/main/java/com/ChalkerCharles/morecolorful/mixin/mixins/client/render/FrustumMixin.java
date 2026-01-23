package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.mixin.extensions.IFrustumExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Frustum.class)
public abstract class FrustumMixin implements IFrustumExtension {
    @Shadow
    private double camX;
    @Shadow
    private double camY;
    @Shadow
    private double camZ;
    @Unique
    private final int moreColorful$distance = Minecraft.getInstance().options.getEffectiveRenderDistance() << 4;

    @Override
    public boolean moreColorful$isInRange(double pMinX, double pMinY, double pMinZ) {
        double ox = pMinX - this.camX;
        double oy = pMinY - this.camY;
        double oz = pMinZ - this.camZ;
        double dx = moreColorful$nearestToZero(ox - 1, ox + 17);
        double dy = moreColorful$nearestToZero(oy - 1, oy + 17);
        double dz = moreColorful$nearestToZero(oz - 1, oz + 17);
        double d = this.moreColorful$distance;
        return dx * dx + dz * dz < d * d && Math.abs(dy) < d;
    }

    @Unique
    private static double moreColorful$nearestToZero(double min, double max) {
        double clamped = 0;
        if (min > 0) {
            clamped = min;
        }
        if (max < 0) {
            clamped = max;
        }
        return clamped;
    }
}
