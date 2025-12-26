package com.ChalkerCharles.morecolorful.common.worldgen.features;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.features.misc.ModLakeFeature;
import com.ChalkerCharles.morecolorful.common.worldgen.features.misc.ReedFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModFeatures {
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, MoreColorful.MODID);

    public static final Supplier<ModLakeFeature> LAKE = FEATURES.register("lake", () -> new ModLakeFeature(LakeFeature.Configuration.CODEC));
    public static final Supplier<ReedFeature> REED = FEATURES.register("reed", () -> new ReedFeature(ProbabilityFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
