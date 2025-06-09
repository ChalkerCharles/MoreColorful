package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.shader.ModRenderTypes;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(RenderType.class)
public abstract class RenderTypeMixin {
    @ModifyReturnValue(method = "chunkBufferLayers", at = @At("TAIL"))
    private static List<RenderType> chunkBufferLayers(List<RenderType> original) {
        return new ImmutableList.Builder<RenderType>()
                .addAll(original)
                .add(ModRenderTypes.WAVY_CUTOUT_MIPPED)
                .add(ModRenderTypes.WAVY_CUTOUT)
                .add(ModRenderTypes.WAVY_TRANSLUCENT)
                .build();
    }
}
