package com.ChalkerCharles.morecolorful.client.renderer.wavy;

import com.ChalkerCharles.morecolorful.mixin.extensions.IFrustumExtension;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class WindFrustum extends Frustum implements IFrustumExtension {
    public WindFrustum(Matrix4f pFrustum, Matrix4f pProjection) {
        super(pFrustum, pProjection);
    }

    @Override
    public boolean isVisible(AABB pAabb) {
        if (pAabb.isInfinite()) return true;
        return moreColorful$isInRange(pAabb.minX, pAabb.minY, pAabb.minZ)
                && this.cubeInFrustum(pAabb.minX, pAabb.minY, pAabb.minZ, pAabb.maxX, pAabb.maxY, pAabb.maxZ);
    }
}
