package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer", remap = false)
public interface ISodiumWorldRendererMixin {
    @Accessor("renderSectionManager")
    RenderSectionManager getManager();
}
