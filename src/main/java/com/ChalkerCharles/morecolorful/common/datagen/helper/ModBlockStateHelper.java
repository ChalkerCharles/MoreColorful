package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.properties.HorizontalDoubleBlockHalf;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
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

    protected void leaves(LeavesBlock block) {
        simpleBlockWithItem(block, models().leaves(name(block), blockTexture(block)));
    }

    protected void cross(Block block) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "block/" + name(block));
        simpleBlock(block, models().cross(name(block), texture).renderType(CUTOUT));
    }

    protected void simpleFlowerPot(FlowerPotBlock potBlock, Block plantBlock) {
        ResourceLocation plantTex = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "block/" + name(plantBlock));
        simpleBlock(potBlock, models().singleTexture(name(potBlock), mcLoc("block/flower_pot_cross"), "plant", plantTex).renderType(CUTOUT));
    }

    protected void hangingSignBlock(CeilingHangingSignBlock pCeiling, WallHangingSignBlock pWall, ResourceLocation texture) {
        ModelFile sign = models().sign(name(pCeiling), texture);
        simpleBlock(pCeiling, sign);
        simpleBlock(pWall, sign);
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
    protected void petalBlock(PinkPetalsBlock block) {
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

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
