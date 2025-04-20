package com.ChalkerCharles.morecolorful;

import com.ChalkerCharles.morecolorful.common.worldgen.ModBiomeModifiers;
import com.ChalkerCharles.morecolorful.util.FileUtils;
import com.ChalkerCharles.morecolorful.util.StringParser;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.*;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final String prefix = MoreColorful.MODID + ".config.";
    static final ModConfigSpec SPEC_COMMON;
    public static final ModConfigSpec.BooleanValue THERMAL_SYSTEM;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> BLOCK_TEMPERATURE;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> THERMAL_RESISTANCE;
    public static final ModConfigSpec.BooleanValue ARCHAEOLOGY_LOOTS;
    public static final ModConfigSpec.IntValue OVERWORLD_REGION_WEIGHT;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_BIOMES;
    public static final ModConfigSpec.BooleanValue ALLOW_ADDING_FEATURES;

    public static Object2IntMap<List<BlockState>> blockTemperature;
    public static Object2IntMap<List<BlockState>> thermalResistance;
    public static Set<ResourceKey<Biome>> disabledBiomes;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Block").translation(prefix + "block").push("block");
        THERMAL_SYSTEM = builder
                .gameRestart()
                .translation(prefix + "thermal_system")
                .comment("Introduce thermal system for blocks. Now each block has a temperature level, and the temperature level will decrease while spreading.",
                        "Ice and snow only melt when the temperature is high enough, instead of depending on light level.",
                        "The conduction of heat will be affected by block's thermal resistance.")
                .define("thermalSystem", true);
        BLOCK_TEMPERATURE = builder
                .translation(prefix + "block_temperature")
                .comment("Define a proper temperature value (Range: 0-15) for blocks with certain block state. Or you can also override the More Colorful configs. Block states are optional.")
                .comment("Format: \"<block name>[block states]=<value>\". Example: \"minecraft:sea_lantern=0\", \"minecraft:redstone_lamp[lit=true]=12\"")
                .defineListAllowEmpty("blockTemperature", ArrayList::new, () -> "", o -> o instanceof String s && StringParser.BlockEntry.validate(s));
        THERMAL_RESISTANCE = builder
                .translation(prefix + "thermal_resistance")
                .comment("Define a proper thermal resistance value (Range: 1-15) for blocks with certain block state, by default the value is 3. Or you can also override the More Colorful configs. Block states are optional.")
                .comment("Format: \"<block name>[block states]=<value>\". Example: \"minecraft:stone=3\", \"minecraft:oak_fence[waterlogged=true]=4\"")
                .defineListAllowEmpty("thermalResistance", ArrayList::new, () -> "", o -> o instanceof String s && StringParser.BlockEntry.validate(s));
        builder.pop();

        builder.comment("Loot").translation(prefix + "loot").push("loot");
        ARCHAEOLOGY_LOOTS = builder
                .translation(prefix + "archaeology_loots")
                .comment("Add new loots in suspicious blocks, and you can get them by archaeology.")
                .define("archaeologyLoots", true);
        builder.pop();

        builder.comment("World Generation").translation(prefix + "world_generation").push("world");
        OVERWORLD_REGION_WEIGHT = builder
                .translation(prefix + "overworld_region_weight")
                .comment("The weight of More Colorful Biome regions in the overworld. Set to 0 to disable it.")
                .defineInRange("overworldRegionWeight", 10, 0, Integer.MAX_VALUE);
        DISABLED_BIOMES = builder
                .translation(prefix + "disabled_biomes")
                .comment("A list of biomes that are disabled from world generation.")
                .defineListAllowEmpty("disabledBiomes", ArrayList::new, () -> "", o -> o instanceof String s && ResourceLocation.tryParse(s) != null);
        ALLOW_ADDING_FEATURES = builder
                .translation(prefix + "allow_adding_features")
                .comment("Allow More Colorful to add new features to vanilla biomes.")
                .define("allowAddingFeatures", true);
        builder.pop();

        SPEC_COMMON = builder.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        blockTemperature = BLOCK_TEMPERATURE.get().stream()
                .map(StringParser::parseBlockEntry)
                .filter(StringParser.BlockEntry::validate)
                .collect(Collectors.toMap(StringParser.BlockEntry::getStates, entry -> Integer.parseInt(entry.value()), (i, j) -> j, Object2IntOpenHashMap::new));
        thermalResistance = THERMAL_RESISTANCE.get().stream()
                .map(StringParser::parseBlockEntry)
                .filter(StringParser.BlockEntry::validate)
                .collect(Collectors.toMap(StringParser.BlockEntry::getStates, entry -> Integer.parseInt(entry.value()), (i, j) -> j, Object2IntOpenHashMap::new));
        disabledBiomes = DISABLED_BIOMES.get().stream()
                .map(biome -> ResourceKey.create(Registries.BIOME, ResourceLocation.parse(biome)))
                .collect(Collectors.toSet());

        checkBiomeModifier(ALLOW_ADDING_FEATURES, ModBiomeModifiers.ADD_FEATURE_MODIFIERS, "Add Feature");
    }

    @SuppressWarnings("SameParameterValue")
    private static void checkBiomeModifier(ModConfigSpec.BooleanValue config, List<String> modifiers, String name) {
        if (config.isTrue()) {
            FileUtils.enableBiomeModifiers(modifiers);
            MoreColorful.LOGGER.info("Enabled Biome Modifiers: {}", name);
        } else {
            FileUtils.disableBiomeModifiers(modifiers);
            MoreColorful.LOGGER.info("Disabled Biome Modifiers: {}", name);
        }
    }
}
