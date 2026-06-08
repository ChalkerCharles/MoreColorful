package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public interface ILivingEntityRendererExtension<T extends LivingEntity> {
    default VertexConsumer morecolorful$wrapVertexConsumer(T entity, MultiBufferSource buffer, RenderType renderType) {
        return null;
    }

    default boolean morecolorful$hasDynamicTransparency(T entity) {
        return false;
    }

    default int morecolorful$getOpacity(T entity, float partialTicks) {
        return 0;
    }
}
