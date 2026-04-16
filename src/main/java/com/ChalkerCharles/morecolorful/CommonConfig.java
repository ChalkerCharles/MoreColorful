package com.ChalkerCharles.morecolorful;

import com.ChalkerCharles.morecolorful.util.StringParser;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CommonConfig {
    private static final Supplier<String> EMPTY = () -> "";
    private static final String prefix = MoreColorful.MODID + ".config.";
    public final ModConfigSpec.BooleanValue thermalSystem;
    public final ModConfigSpec.ConfigValue<List<? extends String>> blockTemperature;
    public final ModConfigSpec.ConfigValue<List<? extends String>> thermalResistance;
    public final ModConfigSpec.BooleanValue archaeologyLoots;
    public final ModConfigSpec.BooleanValue windSystem;
    public final ModConfigSpec.BooleanValue windPhysics;
    public final ModConfigSpec.BooleanValue windAidingFireSpread;
    public final ModConfigSpec.ConfigValue<List<? extends String>> windlessDimensions;
    public final ModConfigSpec.IntValue overworldRegionWeight;
    public final ModConfigSpec.ConfigValue<List<? extends String>> disabledBiomes;
    public final ModConfigSpec.BooleanValue allowAddingFeatures;
    public final ModConfigSpec.BooleanValue allowAddingSpawns;
    public final ModConfigSpec.BooleanValue enhancedLeash;

    public CommonConfig(ModConfigSpec.Builder builder) {
        builder.comment("Block").translation(prefix + "block").push("block");
        thermalSystem = builder
                .gameRestart()
                .translation(prefix + "thermal_system")
                .comment("Introduce thermal system for blocks. Now each block has a temperature level, and the temperature level will decrease while spreading.",
                        "Ice and snow only melt when the temperature is high enough, instead of depending on light level.",
                        "The conduction of heat will be affected by block's thermal resistance.")
                .define("thermalSystem", true);
        blockTemperature = builder
                .translation(prefix + "block_temperature")
                .comment("Define a proper temperature value (Range: 0-15) for blocks with certain block state. Or you can also override the More Colorful configs. Block states are optional.",
                        "Format: \"<block name>[block states]=<value>\". Example: \"minecraft:sea_lantern=0\", \"minecraft:redstone_lamp[lit=true]=12\"")
                .defineListAllowEmpty("blockTemperature", ArrayList::new, EMPTY, StringParser.BlockEntry::validate);
        thermalResistance = builder
                .translation(prefix + "thermal_resistance")
                .comment("Define a proper thermal resistance value (Range: 1-15) for blocks with certain block state, by default the value is 3. Or you can also override the More Colorful configs. Block states are optional.",
                        "Format: \"<block name>[block states]=<value>\". Example: \"minecraft:stone=3\", \"minecraft:oak_fence[waterlogged=true]=4\"")
                .defineListAllowEmpty("thermalResistance", ArrayList::new, EMPTY, StringParser.BlockEntry::validate);
        builder.pop();

        builder.comment("Loot").translation(prefix + "loot").push("loot");
        archaeologyLoots = builder
                .translation(prefix + "archaeology_loots")
                .comment("Add new loots in suspicious blocks, and you can get them by archaeology.")
                .define("archaeologyLoots", true);
        builder.pop();

        builder.comment("World").translation(prefix + "world").push("world");
        windSystem = builder
                .gameRestart()
                .translation(prefix + "wind_system")
                .comment("Wind is a global weather occurrence that randomly happens. The wind consists of two components on X axis and Z axis, and the max speed of each component is 17.5 (m/s).")
                .define("windSystem", true);
        windPhysics = builder
                .translation(prefix + "wind_physics")
                .comment("Allow wind to push entities.")
                .define("windPhysics", true);
        windAidingFireSpread = builder
                .translation(prefix + "wind_aiding_fire_spread")
                .comment("Wind can assist the fire spreading downwind.")
                .define("windAidingFireSpread", true);
        windlessDimensions = builder
                .translation(prefix + "windless_dimensions")
                .comment("Wind won't blow in dimensions in this list.")
                .defineListAllowEmpty("windlessDimensions", ArrayList::new, EMPTY, CommonConfig::validLocation);

        builder.comment("World Generation").translation(prefix + "world.generation").push("generation");
        overworldRegionWeight = builder
                .translation(prefix + "overworld_region_weight")
                .comment("The weight of More Colorful Biome regions in the overworld. Set to 0 to disable it.")
                .defineInRange("overworldRegionWeight", 10, 0, Integer.MAX_VALUE);
        disabledBiomes = builder
                .translation(prefix + "disabled_biomes")
                .comment("A list of biomes that are disabled from world generation.")
                .defineListAllowEmpty("disabledBiomes", ArrayList::new, EMPTY, CommonConfig::validLocation);
        allowAddingFeatures = builder
                .translation(prefix + "allow_adding_features")
                .comment("Allow More Colorful to add new features to vanilla biomes.")
                .define("allowAddingFeatures", true);
        allowAddingSpawns = builder
                .translation(prefix + "allow_adding_spawns")
                .comment("Allow More Colorful to add new mob spawns to vanilla biomes.")
                .define("allowAddingSpawns", true);
        builder.pop();

        builder.pop();

        enhancedLeash = builder
                .translation(prefix + "enhanced_leash")
                .comment("Make leads act like in vanilla 1.21.6+.")
                .define("enhancedLeash", true);
    }

    private static boolean validLocation(Object o) {
        return o instanceof String s && ResourceLocation.tryParse(s) != null;
    }
}
