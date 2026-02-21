package com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface IEntityRendererMixin {
    @Invoker("getBlockLightLevel")
    int invokeGetBlockLightLevel(Entity pEntity, BlockPos pPos);
}
