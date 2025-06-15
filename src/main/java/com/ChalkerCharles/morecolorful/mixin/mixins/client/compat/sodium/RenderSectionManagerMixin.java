package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import com.ChalkerCharles.morecolorful.client.compat.WavyChunkRenderer;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionManagerExtension;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import net.caffeinemc.mods.sodium.client.gl.device.CommandList;
import net.caffeinemc.mods.sodium.client.gl.device.RenderDevice;
import net.caffeinemc.mods.sodium.client.render.chunk.ChunkRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.executor.ChunkBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager", remap = false)
public abstract class RenderSectionManagerMixin implements IRenderSectionManagerExtension {
    @Shadow
    @Final
    @Mutable
    private ChunkRenderer chunkRenderer;

    @Shadow
    @Final
    @Mutable
    private ChunkBuilder builder;

    @Shadow
    @Final
    private Long2ReferenceMap<RenderSection> sectionByPosition;

    @Shadow
    public abstract void scheduleRebuild(int x, int y, int z, boolean important);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(ClientLevel level, int renderDistance, CommandList commandList, CallbackInfo ci) {
        this.chunkRenderer = new WavyChunkRenderer(RenderDevice.INSTANCE, SodiumCompat.WAVY);
        this.builder = new ChunkBuilder(level, SodiumCompat.WAVY);
    }

    @Override
    public void moreColorful$updateAllSections() {
        SectionPos sectionPos;
        for (RenderSection section : this.sectionByPosition.values()) {
            this.scheduleRebuild(section.getChunkX(), section.getChunkY(), section.getChunkZ(), false);
            sectionPos = section.getPosition();
            RenderUtils.VERTICES.remove(sectionPos);
            WeatherUtils.WINDY_BLOCKS.remove(sectionPos);
        }
    }
}
