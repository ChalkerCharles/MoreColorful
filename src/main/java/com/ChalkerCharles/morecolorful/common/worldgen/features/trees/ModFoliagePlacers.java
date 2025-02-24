package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.foliageplacers.DawnRedwoodFoliagePlacer;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.foliageplacers.GinkgoFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFoliagePlacers {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, MoreColorful.MODID);

    public static final Supplier<FoliagePlacerType<GinkgoFoliagePlacer>> GINKGO_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("ginkgo_foliage_placer", () -> new FoliagePlacerType<>(GinkgoFoliagePlacer.CODEC));
    public static final Supplier<FoliagePlacerType<DawnRedwoodFoliagePlacer>> DAWN_REDWOOD_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("dawn_redwood_foliage_placer", () -> new FoliagePlacerType<>(DawnRedwoodFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
    }
}
