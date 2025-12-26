package com.ChalkerCharles.morecolorful.common.block.natural;

import com.ChalkerCharles.morecolorful.common.block.properties.HangingBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.MultipartBlock;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WillowBranchesBlock extends Block implements BonemealableBlock, WindSensitive, HangingBlock, MultipartBlock {
    private static final VoxelShape TIP_SHAPE = Block.box(0.0, 2.0, 0.0, 16.0, 16.0, 16.0);
    public static final BooleanProperty TIP = ModBlockStateProperties.TIP;
    public WillowBranchesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP, true));
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(TIP) ? TIP_SHAPE : super.getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return this.canStayAtPosition(pLevel, pPos);
    }

    private boolean canStayAtPosition(BlockGetter level, BlockPos pos) {
        BlockPos blockpos = pos.relative(Direction.UP);
        BlockState blockstate = level.getBlockState(blockpos);
        return MultifaceBlock.canAttachTo(level, Direction.UP, blockpos, blockstate) || blockstate.is(this);
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!this.canStayAtPosition(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        return pState.setValue(TIP, !pLevel.getBlockState(pCurrentPos.below()).is(this));
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!this.canStayAtPosition(pLevel, pPos)) {
            pLevel.destroyBlock(pPos, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TIP);
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    public BlockPos getTip(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

        BlockState blockstate;
        do {
            mutableBlockPos.move(Direction.DOWN);
            blockstate = level.getBlockState(mutableBlockPos);
        } while (blockstate.is(this));

        return mutableBlockPos.relative(Direction.UP).immutable();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return this.canGrowInto(pLevel.getBlockState(this.getTip(pLevel, pPos).below()));
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlockPos blockpos = this.getTip(pLevel, pPos).below();
        if (this.canGrowInto(pLevel.getBlockState(blockpos))) {
            pLevel.setBlockAndUpdate(blockpos, pState.setValue(TIP, true));
        }
    }
}
