package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import net.minecraft.world.level.chunk.ChunkSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkSource.class)
public abstract class ChunkSourceMixin implements IChunkSourceExtension {
}
