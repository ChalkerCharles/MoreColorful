package com.ChalkerCharles.morecolorful.mixin.accessor;

import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkType;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;
import java.util.EnumSet;

@Mixin(ChunkStatus.class)
public interface IChunkStatusMixin {
    @Accessor("parent")
    @Mutable
    void setParent(ChunkStatus status);

    @Accessor("index")
    @Mutable
    void setIndex(int index);

    @Invoker("<init>")
    static ChunkStatus create(@Nullable ChunkStatus pParent, EnumSet<Heightmap.Types> pHeightmapsAfter, ChunkType pChunkType) {
        throw new UnsupportedOperationException();
    }
}
