package com.ChalkerCharles.morecolorful.common.worldgen.features;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ModConfiguredFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String pName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, pName));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        ModTreeFeatures.bootstrap(context);
        ModVegetationFeatures.bootstrap(context);
    }
}
