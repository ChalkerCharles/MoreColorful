package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.common.entity.animal.Butterfly;
import com.ChalkerCharles.morecolorful.common.entity.animal.Moth;
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
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(i);
        getBuilder(i.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", location.withPath("block/" + name));
    }

    protected void blockItem2d(ItemLike item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item.asItem());
        blockItem2d(item, location.getPath());
    }

    protected void fromBlock(ItemLike item, Supplier<Block> block) {
        getBuilder(item.asItem().toString()).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name(block.get()))));
    }

    protected void itemWithCustomName(ItemLike item, String name) {
        Item i = item.asItem();
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(i);
        getBuilder(i.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", location.withPath("item/" + name));
    }

    protected void handheld(ItemLike item) {
        Item i = item.asItem();
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(i);
        getBuilder(i.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", location.withPrefix("item/"));
    }

    protected void dynamicFlat(ItemLike item) {
        Item i = item.asItem();
        getBuilder(i.toString()).parent(new ModelFile.UncheckedModelFile("morecolorful:item/dynamic_flat"));
    }

    protected void ribbon(ItemLike item) {
        this.basicItem(item.asItem()).transforms().transform(ItemDisplayContext.HEAD).translation(0, 5, 7).end().end();
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

    protected void bundle(ItemLike item) {
        Item it = item.asItem();
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(it);
        String name = it.toString();
        ModelFile parent = new ModelFile.UncheckedModelFile("item/generated");
        ResourceLocation texture = location.withPrefix("item/");
        ModelFile filled = getBuilder(name + "_filled").parent(parent).texture("layer0", texture.withSuffix("_filled"));
        getBuilder(name).parent(parent).texture("layer0", texture).override().predicate(mcLoc("filled"), 0.0000001F).model(filled).end();
    }

    protected void butterflies() {
        String name = ModItems.BUTTERFLY.get().toString();
        ModelFile parent = new ModelFile.UncheckedModelFile("item/generated");
        ResourceLocation texture = modLoc("item/");
        ItemModelBuilder builder = getBuilder(name).parent(parent).texture("layer0", texture.withSuffix(Butterfly.Variant.MONARCH.getTextureName()));
        for (Butterfly.Variant variant : Butterfly.Variant.VALUES) {
            ModelFile file = getBuilder(name + '_' + variant.getName()).parent(parent).texture("layer0", texture.withSuffix(variant.getTextureName()));
            builder.override().predicate(modLoc("type"), variant.ordinal() / 32.0F).model(file);
        }
    }

    protected void moths() {
        String name = ModItems.MOTH.get().toString();
        ModelFile parent = new ModelFile.UncheckedModelFile("item/generated");
        ResourceLocation texture = modLoc("item/");
        ItemModelBuilder builder = getBuilder(name).parent(parent).texture("layer0", texture.withSuffix(Moth.Variant.WILD_SILK.getTextureName()));
        for (Moth.Variant variant : Moth.Variant.VALUES) {
            ModelFile file = getBuilder(name + '_' + variant.getName()).parent(parent).texture("layer0", texture.withSuffix(variant.getTextureName()));
            builder.override().predicate(modLoc("type"), variant.ordinal() / 32.0F).model(file);
        }
    }

    protected void caterpillars() {
        String name = ModItems.CATERPILLAR.get().toString();
        ModelFile parent = new ModelFile.UncheckedModelFile("item/generated");
        ResourceLocation texture = modLoc("item/");
        ItemModelBuilder builder = getBuilder(name).parent(parent).texture("layer0", texture.withSuffix(Butterfly.Variant.MONARCH.getTextureName() + "_caterpillar"));
        for (AbstractMoth.Variant variant : AbstractMoth.Variant.values()) {
            ModelFile file = getBuilder(name + '_' + variant.getName()).parent(parent).texture("layer0", texture.withSuffix(variant.getTextureName() + "_caterpillar"));
            builder.override().predicate(modLoc("type"), variant.getIndex() / 64.0F).model(file);
        }
    }

    protected void spawnEgg(ItemLike item) {
        Item i = item.asItem();
        getBuilder(i.toString()).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
