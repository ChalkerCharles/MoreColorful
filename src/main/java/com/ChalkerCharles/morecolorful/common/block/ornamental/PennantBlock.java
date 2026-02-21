package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.entity.PennantBlockEntity;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public class PennantBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PennantBlock> CODEC = simpleCodec(PennantBlock::new);
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    public static final Lazy<Block[]> ALL_BLOCKS = Lazy.of(() -> new Block[] {
            ModBlocks.WHITE_PENNANT.get(),
            ModBlocks.ORANGE_PENNANT.get(),
            ModBlocks.MAGENTA_PENNANT.get(),
            ModBlocks.LIGHT_BLUE_PENNANT.get(),
            ModBlocks.YELLOW_PENNANT.get(),
            ModBlocks.LIME_PENNANT.get(),
            ModBlocks.PINK_PENNANT.get(),
            ModBlocks.GRAY_PENNANT.get(),
            ModBlocks.LIGHT_GRAY_PENNANT.get(),
            ModBlocks.CYAN_PENNANT.get(),
            ModBlocks.PURPLE_PENNANT.get(),
            ModBlocks.BLUE_PENNANT.get(),
            ModBlocks.BROWN_PENNANT.get(),
            ModBlocks.GREEN_PENNANT.get(),
            ModBlocks.RED_PENNANT.get(),
            ModBlocks.BLACK_PENNANT.get()
    });
    public static final ItemLike[] ALL_ITEMS = new ItemLike[] {
            ModItems.WHITE_PENNANT,
            ModItems.LIGHT_GRAY_PENNANT,
            ModItems.GRAY_PENNANT,
            ModItems.BLACK_PENNANT,
            ModItems.BROWN_PENNANT,
            ModItems.RED_PENNANT,
            ModItems.ORANGE_PENNANT,
            ModItems.YELLOW_PENNANT,
            ModItems.LIME_PENNANT,
            ModItems.GREEN_PENNANT,
            ModItems.CYAN_PENNANT,
            ModItems.LIGHT_BLUE_PENNANT,
            ModItems.BLUE_PENNANT,
            ModItems.PURPLE_PENNANT,
            ModItems.MAGENTA_PENNANT,
            ModItems.PINK_PENNANT
    };

    public PennantBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ROTATION, 0)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public static ItemLike itemByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_PENNANT;
            case ORANGE -> ModItems.ORANGE_PENNANT;
            case MAGENTA -> ModItems.MAGENTA_PENNANT;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_PENNANT;
            case YELLOW -> ModItems.YELLOW_PENNANT;
            case LIME -> ModItems.LIME_PENNANT;
            case PINK -> ModItems.PINK_PENNANT;
            case GRAY -> ModItems.GRAY_PENNANT;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_PENNANT;
            case CYAN -> ModItems.CYAN_PENNANT;
            case PURPLE -> ModItems.PURPLE_PENNANT;
            case BLUE -> ModItems.BLUE_PENNANT;
            case BROWN -> ModItems.BROWN_PENNANT;
            case GREEN -> ModItems.GREEN_PENNANT;
            case RED -> ModItems.RED_PENNANT;
            case BLACK -> ModItems.BLACK_PENNANT;
        };
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState()
                .setValue(ROTATION, RotationSegment.convertToSegment(pContext.getRotation() - 90.0F))
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isFaceSturdy(pLevel, pPos, Direction.UP, SupportType.CENTER);
    }

    @Override
    protected BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(ROTATION, pRot.rotate(pState.getValue(ROTATION), 16));
    }

    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.setValue(ROTATION, pMirror.mirror(pState.getValue(ROTATION), 16));
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ROTATION, WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PennantBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PENNANT.get(), PennantBlockEntity::tick);
    }
}
