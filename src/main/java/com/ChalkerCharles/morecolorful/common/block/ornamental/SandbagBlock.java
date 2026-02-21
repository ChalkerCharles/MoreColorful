package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.entity.EntityUtils;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.ChalkerCharles.morecolorful.common.entity.misc.SandbagEntity;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SandbagBlock extends FallingBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<SandbagBlock> CODEC = simpleCodec(SandbagBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

    public SandbagBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<SandbagBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        return !pLevel.isClientSide() ? bindBalloons(pPlayer, pLevel, pPos, pState) : InteractionResult.PASS;
    }

    public static InteractionResult bindBalloons(Player player, Level level, BlockPos pos, BlockState state) {
        SandbagEntity sandbag = null;
        boolean tied = false;
        List<Balloon> list = EntityUtils.getTiedBalloons(player);

        for (Balloon balloon : list) {
            if (sandbag == null) {
                sandbag = SandbagEntity.getOrCreate(level, pos, state);
                sandbag.playSound(ModSounds.LEAD_TIED.get());
            }
            if (Config.enhancedLeash) {
                if (ILeashableExtension.canHaveALeashAttachedTo(balloon, sandbag)) {
                    balloon.setLeashedTo(sandbag, true);
                    tied = true;
                }
            } else {
                balloon.setLeashedTo(sandbag, true);
            }

        }
        if (Config.enhancedLeash ? tied : !list.isEmpty()) {
            level.gameEvent(GameEvent.BLOCK_ATTACH, pos, GameEvent.Context.of(player));
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            SandbagEntity sandbag = SandbagEntity.getOrCreate(level, pos, state);
            sandbag.startFall(level, pos, state);
            this.falling(sandbag);
        }
    }

    @Override
    protected void falling(FallingBlockEntity fallingEntity) {
        fallingEntity.setHurtsEntities(1.0F, 40);
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {
        ((SandbagEntity) fallingBlock).setFalling(false);
        fallingBlock.playSound(ModSounds.SANDBAG_LAND.get());
    }

    public int getDustColor(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getMapColor(pLevel, pPos).col;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }
}
