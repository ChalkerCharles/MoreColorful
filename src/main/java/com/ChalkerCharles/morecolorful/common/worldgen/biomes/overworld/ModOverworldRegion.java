package com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(int weight) {
        super(LOCATION, RegionType.OVERWORLD, weight);
    }

    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "overworld");

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        (new ModOverworldBiomeBuilder()).addBiomes(registry, mapper);
    }
}
