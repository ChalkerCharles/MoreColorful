package com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor;

import net.neoforged.neoforge.client.model.lighting.QuadLighter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(QuadLighter.class)
public interface IQuadLighterMixin {
    @Accessor("brightness")
    float[] brightness();

    @Accessor("lightmap")
    int[] lightmap();

    @Invoker("getColorFast")
    float[] invokeGetColorFast(int tintIndex);
}
