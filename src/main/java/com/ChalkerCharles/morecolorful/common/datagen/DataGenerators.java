package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.datagen.loot.ModLootTableProvider;
import com.ChalkerCharles.morecolorful.common.datagen.tag.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        ModDatapackProvider datapackProvider = new ModDatapackProvider(output, event.getLookupProvider());
        generator.addProvider(server, datapackProvider);
        CompletableFuture<HolderLookup.Provider> provider = datapackProvider.getRegistryProvider();

        BlockTagsProvider blockTagProvider = new ModBlockTagProvider(output, provider, helper);
        generator.addProvider(server, blockTagProvider);
        generator.addProvider(server, new ModItemTagProvider(output, provider, blockTagProvider.contentsGetter(), helper));
        generator.addProvider(server, new ModBiomeTagProvider(output, provider, helper));
        generator.addProvider(server, new ModEntityTypeTagProvider(output, provider, helper));
        generator.addProvider(server, new ModDamageTypeTagProvider(output, provider, helper));
        generator.addProvider(server, new ModLootTableProvider(output, provider));
        generator.addProvider(server, new ModRecipeProvider(output, provider));
        generator.addProvider(server, new ModDataMapProvider(output, provider));
        generator.addProvider(server, new ModGlobalLootModifierProvider(output, provider));

        boolean client = event.includeClient();
        generator.addProvider(client, new ModAtlasProvider(output, provider, helper));
        generator.addProvider(client, new ModBlockStateProvider(output, helper));
        generator.addProvider(client, new ModItemModelProvider(output, helper));
        generator.addProvider(client, new ModSoundDefinitionProvider(output, helper));
    }
}
