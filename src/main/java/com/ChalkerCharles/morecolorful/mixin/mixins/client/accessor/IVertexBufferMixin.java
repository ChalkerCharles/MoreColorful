package com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.VertexBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VertexBuffer.class)
public interface IVertexBufferMixin {
    @Accessor("vertexBufferId")
    int getVertexBufferId();
}
