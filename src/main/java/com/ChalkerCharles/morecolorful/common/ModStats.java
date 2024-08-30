package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModStats {
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, MoreColorful.MODID);
    private static final List<Runnable> STAT_SETUP = new ArrayList<>();
    public static final Supplier<ResourceLocation> INTERACT_WITH_HARP = makeCustomStat("interact_with_harp");
    public static final Supplier<ResourceLocation> INTERACT_WITH_PIANO = makeCustomStat("interact_with_piano");
    public static final Supplier<ResourceLocation> INTERACT_WITH_BASS_DRUM = makeCustomStat("interact_with_bass_drum");
    public static final Supplier<ResourceLocation> INTERACT_WITH_SNARE = makeCustomStat("interact_with_snare");
    public static final Supplier<ResourceLocation> INTERACT_WITH_TOM = makeCustomStat("interact_with_tom");
    public static final Supplier<ResourceLocation> INTERACT_WITH_HAT = makeCustomStat("interact_with_hat");
    public static final Supplier<ResourceLocation> INTERACT_WITH_RIDE = makeCustomStat("interact_with_ride");
    public static final Supplier<ResourceLocation> INTERACT_WITH_CRASH = makeCustomStat("interact_with_crash");
    public static final Supplier<ResourceLocation> INTERACT_WITH_GLOCKENSPIEL = makeCustomStat("interact_with_glockenspiel");
    public static final Supplier<ResourceLocation> INTERACT_WITH_CHIMES = makeCustomStat("interact_with_chimes");
    public static final Supplier<ResourceLocation> INTERACT_WITH_XYLOPHONE = makeCustomStat("interact_with_xylophone");
    public static final Supplier<ResourceLocation> INTERACT_WITH_VIBRAPHONE = makeCustomStat("interact_with_vibraphone");
    public static final Supplier<ResourceLocation> INTERACT_WITH_SYNTHESIZER_KEYBOARD = makeCustomStat("interact_with_synthesizer_keyboard");

    private static Supplier<ResourceLocation> makeCustomStat(String pKey) {
        ResourceLocation resourcelocation = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, pKey);
        STAT_SETUP.add(() -> Stats.CUSTOM.get(resourcelocation, StatFormatter.DEFAULT));
        return STATS.register(pKey, () -> resourcelocation);
    }
    public static void init() {
        STAT_SETUP.forEach(Runnable::run);
    }
    public static void register(IEventBus eventBus){
        STATS.register(eventBus);
    }
}
