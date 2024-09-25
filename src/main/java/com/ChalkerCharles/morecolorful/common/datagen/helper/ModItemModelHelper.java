package com.ChalkerCharles.morecolorful.common.datagen.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public abstract class ModItemModelHelper extends ItemModelProvider {
    public ModItemModelHelper(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    protected void buttonFenceWall(Item item, Block baseBlock, String type) { // "type" can only be "button", "fence" or "wall"
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile(mcLoc("block/" + type + "_inventory")))
                .texture("texture", modLoc("block/" + name(baseBlock)));
    }

    protected void blockItem2d(Item item) {
        ResourceLocation resourceLocation = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath()));
    }

    protected void itemWithCustomName(Item item, String name) {
        ResourceLocation resourceLocation = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        getBuilder(item.toString())
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
