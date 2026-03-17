package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.RibbonState;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.Lazy;

import javax.annotation.Nullable;

public class RibbonBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<RibbonBlock> CODEC =  RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    propertiesCodec(),
                    DyeColor.CODEC.fieldOf("color").forGetter(RibbonBlock::color)
            ).apply(instance, RibbonBlock::new)
    );
    public static final EnumProperty<RibbonState> RIBBON_STATE = ModBlockStateProperties.RIBBON_STATE;
    public static final BooleanProperty AUTO_CONNECT = ModBlockStateProperties.AUTO_CONNECT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final VoxelShape WEST_LOW = Block.box(0.0, 0.0, 0.0, 1.0, 5.0, 16.0);
    private static final VoxelShape EAST_LOW = Block.box(15.0, 0.0, 0.0, 16.0, 5.0, 16.0);
    private static final VoxelShape NORTH_LOW = Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 1.0);
    private static final VoxelShape SOUTH_LOW = Block.box(0.0, 0.0, 15.0, 16.0, 5.0, 16.0);
    private static final VoxelShape WEST_THIN = Block.box(0.0, 0.0, 3.0, 1.0, 16.0, 13.0);
    private static final VoxelShape EAST_THIN = Block.box(15.0, 0.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape NORTH_THIN = Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_THIN = Block.box(3.0, 0.0, 15.0, 13.0, 16.0, 16.0);
    public static final Lazy<Block[]> ALL_COLORS = Lazy.of(() -> new Block[] {
            ModBlocks.WHITE_RIBBON.get(),
            ModBlocks.ORANGE_RIBBON.get(),
            ModBlocks.MAGENTA_RIBBON.get(),
            ModBlocks.LIGHT_BLUE_RIBBON.get(),
            ModBlocks.YELLOW_RIBBON.get(),
            ModBlocks.LIME_RIBBON.get(),
            ModBlocks.PINK_RIBBON.get(),
            ModBlocks.GRAY_RIBBON.get(),
            ModBlocks.LIGHT_GRAY_RIBBON.get(),
            ModBlocks.CYAN_RIBBON.get(),
            ModBlocks.PURPLE_RIBBON.get(),
            ModBlocks.BLUE_RIBBON.get(),
            ModBlocks.BROWN_RIBBON.get(),
            ModBlocks.GREEN_RIBBON.get(),
            ModBlocks.RED_RIBBON.get(),
            ModBlocks.BLACK_RIBBON.get(),
    });
    public static final ItemLike[] ALL_ITEMS = new ItemLike[] {
            ModItems.WHITE_RIBBON,
            ModItems.LIGHT_GRAY_RIBBON,
            ModItems.GRAY_RIBBON,
            ModItems.BLACK_RIBBON,
            ModItems.BROWN_RIBBON,
            ModItems.RED_RIBBON,
            ModItems.ORANGE_RIBBON,
            ModItems.YELLOW_RIBBON,
            ModItems.LIME_RIBBON,
            ModItems.GREEN_RIBBON,
            ModItems.CYAN_RIBBON,
            ModItems.LIGHT_BLUE_RIBBON,
            ModItems.BLUE_RIBBON,
            ModItems.PURPLE_RIBBON,
            ModItems.MAGENTA_RIBBON,
            ModItems.PINK_RIBBON
    };
    private final DyeColor color;

    public RibbonBlock(Properties properties, DyeColor color) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(RIBBON_STATE, RibbonState.DEFAULT)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
        this.color = color;
    }

    @Override
    protected MapCodec<RibbonBlock> codec() {
        return CODEC;
    }

    public DyeColor color() {
        return this.color;
    }

    public static ItemLike itemByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_RIBBON;
            case ORANGE -> ModItems.ORANGE_RIBBON;
            case MAGENTA -> ModItems.MAGENTA_RIBBON;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_RIBBON;
            case YELLOW -> ModItems.YELLOW_RIBBON;
            case LIME -> ModItems.LIME_RIBBON;
            case PINK -> ModItems.PINK_RIBBON;
            case GRAY -> ModItems.GRAY_RIBBON;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_RIBBON;
            case CYAN -> ModItems.CYAN_RIBBON;
            case PURPLE -> ModItems.PURPLE_RIBBON;
            case BLUE -> ModItems.BLUE_RIBBON;
            case BROWN -> ModItems.BROWN_RIBBON;
            case GREEN -> ModItems.GREEN_RIBBON;
            case RED -> ModItems.RED_RIBBON;
            case BLACK -> ModItems.BLACK_RIBBON;
        };
    }

    private static boolean sameFace(BlockState neighborState, Direction direction) {
        return direction == neighborState.getValue(FACING);
    }

    private static BlockState connectionCheck(BlockGetter level, BlockPos pos, BlockState state, Direction direction) {
        if (!state.getValue(AUTO_CONNECT)) {
            return state;
        }
        boolean up = shouldConnectTo(level.getBlockState(pos.above()), direction);
        boolean down = shouldConnectTo(level.getBlockState(pos.below()), direction);
        Direction leftSide = direction.getCounterClockWise();
        boolean left = shouldConnectTo(level.getBlockState(pos.relative(leftSide)), direction);
        Direction rightSide = direction.getClockWise();
        boolean right = shouldConnectTo(level.getBlockState(pos.relative(rightSide)), direction);
        return state.setValue(RIBBON_STATE, RibbonState.getState(up, down, left, right));
    }

    private static boolean shouldConnectTo(BlockState state, Direction direction) {
        return state.getBlock() instanceof RibbonBlock && sameFace(state, direction);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        RibbonState ribbonState = pState.getValue(RIBBON_STATE);
        Direction direction = pState.getValue(FACING);
        return switch (ribbonState) {
            case HORIZONTAL_CONNECT -> switch (direction) {
                case NORTH -> NORTH_LOW;
                case SOUTH -> SOUTH_LOW;
                case WEST -> WEST_LOW;
                default -> EAST_LOW;
            };
            case VERTICAL_CONNECT, TIP -> switch (direction) {
                case NORTH -> NORTH_THIN;
                case SOUTH -> SOUTH_THIN;
                case WEST -> WEST_THIN;
                default -> EAST_THIN;
            };
            default -> switch (direction) {
                case NORTH -> NORTH_AABB;
                case SOUTH -> SOUTH_AABB;
                case WEST -> WEST_AABB;
                default -> EAST_AABB;
            };
        };
    }

    private static boolean notAtTop(BlockState state, BlockState aboveState, Direction direction) {
        if (shouldConnectTo(aboveState, direction)) {
            return state.getValue(RIBBON_STATE).connectUp && aboveState.getValue(RIBBON_STATE).connectDown;
        }
        return false;
    }

    private static boolean isHorizontallyConnecting(BlockState state, LevelReader level, BlockPos pos, Direction direction) {
        BlockPos leftPos = pos.relative(direction.getCounterClockWise());
        BlockPos rightPos = pos.relative(direction.getClockWise());
        BlockState leftState = level.getBlockState(leftPos);
        BlockState rightState = level.getBlockState(rightPos);
        if (shouldConnectTo(leftState, direction) && shouldConnectTo(rightState, direction)) {
            RibbonState ribbonState = state.getValue(RIBBON_STATE);
            return ribbonState.connectLeft && ribbonState.connectRight
                    && leftState.getValue(RIBBON_STATE).connectRight
                    && rightState.getValue(RIBBON_STATE).connectLeft;
        }
        return false;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState above = pLevel.getBlockState(pPos.above());
        Direction direction = pState.getValue(FACING);
        if (notAtTop(pState, above, direction) || isHorizontallyConnecting(pState, pLevel, pPos, direction)) {
            return true;
        } else {
            return VineBlock.isAcceptableNeighbour(pLevel, pPos.relative(direction), direction);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        if (!pContext.replacingClickedOnBlock()) {
            Direction direction = pContext.getClickedFace();
            BlockState state = level.getBlockState(pos.relative(direction));
            if (shouldConnectTo(state, direction)) {
                return null;
            }
        }
        BlockState state = this.defaultBlockState();
        FluidState fluidstate = pContext.getLevel().getFluidState(pos);
        for (Direction direction : pContext.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction);
                if (state.canSurvive(level, pos)) {
                    return state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }
        return null;
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        Direction direction = pState.getValue(FACING);
        BlockState blockstate = connectionCheck(pLevel, pCurrentPos, pState, direction);
        return blockstate.canSurvive(pLevel, pCurrentPos) ? blockstate : Blocks.AIR.defaultBlockState();
    }

    private static BlockState stopAutoConnect(BlockState state) {
        if (state.getValue(AUTO_CONNECT)) {
            return state.setValue(AUTO_CONNECT, false);
        }
        return state;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pPlayer.isSecondaryUseActive()) {
            RibbonState ribbonState = pState.getValue(RIBBON_STATE);
            Section section = getHitSection(pHitResult, pState);
            RibbonState ribbonState1 = switch (section) {
                case null -> ribbonState;
                case UP -> ribbonState.connectUp();
                case DOWN -> ribbonState.connectDown();
                case LEFT -> ribbonState.connectLeft();
                case RIGHT -> ribbonState.connectRight();
                case CENTER -> ribbonState.withBow();
            };
            if (ribbonState != ribbonState1) {
                BlockState state = stopAutoConnect(pState).setValue(RIBBON_STATE, ribbonState1);
                pLevel.setBlock(pPos, state, 3);
                pLevel.playSound(null, pPos, ModSounds.RIBBON_TIED.get(), SoundSource.BLOCKS);
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_ATTACH, pPos);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (ItemUtils.isShears(pStack)) {
            RibbonState ribbonState = pState.getValue(RIBBON_STATE);
            Section section = getHitSection(pHitResult, pState);
            RibbonState ribbonState1 = switch (section) {
                case null -> ribbonState;
                case UP -> ribbonState.cutUp();
                case DOWN -> ribbonState.cutDown();
                case LEFT -> ribbonState.cutLeft();
                case RIGHT -> ribbonState.cutRight();
                case CENTER -> ribbonState.withoutBow();
            };
            if (ribbonState != ribbonState1) {
                BlockState state = stopAutoConnect(pState).setValue(RIBBON_STATE, ribbonState1);
                pLevel.setBlock(pPos, state, 3);
                pLevel.playSound(null, pPos, ModSounds.SHEARS_SNIP.get(), SoundSource.BLOCKS);
                pLevel.gameEvent(pPlayer, GameEvent.SHEAR, pPos);
                pPlayer.awardStat(Stats.ITEM_USED.get(pStack.getItem()));
                pStack.hurtAndBreak(1, pPlayer, LivingEntity.getSlotForHand(pHand));
                return ItemInteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
    }

    @Nullable
    private static Section getHitSection(BlockHitResult result, BlockState state) {
        Vec2 vec = getRelativeHitCoordinatesForBlockFace(result, state.getValue(FACING));
        if (vec == null) return null;
        float x = vec.x, y = vec.y;
        if (y > 0.75F) {
            return Section.UP;
        } else if (y < 0.25F) {
            return Section.DOWN;
        } else if (x < 0.1875F) {
            return Section.LEFT;
        } else if (x > 0.8125F) {
            return Section.RIGHT;
        } else {
            return Section.CENTER;
        }
    }

    @Nullable
    private static Vec2 getRelativeHitCoordinatesForBlockFace(BlockHitResult result, Direction face) {
        Direction direction = result.getDirection().getOpposite();
        if (face != direction) {
            return null;
        } else {
            BlockPos pos = result.getBlockPos().relative(direction);
            Vec3 vec3 = result.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
            double d0 = vec3.x();
            double d1 = vec3.y();
            double d2 = vec3.z();

            return switch (direction) {
                case NORTH -> new Vec2((float)d0, (float)d1);
                case SOUTH -> new Vec2((float)(1.0 - d0), (float)d1);
                case WEST -> new Vec2((float)(1.0 - d2), (float)d1);
                case EAST -> new Vec2((float)d2, (float)d1);
                case DOWN, UP -> null;
            };
        }
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected BlockState rotate(BlockState pState, Rotation pRotate) {
        return pState.setValue(FACING, pRotate.rotate(pState.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.setValue(FACING, pMirror.mirror(pState.getValue(FACING)))
                .setValue(RIBBON_STATE, pState.getValue(RIBBON_STATE).mirror());
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(RIBBON_STATE, AUTO_CONNECT, FACING, WATERLOGGED);
    }

    private enum Section {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        CENTER
    }
}
