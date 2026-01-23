package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.entity.PinwheelBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class PinwheelBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PinwheelBlock> CODEC = simpleCodec(PinwheelBlock::new);
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
    public static final Lazy<Block[]> ALL_TYPES = Lazy.of(() -> new Block[] {
            ModBlocks.WHITE_PINWHEEL.get(),
            ModBlocks.ORANGE_PINWHEEL.get(),
            ModBlocks.MAGENTA_PINWHEEL.get(),
            ModBlocks.LIGHT_BLUE_PINWHEEL.get(),
            ModBlocks.YELLOW_PINWHEEL.get(),
            ModBlocks.LIME_PINWHEEL.get(),
            ModBlocks.PINK_PINWHEEL.get(),
            ModBlocks.GRAY_PINWHEEL.get(),
            ModBlocks.LIGHT_GRAY_PINWHEEL.get(),
            ModBlocks.CYAN_PINWHEEL.get(),
            ModBlocks.PURPLE_PINWHEEL.get(),
            ModBlocks.BLUE_PINWHEEL.get(),
            ModBlocks.BROWN_PINWHEEL.get(),
            ModBlocks.GREEN_PINWHEEL.get(),
            ModBlocks.RED_PINWHEEL.get(),
            ModBlocks.BLACK_PINWHEEL.get(),
            ModBlocks.MULTICOLORED_PINWHEEL.get()
    });

    public PinwheelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ROTATION, 0)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<PinwheelBlock> codec() {
        return CODEC;
    }

    public static boolean isMulticolor(BlockState state) {
        return state.is(ModBlocks.MULTICOLORED_PINWHEEL);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState()
                .setValue(ROTATION, RotationSegment.convertToSegment(pContext.getRotation() + 180.0F))
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
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ROTATION, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PinwheelBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PINWHEEL.get(), PinwheelBlockEntity::tick);
    }
}
