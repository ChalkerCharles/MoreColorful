package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.util.EnumExtensions;
import com.google.common.collect.Maps;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

public final class ModCommonSetup {
    @SubscribeEvent
    public static void setStrippedWoodBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
            AxeItem.STRIPPABLES.put(ModBlocks.CRABAPPLE_LOG.get(), ModBlocks.STRIPPED_CRABAPPLE_LOG.get());
            AxeItem.STRIPPABLES.put(ModBlocks.CRABAPPLE_WOOD.get(), ModBlocks.STRIPPED_CRABAPPLE_WOOD.get());
            AxeItem.STRIPPABLES.put(ModBlocks.EBONY_LOG.get(), ModBlocks.STRIPPED_EBONY_LOG.get());
            AxeItem.STRIPPABLES.put(ModBlocks.EBONY_WOOD.get(), ModBlocks.STRIPPED_EBONY_WOOD.get());
        });
    }

    @SubscribeEvent
    public static void addFlowerPotBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
            pot.addPlant(ModBlocks.CRABAPPLE_SAPLING.getId(), ModBlocks.POTTED_CRABAPPLE_SAPLING);
            pot.addPlant(ModBlocks.PINK_DAISY.getId(), ModBlocks.POTTED_PINK_DAISY);
            pot.addPlant(ModBlocks.RED_CARNATION.getId(), ModBlocks.POTTED_RED_CARNATION);
            pot.addPlant(ModBlocks.PINK_CARNATION.getId(), ModBlocks.POTTED_PINK_CARNATION);
        });
    }

    @SubscribeEvent
    public static void setFlammableBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
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

            fireblock.setFlammable(ModBlocks.CRABAPPLE_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BEGONIAS.get(), 60, 100);
        });
    }

    @SubscribeEvent
    public static void addBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN,
                ModBlocks.CRABAPPLE_SIGN.get(),
                ModBlocks.CRABAPPLE_WALL_SIGN.get(),
                ModBlocks.EBONY_SIGN.get(),
                ModBlocks.EBONY_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN,
                ModBlocks.CRABAPPLE_HANGING_SIGN.get(),
                ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(),
                ModBlocks.EBONY_HANGING_SIGN.get(),
                ModBlocks.EBONY_WALL_HANGING_SIGN.get());
    }
}
