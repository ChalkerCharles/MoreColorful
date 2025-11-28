package com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ByteBufferBuilder.Result.class)
public interface IByteBufferBuilderResultMixin {
    @Accessor("capacity")
    int getCapacity();
}
