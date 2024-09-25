package com.ChalkerCharles.morecolorful.common.worldgen.biomes;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public abstract class ModBiomes {
    public static final ResourceKey<Biome> CRABAPPLE_GARDEN = register("crabapple_garden");

    private static ResourceKey<Biome> register(String pKey) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, pKey));
    }
}
