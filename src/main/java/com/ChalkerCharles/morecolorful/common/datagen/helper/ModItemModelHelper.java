package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public abstract class ModItemModelHelper extends ItemModelProvider {
    public ModItemModelHelper(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    protected void basicItem(ItemLike item) {
        this.basicItem(item.asItem());
    }

    protected void buttonFenceWall(ItemLike item, Supplier<Block> baseBlock, String type) { // "type" can only be "button", "fence" or "wall"
        getBuilder(item.asItem().toString())
                .parent(new ModelFile.UncheckedModelFile(mcLoc("block/" + type + "_inventory")))
                .texture("texture", modLoc("block/" + name(baseBlock.get())));
    }

    protected void blockItem2d(ItemLike item, String name) {
        Item i = item.asItem();
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(i);
        getBuilder(i.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "block/" + name));
    }

    protected void blockItem2d(ItemLike item) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item.asItem());
        blockItem2d(item, resourceLocation.getPath());
    }

    @SuppressWarnings("SameParameterValue")
    protected void fromBlock(ItemLike item, Supplier<Block> block) {
        getBuilder(item.asItem().toString()).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name(block.get()))));
    }

    protected void itemWithCustomName(ItemLike item, String name) {
        Item i = item.asItem();
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(i);
        getBuilder(i.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "item/" + name));
    }

    protected void ribbon(ItemLike item) {
        this.basicItem(item.asItem()).transforms().transform(ItemDisplayContext.HEAD).translation(0, 5, 7).end().end();
    }

    protected void pinwheel(ItemLike item) {
        Item it = item.asItem();
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(it);
        String name = it.toString();
        ModelFile parent = new ModelFile.UncheckedModelFile(modLoc("item/handheld_pinwheel"));
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath());
        ItemModelBuilder builder = getBuilder(name).parent(parent).texture("layer0", texture);
        int n = item == ModItems.MULTICOLORED_PINWHEEL ? 16 : 4;
        ResourceLocation spin = modLoc("spin");
        for (int i = 1; i < n; i++) {
            ModelFile override = getBuilder(name + "_" + i).parent(parent).texture("layer0", texture.withSuffix("_" + i));
            builder.override().predicate(spin, (float) i / n).model(override).end();
        }
    }

    protected void sparkler(ItemLike item) {
        Item it = item.asItem();
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(it);
        String name = it.toString();
        ModelFile parent = new ModelFile.UncheckedModelFile("item/handheld");
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath());
        ModelFile lit = getBuilder(name + "_lit").parent(parent).texture("layer0", texture.withSuffix("_lit"))
                .customLoader(ItemLayerModelBuilder::begin).emissive(15, 15, 0).end();
        getBuilder(name).parent(parent).texture("layer0", texture).override().predicate(modLoc("activated"), 1).model(lit).end();
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
