package com.ChalkerCharles.morecolorful;

import com.ChalkerCharles.morecolorful.client.ModClientEvents;
import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.*;
import com.ChalkerCharles.morecolorful.common.attachment.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.VanillaBlockPropertyModifier;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.item.ModCreativeTabs;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.loot.modifiers.ModLootModifiers;
import com.ChalkerCharles.morecolorful.common.recipe.ModRecipeSerializers;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.TerraBlenderUtils;
import com.ChalkerCharles.morecolorful.common.worldgen.features.ModFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModFoliagePlacers;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModRootPlacers;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeDecorators;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTrunkPlacers;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.Random;

@Mod(MoreColorful.MODID)
public class MoreColorful {
    public static final String MODID = "morecolorful";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreColorful(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        modEventBus.addListener(MoreColorful::commonSetup);
        modEventBus.addListener(ModCreativeTabs::insertInVanillaTabs);
        modEventBus.register(ModCommonSetup.class);

        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModStats.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModDataAttachments.register(modEventBus);
        ModParticles.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModFoliagePlacers.register(modEventBus);
        ModTrunkPlacers.register(modEventBus);
        ModRootPlacers.register(modEventBus);
        ModTreeDecorators.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModChunkStatus.register(modEventBus);

        NeoForge.EVENT_BUS.register(ModCommonEvents.class);
        NeoForge.EVENT_BUS.addListener(VanillaBlockPropertyModifier::modifyDynamicProperties);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        if (dist.isClient()) {
            NeoForge.EVENT_BUS.register(ModClientEvents.class);
            modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        greetings();

        Config.disabledBiomes.forEach(biome -> LOGGER.info("Biome Disabled: {}", biome.location()));

        ModStats.init();
        ModChunkStatus.modifyFullStatus();
        VanillaBlockPropertyModifier.modifyStaticProperties();

        ModList modList = ModList.get();
        if (modList.isLoaded("terrablender")) {
            TerraBlenderUtils.registerRegions();
            TerraBlenderUtils.registerSurfaceRules();
        }
    }

    private static void greetings() {
        int i = new Random().nextInt(10);
        LOGGER.info(switch (i) {
            case 0 -> "Thank You For Downloading!";
            case 1 -> "Hope You Have Fun!";
            case 2 -> "I'm Doing Well! :)";
            case 3 -> "Long Time No See!";
            case 4 -> "Yay, Minecraft Time!";
            case 5 -> "Hello World!";
            case 6 -> "Hi There!";
            case 7 -> "How are you today?";
            case 8 -> "Remember to Smile :)";
            default -> "More Colorful!";
        });
    }

    public static ResourceLocation location(String key) {
        return ResourceLocation.fromNamespaceAndPath(MODID, key);
    }

    public static String key(String name) {
        return MODID + ':' + name;
    }

    public static String name(String name) {
        return MODID + '_' + name;
    }
}
