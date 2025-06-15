package com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderStateShard.class)
public interface IRenderStateShardMixin {
    @Accessor("setupState")
    @Mutable
    void setSetupState(Runnable runnable);

    @Accessor("clearState")
    @Mutable
    void setClearState(Runnable runnable);
}
