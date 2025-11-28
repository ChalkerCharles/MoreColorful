package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.mixin.extensions.IFrustumExtension;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

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

    @WrapOperation(method = "isVisible", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/culling/Frustum;cubeInFrustum(DDDDDD)Z"))
    private boolean isVisible(Frustum instance, double pMinX, double pMinY, double pMinZ, double pMaxX, double pMaxY, double pMaxZ, Operation<Boolean> original) {
        if (RenderUtils.SODIUM_ON) {
            return original.call(instance, pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ);
        }
        return moreColorful$isInRange(pMinX, pMinY, pMinZ) && original.call(instance, pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ);
    }

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
