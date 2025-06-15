package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionManagerExtension;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

@Pseudo
@Mixin(LevelRenderer.class)
public abstract class SodiumLevelRendererMixin implements ILevelRendererExtension {
    @Shadow(remap = false)
    @Dynamic("Added by Sodium")
    private SodiumWorldRenderer renderer;

    @Override
    public void moreColorful$updateWavySectionsSodium() {
        ((IRenderSectionManagerExtension)
                ((SodiumWorldRendererMixin.Access) this.renderer)
                        .getManager())
                .moreColorful$updateAllSections();
    }
}
