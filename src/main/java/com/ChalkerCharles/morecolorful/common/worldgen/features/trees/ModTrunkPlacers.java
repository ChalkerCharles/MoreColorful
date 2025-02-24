package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.trunklplacers.DawnRedwoodTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTrunkPlacers {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, MoreColorful.MODID);

    public static final Supplier<TrunkPlacerType<DawnRedwoodTrunkPlacer>> DAWN_REDWOOD_TRUNK_PLACER =
            TRUNK_PLACERS.register("dawn_redwood_trunk_placer", () -> new TrunkPlacerType<>(DawnRedwoodTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
    }
}
