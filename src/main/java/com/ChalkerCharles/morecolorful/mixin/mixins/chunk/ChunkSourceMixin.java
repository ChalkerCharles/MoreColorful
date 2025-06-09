package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.common.level.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkSource.class)
public abstract class ChunkSourceMixin implements IChunkSourceExtension {
    @Shadow
    @Nullable
    public abstract ChunkAccess getChunk(int pX, int pZ, ChunkStatus pChunkStatus, boolean pRequireChunk);

    @Override
    @Nullable
    public ChunkAccess moreColorful$getThermalChunk(int pChunkX, int pChunkZ) {
        return this.getChunk(pChunkX, pChunkZ, ChunkStatus.EMPTY, false);
    }

    @Override
    public void moreColorful$onThermalUpdate(SectionPos pPos) {
    }

    @Override
    public ILevelThermalEngine moreColorful$getThermalEngine() {
        return ILevelThermalEngine.DummyLevelThermalEngine.INSTANCE;
    }
}
