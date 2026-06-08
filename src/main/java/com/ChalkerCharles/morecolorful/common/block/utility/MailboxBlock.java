package com.ChalkerCharles.morecolorful.common.block.utility;

import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.entity.MailboxBlockEntity;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public class MailboxBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<MailboxBlock> CODEC = simpleCodec(MailboxBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 1.0, 13.0, 10.0, 15.0);
    protected static final VoxelShape SHAPE_1 = Block.box(1.0, 0.0, 3.0, 15.0, 10.0, 13.0);
    public static final Lazy<Block[]> ALL_BLOCKS = Lazy.of(() -> new Block[] {
            ModBlocks.OAK_MAILBOX.get(),
            ModBlocks.SPRUCE_MAILBOX.get(),
            ModBlocks.BIRCH_MAILBOX.get(),
            ModBlocks.JUNGLE_MAILBOX.get(),
            ModBlocks.ACACIA_MAILBOX.get(),
            ModBlocks.DARK_OAK_MAILBOX.get(),
            ModBlocks.CRIMSON_MAILBOX.get(),
            ModBlocks.WARPED_MAILBOX.get(),
            ModBlocks.MANGROVE_MAILBOX.get(),
            ModBlocks.CHERRY_MAILBOX.get(),
            ModBlocks.BAMBOO_MAILBOX.get(),
            ModBlocks.CRABAPPLE_MAILBOX.get(),
            ModBlocks.EBONY_MAILBOX.get(),
            ModBlocks.GINKGO_MAILBOX.get(),
            ModBlocks.MAPLE_MAILBOX.get(),
            ModBlocks.FROST_MAILBOX.get(),
            ModBlocks.DAWN_REDWOOD_MAILBOX.get(),
            ModBlocks.JACARANDA_MAILBOX.get(),
            ModBlocks.WILLOW_MAILBOX.get()
    });
    public static final ItemLike[] ALL_ITEMS = new ItemLike[] {
            ModItems.OAK_MAILBOX,
            ModItems.SPRUCE_MAILBOX,
            ModItems.BIRCH_MAILBOX,
            ModItems.JUNGLE_MAILBOX,
            ModItems.ACACIA_MAILBOX,
            ModItems.DARK_OAK_MAILBOX,
            ModItems.CRIMSON_MAILBOX,
            ModItems.WARPED_MAILBOX,
            ModItems.MANGROVE_MAILBOX,
            ModItems.CHERRY_MAILBOX,
            ModItems.BAMBOO_MAILBOX,
            ModItems.CRABAPPLE_MAILBOX,
            ModItems.EBONY_MAILBOX,
            ModItems.GINKGO_MAILBOX,
            ModItems.MAPLE_MAILBOX,
            ModItems.FROST_MAILBOX,
            ModItems.DAWN_REDWOOD_MAILBOX,
            ModItems.JACARANDA_MAILBOX,
            ModItems.WILLOW_MAILBOX
    };

    public MailboxBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(OPEN, false)
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(FACING).getAxis() == Direction.Axis.X) {
            return SHAPE_1;
        }
        return SHAPE;
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof MailboxBlockEntity entity) {
                pPlayer.openMenu(entity);
                pPlayer.awardStat(ModStats.OPEN_MAILBOX.get());
                PiglinAi.angerNearbyPiglins(pPlayer, true);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof MailboxBlockEntity mailbox && pPlacer instanceof Player) {
            mailbox.setOwner(pPlacer.getScoreboardName());
        }
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        Containers.dropContentsOnDestroy(pState, pNewState, pLevel, pPos);
        if (!pState.is(pNewState.getBlock()) && pLevel.getBlockEntity(pPos) instanceof MailboxBlockEntity mailbox) {
            mailbox.removeMailbox(pLevel);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof MailboxBlockEntity entity) {
            entity.recheckOpen();
        }
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN, WATERLOGGED);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
    }

    @Override
    protected BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return this.rotate(pState, pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MailboxBlockEntity(pPos, pState);
    }
}
