package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PinwheelBlock;
import com.ChalkerCharles.morecolorful.common.command.ModWeatherCommand;
import com.ChalkerCharles.morecolorful.common.entity.ai.memory.KiteMemory;
import com.ChalkerCharles.morecolorful.common.entity.misc.PrimedUnderwaterTnt;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.BurstWindZone;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IExplosionExtension;
import com.ChalkerCharles.morecolorful.network.packets.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public final class ModCommonEvents {
    @SubscribeEvent
    public static void postLevelTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();
        if (level.tickRateManager().runsNormally()) {
            LevelSavedData.tick(level);
        }
    }

    @SubscribeEvent
    public static void onChunkSent(ChunkWatchEvent.Sent event) {
        ServerLevel level = event.getLevel();
        if (Config.thermalSystem) {
            ILevelThermalEngine thermalEngine = LevelSavedData.getThermalEngine(level);
            PacketDistributor.sendToPlayer(event.getPlayer(), new ThermalUpdatePacket(
                    event.getPos(), thermalEngine, null, true
            ));
        }
        if (Config.windSystem) {
            ILevelVentEngine ventEngine = LevelSavedData.getVentEngine(level);
            PacketDistributor.sendToPlayer(event.getPlayer(), new VentUpdatePacket(
                    event.getPos(), ventEngine, null, true
            ));
        }
    }

    @SubscribeEvent
    public static void onChunkUnWatch(ChunkWatchEvent.UnWatch event) {
        if (Config.thermalSystem) {
            PacketDistributor.sendToPlayer(event.getPlayer(), new ThermalRemovalPacket(event.getPos()));
        }
        if (Config.windSystem) {
            PacketDistributor.sendToPlayer(event.getPlayer(), new VentRemovalPacket(event.getPos()));
        }
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (!Config.windSystem) return;
        ChunkAccess chunk = event.getChunk();
        Level level = chunk.getLevel();
        if (level == null) return;
        LevelSavedData.onChunkUnload(level, chunk.getPos());
    }

    @SubscribeEvent
    public static void preEntityTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (Config.windSystem) {
            PinwheelBlock.applyWind(entity);
            if (Config.windPhysics && entity.isControlledByLocalInstance()) {
                IEntityExtension.applyWind(entity);
            }
        }
        UmbrellaItem.addCanopy(entity);
    }

    @SubscribeEvent
    public static void postEntityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (!entity.isPassenger() && !entity.isInFluidType()) {
            UmbrellaItem.applyAirResistance(entity);
        }
        if (entity instanceof Mob mob) {
            KiteMemory.check(mob);
        }
    }

    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();
        Level level = event.getLevel().getLevel();
        RandomSource random = level.random;
        if (level.isRaining()
                && mob.getType().is(ModTags.EntityTypes.CAN_SPAWN_WITH_UMBRELLA)
                && random.nextFloat() < 0.1F
                && mob.getOffhandItem().isEmpty()) {
            ItemStack item;
            float chance = random.nextFloat();
            if (mob instanceof Bogged && chance < 0.5F) {
                item = ModItems.DRIPLEAF_UMBRELLA.toStack();
            } else {
                item = ModItems.UMBRELLA.toStack();
                if (chance < 0.75F) {
                    item.set(ModDataComponents.UMBRELLA_COLOR, UmbrellaItem.chooseColor(chance, random));
                }
            }
            UmbrellaItem.open(item);
            mob.setItemSlot(EquipmentSlot.OFFHAND, item);
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        ModWeatherCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onExplosionStart(ExplosionEvent.Start event) {
        Level level = event.getLevel();
        if (level.isClientSide) return;
        Explosion explosion = event.getExplosion();
        if (Config.windSystem) {
            Vec3 origin = explosion.center();
            float radius = explosion.radius() * 1.5F;
            BurstWindZone windZone = new BurstWindZone(origin, radius, 16, 20);
            LevelSavedData.addWindZone(level, windZone);
            PacketDistributor.sendToPlayersInDimension((ServerLevel) level, new WindZonePacket(windZone));
        }
        Entity entity = explosion.getDirectSourceEntity();
        if (entity instanceof PrimedUnderwaterTnt) {
            IExplosionExtension.setIgnoreFluid(explosion);
        }
    }

    @SubscribeEvent
    public static void onItemStackedOn(ItemStackedOnOtherEvent event) {
        ItemStack carried = event.getCarriedItem();
        ItemStack stackedOn = event.getStackedOnItem();
        if (carried.is(ModItems.PINWHEEL) && stackedOn.is(ModItems.PINWHEEL)) {
           PinwheelBlock.merge(carried, stackedOn);
        }
    }
}
