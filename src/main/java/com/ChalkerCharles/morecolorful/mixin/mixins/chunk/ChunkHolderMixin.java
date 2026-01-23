package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.thermal.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.level.wind.LevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkHolderExtension;
import com.ChalkerCharles.morecolorful.network.packets.ThermalUpdatePacket;
import com.ChalkerCharles.morecolorful.network.packets.VentUpdatePacket;
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
    @Unique
    private final BitSet moreColorful$changedVentSectionFilter = new BitSet();
    @Unique
    private LevelVentEngine moreColorful$ventEngine;

    private ChunkHolderMixin(ChunkPos pPos) {
        super(pPos);
    }

    @ModifyExpressionValue(method = "broadcastChanges", at = @At(value = "FIELD", target = "Lnet/minecraft/server/level/ChunkHolder;hasChangedSections:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private boolean broadcastChanges$modifyCondition(boolean original) {
        if (Config.thermalSystem) {
            original = original || !this.moreColorful$changedThermalSectionFilter.isEmpty();
        }
        if (Config.windSystem) {
            original = original || !this.moreColorful$changedVentSectionFilter.isEmpty();
        }
        return original;
    }

    @Inject(method = "broadcastChanges", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;getLevel()Lnet/minecraft/world/level/Level;", shift = At.Shift.AFTER))
    private void broadcastChanges(LevelChunk pChunk, CallbackInfo ci) {
        if (Config.thermalSystem) {
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
        if (Config.windSystem) {
            if (!this.moreColorful$changedVentSectionFilter.isEmpty()) {
                List<ServerPlayer> list = this.playerProvider.getPlayers(this.pos, true);
                if (!list.isEmpty()) {
                    VentUpdatePacket packet = new VentUpdatePacket(
                            pChunk.getPos(), this.moreColorful$ventEngine, this.moreColorful$changedVentSectionFilter, false
                    );
                    list.forEach(p -> PacketDistributor.sendToPlayer(p, packet));
                }

                this.moreColorful$changedVentSectionFilter.clear();
            }
        }
    }

    @Override
    public void moreColorful$setThermalEngine(LevelThermalEngine engine) {
        this.moreColorful$thermalEngine = engine;
    }

    @Override
    public void moreColorful$sectionThermalChanged(int pSectionY) {
        if (!Config.thermalSystem) return;
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

    @Override
    public void moreColorful$setVentEngine(LevelVentEngine engine) {
        this.moreColorful$ventEngine = engine;
    }

    @Override
    public void moreColorful$sectionVentChanged(int pSectionY) {
        if (!Config.windSystem) return;
        ChunkAccess chunkaccess = this.getChunkIfPresent(ModChunkStatus.INITIALIZE_VENT.get());
        if (chunkaccess != null) {
            chunkaccess.setUnsaved(true);
            LevelChunk levelchunk = this.getTickingChunk();
            if (levelchunk != null) {
                int i = this.moreColorful$ventEngine.getMinVentSection();
                int j = this.moreColorful$ventEngine.getMaxVentSection();
                if (pSectionY >= i && pSectionY <= j) {
                    int k = pSectionY - i;
                    this.moreColorful$changedVentSectionFilter.set(k);
                }
            }
        }
    }
}
