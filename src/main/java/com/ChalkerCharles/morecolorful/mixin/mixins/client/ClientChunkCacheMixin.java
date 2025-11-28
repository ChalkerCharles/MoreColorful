package com.ChalkerCharles.morecolorful.mixin.mixins.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.thermal.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.LevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.mixins.chunk.ChunkSourceMixin;
import com.ChalkerCharles.morecolorful.util.Self;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientChunkCache.class)
public abstract class ClientChunkCacheMixin extends ChunkSourceMixin implements Self<ClientChunkCache> {
    @Unique
    @Nullable
    private LevelThermalEngine moreColorful$thermalEngine;
    @Unique
    @Nullable
    private LevelVentEngine moreColorful$ventEngine;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(ClientLevel pLevel, int pViewDistance, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            this.moreColorful$thermalEngine = new LevelThermalEngine(moreColorful$self());
        }
        if (Config.WIND_SYSTEM.isTrue()) {
            this.moreColorful$ventEngine = new LevelVentEngine(moreColorful$self(), WeatherUtils.isWindy(pLevel));
        }
    }

    @Override
    public void moreColorful$onThermalUpdate(SectionPos pPos) {
        Minecraft.getInstance().levelRenderer.setSectionDirty(pPos.x(), pPos.y(), pPos.z());
    }

    @Override
    public ILevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine == null
                ? super.moreColorful$getThermalEngine()
                : this.moreColorful$thermalEngine;
    }

    @Override
    public void moreColorful$onVentUpdate(SectionPos pPos) {
        Minecraft.getInstance().levelRenderer.setSectionDirty(pPos.x(), pPos.y(), pPos.z());
    }

    @Override
    public ILevelVentEngine moreColorful$getVentEngine() {
        return this.moreColorful$ventEngine == null
                ? super.moreColorful$getVentEngine()
                : this.moreColorful$ventEngine;
    }
}
