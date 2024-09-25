package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static ResourceKey<PlacedFeature> registerKey(String pName) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, pName));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        ModTreePlacements.bootstrap(context);
        ModVegetationPlacements.bootstrap(context);
    }
}
