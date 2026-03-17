package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModStats {
    private static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, MoreColorful.MODID);
    private static final Map<ResourceLocation, StatFormatter> STAT_SETUP = new HashMap<>();

    public static final Supplier<ResourceLocation> INTERACT_WITH_HARP = makeCustomStat("interact_with_harp");
    public static final Supplier<ResourceLocation> INTERACT_WITH_PIANO = makeCustomStat("interact_with_piano");
    public static final Supplier<ResourceLocation> INTERACT_WITH_BASS_DRUM = makeCustomStat("interact_with_bass_drum");
    public static final Supplier<ResourceLocation> INTERACT_WITH_SNARE = makeCustomStat("interact_with_snare");
    public static final Supplier<ResourceLocation> INTERACT_WITH_TOM = makeCustomStat("interact_with_tom");
    public static final Supplier<ResourceLocation> INTERACT_WITH_HAT = makeCustomStat("interact_with_hat");
    public static final Supplier<ResourceLocation> INTERACT_WITH_RIDE = makeCustomStat("interact_with_ride");
    public static final Supplier<ResourceLocation> INTERACT_WITH_CRASH = makeCustomStat("interact_with_crash");
    public static final Supplier<ResourceLocation> INTERACT_WITH_DRUM_SET = makeCustomStat("interact_with_drum_set");
    public static final Supplier<ResourceLocation> INTERACT_WITH_GLOCKENSPIEL = makeCustomStat("interact_with_glockenspiel");
    public static final Supplier<ResourceLocation> INTERACT_WITH_CHIMES = makeCustomStat("interact_with_chimes");
    public static final Supplier<ResourceLocation> INTERACT_WITH_XYLOPHONE = makeCustomStat("interact_with_xylophone");
    public static final Supplier<ResourceLocation> INTERACT_WITH_VIBRAPHONE = makeCustomStat("interact_with_vibraphone");
    public static final Supplier<ResourceLocation> INTERACT_WITH_SYNTHESIZER_KEYBOARD = makeCustomStat("interact_with_synthesizer_keyboard");
    public static final Supplier<ResourceLocation> INTERACT_WITH_GUZHENG = makeCustomStat("interact_with_guzheng");
    public static final Supplier<ResourceLocation> CLEAN_UMBRELLA = makeCustomStat("clean_umbrella");
    public static final Supplier<ResourceLocation> DAMAGE_BLOCKED_BY_UMBRELLA = makeCustomStat("damage_blocked_by_umbrella", StatFormatter.DIVIDE_BY_TEN);
    public static final Supplier<ResourceLocation> INTERACT_WITH_PYROTECHNICS_TABLE = makeCustomStat("interact_with_pyrotechnics_table");
    public static final Supplier<ResourceLocation> INTERACT_WITH_PAPERCRAFT_TABLE = makeCustomStat("interact_with_papercraft_table");

    private static Supplier<ResourceLocation> makeCustomStat(String key, StatFormatter formatter) {
        return STATS.register(key, location -> {
            STAT_SETUP.put(location, formatter);
            return location;
        });
    }

    private static Supplier<ResourceLocation> makeCustomStat(String key) {
        return makeCustomStat(key, StatFormatter.DEFAULT);
    }

    public static void init() {
        STAT_SETUP.forEach(Stats.CUSTOM::get);
    }

    public static void register(IEventBus eventBus){
        STATS.register(eventBus);
    }
}
