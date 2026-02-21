package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.common.block.natural.BerryBushBlock;
import com.ChalkerCharles.morecolorful.common.block.natural.WillowBranchesBlock;
import com.ChalkerCharles.morecolorful.common.block.natural.WindFlowerBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.HorizontalHalf;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.RibbonState;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public abstract class ModBlockStateHelper extends BlockStateProvider {
    private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");

    public ModBlockStateHelper(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    // States and Models
    protected void simpleCube(Supplier<? extends Block> block) {
        Block b = block.get();
        simpleBlockWithItem(b, cubeAll(b));
    }

    protected void simpleStairs(Supplier<? extends StairBlock> block, Supplier<? extends Block> baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock.get());
        StairBlock stair = block.get();
        stairsBlock(stair, tex);
        simpleBlockItem(stair, models().stairs(name(stair), tex, tex, tex));
    }

    protected void simpleSlab(Supplier<? extends SlabBlock> block, Supplier<? extends Block> baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock.get());
        SlabBlock slab = block.get();
        slabBlock(slab, tex, tex);
        simpleBlockItem(slab, models().slab(name(slab), tex, tex, tex));
    }

    protected void logAndWood(Supplier<? extends RotatedPillarBlock> log, Supplier<? extends RotatedPillarBlock> wood) {
        RotatedPillarBlock l = log.get(), w = wood.get();
        logBlock(l);
        ResourceLocation sideTex = blockTexture(l);
        axisBlock(w, sideTex, sideTex);
        simpleBlockItem(l, models().cubeColumn(name(l), sideTex, modLoc("block/" + name(l) + "_top")));
        simpleBlockItem(w, models().cubeColumn(name(w), sideTex, sideTex));
    }

    protected void fenceBlock(Supplier<? extends FenceBlock> block, Supplier<? extends Block> baseBlock) {
        this.fenceBlock(block.get(), blockTexture(baseBlock.get()));
    }

    protected void simpleFenceGate(Supplier<? extends FenceGateBlock> block, Supplier<? extends Block> baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock.get());
        FenceGateBlock fenceGate = block.get();
        fenceGateBlock(fenceGate, tex);
        simpleBlockItem(fenceGate, models().fenceGate(name(fenceGate), tex));
    }

    protected void simpleDoor(Supplier<? extends DoorBlock> block, boolean cutout) {
        DoorBlock door = block.get();
        if (cutout) {
            doorBlockWithRenderType(door, modLoc("block/" + name(door) + "_bottom"), modLoc("block/" + name(door) + "_top"), CUTOUT);
        } else {
            doorBlock(door, modLoc("block/" + name(door) + "_bottom"), modLoc("block/" + name(door) + "_top"));
        }
    }

    protected void simpleTrapdoor(Supplier<? extends TrapDoorBlock> block, boolean orientable, boolean cutout) {
        TrapDoorBlock trapDoor = block.get();
        if (cutout) {
            trapdoorBlockWithRenderType(trapDoor, modLoc("block/" + name(trapDoor)), orientable, CUTOUT);
            if (orientable) {
                simpleBlockItem(trapDoor, models().trapdoorOrientableBottom(name(trapDoor) + "_bottom", modLoc("block/" + name(trapDoor))).renderType(CUTOUT));
            } else {
                simpleBlockItem(trapDoor, models().trapdoorBottom(name(trapDoor) + "_bottom", modLoc("block/" + name(trapDoor))).renderType(CUTOUT));
            }
        } else {
            trapdoorBlock(trapDoor, modLoc("block/" + name(trapDoor)), orientable);
            if (orientable) {
                simpleBlockItem(trapDoor, models().trapdoorOrientableBottom(name(trapDoor) + "_bottom", modLoc("block/" + name(trapDoor))));
            } else {
                simpleBlockItem(trapDoor, models().trapdoorBottom(name(trapDoor) + "_bottom", modLoc("block/" + name(trapDoor))));
            }
        }
    }

    protected void simplePressurePlate(Supplier<? extends PressurePlateBlock> block, Supplier<? extends Block> baseBlock) {
        ResourceLocation tex = blockTexture(baseBlock.get());
        PressurePlateBlock pressurePlate = block.get();
        pressurePlateBlock(pressurePlate, tex);
        simpleBlockItem(pressurePlate, models().pressurePlate(name(pressurePlate), tex));
    }

    protected void buttonBlock(Supplier<? extends ButtonBlock> block, Supplier<? extends Block> baseBlock) {
        this.buttonBlock(block.get(), blockTexture(baseBlock.get()));
    }

    public void signBlock(Supplier<? extends StandingSignBlock> signBlock, Supplier<? extends WallSignBlock> wallSignBlock, Supplier<? extends Block> baseBlock) {
        this.signBlock(signBlock.get(), wallSignBlock.get(), blockTexture(baseBlock.get()));
    }

    protected void hangingSignBlock(Supplier<? extends Block> ceiling, Supplier<? extends Block> wall, Supplier<? extends Block> baseBlock) {
        Block b = ceiling.get();
        ModelFile sign = models().sign(name(b), blockTexture(baseBlock.get()));
        simpleBlock(b, sign);
        simpleBlock(wall.get(), sign);
    }

    protected void leaves(Supplier<? extends Block> block) {
        Block b = block.get();
        simpleBlockWithItem(b, models().leaves(name(b), blockTexture(b)));
    }

    protected void cross(Supplier<? extends Block> block, boolean isTinted) {
        Block b = block.get();
        ResourceLocation texture = modLoc("block/" + name(b));
        if (isTinted) {
            simpleBlock(b, tintedCross(name(b), texture).renderType(CUTOUT));
        } else {
            simpleBlock(b, models().cross(name(b), texture).renderType(CUTOUT));
        }
    }

    protected void cross(Supplier<? extends Block> block) {
        cross(block, false);
    }

    protected void alternativeCross(Supplier<? extends Block> block) {
        Block b = block.get();
        ResourceLocation tex1 = modLoc("block/" + name(b));
        ResourceLocation tex2 = modLoc("block/" + name(b) + "_alt");
        simpleBlock(b,
                new ConfiguredModel(models().cross(name(b), tex1).renderType(CUTOUT)),
                new ConfiguredModel(models().cross(name(b) + "_alt", tex2).renderType(CUTOUT))
        );
    }

    protected void doubleCross(Supplier<? extends Block> block, boolean isTinted) {
        Block b = block.get();
        ResourceLocation top = modLoc("block/" + name(b) + "_top");
        ResourceLocation bottom = modLoc("block/" + name(b) + "_bottom");
        if (isTinted) {
            getVariantBuilder(b)
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .addModels(new ConfiguredModel(tintedCross(name(b) + "_bottom", bottom).renderType(CUTOUT)))
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .addModels(new ConfiguredModel(tintedCross(name(b) + "_top", top).renderType(CUTOUT)));
        } else {
            getVariantBuilder(b)
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .addModels(new ConfiguredModel(models().cross(name(b) + "_bottom", bottom).renderType(CUTOUT)))
                    .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .addModels(new ConfiguredModel(models().cross(name(b) + "_top", top).renderType(CUTOUT)));
        }
    }

    protected void doubleCross(Supplier<? extends Block> block) {
        doubleCross(block, false);
    }

    protected void simpleFlowerPot(Supplier<? extends FlowerPotBlock> potBlock, boolean hasPottedPrefix) {
        String path = hasPottedPrefix ? "block/potted_" : "block/";
        FlowerPotBlock pot = potBlock.get();
        ResourceLocation plantTex = modLoc(path + name(pot.getPotted()));
        simpleBlock(pot, models().singleTexture(name(pot), mcLoc("block/flower_pot_cross"), "plant", plantTex).renderType(CUTOUT));
    }

    protected void simpleFlowerPot(Supplier<? extends FlowerPotBlock> potBlock) {
        simpleFlowerPot(potBlock, false);
    }

    protected void berryBush(Supplier<? extends Block> block) {
        Block b = block.get();
        getVariantBuilder(b).forAllStates(state -> {
            int age = state.getValue(BerryBushBlock.AGE);
            ResourceLocation texture = modLoc("block/" + name(b) + "_stage" + age);
            return ConfiguredModel.builder()
                    .modelFile(models().cross(name(b) + "_stage" + age, texture).renderType(CUTOUT))
                    .build();
        });
    }

    protected void willowBranches(Supplier<? extends Block> block) {
        Block b = block.get();
        getVariantBuilder(b).forAllStates(state -> {
            boolean tip = state.getValue(WillowBranchesBlock.TIP);
            String name = tip ? name(b) + "_tip" : name(b);
            ResourceLocation texture = modLoc("block/" + name);
            return ConfiguredModel.builder()
                    .modelFile(tintedCross(name, texture).renderType(CUTOUT))
                    .build();
        });
    }

    protected void windflower(Supplier<? extends Block> block) {
        Block b = block.get();
        getVariantBuilder(b).forAllStates(state -> {
            int windLevel = state.getValue(WindFlowerBlock.WIND_LEVEL);
            String name = windLevel == 0 ? name(b) : name(b) + '_' + windLevel;
            ResourceLocation texture = modLoc("block/" + name);
            return ConfiguredModel.builder()
                    .modelFile(models().cross(name, texture).renderType(CUTOUT))
                    .build();
        });
    }

    protected void pottedWindflower(Supplier<? extends FlowerPotBlock> block) {
        FlowerPotBlock pot = block.get();
        Block potted = pot.getPotted();
        getVariantBuilder(pot).forAllStates(state -> {
            int windLevel = state.getValue(WindFlowerBlock.WIND_LEVEL);
            String name = windLevel == 0 ? name(pot) : name(pot) + '_' + windLevel;
            String flower = windLevel == 0? name(potted) : name(potted) + '_' + windLevel;
            ResourceLocation texture = modLoc("block/" + flower);
            return ConfiguredModel.builder()
                    .modelFile(models().singleTexture(name, mcLoc("block/flower_pot_cross"), "plant", texture).renderType(CUTOUT))
                    .build();
        });
    }

    private static final List<Pair<String, RibbonState[]>> ribbonHelper = List.of(
            Pair.of("_bow", RibbonState.arrayOf(RibbonState::hasBow)),
            Pair.of("_def", ArrayUtils.toArray(RibbonState.DEFAULT, RibbonState.UP)),
            Pair.of("_up", RibbonState.arrayOf(r -> r.connectUp && r.hasBow())),
            Pair.of("_down", ArrayUtils.toArray(RibbonState.DOWN, RibbonState.VERTICAL)),
            Pair.of("_left", ArrayUtils.toArray(RibbonState.LEFT, RibbonState.UP_LEFT)),
            Pair.of("_right", ArrayUtils.toArray(RibbonState.RIGHT, RibbonState.UP_RIGHT)),
            Pair.of("_vert", ArrayUtils.toArray(RibbonState.VERTICAL_CONNECT)),
            Pair.of("_hor", ArrayUtils.toArray(RibbonState.HORIZONTAL, RibbonState.UP_HORIZONTAL)),
            Pair.of("_down_l", ArrayUtils.toArray(RibbonState.DOWN_LEFT, RibbonState.VERTICAL_LEFT)),
            Pair.of("_down_r", ArrayUtils.toArray(RibbonState.DOWN_RIGHT, RibbonState.VERTICAL_RIGHT)),
            Pair.of("_down_h", ArrayUtils.toArray(RibbonState.DOWN_HORIZONTAL, RibbonState.CROSS)),
            Pair.of("_tip", ArrayUtils.toArray(RibbonState.TIP)),
            Pair.of("_hor_c", ArrayUtils.toArray(RibbonState.HORIZONTAL_CONNECT)),
            Pair.of("_cross", ArrayUtils.toArray(RibbonState.CROSS_CONNECT))
    );

    protected void ribbon(Block block) {
        String name = name(block);
        String key = "texture";
        ResourceLocation template = modLoc("block/template_ribbon");
        List<Pair<ModelFile, RibbonState[]>> list = new ArrayList<>(ribbonHelper.size());
        for (Pair<String, RibbonState[]> pair : ribbonHelper) {
            String s = pair.left();
            RibbonState[] ribbonStates = pair.right();
            ResourceLocation texture = modLoc("block/" + name + s);
            ModelFile file = models().withExistingParent(name + s, template).texture(key, texture);
            list.add(Pair.of(file, ribbonStates));
        }
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int yRot = (int) ((direction.toYRot() + 180) % 360);
            for (Pair<ModelFile, RibbonState[]> pair : list) {
                ModelFile file = pair.left();
                RibbonState[] ribbonStates = pair.right();
                builder.part().modelFile(file).rotationY(yRot).addModel()
                        .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .condition(ModBlockStateProperties.RIBBON_STATE, ribbonStates).end();
            }
        }
    }

    protected void emptyModelWithParticle(Block block) {
        ModelFile model = models().sign(name(block), blockTexture(block).withSuffix("_particle"));
        simpleBlock(block, model);
    }

    // States Only (For the blocks that have custom models)
    protected void simpleStateBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        simpleBlock(b, new ModelFile.UncheckedModelFile(modLoc("block/" + name(b))));
    }
    protected void simpleDirectionalBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        getVariantBuilder(b).forAllStates(state -> {
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b)));
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) direction.toYRot() + 180) % 360)
                    .build();
        });
    }
    protected void simpleDoubleBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        getVariantBuilder(b).forAllStates(state -> {
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            boolean isBottom = half == DoubleBlockHalf.LOWER;
            ModelFile bottom = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_bottom"));
            ModelFile top = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_top"));
            ModelFile model = isBottom ? bottom : top;
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }
    protected void directionalDoubleBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        getVariantBuilder(b).forAllStates(state -> {
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            boolean isBottom = half == DoubleBlockHalf.LOWER;
            ModelFile bottom = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_bottom"));
            ModelFile top = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_top"));
            ModelFile model = isBottom ? bottom : top;
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) direction.toYRot() + 180) % 360)
                    .build();
        });
    }
    protected void horizontalDoubleBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        getVariantBuilder(b).forAllStates(state -> {
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            HorizontalHalf half = state.getValue(ModBlockStateProperties.HORIZONTAL_HALF);
            boolean isLeft = half == HorizontalHalf.LEFT;
            ModelFile left = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_left"));
            ModelFile right = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_right"));
            ModelFile model = isLeft ? left : right;
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) direction.toYRot() + 180) % 360)
                    .build();
        });
    }
    protected void waterLilyBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        ModelFile modelFile = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b)));
        VariantBlockStateBuilder builder = getVariantBuilder(b);
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int yRot = (int) ((direction.toYRot() + 180) % 360);
            builder.partialState().addModels(ConfiguredModel.builder().modelFile(modelFile).rotationY(yRot).build());
        }
    }
    protected void petalBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        ModelFile part1 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_1"));
        ModelFile part2 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_2"));
        ModelFile part3 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_3"));
        ModelFile part4 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_4"));
        MultiPartBlockStateBuilder builder = getMultipartBuilder(b);
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
    protected void woodSorrelsBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        ModelFile[] part1 = suffixForModels(getAlternatives(b, 4), "_1");
        ModelFile[] part2 = suffixForModels(getAlternatives(b, 4), "_2");
        ModelFile[] part3 = suffixForModels(getAlternatives(b, 4), "_3");
        ModelFile[] part4 = suffixForModels(getAlternatives(b, 4), "_4");
        MultiPartBlockStateBuilder builder = getMultipartBuilder(b);
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
    protected void leafLitterBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        ModelFile part1 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_1"));
        ModelFile part2 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_2"));
        ModelFile part3 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_3"));
        ModelFile part4 = new ModelFile.UncheckedModelFile(modLoc("block/" + name(b) + "_4"));
        MultiPartBlockStateBuilder builder = getMultipartBuilder(b);
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

    private BlockModelBuilder tintedCross(String name, ResourceLocation texture) {
        return models().singleTexture(name, mcLoc("block/tinted_cross"), "cross", texture);
    }

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

    private static MultiPartBlockStateBuilder.PartBuilder addAlternativeModels(MultiPartBlockStateBuilder builder, ModelFile[] models, int yRot) {
        ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> builderBuilder = builder.part();
        for (int i = 0; i < models.length; i++) {
            builderBuilder = i < models.length - 1
                    ? builderBuilder.modelFile(models[i]).rotationY(yRot).nextModel()
                    : builderBuilder.modelFile(models[i]).rotationY(yRot);
        }
        return builderBuilder.addModel();
    }

    private static ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private static String name(Block block) {
        return key(block).getPath();
    }
}
