package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.entity.PapercuttingBlockEntity;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.Arrays;

public class PapercuttingBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<PapercuttingBlock> CODEC = simpleCodec(PapercuttingBlock::new);
    protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    public static final Lazy<Block[]> ALL_BLOCKS = Lazy.of(() -> new Block[] {
            ModBlocks.WHITE_PAPERCUTTING.get(),
            ModBlocks.ORANGE_PAPERCUTTING.get(),
            ModBlocks.MAGENTA_PAPERCUTTING.get(),
            ModBlocks.LIGHT_BLUE_PAPERCUTTING.get(),
            ModBlocks.YELLOW_PAPERCUTTING.get(),
            ModBlocks.LIME_PAPERCUTTING.get(),
            ModBlocks.PINK_PAPERCUTTING.get(),
            ModBlocks.GRAY_PAPERCUTTING.get(),
            ModBlocks.LIGHT_GRAY_PAPERCUTTING.get(),
            ModBlocks.CYAN_PAPERCUTTING.get(),
            ModBlocks.PURPLE_PAPERCUTTING.get(),
            ModBlocks.BLUE_PAPERCUTTING.get(),
            ModBlocks.BROWN_PAPERCUTTING.get(),
            ModBlocks.GREEN_PAPERCUTTING.get(),
            ModBlocks.RED_PAPERCUTTING.get(),
            ModBlocks.BLACK_PAPERCUTTING.get(),
    });
    public static final ItemLike[] ALL_ITEMS = new ItemLike[] {
            ModItems.WHITE_PAPERCUTTING,
            ModItems.LIGHT_GRAY_PAPERCUTTING,
            ModItems.GRAY_PAPERCUTTING,
            ModItems.BLACK_PAPERCUTTING,
            ModItems.BROWN_PAPERCUTTING,
            ModItems.RED_PAPERCUTTING,
            ModItems.ORANGE_PAPERCUTTING,
            ModItems.YELLOW_PAPERCUTTING,
            ModItems.LIME_PAPERCUTTING,
            ModItems.GREEN_PAPERCUTTING,
            ModItems.CYAN_PAPERCUTTING,
            ModItems.LIGHT_BLUE_PAPERCUTTING,
            ModItems.BLUE_PAPERCUTTING,
            ModItems.PURPLE_PAPERCUTTING,
            ModItems.MAGENTA_PAPERCUTTING,
            ModItems.PINK_PAPERCUTTING
    };

    public PapercuttingBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<PapercuttingBlock> codec() {
        return CODEC;
    }

    public static boolean isPapercutting(Item item) {
        return Arrays.stream(ALL_ITEMS).anyMatch(i -> item == i.asItem());
    }

    public static ItemLike itemByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_PAPERCUTTING;
            case ORANGE -> ModItems.ORANGE_PAPERCUTTING;
            case MAGENTA -> ModItems.MAGENTA_PAPERCUTTING;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_PAPERCUTTING;
            case YELLOW -> ModItems.YELLOW_PAPERCUTTING;
            case LIME -> ModItems.LIME_PAPERCUTTING;
            case PINK -> ModItems.PINK_PAPERCUTTING;
            case GRAY -> ModItems.GRAY_PAPERCUTTING;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_PAPERCUTTING;
            case CYAN -> ModItems.CYAN_PAPERCUTTING;
            case PURPLE -> ModItems.PURPLE_PAPERCUTTING;
            case BLUE -> ModItems.BLUE_PAPERCUTTING;
            case BROWN -> ModItems.BROWN_PAPERCUTTING;
            case GREEN -> ModItems.GREEN_PAPERCUTTING;
            case RED -> ModItems.RED_PAPERCUTTING;
            case BLACK -> ModItems.BLACK_PAPERCUTTING;
        };
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            default -> EAST_AABB;
        };
    }

    private boolean canAttachTo(BlockGetter pBlockReader, BlockPos pPos, Direction pDirection) {
        BlockState blockstate = pBlockReader.getBlockState(pPos);
        return blockstate.isFaceSturdy(pBlockReader, pPos, pDirection);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        Direction direction = pState.getValue(FACING);
        return this.canAttachTo(pLevel, pPos.relative(direction.getOpposite()), direction);
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.getOpposite() == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (!pContext.replacingClickedOnBlock()) {
            BlockState state = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(pContext.getClickedFace().getOpposite()));
            if (state.is(this) && state.getValue(FACING) == pContext.getClickedFace()) {
                return null;
            }
        }
        BlockState state = this.defaultBlockState();
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        for (Direction direction : pContext.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction.getOpposite());
                if (state.canSurvive(levelreader, blockpos)) {
                    return state;
                }
            }
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return pLevel.getBlockEntity(pPos) instanceof PapercuttingBlockEntity blockEntity
                ? blockEntity.getItem()
                : super.getCloneItemStack(pLevel, pPos, pState);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PapercuttingBlockEntity(pPos, pState);
    }
}
