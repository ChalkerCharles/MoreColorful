package com.ChalkerCharles.morecolorful.mixin.mixins.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin {
    @Shadow
    @Final
    private static Object2IntMap<ChunkStatus> COLORS;

    static {
        if (Config.thermalSystem) {
            COLORS.put(ModChunkStatus.INITIALIZE_THERMAL.get(), 0xf5c469);
            COLORS.put(ModChunkStatus.THERMAL.get(), 0xff9555);
        }
        if (Config.windSystem) {
            COLORS.put(ModChunkStatus.INITIALIZE_VENT.get(), 0x94dbf7);
            COLORS.put(ModChunkStatus.VENTILATION.get(), 0x7492ff);
        }
    }
}
