package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.rootplacers.DawnRedwoodRootPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRootPlacers {
    public static final DeferredRegister<RootPlacerType<?>> ROOT_PLACERS = DeferredRegister.create(Registries.ROOT_PLACER_TYPE, MoreColorful.MODID);

    public static final Supplier<RootPlacerType<DawnRedwoodRootPlacer>> DAWN_REDWOOD_PLACER =
            ROOT_PLACERS.register("dawn_redwood_root_placer", () -> new RootPlacerType<>(DawnRedwoodRootPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        ROOT_PLACERS.register(eventBus);
    }
}
