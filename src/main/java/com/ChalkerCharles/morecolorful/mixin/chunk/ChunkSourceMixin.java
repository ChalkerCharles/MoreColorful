package com.ChalkerCharles.morecolorful.mixin.chunk;

import com.ChalkerCharles.morecolorful.util.mixin.IChunkSourceExtension;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChunkSource.class)
public abstract class ChunkSourceMixin implements IChunkSourceExtension {
    @Shadow
    @Nullable
    public abstract ChunkAccess getChunk(int pX, int pZ, ChunkStatus pChunkStatus, boolean pRequireChunk);

    @Unique
    @Override
    @Nullable
    public ChunkAccess moreColorful$getThermalChunk(int pChunkX, int pChunkZ) {
        return this.getChunk(pChunkX, pChunkZ, ChunkStatus.EMPTY, false);
    }
}
