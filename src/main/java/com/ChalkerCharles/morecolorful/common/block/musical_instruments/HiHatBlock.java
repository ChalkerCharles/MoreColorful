package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class HiHatBlock extends PercussionInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HIT = ModBlockStateProperties.HIT;
    protected static final VoxelShape HIHAT = Shapes.or(
            Block.box(7.0, 15.5, 7.0, 9.0, 16.5, 9.0),
            Block.box(7.0, 12.5, 7.0, 9.0, 14.5, 9.0),
            Block.box(7.0, 2.5, 7.0, 9.0, 4.5, 9.0),
            Block.box(7.7, 0.0, 7.7, 8.3, 18.0, 8.3),
            Block.box(3.0, 14.5, 3.0, 13.0, 15.5, 13.0));
    protected static final VoxelShape PEDAL_NORTH = Block.box(7.0, 0.0, 3.0, 9.0, 0.5, 8.0);
    protected static final VoxelShape PEDAL_EAST = Block.box(8.0, 0.0, 7.0, 13.0, 0.5, 9.0);
    protected static final VoxelShape PEDAL_SOUTH = Block.box(7.0, 0.0, 8.0, 9.0, 0.5, 13.0);
    protected static final VoxelShape PEDAL_WEST = Block.box(3.0, 0.0, 7.0, 8.0, 0.5, 9.0);
    protected static final VoxelShape HIHAT_NORTH = Shapes.or(HIHAT, PEDAL_NORTH);
    protected static final VoxelShape HIHAT_EAST = Shapes.or(HIHAT, PEDAL_EAST);
    protected static final VoxelShape HIHAT_SOUTH = Shapes.or(HIHAT, PEDAL_SOUTH);
    protected static final VoxelShape HIHAT_WEST = Shapes.or(HIHAT, PEDAL_WEST);

    public HiHatBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HIT, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (direction){
            case EAST -> HIHAT_EAST;
            case SOUTH -> HIHAT_SOUTH;
            case WEST -> HIHAT_WEST;
            default -> HIHAT_NORTH;
        };
    }
    @Override
    protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportRigidBlock(pLevel, pPos.below());
    }
    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
        return Direction.DOWN == pDirection && !this.canSurvive(pState, pLevel, pPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HIT, FACING);
    }
    @Override
    protected void onPlace(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pOldState, boolean pMovedByPiston) {
        pLevel.scheduleTick(pPos, this, 1);
    }
    @Override
    protected void tick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (!pLevel.isClientSide) {
            pLevel.scheduleTick(pPos, this, 1);
            if (PlayingScreen.isPressing && PlayingScreen.pType == pType) {
                pPos = PlayingScreen.pPos;
                pLevel.setBlock(pPos, pState.setValue(HIT, true), 2);
            } else {
                pLevel.setBlock(pPos, pState.setValue(HIT, false), 2);
            }
        }
    }
}
