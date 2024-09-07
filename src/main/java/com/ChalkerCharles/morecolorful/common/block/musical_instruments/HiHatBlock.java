package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.entity.HiHatBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HiHatBlock extends PercussionInstrumentBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HIT = ModBlockStateProperties.HIT;
    private static final VoxelShape HIHAT = Shapes.or(
            Block.box(7.0, 15.5, 7.0, 9.0, 16.5, 9.0),
            Block.box(7.0, 12.5, 7.0, 9.0, 14.5, 9.0),
            Block.box(7.0, 2.5, 7.0, 9.0, 4.5, 9.0),
            Block.box(7.7, 0.0, 7.7, 8.3, 18.0, 8.3),
            Block.box(3.0, 14.5, 3.0, 13.0, 15.5, 13.0));
    private static final VoxelShape PEDAL_NORTH = Block.box(7.0, 0.0, 3.0, 9.0, 0.5, 8.0);
    private static final VoxelShape PEDAL_EAST = Block.box(8.0, 0.0, 7.0, 13.0, 0.5, 9.0);
    private static final VoxelShape PEDAL_SOUTH = Block.box(7.0, 0.0, 8.0, 9.0, 0.5, 13.0);
    private static final VoxelShape PEDAL_WEST = Block.box(3.0, 0.0, 7.0, 8.0, 0.5, 9.0);
    private static final VoxelShape HIHAT_NORTH = Shapes.or(HIHAT, PEDAL_NORTH);
    private static final VoxelShape HIHAT_EAST = Shapes.or(HIHAT, PEDAL_EAST);
    private static final VoxelShape HIHAT_SOUTH = Shapes.or(HIHAT, PEDAL_SOUTH);
    private static final VoxelShape HIHAT_WEST = Shapes.or(HIHAT, PEDAL_WEST);

    public HiHatBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HIT, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (direction){
            case EAST -> HIHAT_EAST;
            case SOUTH -> HIHAT_SOUTH;
            case WEST -> HIHAT_WEST;
            default -> HIHAT_NORTH;
        };
    }
    @Override
    protected boolean canSurvive( BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportRigidBlock(pLevel, pPos.below());
    }
    @Override
    protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return Direction.DOWN == pDirection && !this.canSurvive(pState, pLevel, pPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    protected BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    @SuppressWarnings("deprecation")
    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HIT, FACING);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HiHatBlockEntity(pPos, pState);
    }
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == ModBlockEntities.HIHAT.get() ? HiHatBlockEntity::tick : null;
    }
}
