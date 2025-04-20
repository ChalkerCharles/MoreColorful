package com.ChalkerCharles.morecolorful.mixin.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkSourceExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientChunkCache.class)
public abstract class ClientChunkCacheMixin implements IChunkSourceExtension {
    @Unique
    private LevelThermalEngine moreColorful$thermalEngine;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(ClientLevel pLevel, int pViewDistance, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            this.moreColorful$thermalEngine = new LevelThermalEngine((ClientChunkCache) (Object) this);
        }
    }

    @Unique
    @Override
    public void moreColorful$onThermalUpdate(SectionPos pPos) {
        Minecraft.getInstance().levelRenderer.setSectionDirty(pPos.x(), pPos.y(), pPos.z());
    }

    @Unique
    @Override
    public LevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine;
    }
}
