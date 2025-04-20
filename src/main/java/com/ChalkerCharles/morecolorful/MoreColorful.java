package com.ChalkerCharles.morecolorful;

import com.ChalkerCharles.morecolorful.client.ModClientEvents;
import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.ModCommonSetup;
import com.ChalkerCharles.morecolorful.common.attachment.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.VanillaBlockPropertyModifier;
import com.ChalkerCharles.morecolorful.common.item.ModCreativeTabs;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.loot.modifiers.ModLootModifiers;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.ModBiomeSetup;
import com.ChalkerCharles.morecolorful.common.worldgen.features.ModFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModFoliagePlacers;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModRootPlacers;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeDecorators;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTrunkPlacers;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;

import java.util.Random;

@Mod(MoreColorful.MODID)
public class MoreColorful {
    public static final String MODID = "morecolorful";
    public static final Logger LOGGER = LogUtils.getLogger();
    private final Random random = new Random();

    public MoreColorful(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModCreativeTabs::insertInVanillaTabs);
        modEventBus.register(ModCommonSetup.class);

        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModStats.register(modEventBus);
        ModDataAttachments.register(modEventBus);
        ModParticles.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModFoliagePlacers.register(modEventBus);
        ModTrunkPlacers.register(modEventBus);
        ModRootPlacers.register(modEventBus);
        ModTreeDecorators.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModChunkStatus.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(VanillaBlockPropertyModifier::modifyProperties);
        //NeoForge.EVENT_BUS.register(new MelodyHandler());
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC_COMMON);

        if (dist.isClient()) {
            NeoForge.EVENT_BUS.register(ModClientEvents.class);
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        int i = random.nextInt(4);
        switch (i) {
            case 0 -> LOGGER.info("Thank You For Downloading!");
            case 1 -> LOGGER.info("Hope You Have Fun!");
            case 2 -> LOGGER.info("I'm Doing Well! :)");
            case 3 -> LOGGER.info("Long Time No See!");
        }

        Config.disabledBiomes.forEach(biome -> LOGGER.info("Biome Disabled: {}", biome.location()));

        ModStats.init();
        ModChunkStatus.modifyFullStatus();

        ModList modList = ModList.get();
        if (modList.isLoaded("terrablender")) {
            ModBiomeSetup.registerRegions();
            ModBiomeSetup.registerSurfaceRules();
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        int i = random.nextInt(4);
        switch (i) {
            case 0 -> LOGGER.info("More Colorful!");
            case 1 -> LOGGER.info("Hello Server!");
            case 2 -> LOGGER.info("Have A Nice Day!");
            case 3 -> LOGGER.info("YAY! Server Time!");
        }
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        int i = random.nextInt(4);
        switch (i) {
            case 0 -> LOGGER.info("Goodbye!");
            case 1 -> LOGGER.info("Bye-bye!");
            case 2 -> LOGGER.info("I'll Miss You.");
            case 3 -> LOGGER.info("See You Around!");
        }
    }
}
