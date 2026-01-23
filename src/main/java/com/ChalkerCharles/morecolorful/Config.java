package com.ChalkerCharles.morecolorful;

import com.ChalkerCharles.morecolorful.common.worldgen.ModBiomeModifiers;
import com.ChalkerCharles.morecolorful.util.FileUtils;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.StringParser;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ModConfigSpec COMMON_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;
    public static final CommonConfig COMMON_CONFIG;
    public static final ClientConfig CLIENT_CONFIG;

    public static boolean thermalSystem = true;
    public static Object2IntMap<List<BlockState>> blockTemperature = Object2IntMaps.emptyMap();
    public static Object2IntMap<List<BlockState>> thermalResistance = Object2IntMaps.emptyMap();
    public static boolean archaeologyLoots = true;
    public static boolean windSystem = true;
    public static boolean windPhysics = true;
    public static boolean windAidingFireSpread = true;
    public static Set<ResourceKey<Level>> windlessDimensions = Set.of();
    public static int overworldRegionWeight = 10;
    public static Set<ResourceKey<Biome>> disabledBiomes = Set.of();
    public static boolean allowAddingFeatures = true;

    public static boolean wavyBlocks = true;
    public static boolean wavyParticles = true;
    public static boolean windAndRain = true;
    public static boolean windAndCloud = true;
    public static boolean windParticles = true;
    public static boolean windSounds = true;
    public static boolean leavesOnGround = true;
    public static boolean confettiOnGround = true;

    static {
        Pair<CommonConfig, ModConfigSpec> common = new ModConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_CONFIG = common.getLeft();
        COMMON_SPEC = common.getRight();

        Pair<ClientConfig, ModConfigSpec> client = new ModConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_CONFIG = client.getLeft();
        CLIENT_SPEC = client.getRight();
    }

    public static void setCommonConfigs(CommonConfig config) {
        thermalSystem = config.thermalSystem.get();
        blockTemperature = config.blockTemperature.get().stream()
                .map(StringParser::parseBlockEntry)
                .filter(StringParser.BlockEntry::validate)
                .collect(Collectors.toMap(StringParser.BlockEntry::getStates, StringParser.BlockEntry::parseValue, Maths.ONLY_SECOND, Object2IntOpenHashMap::new));
        thermalResistance = config.thermalResistance.get().stream()
                .map(StringParser::parseBlockEntry)
                .filter(StringParser.BlockEntry::validate)
                .collect(Collectors.toMap(StringParser.BlockEntry::getStates, StringParser.BlockEntry::parseValue, Maths.ONLY_SECOND, Object2IntOpenHashMap::new));
        archaeologyLoots = config.archaeologyLoots.get();
        windSystem = config.windSystem.get();
        windPhysics = config.windPhysics.get();
        windAidingFireSpread = config.windAidingFireSpread.get();
        windlessDimensions = config.windlessDimensions.get().stream()
                .map(level -> ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(level)))
                .collect(Collectors.toSet());
        overworldRegionWeight = config.overworldRegionWeight.get();
        disabledBiomes = config.disabledBiomes.get().stream()
                .map(biome -> ResourceKey.create(Registries.BIOME, ResourceLocation.parse(biome)))
                .collect(Collectors.toSet());
        allowAddingFeatures = config.allowAddingFeatures.get();
        checkBiomeModifier(allowAddingFeatures, ModBiomeModifiers.ADD_FEATURE_MODIFIERS, "Add Feature");
    }

    public static void setClientConfigs(ClientConfig config) {
        wavyBlocks = windSystem && config.wavyBlocks.get();
        wavyParticles = windSystem && config.wavyParticles.get();
        windAndRain = windSystem && config.windAndRain.get();
        windAndCloud = windSystem && config.windAndCloud.get();
        windParticles = windSystem && config.windParticles.get();
        windSounds = windSystem && config.windSounds.get();
        leavesOnGround = config.leavesOnGround.get();
        confettiOnGround = config.confettiOnGround.get();
    }

    public static void setConfigs(ModConfigEvent event) {
        switch (event.getConfig().getType()) {
            case COMMON -> setCommonConfigs(COMMON_CONFIG);
            case CLIENT -> setClientConfigs(CLIENT_CONFIG);
        }
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        setConfigs(event);
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        setConfigs(event);
    }

    @SuppressWarnings("SameParameterValue")
    private static void checkBiomeModifier(boolean config, List<String> modifiers, String name) {
        if (config) {
            FileUtils.enableBiomeModifiers(modifiers);
            MoreColorful.LOGGER.info("Enabled Biome Modifiers: {}", name);
        } else {
            FileUtils.disableBiomeModifiers(modifiers);
            MoreColorful.LOGGER.info("Disabled Biome Modifiers: {}", name);
        }
    }
}
