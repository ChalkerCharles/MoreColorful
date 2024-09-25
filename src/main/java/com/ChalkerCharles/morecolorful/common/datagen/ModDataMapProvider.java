package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.BASS_DRUM, new FurnaceFuel(300), false)
                .add(ModItems.TOMTOM_DRUM, new FurnaceFuel(300), false)
                .add(ModItems.XYLOPHONE, new FurnaceFuel(300), false)
                .add(ModItems.GUZHENG, new FurnaceFuel(300), false)
                .add(ModItems.VIOLIN, new FurnaceFuel(200), false)
                .add(ModItems.CELLO, new FurnaceFuel(200), false)
                .add(ModItems.FIDDLE_BOW, new FurnaceFuel(200), false)
                .add(ModItems.DRUMSTICK, new FurnaceFuel(200), false)
                .add(ModItems.GUITAR, new FurnaceFuel(200), false)
                .add(ModItems.BASS, new FurnaceFuel(200), false)
                .add(ModItems.BANJO, new FurnaceFuel(200), false)
                .add(ModItems.FLUTE, new FurnaceFuel(200), false)
                .add(ModItems.DIDGERIDOO, new FurnaceFuel(200), false)
                .add(ModItems.PIPA, new FurnaceFuel(200), false)
                .add(ModItems.ERHU, new FurnaceFuel(200), false);

        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.CRABAPPLE_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.BEGONIAS, new Compostable(0.3F), false)
                .add(ModItems.PINK_DAISY, new Compostable(0.65F), false)
                .add(ModItems.RED_CARNATION, new Compostable(0.65F), false)
                .add(ModItems.PINK_CARNATION, new Compostable(0.65F), false);
    }
}
