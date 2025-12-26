package com.ChalkerCharles.morecolorful.common.datagen.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public abstract class ModItemModelHelper extends ItemModelProvider {
    public ModItemModelHelper(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public void basicItem(Supplier<? extends Item> item) {
        basicItem(item.get());
    }

    protected void buttonFenceWall(Supplier<? extends Item> item, Supplier<Block> baseBlock, String type) { // "type" can only be "button", "fence" or "wall"
        getBuilder(item.get().toString())
                .parent(new ModelFile.UncheckedModelFile(mcLoc("block/" + type + "_inventory")))
                .texture("texture", modLoc("block/" + name(baseBlock.get())));
    }

    protected void blockItem2d(Supplier<? extends Item> item, String name) {
        Item i = item.get();
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(i);
        getBuilder(i.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "block/" + name));
    }

    protected void blockItem2d(Supplier<? extends Item> item) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item.get());
        blockItem2d(item, resourceLocation.getPath());
    }

    @SuppressWarnings("SameParameterValue")
    protected void fromBlock(Supplier<? extends Item> item, Supplier<Block> block) {
        getBuilder(item.get().toString()).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name(block.get()))));
    }

    protected void itemWithCustomName(Supplier<? extends Item> item, String name) {
        Item i = item.get();
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(i);
        getBuilder(i.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "item/" + name));
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
