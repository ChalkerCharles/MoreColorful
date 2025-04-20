package com.ChalkerCharles.morecolorful.mixin.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.network.packets.ThermalUpdatePacket;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkHolderExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.PacketDistributor;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.List;

@Mixin(ChunkHolder.class)
public abstract class ChunkHolderMixin extends GenerationChunkHolder implements IChunkHolderExtension {
    @Shadow
    @Nullable
    public abstract LevelChunk getTickingChunk();
    @Shadow
    @Final
    private ChunkHolder.PlayerProvider playerProvider;

    @Unique
    private final BitSet moreColorful$changedThermalSectionFilter = new BitSet();
    @Unique
    private LevelThermalEngine moreColorful$thermalEngine;

    private ChunkHolderMixin(ChunkPos pPos) {
        super(pPos);
    }

    @ModifyExpressionValue(method = "broadcastChanges", at = @At(value = "FIELD", target = "Lnet/minecraft/server/level/ChunkHolder;hasChangedSections:Z", opcode = Opcodes.GETFIELD))
    private boolean broadcastChanges$modifyCondition(boolean original) {
        if (Config.THERMAL_SYSTEM.isFalse()) return original;
        return original || !this.moreColorful$changedThermalSectionFilter.isEmpty();
    }

    @Inject(method = "broadcastChanges", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;getLevel()Lnet/minecraft/world/level/Level;", shift = At.Shift.AFTER))
    private void broadcastChanges(LevelChunk pChunk, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        if (!this.moreColorful$changedThermalSectionFilter.isEmpty()) {
            List<ServerPlayer> list = this.playerProvider.getPlayers(this.pos, true);
            if (!list.isEmpty()) {
                ThermalUpdatePacket packet = new ThermalUpdatePacket(
                        pChunk.getPos(), this.moreColorful$thermalEngine, this.moreColorful$changedThermalSectionFilter, false
                );
                list.forEach(p -> PacketDistributor.sendToPlayer(p, packet));
            }

            this.moreColorful$changedThermalSectionFilter.clear();
        }
    }

    @Unique
    @Override
    public void moreColorful$setThermalEngine(LevelThermalEngine engine) {
        this.moreColorful$thermalEngine = engine;
    }

    @Unique
    @Override
    public void moreColorful$sectionThermalChanged(int pSectionY) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        ChunkAccess chunkaccess = this.getChunkIfPresent(ModChunkStatus.INITIALIZE_THERMAL.get());
        if (chunkaccess != null) {
            chunkaccess.setUnsaved(true);
            LevelChunk levelchunk = this.getTickingChunk();
            if (levelchunk != null) {
                int i = this.moreColorful$thermalEngine.getMinThermalSection();
                int j = this.moreColorful$thermalEngine.getMaxThermalSection();
                if (pSectionY >= i && pSectionY <= j) {
                    int k = pSectionY - i;
                    this.moreColorful$changedThermalSectionFilter.set(k);
                }
            }
        }
    }
}
