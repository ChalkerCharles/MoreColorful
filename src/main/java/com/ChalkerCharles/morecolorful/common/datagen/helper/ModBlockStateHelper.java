package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.common.block.common.BerryBushBlock;
import com.ChalkerCharles.morecolorful.common.block.common.WillowBranchesBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.HorizontalDoubleBlockHalf;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class ModBlockStateHelper extends BlockStateProvider {
    private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");

    public ModBlockStateHelper(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    // States and Models
    protected void simpleCube(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    protected void simpleStairs(StairBlock block, Block baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock);
        stairsBlock(block, tex);
        simpleBlockItem(block, models().stairs(name(block), tex, tex, tex));
    }

    protected void simpleSlab(SlabBlock block, Block baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock);
        slabBlock(block, tex, tex);
        simpleBlockItem(block, models().slab(name(block), tex, tex, tex));
    }

    protected void logAndWood(RotatedPillarBlock log, RotatedPillarBlock wood) {
        logBlock(log);
        ResourceLocation sideTex = blockTexture(log);
        axisBlock(wood, sideTex, sideTex);
        simpleBlockItem(log, models().cubeColumn(name(log), sideTex, modLoc("block/" + name(log) + "_top")));
        simpleBlockItem(wood, models().cubeColumn(name(wood), sideTex, sideTex));
    }

    protected void simpleFenceGate(FenceGateBlock block, Block baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock);
        fenceGateBlock(block, tex);
        simpleBlockItem(block, models().fenceGate(name(block), tex));
    }

    protected void simpleDoor(DoorBlock block, boolean cutout) {
        if (cutout) {
            doorBlockWithRenderType(block, modLoc("block/" + name(block) + "_bottom"), modLoc("block/" + name(block) + "_top"), CUTOUT);
        } else {
            doorBlock(block, modLoc("block/" + name(block) + "_bottom"), modLoc("block/" + name(block) + "_top"));
        }
    }

    protected void simpleTrapdoor(TrapDoorBlock block, boolean orientable, boolean cutout) {
        if (cutout) {
            trapdoorBlockWithRenderType(block, modLoc("block/" + name(block)), orientable, CUTOUT);
            if (orientable) {
                simpleBlockItem(block, models().trapdoorOrientableBottom(name(block) + "_bottom", modLoc("block/" + name(block))).renderType(CUTOUT));
            } else {
                simpleBlockItem(block, models().trapdoorBottom(name(block) + "_bottom", modLoc("block/" + name(block))).renderType(CUTOUT));
            }
        } else {
            trapdoorBlock(block, modLoc("block/" + name(block)), orientable);
            if (orientable) {
                simpleBlockItem(block, models().trapdoorOrientableBottom(name(block) + "_bottom", modLoc("block/" + name(block))));
            } else {
                simpleBlockItem(block, models().trapdoorBottom(name(block) + "_bottom", modLoc("block/" + name(block))));
            }
        }
    }

    protected void simplePressurePlate(PressurePlateBlock block, Block baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock);
        pressurePlateBlock(block, tex);
        simpleBlockItem(block, models().pressurePlate(name(block), tex));
    }

    protected void leaves(Block block) {
        simpleBlockWithItem(block, models().leaves(name(block), blockTexture(block)));
    }

    protected ModelBuilder<BlockModelBuilder> tintedCross(String name, ResourceLocation texture) {
        return models().singleTexture(name, mcLoc("block/tinted_cross"), "cross", texture);
    }

    protected void cross(Block block, boolean isTinted) {
        ResourceLocation texture = modLoc("block/" + name(block));
        if (isTinted) {
            simpleBlock(block, tintedCross(name(block), texture).renderType(CUTOUT));
        } else {
            simpleBlock(block, models().cross(name(block), texture).renderType(CUTOUT));
        }
    }

    protected void cross(Block block) {
        cross(block, false);
    }

    protected void alternativeCross(Block block) {
        ResourceLocation tex1 = modLoc("block/" + name(block));
        ResourceLocation tex2 = modLoc("block/" + name(block) + "_alt");
        simpleBlock(block,
                new ConfiguredModel(models().cross(name(block), tex1).renderType(CUTOUT)),
                new ConfiguredModel(models().cross(name(block) + "_alt", tex2).renderType(CUTOUT))
        );
    }

    protected void doubleCross(Block block, boolean isTinted) {
        ResourceLocation top = modLoc("block/" + name(block) + "_top");
        ResourceLocation bottom = modLoc("block/" + name(block) + "_bottom");
        if (isTinted) {
            getVariantBuilder(block)
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .addModels(new ConfiguredModel(tintedCross(name(block) + "_bottom", bottom).renderType(CUTOUT)))
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .addModels(new ConfiguredModel(tintedCross(name(block) + "_top", top).renderType(CUTOUT)));
        } else {
            getVariantBuilder(block)
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .addModels(new ConfiguredModel(models().cross(name(block) + "_bottom", bottom).renderType(CUTOUT)))
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .addModels(new ConfiguredModel(models().cross(name(block) + "_top", top).renderType(CUTOUT)));
        }
    }

    protected void doubleCross(Block block) {
        doubleCross(block, false);
    }

    protected void simpleFlowerPot(FlowerPotBlock potBlock, boolean hasPottedPrefix) {
        String path = hasPottedPrefix ? "block/potted_" : "block/";
        ResourceLocation plantTex = modLoc(path + name(potBlock.getPotted()));
        simpleBlock(potBlock, models().singleTexture(name(potBlock), mcLoc("block/flower_pot_cross"), "plant", plantTex).renderType(CUTOUT));
    }
    protected void simpleFlowerPot(FlowerPotBlock potBlock) {
        simpleFlowerPot(potBlock, false);
    }

    protected void hangingSignBlock(Block pCeiling, Block pWall, ResourceLocation texture) {
        ModelFile sign = models().sign(name(pCeiling), texture);
        simpleBlock(pCeiling, sign);
        simpleBlock(pWall, sign);
    }

    protected void berryBush(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(BerryBushBlock.AGE);
            ResourceLocation texture = modLoc("block/" + name(block) + "_stage" + age);
            return ConfiguredModel.builder()
                    .modelFile(models().cross(name(block) + "_stage" + age, texture).renderType(CUTOUT))
                    .build();
        });
    }

    protected void willowBranches(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            boolean tip = state.getValue(WillowBranchesBlock.TIP);
            String name = tip ? name(block) + "_tip" : name(block);
            ResourceLocation texture = modLoc("block/" + name);
            return ConfiguredModel.builder()
                    .modelFile(tintedCross(name, texture).renderType(CUTOUT))
                    .build();
        });
    }

    // States Only (For the blocks that have custom models)
    protected void simpleStateBlock(Block block) {
        getVariantBuilder(block).partialState().setModels(
                new ConfiguredModel(
                        new ModelFile.UncheckedModelFile(modLoc("block/" + name(block)))
                )
        );
    }
    protected void simpleDirectionalBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block)));
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) direction.toYRot() + 180) % 360)
                    .build();
        });
    }
    protected void simpleDoubleBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            boolean isBottom = half == DoubleBlockHalf.LOWER;
            ModelFile bottom = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_bottom"));
            ModelFile top = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_top"));
            ModelFile model = isBottom ? bottom : top;
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }
    protected void directionalDoubleBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            boolean isBottom = half == DoubleBlockHalf.LOWER;
            ModelFile bottom = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_bottom"));
            ModelFile top = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_top"));
            ModelFile model = isBottom ? bottom : top;
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) direction.toYRot() + 180) % 360)
                    .build();
        });
    }
    protected void horizontalDoubleBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            HorizontalDoubleBlockHalf half = state.getValue(ModBlockStateProperties.HORIZONTAL_HALF);
            boolean isLeft = half == HorizontalDoubleBlockHalf.LEFT;
            ModelFile left = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_left"));
            ModelFile right = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_right"));
            ModelFile model = isLeft ? left : right;
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) direction.toYRot() + 180) % 360)
                    .build();
        });
    }
    protected void waterLilyBlock(Block block) {
        ModelFile modelFile = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block)));
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int yRot = (int) ((direction.toYRot() + 180) % 360);
            builder.partialState().addModels(ConfiguredModel.builder().modelFile(modelFile).rotationY(yRot).build());
        }
    }
    protected void petalBlock(Block block) {
        ModelFile part1 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_1"));
        ModelFile part2 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_2"));
        ModelFile part3 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_3"));
        ModelFile part4 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_4"));
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int yRot = (int) ((direction.toYRot() + 180) % 360);
            builder.part().modelFile(part1).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end();
            builder.part().modelFile(part2).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end();
            builder.part().modelFile(part3).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end();
            builder.part().modelFile(part4).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 4).end();
        }
    }
    protected void woodSorrelsBlock(Block block) {
        ModelFile[] part1 = suffixForModels(getAlternatives(block, 4), "_1");
        ModelFile[] part2 = suffixForModels(getAlternatives(block, 4), "_2");
        ModelFile[] part3 = suffixForModels(getAlternatives(block, 4), "_3");
        ModelFile[] part4 = suffixForModels(getAlternatives(block, 4), "_4");
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int yRot = (int) ((direction.toYRot() + 180) % 360);
            addAlternativeModels(builder, part1, yRot)
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end();
            addAlternativeModels(builder, part2, yRot)
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end();
            addAlternativeModels(builder, part3, yRot)
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end();
            addAlternativeModels(builder, part4, yRot)
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(BlockStateProperties.FLOWER_AMOUNT, 4).end();
        }
    }
    protected void leafLitterBlock(Block block) {
        ModelFile part1 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_1"));
        ModelFile part2 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_2"));
        ModelFile part3 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_3"));
        ModelFile part4 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block) + "_4"));
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int yRot = (int) ((direction.toYRot() + 180) % 360);
            builder.part().modelFile(part1).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(ModBlockStateProperties.SEGMENT_AMOUNT, 1, 2, 3, 4).end();
            builder.part().modelFile(part2).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(ModBlockStateProperties.SEGMENT_AMOUNT, 2, 3, 4).end();
            builder.part().modelFile(part3).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(ModBlockStateProperties.SEGMENT_AMOUNT, 3, 4).end();
            builder.part().modelFile(part4).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .condition(ModBlockStateProperties.SEGMENT_AMOUNT, 4).end();
        }
    }

    @SuppressWarnings("SameParameterValue")
    private ModelFile[] getAlternatives(Block block, int variants) {
        ModelFile[] models = new ModelFile[variants];
        for (int i = 0; i < variants; i++) {
            String name = i == 0
                    ? "block/" + name(block)
                    : i == 1 ? "block/" + name(block) + "_alt" : "block/" + name(block) + "_alt" + (i - 1);
            models[i] = new ModelFile.UncheckedModelFile(modLoc(name));
        }
        return models;
    }

    private ModelFile[] suffixForModels(ModelFile[] models, String suffix) {
        ModelFile[] newModels = new ModelFile[models.length];
        for (int i = 0; i < models.length ; i++) {
            newModels[i] = new ModelFile.UncheckedModelFile(modLoc(models[i].getUncheckedLocation().getPath() + suffix));
        }
        return newModels;
    }

    private MultiPartBlockStateBuilder.PartBuilder addAlternativeModels(MultiPartBlockStateBuilder builder, ModelFile[] models, int yRot) {
        ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> builderBuilder = builder.part();
        for (int i = 0; i < models.length; i++) {
            builderBuilder = i < models.length - 1
                    ? builderBuilder.modelFile(models[i]).rotationY(yRot).nextModel()
                    : builderBuilder.modelFile(models[i]).rotationY(yRot);
        }
        return builderBuilder.addModel();
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
