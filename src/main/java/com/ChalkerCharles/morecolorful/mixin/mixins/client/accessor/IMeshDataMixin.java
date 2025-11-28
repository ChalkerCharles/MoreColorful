package com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MeshData.class)
public interface IMeshDataMixin {
    @Accessor("vertexBuffer")
    ByteBufferBuilder.Result getVertexBuffer();
}
