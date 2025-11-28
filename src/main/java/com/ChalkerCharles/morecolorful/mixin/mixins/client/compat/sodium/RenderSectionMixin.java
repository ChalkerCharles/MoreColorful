package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.client.compat.SodiumWavyTask;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionExtension;
import com.ChalkerCharles.morecolorful.util.Self;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegion;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.RenderSection", remap = false)
public abstract class RenderSectionMixin implements IRenderSectionExtension, Self<RenderSection> {
    @Unique
    private SodiumWavyTask moreColorful$wavyTask;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(RenderRegion region, int chunkX, int chunkY, int chunkZ, CallbackInfo ci) {
        if (!RenderUtils.isClientWindOn) return;
        this.moreColorful$wavyTask = new SodiumWavyTask(moreColorful$self());
    }

    @Inject(method = "delete", at = @At("TAIL"))
    private void delete(CallbackInfo ci) {
        if (this.moreColorful$wavyTask != null) {
            this.moreColorful$wavyTask.close();
        }
    }

    @Override
    public SodiumWavyTask moreColorful$getWavyTask() {
        return this.moreColorful$wavyTask;
    }
}
