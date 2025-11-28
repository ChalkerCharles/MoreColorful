package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.util.client.MultiBlockGroup;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.DebugLevelSource;

public interface ILevelChunkExtension {
    MultiBlockGroup moreColorful$getMultiBlockGroup();

    private static ILevelChunkExtension self(LevelChunk levelChunk) {
        return (ILevelChunkExtension) levelChunk;
    }

    static BlockState getBlockState(LevelChunk levelChunk, int x, int y, int z) {
        if (levelChunk.getLevel().isDebug()) {
            BlockState state = null;
            if (y == 60) {
                state = Blocks.BARRIER.defaultBlockState();
            }
            if (y == 70) {
                state = DebugLevelSource.getBlockStateFor(x, z);
            }
            return state == null ? Blocks.AIR.defaultBlockState() : state;
        } else {
            try {
                LevelChunkSection[] sections = levelChunk.getSections();
                int l = levelChunk.getSectionIndex(y);
                if (l >= 0 && l < sections.length) {
                    LevelChunkSection section = sections[l];
                    if (!section.hasOnlyAir()) {
                        return section.getBlockState(x & 15, y & 15, z & 15);
                    }
                }
                return Blocks.AIR.defaultBlockState();
            } catch (Throwable throwable) {
                CrashReport report = CrashReport.forThrowable(throwable, "Getting block state");
                CrashReportCategory category = report.addCategory("Block being got");
                category.setDetail("Location", () -> CrashReportCategory.formatLocation(levelChunk, x, y, z));
                throw new ReportedException(report);
            }
        }
    }

    static MultiBlockGroup getMultiBlockGroup(LevelChunk levelChunk) {
        return self(levelChunk).moreColorful$getMultiBlockGroup();
    }
}
