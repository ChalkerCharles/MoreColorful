package com.ChalkerCharles.morecolorful;

import com.ChalkerCharles.morecolorful.common.worldgen.ModBiomeModifiers;
import com.ChalkerCharles.morecolorful.util.FileUtils;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.StringParser;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final Supplier<String> EMPTY = () -> "";

    protected static final ModConfigSpec SPEC_COMMON;
    public static final ModConfigSpec.BooleanValue THERMAL_SYSTEM;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> BLOCK_TEMPERATURE;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> THERMAL_RESISTANCE;
    public static final ModConfigSpec.BooleanValue ARCHAEOLOGY_LOOTS;
    public static final ModConfigSpec.BooleanValue WIND_SYSTEM;
    public static final ModConfigSpec.BooleanValue WIND_PHYSICS;
    public static final ModConfigSpec.BooleanValue WIND_AIDING_FIRE_SPREAD;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> WINDLESS_DIMENSIONS;
    public static final ModConfigSpec.IntValue OVERWORLD_REGION_WEIGHT;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_BIOMES;
    public static final ModConfigSpec.BooleanValue ALLOW_ADDING_FEATURES;

    protected static final ModConfigSpec SPEC_CLIENT;
    public static final ModConfigSpec.BooleanValue WAVY_BLOCKS;
    public static final ModConfigSpec.BooleanValue WAVY_PARTICLES;
    public static final ModConfigSpec.BooleanValue WIND_AND_RAIN;
    public static final ModConfigSpec.BooleanValue WIND_AND_CLOUD;
    public static final ModConfigSpec.BooleanValue WIND_PARTICLES;
    public static final ModConfigSpec.BooleanValue WIND_SOUNDS;

    public static Object2IntMap<List<BlockState>> blockTemperature;
    public static Object2IntMap<List<BlockState>> thermalResistance;
    public static Set<ResourceKey<Level>> windlessDimensions;
    public static Set<ResourceKey<Biome>> disabledBiomes;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        String prefix = MoreColorful.MODID + ".config.";

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
                .comment("Define a proper temperature value (Range: 0-15) for blocks with certain block state. Or you can also override the More Colorful configs. Block states are optional.",
                        "Format: \"<block name>[block states]=<value>\". Example: \"minecraft:sea_lantern=0\", \"minecraft:redstone_lamp[lit=true]=12\"")
                .defineListAllowEmpty("blockTemperature", ArrayList::new, EMPTY, StringParser.BlockEntry::validate);
        THERMAL_RESISTANCE = builder
                .translation(prefix + "thermal_resistance")
                .comment("Define a proper thermal resistance value (Range: 1-15) for blocks with certain block state, by default the value is 3. Or you can also override the More Colorful configs. Block states are optional.",
                        "Format: \"<block name>[block states]=<value>\". Example: \"minecraft:stone=3\", \"minecraft:oak_fence[waterlogged=true]=4\"")
                .defineListAllowEmpty("thermalResistance", ArrayList::new, EMPTY, StringParser.BlockEntry::validate);
        builder.pop();

        builder.comment("Loot").translation(prefix + "loot").push("loot");
        ARCHAEOLOGY_LOOTS = builder
                .translation(prefix + "archaeology_loots")
                .comment("Add new loots in suspicious blocks, and you can get them by archaeology.")
                .define("archaeologyLoots", true);
        builder.pop();

        builder.comment("World").translation(prefix + "world").push("world");
        WIND_SYSTEM = builder
                .gameRestart()
                .translation(prefix + "wind_system")
                .comment("Wind is a global weather occurrence that randomly happens. The wind consists of two components on X axis and Z axis, and the max speed of each component is 17.5 (m/s).")
                .define("windSystem", true);
        WIND_PHYSICS = builder
                .translation(prefix + "wind_physics")
                .comment("Allow wind to push entities.")
                .define("windPhysics", true);
        WIND_AIDING_FIRE_SPREAD = builder
                .translation(prefix + "wind_aiding_fire_spread")
                .comment("Wind can assist the fire spreading downwind.")
                .define("windAidingFireSpread", true);
        WINDLESS_DIMENSIONS = builder
                .translation(prefix + "windless_dimensions")
                .comment("Wind won't blow in dimensions in this list.")
                .defineListAllowEmpty("windlessDimensions", ArrayList::new, EMPTY, Config::validLocation);

        builder.comment("World Generation").translation(prefix + "world.generation").push("generation");
        OVERWORLD_REGION_WEIGHT = builder
                .translation(prefix + "overworld_region_weight")
                .comment("The weight of More Colorful Biome regions in the overworld. Set to 0 to disable it.")
                .defineInRange("overworldRegionWeight", 10, 0, Integer.MAX_VALUE);
        DISABLED_BIOMES = builder
                .translation(prefix + "disabled_biomes")
                .comment("A list of biomes that are disabled from world generation.")
                .defineListAllowEmpty("disabledBiomes", ArrayList::new, EMPTY, Config::validLocation);
        ALLOW_ADDING_FEATURES = builder
                .translation(prefix + "allow_adding_features")
                .comment("Allow More Colorful to add new features to vanilla biomes.")
                .define("allowAddingFeatures", true);
        builder.pop();

        builder.pop();

        SPEC_COMMON = builder.build();
    }

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        String prefix = MoreColorful.MODID + ".config.client.";

        builder.comment("World").translation(prefix + "world").push("world");
        WAVY_BLOCKS = builder
                .worldRestart()
                .translation(prefix + "wavy_blocks")
                .comment("Allow blocks to wave in the wind.")
                .define("wavyBlocks", true);
        WAVY_PARTICLES = builder
                .worldRestart()
                .translation(prefix + "wavy_particles")
                .comment("Allow wind to affect particle motion.")
                .define("wavyParticles", true);
        WIND_AND_RAIN = builder
                .worldRestart()
                .translation(prefix + "wind_and_rain")
                .comment("Allow wind to affect rain and snow.")
                .define("windAndRain", true);
        WIND_AND_CLOUD = builder
                .worldRestart()
                .translation(prefix + "wind_and_cloud")
                .comment("Allow wind to affect cloud motion.")
                .define("windAndCloud", true);
        WIND_PARTICLES = builder
                .worldRestart()
                .translation(prefix + "wind_particles")
                .comment("Wind can generate particles.")
                .define("windParticles", true);
        WIND_SOUNDS = builder
                .worldRestart()
                .translation(prefix + "wind_sounds")
                .comment("Wind can make sound effects. It also allows the leaves rustling.")
                .define("windSounds", true);
        builder.pop();

        SPEC_CLIENT = builder.build();
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (config.getType() == ModConfig.Type.COMMON) {
            blockTemperature = BLOCK_TEMPERATURE.get().stream()
                    .map(StringParser::parseBlockEntry)
                    .filter(StringParser.BlockEntry::validate)
                    .collect(Collectors.toMap(StringParser.BlockEntry::getStates, StringParser.BlockEntry::parseValue, Maths.ONLY_SECOND, Object2IntOpenHashMap::new));
            thermalResistance = THERMAL_RESISTANCE.get().stream()
                    .map(StringParser::parseBlockEntry)
                    .filter(StringParser.BlockEntry::validate)
                    .collect(Collectors.toMap(StringParser.BlockEntry::getStates, StringParser.BlockEntry::parseValue, Maths.ONLY_SECOND, Object2IntOpenHashMap::new));
            windlessDimensions = WINDLESS_DIMENSIONS.get().stream()
                    .map(level -> ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(level)))
                    .collect(Collectors.toSet());
            disabledBiomes = DISABLED_BIOMES.get().stream()
                    .map(biome -> ResourceKey.create(Registries.BIOME, ResourceLocation.parse(biome)))
                    .collect(Collectors.toSet());

            checkBiomeModifier(ALLOW_ADDING_FEATURES, ModBiomeModifiers.ADD_FEATURE_MODIFIERS, "Add Feature");
        }
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

    private static boolean validLocation(Object o) {
        return o instanceof String s && ResourceLocation.tryParse(s) != null;
    }
}
