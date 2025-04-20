package com.ChalkerCharles.morecolorful.mixin.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin {
    @Shadow
    @Final
    private static Object2IntMap<ChunkStatus> COLORS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addColors(CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        COLORS.put(ModChunkStatus.INITIALIZE_THERMAL.get(), 13421772);
        COLORS.put(ModChunkStatus.THERMAL.get(), 16769184);
    }
}
