package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PennantBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.block.utility.UnderwaterTntBlock;
import com.ChalkerCharles.morecolorful.common.entity.BoatTypeExtension;
import com.ChalkerCharles.morecolorful.common.entity.EntityUtils;
import com.ChalkerCharles.morecolorful.common.entity.ModAttributes;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.entity.ai.memory.ModMemoryModuleTypes;
import com.ChalkerCharles.morecolorful.common.entity.ai.sensor.ModSensorTypes;
import com.ChalkerCharles.morecolorful.common.entity.animal.*;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.misc.PaperBoatItem;
import com.ChalkerCharles.morecolorful.common.item.utility.MailItem;
import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import java.util.Map;

public final class ModCommonSetup {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModCommonSetup::setStrippedWoodBlocks);
        event.enqueueWork(ModCommonSetup::addFlowerPotBlocks);
        event.enqueueWork(ModCommonSetup::setFlammableBlocks);
        event.enqueueWork(ModCommonSetup::registerDispenserBehaviors);
        event.enqueueWork(ModCommonSetup::registerCauldronInteractions);
        event.enqueueWork(ModCommonSetup::modifyVillager);
    }

    public static void setStrippedWoodBlocks() {
        AxeItem.STRIPPABLES = ImmutableMap.<Block, Block>builder()
                .putAll(AxeItem.STRIPPABLES)
                .put(ModBlocks.CRABAPPLE_LOG.get(), ModBlocks.STRIPPED_CRABAPPLE_LOG.get())
                .put(ModBlocks.CRABAPPLE_WOOD.get(), ModBlocks.STRIPPED_CRABAPPLE_WOOD.get())
                .put(ModBlocks.EBONY_LOG.get(), ModBlocks.STRIPPED_EBONY_LOG.get())
                .put(ModBlocks.EBONY_WOOD.get(), ModBlocks.STRIPPED_EBONY_WOOD.get())
                .put(ModBlocks.GINKGO_LOG.get(), ModBlocks.STRIPPED_GINKGO_LOG.get())
                .put(ModBlocks.GINKGO_WOOD.get(), ModBlocks.STRIPPED_GINKGO_WOOD.get())
                .put(ModBlocks.MAPLE_LOG.get(), ModBlocks.STRIPPED_MAPLE_LOG.get())
                .put(ModBlocks.MAPLE_WOOD.get(), ModBlocks.STRIPPED_MAPLE_WOOD.get())
                .put(ModBlocks.FROST_LOG.get(), ModBlocks.STRIPPED_FROST_LOG.get())
                .put(ModBlocks.FROST_WOOD.get(), ModBlocks.STRIPPED_FROST_WOOD.get())
                .put(ModBlocks.DAWN_REDWOOD_LOG.get(), ModBlocks.STRIPPED_DAWN_REDWOOD_LOG.get())
                .put(ModBlocks.DAWN_REDWOOD_WOOD.get(), ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get())
                .put(ModBlocks.JACARANDA_LOG.get(), ModBlocks.STRIPPED_JACARANDA_LOG.get())
                .put(ModBlocks.JACARANDA_WOOD.get(), ModBlocks.STRIPPED_JACARANDA_WOOD.get())
                .put(ModBlocks.WILLOW_LOG.get(), ModBlocks.STRIPPED_WILLOW_LOG.get())
                .put(ModBlocks.WILLOW_WOOD.get(), ModBlocks.STRIPPED_WILLOW_WOOD.get())
                .build();
    }

    public static void addFlowerPotBlocks() {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(ModBlocks.CRABAPPLE_SAPLING.getId(), ModBlocks.POTTED_CRABAPPLE_SAPLING);
        pot.addPlant(ModBlocks.WHITE_CHERRY_SAPLING.getId(), ModBlocks.POTTED_WHITE_CHERRY_SAPLING);
        pot.addPlant(ModBlocks.ORANGE_BIRCH_SAPLING.getId(), ModBlocks.POTTED_ORANGE_BIRCH_SAPLING);
        pot.addPlant(ModBlocks.YELLOW_BIRCH_SAPLING.getId(), ModBlocks.POTTED_YELLOW_BIRCH_SAPLING);
        pot.addPlant(ModBlocks.GINKGO_SAPLING.getId(), ModBlocks.POTTED_GINKGO_SAPLING);
        pot.addPlant(ModBlocks.MAPLE_SAPLING.getId(), ModBlocks.POTTED_MAPLE_SAPLING);
        pot.addPlant(ModBlocks.FROST_SAPLING.getId(), ModBlocks.POTTED_FROST_SAPLING);
        pot.addPlant(ModBlocks.DAWN_REDWOOD_SAPLING.getId(), ModBlocks.POTTED_DAWN_REDWOOD_SAPLING);
        pot.addPlant(ModBlocks.JACARANDA_SAPLING.getId(), ModBlocks.POTTED_JACARANDA_SAPLING);
        pot.addPlant(ModBlocks.WILLOW_SAPLING.getId(), ModBlocks.POTTED_WILLOW_SAPLING);
        pot.addPlant(ModBlocks.PINK_DAISY.getId(), ModBlocks.POTTED_PINK_DAISY);
        pot.addPlant(ModBlocks.RED_CARNATION.getId(), ModBlocks.POTTED_RED_CARNATION);
        pot.addPlant(ModBlocks.PINK_CARNATION.getId(), ModBlocks.POTTED_PINK_CARNATION);
        pot.addPlant(ModBlocks.WHITE_CARNATION.getId(), ModBlocks.POTTED_WHITE_CARNATION);
        pot.addPlant(ModBlocks.RED_SPIDER_LILY.getId(), ModBlocks.POTTED_RED_SPIDER_LILY);
        pot.addPlant(ModBlocks.YELLOW_CHRYSANTHEMUM.getId(), ModBlocks.POTTED_YELLOW_CHRYSANTHEMUM);
        pot.addPlant(ModBlocks.GREEN_CHRYSANTHEMUM.getId(), ModBlocks.POTTED_GREEN_CHRYSANTHEMUM);
        pot.addPlant(ModBlocks.OPEN_DAYBLOOM.getId(), ModBlocks.POTTED_OPEN_DAYBLOOM);
        pot.addPlant(ModBlocks.CLOSED_DAYBLOOM.getId(), ModBlocks.POTTED_CLOSED_DAYBLOOM);
        pot.addPlant(ModBlocks.EDELWEISS.getId(), ModBlocks.POTTED_EDELWEISS);
        pot.addPlant(ModBlocks.CROCUS.getId(), ModBlocks.POTTED_CROCUS);
        pot.addPlant(ModBlocks.IRIS.getId(), ModBlocks.POTTED_IRIS);
        pot.addPlant(ModBlocks.LAVENDER.getId(), ModBlocks.POTTED_LAVENDER);
        pot.addPlant(ModBlocks.DAFFODIL.getId(), ModBlocks.POTTED_DAFFODIL);
        pot.addPlant(ModBlocks.GERBERA_DAISY.getId(), ModBlocks.POTTED_GERBERA_DAISY);
        pot.addPlant(ModBlocks.RAPESEED_FLOWER.getId(), ModBlocks.POTTED_RAPESEED_FLOWER);
        pot.addPlant(ModBlocks.WINDFLOWER.getId(), ModBlocks.POTTED_WINDFLOWER);
    }

    public static void setFlammableBlocks() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(ModBlocks.CRABAPPLE_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.CRABAPPLE_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_CRABAPPLE_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_CRABAPPLE_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.CRABAPPLE_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.CRABAPPLE_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.CRABAPPLE_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.CRABAPPLE_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.CRABAPPLE_FENCE_GATE.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.EBONY_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.EBONY_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_EBONY_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_EBONY_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.EBONY_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.EBONY_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.EBONY_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.EBONY_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.EBONY_FENCE_GATE.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.GINKGO_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.GINKGO_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_GINKGO_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_GINKGO_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.GINKGO_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.GINKGO_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.GINKGO_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.GINKGO_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.GINKGO_FENCE_GATE.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.MAPLE_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.MAPLE_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_MAPLE_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_MAPLE_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.MAPLE_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.MAPLE_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.MAPLE_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.MAPLE_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.MAPLE_FENCE_GATE.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.FROST_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.FROST_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_FROST_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_FROST_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.FROST_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.FROST_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.FROST_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.FROST_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.FROST_FENCE_GATE.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_DAWN_REDWOOD_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_FENCE_GATE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_ROOTS.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.JACARANDA_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.JACARANDA_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_JACARANDA_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_JACARANDA_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.JACARANDA_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.JACARANDA_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.JACARANDA_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.JACARANDA_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.JACARANDA_FENCE_GATE.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.WILLOW_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.WILLOW_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_WILLOW_LOG.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.STRIPPED_WILLOW_WOOD.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.WILLOW_PLANKS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.WILLOW_STAIRS.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.WILLOW_SLAB.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.WILLOW_FENCE.get(), 5, 20);
        fireblock.setFlammable(ModBlocks.WILLOW_FENCE_GATE.get(), 5, 20);

        fireblock.setFlammable(ModBlocks.CRABAPPLE_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.BEGONIAS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.WHITE_CHERRY_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.WHITE_PETALS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.ORANGE_BIRCH_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.YELLOW_BIRCH_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.YELLOW_BIRCH_LEAF_LITTER.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.GINKGO_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.GINKGO_LEAF_LITTER.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.MAPLE_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.MAPLE_LEAF_LITTER.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.FROST_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.FROSTY_PETALS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_LEAF_LITTER.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.JACARANDA_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.VIOLETS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.BUTTERCUPS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.FORGET_ME_NOTS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.BABY_BLUE_EYES.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.SPEEDWELLS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.WOOD_SORRELS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.WILLOW_LEAVES.get(), 30, 60);
        fireblock.setFlammable(ModBlocks.WILLOW_BRANCHES.get(), 15, 100);

        fireblock.setFlammable(ModBlocks.PINK_DAISY.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.RED_CARNATION.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.PINK_CARNATION.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.WHITE_CARNATION.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.RED_SPIDER_LILY.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.YELLOW_CHRYSANTHEMUM.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.GREEN_CHRYSANTHEMUM.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.OPEN_DAYBLOOM.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.CLOSED_DAYBLOOM.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.EDELWEISS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.CROCUS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.IRIS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.LAVENDER.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.DAFFODIL.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.GERBERA_DAISY.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.RAPESEED_FLOWER.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.WINDFLOWER.get(), 60, 100);

        fireblock.setFlammable(ModBlocks.CATTAIL.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.TALL_RAPESEED_FLOWER.get(), 60, 100);

        fireblock.setFlammable(ModBlocks.SHORT_WATER_GRASS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.TALL_WATER_GRASS.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.REED.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.STRAWBERRY_BUSH.get(), 60, 100);
        fireblock.setFlammable(ModBlocks.BLUEBERRY_BUSH.get(), 60, 100);

        for (Block block : RibbonBlock.ALL_COLORS.get()) {
            fireblock.setFlammable(block, 60, 100);
        }
        fireblock.setFlammable(ModBlocks.PINWHEEL.get(), 30, 60);
        for (Block block : PennantBlock.ALL_BLOCKS.get()) {
            fireblock.setFlammable(block, 30, 60);
        }
        fireblock.setFlammable(ModBlocks.UNDERWATER_TNT.get(), 15, 100);
        for (Block block : PapercuttingBlock.ALL_BLOCKS.get()) {
            fireblock.setFlammable(block, 60, 100);
        }
        fireblock.setFlammable(ModBlocks.CARDBOARD_BLOCK.get(), 5, 20);
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(ModItems.CRABAPPLE_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.CRABAPPLE));
        DispenserBlock.registerBehavior(ModItems.CRABAPPLE_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.CRABAPPLE, true));
        DispenserBlock.registerBehavior(ModItems.EBONY_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.EBONY));
        DispenserBlock.registerBehavior(ModItems.EBONY_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.EBONY, true));
        DispenserBlock.registerBehavior(ModItems.GINKGO_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.GINKGO));
        DispenserBlock.registerBehavior(ModItems.GINKGO_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.GINKGO, true));
        DispenserBlock.registerBehavior(ModItems.MAPLE_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.MAPLE));
        DispenserBlock.registerBehavior(ModItems.MAPLE_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.MAPLE, true));
        DispenserBlock.registerBehavior(ModItems.FROST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.FROST));
        DispenserBlock.registerBehavior(ModItems.FROST_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.FROST, true));
        DispenserBlock.registerBehavior(ModItems.DAWN_REDWOOD_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.DAWN_REDWOOD));
        DispenserBlock.registerBehavior(ModItems.DAWN_REDWOOD_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.DAWN_REDWOOD, true));
        DispenserBlock.registerBehavior(ModItems.JACARANDA_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.JACARANDA));
        DispenserBlock.registerBehavior(ModItems.JACARANDA_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.JACARANDA, true));
        DispenserBlock.registerBehavior(ModItems.WILLOW_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.WILLOW));
        DispenserBlock.registerBehavior(ModItems.WILLOW_CHEST_BOAT, new BoatDispenseItemBehavior(BoatTypeExtension.WILLOW, true));
        DispenserBlock.registerProjectileBehavior(ModItems.PAPER_PLANE);
        DispenserBlock.registerBehavior(ModItems.PAPER_BOAT, PaperBoatItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModItems.UNDERWATER_TNT, UnderwaterTntBlock.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerProjectileBehavior(ModItems.BOMB);
        DispenserBlock.registerBehavior(ModItems.MAIL, MailItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static void registerCauldronInteractions() {
        Map<Item, CauldronInteraction> water = CauldronInteraction.WATER.map();
        water.put(ModItems.UMBRELLA.get(), UmbrellaItem.CAULDRON_INTERACTION);
    }

    public static void modifyVillager() {
        Villager.MEMORY_TYPES = ImmutableList.<MemoryModuleType<?>>builder()
                .addAll(Villager.MEMORY_TYPES)
                .add(ModMemoryModuleTypes.NEAREST_OPEN_SPACE.get())
                .add(ModMemoryModuleTypes.KITE_MEMORY.get())
                .build();
        Villager.SENSOR_TYPES = ImmutableList.<SensorType<? extends Sensor<? super Villager>>>builder()
                .addAll(Villager.SENSOR_TYPES)
                .add(ModSensorTypes.NEAREST_OPEN_SPACE.get())
                .build();
    }

    @SubscribeEvent
    public static void addBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN,
                ModBlocks.CRABAPPLE_SIGN.get(),
                ModBlocks.CRABAPPLE_WALL_SIGN.get(),
                ModBlocks.EBONY_SIGN.get(),
                ModBlocks.EBONY_WALL_SIGN.get(),
                ModBlocks.GINKGO_SIGN.get(),
                ModBlocks.GINKGO_WALL_SIGN.get(),
                ModBlocks.MAPLE_SIGN.get(),
                ModBlocks.MAPLE_WALL_SIGN.get(),
                ModBlocks.FROST_SIGN.get(),
                ModBlocks.FROST_WALL_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_WALL_SIGN.get(),
                ModBlocks.JACARANDA_SIGN.get(),
                ModBlocks.JACARANDA_WALL_SIGN.get(),
                ModBlocks.WILLOW_SIGN.get(),
                ModBlocks.WILLOW_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN,
                ModBlocks.CRABAPPLE_HANGING_SIGN.get(),
                ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(),
                ModBlocks.EBONY_HANGING_SIGN.get(),
                ModBlocks.EBONY_WALL_HANGING_SIGN.get(),
                ModBlocks.GINKGO_HANGING_SIGN.get(),
                ModBlocks.GINKGO_WALL_HANGING_SIGN.get(),
                ModBlocks.MAPLE_HANGING_SIGN.get(),
                ModBlocks.MAPLE_WALL_HANGING_SIGN.get(),
                ModBlocks.FROST_HANGING_SIGN.get(),
                ModBlocks.FROST_WALL_HANGING_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_HANGING_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get(),
                ModBlocks.JACARANDA_HANGING_SIGN.get(),
                ModBlocks.JACARANDA_WALL_HANGING_SIGN.get(),
                ModBlocks.WILLOW_HANGING_SIGN.get(),
                ModBlocks.WILLOW_WALL_HANGING_SIGN.get());
    }

    @SubscribeEvent
    public static void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BUTTERFLY.get(), AbstractMoth.createAttributes().build());
        event.put(ModEntities.MOTH.get(), AbstractMoth.createAttributes().build());
        event.put(ModEntities.CATERPILLAR.get(), Caterpillar.createAttributes().build());
        event.put(ModEntities.DRAGONFLY.get(), Dragonfly.createAttributes().build());
        event.put(ModEntities.BIRD.get(), AbstractBird.createAttributes().build());
        event.put(ModEntities.PIGEON.get(), AbstractBird.createAttributes().build());
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            AttributeSupplier globalMap = DefaultAttributes.getSupplier(type);
            double d = globalMap.getBaseValue(Attributes.KNOCKBACK_RESISTANCE);
            event.add(type, ModAttributes.HORIZONTAL_WINDAGE, 1.0 - d);
            event.add(type, ModAttributes.VERTICAL_WINDAGE, 1.0 - d);
            event.add(type, ModAttributes.WEIGHT);
        }
        EntityUtils.addVanillaWeightAttribute((type, value) -> event.add(type, ModAttributes.WEIGHT, value));
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        RegisterSpawnPlacementsEvent.Operation replace = RegisterSpawnPlacementsEvent.Operation.REPLACE;
        event.register(ModEntities.BUTTERFLY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Butterfly::checkSpawnRules, replace);
        event.register(ModEntities.MOTH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Moth::checkSpawnRules, replace);
        event.register(ModEntities.DRAGONFLY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dragonfly::checkSpawnRules, replace);
        event.register(ModEntities.BIRD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, AbstractBird::checkSpawnRules, replace);
        event.register(ModEntities.PIGEON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, AbstractBird::checkSpawnRules, replace);
    }
}
