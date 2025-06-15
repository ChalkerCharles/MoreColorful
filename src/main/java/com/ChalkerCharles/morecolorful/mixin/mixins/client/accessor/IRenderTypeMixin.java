package com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderType.class)
public interface IRenderTypeMixin {
    @Accessor("format")
    @Mutable
    void setFormat(VertexFormat format);

    @Mixin(targets = "net.minecraft.client.renderer.RenderType$CompositeRenderType")
    interface ICompositeRenderTypeMixin {
        @Accessor("state")
        @Mutable
        void setState(RenderType.CompositeState state);
    }

    @Mixin(RenderType.CompositeState.class)
    interface ICompositeStateMixin {
        @Accessor("states")
        ImmutableList<RenderStateShard> getStates();
    }
}
