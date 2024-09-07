package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.DrumSetScreen;
import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.entity.DrumSetBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.network.packets.DrumSetPacket;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantments;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;

public class DrumSetBlock extends BaseEntityBlock {
    public static final MapCodec<DrumSetBlock> CODEC = simpleCodec(DrumSetBlock::new);
    public static final BooleanProperty HIT = ModBlockStateProperties.HIT;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DrumSetPart> PART = ModBlockStateProperties.DRUM_SET_PART;
    private static final VoxelShape NORTH_LEFT_LOWER = Shapes.or(
            Block.box(5.0, 2.5, 4.0, 7.0, 4.5, 6.0),
            Block.box(5.0, 12.5, 4.0, 7.0, 14.5, 6.0),
            Block.box(5.0, 15.5, 4.0, 7.0, 16.5, 6.0),
            Block.box(5.7, 0.0, 4.7, 6.3, 13.0, 5.3),
            Block.box(1.0, 14.5, 0.0, 11.0, 15.5, 10.0),
            Block.box(9.0, 3.0, 10.0, 11.0, 5.0, 12.0),
            Block.box(9.7, 5.0, 10.7, 10.3, 18.0, 11.3),
            Block.box(5.0, 0.0, 0.0, 7.0, 0.5, 5.0));
    private static final VoxelShape NORTH_LEFT_UPPER = Shapes.or(
            Block.box(9.0, 2.0, 10.0, 11.0, 4.0, 12.0),
            Block.box(8.5, 4.0, 10.5, 9.5, 9.5, 11.5),
            Block.box(6.0, 9.5, 9.0, 10.0, 10.5, 13.0),
            Block.box(2.0, 9.9, 5.0, 14.0, 10.0, 17.0),
            Block.box(7.7, 10.5, 10.7, 8.3, 11.0, 11.3));
    private static final VoxelShape NORTH_MID_LOWER = Shapes.or(
            Block.box(1.0, 0.0, 4.0, 15.0, 14.0, 14.0),
            Block.box(7.0, 0.0, 2.0, 9.0, 1.0, 4.0));
    private static final VoxelShape NORTH_MID_UPPER = Shapes.or(
            Block.box(-1.0, -1.0, 5.0, 7.0, 7.0, 13.0),
            Block.box(9.0, -1.0, 5.0, 17.0, 7.0, 13.0));
    private static final VoxelShape NORTH_RIGHT_LOWER = Shapes.or(
            Block.box(9.0, 3.4, 4.0, 11.0, 4.4, 6.0),
            Block.box(9.0, 9.0, 4.0, 11.0, 10.0, 6.0),
            Block.box(9.5, 4.4, 4.5, 10.5, 9.0, 5.5),
            Block.box(5.0, 10.0, 0.0, 15.0, 15.0, 10.0),
            Block.box(7.0, 3.0, 10.0, 9.0, 5.0, 12.0),
            Block.box(7.7, 5.0, 10.7, 8.3, 17.0, 11.3));
    private static final VoxelShape NORTH_RIGHT_UPPER = Shapes.or(
            Block.box(7.0, 1.0, 10.0, 9.0, 3.0, 12.0),
            Block.box(7.7, 3.0, 10.7, 8.3, 10.0, 11.3),
            Block.box(6.0, 8.5, 9.0, 10.0, 9.5, 13.0),
            Block.box(2.0, 8.9, 5.0, 14.0, 9.0, 17.0));
    private static final VoxelShape EAST_LEFT_LOWER = Shapes.or(
            Block.box(10.0, 2.5, 5.0, 12.0, 4.5, 7.0),
            Block.box(10.0, 12.5, 5.0, 12.0, 14.5, 7.0),
            Block.box(10.0, 15.5, 5.0, 12.0, 16.5, 7.0),
            Block.box(10.7, 0.0, 5.7, 11.3, 13.0, 6.3),
            Block.box(6.0, 14.5, 1.0, 16.0, 15.5, 11.0),
            Block.box(4.0, 3.0, 9.0, 6.0, 5.0, 11.0),
            Block.box(4.7, 5.0, 9.7, 5.3, 18.0, 10.3),
            Block.box(11.0, 0.0, 5.0, 16.0, 0.5, 7.0));
    private static final VoxelShape EAST_LEFT_UPPER = Shapes.or(
            Block.box(4.0, 2.0, 9.0, 6.0, 4.0, 11.0),
            Block.box(4.5, 4.0, 8.5, 5.5, 9.5, 9.5),
            Block.box(3.0, 9.5, 6.0, 7.0, 10.5, 10.0),
            Block.box(-1.0, 9.9, 2.0, 11.0, 10.0, 14.0),
            Block.box(4.7, 10.5, 7.7, 5.3, 11.0, 8.3));
    private static final VoxelShape EAST_MID_LOWER = Shapes.or(
            Block.box(2.0, 0.0, 1.0, 12.0, 14.0, 15.0),
            Block.box(12.0, 0.0, 7.0, 14.0, 1.0, 9.0));
    private static final VoxelShape EAST_MID_UPPER = Shapes.or(
            Block.box(3.0, -1.0, -1.0, 11.0, 7.0, 7.0),
            Block.box(3.0, -1.0, 9.0, 11.0, 7.0, 17.0));
    private static final VoxelShape EAST_RIGHT_LOWER = Shapes.or(
            Block.box(10.0, 3.4, 9.0, 12.0, 4.4, 11.0),
            Block.box(10.0, 9.0, 9.0, 12.0, 10.0, 11.0),
            Block.box(10.5, 4.4, 9.5, 11.5, 9.0, 10.5),
            Block.box(6.0, 10.0, 5.0, 16.0, 15.0, 15.0),
            Block.box(4.0, 3.0, 7.0, 6.0, 5.0, 9.0),
            Block.box(4.7, 5.0, 7.7, 5.3, 17.0, 8.3));
    private static final VoxelShape EAST_RIGHT_UPPER = Shapes.or(
            Block.box(4.0, 1.0, 7.0, 6.0, 3.0, 9.0),
            Block.box(4.7, 3.0, 7.7, 5.3, 10.0, 8.3),
            Block.box(3.0, 8.5, 6.0, 7.0, 9.5, 10.0),
            Block.box(-1.0, 8.9, 2.0, 11.0, 9.0, 14.0));
    private static final VoxelShape SOUTH_LEFT_LOWER = Shapes.or(
            Block.box(9.0, 2.5, 10.0, 11.0, 4.5, 12.0),
            Block.box(9.0, 12.5, 10.0, 11.0, 14.5, 12.0),
            Block.box(9.0, 15.5, 10.0, 11.0, 16.5, 12.0),
            Block.box(9.7, 0.0, 10.7, 10.3, 13.0, 11.3),
            Block.box(5.0, 14.5, 6.0, 15.0, 15.5, 16.0),
            Block.box(5.0, 3.0, 4.0, 7.0, 5.0, 6.0),
            Block.box(5.7, 5.0, 4.7, 6.3, 18.0, 5.3),
            Block.box(9.0, 0.0, 11.0, 11.0, 0.5, 16.0));
    private static final VoxelShape SOUTH_LEFT_UPPER = Shapes.or(
            Block.box(5.0, 2.0, 4.0, 7.0, 4.0, 6.0),
            Block.box(6.5, 4.0, 4.5, 7.5, 9.5, 5.5),
            Block.box(6.0, 9.5, 3.0, 10.0, 10.5, 7.0),
            Block.box(2.0, 9.9, -1.0, 14.0, 10.0, 11.0),
            Block.box(7.7, 10.5, 4.7, 8.3, 11.0, 5.3));
    private static final VoxelShape SOUTH_MID_LOWER = Shapes.or(
            Block.box(1.0, 0.0, 2.0, 15.0, 14.0, 12.0),
            Block.box(7.0, 0.0, 12.0, 9.0, 1.0, 14.0));
    private static final VoxelShape SOUTH_MID_UPPER = Shapes.or(
            Block.box(9.0, -1.0, 3.0, 17.0, 7.0, 11.0),
            Block.box(-1.0, -1.0, 3.0, 7.0, 7.0, 11.0));
    private static final VoxelShape SOUTH_RIGHT_LOWER = Shapes.or(
            Block.box(5.0, 3.4, 10.0, 7.0, 4.4, 12.0),
            Block.box(5.0, 9.0, 10.0, 7.0, 10.0, 12.0),
            Block.box(5.5, 4.4, 10.5, 6.5, 9.0, 11.5),
            Block.box(1.0, 10.0, 6.0, 11.0, 15.0, 16.0),
            Block.box(7.0, 3.0, 4.0, 9.0, 5.0, 6.0),
            Block.box(7.7, 5.0, 4.7, 8.3, 17.0, 5.3));
    private static final VoxelShape SOUTH_RIGHT_UPPER = Shapes.or(
            Block.box(7.0, 1.0, 4.0, 9.0, 3.0, 6.0),
            Block.box(7.7, 3.0, 4.7, 8.3, 10.0, 5.3),
            Block.box(6.0, 8.5, 3.0, 10.0, 9.5, 7.0),
            Block.box(2.0, 8.9, -1.0, 14.0, 9.0, 11.0));
    private static final VoxelShape WEST_LEFT_LOWER = Shapes.or(
            Block.box(4.0, 2.5, 9.0, 6.0, 4.5, 11.0),
            Block.box(4.0, 12.5, 9.0, 6.0, 14.5, 11.0),
            Block.box(4.0, 15.5, 9.0, 6.0, 16.5, 11.0),
            Block.box(4.7, 0.0, 9.7, 5.3, 13.0, 10.3),
            Block.box(0.0, 14.5, 5.0, 10.0, 15.5, 15.0),
            Block.box(10.0, 3.0, 5.0, 12.0, 5.0, 7.0),
            Block.box(10.7, 5.0, 5.7, 11.3, 18.0, 6.3),
            Block.box(0.0, 0.0, 9.0, 5.0, 0.5, 11.0));
    private static final VoxelShape WEST_LEFT_UPPER = Shapes.or(
            Block.box(10.0, 2.0, 5.0, 12.0, 4.0, 7.0),
            Block.box(10.5, 4.0, 6.5, 11.5, 9.5, 7.5),
            Block.box(9.0, 9.5, 6.0, 13.0, 10.5, 10.0),
            Block.box(5.0, 9.9, 2.0, 17.0, 10.0, 14.0),
            Block.box(10.7, 10.5, 7.7, 11.3, 11.0, 8.3));
    private static final VoxelShape WEST_MID_LOWER = Shapes.or(
            Block.box(4.0, 0.0, 1.0, 14.0, 14.0, 15.0),
            Block.box(2.0, 0.0, 7.0, 4.0, 1.0, 9.0));
    private static final VoxelShape WEST_MID_UPPER = Shapes.or(
            Block.box(5.0, -1.0, 9.0, 13.0, 7.0, 17.0),
            Block.box(5.0, -1.0, -1.0, 13.0, 7.0, 7.0));
    private static final VoxelShape WEST_RIGHT_LOWER = Shapes.or(
            Block.box(4.0, 3.4, 5.0, 6.0, 4.4, 7.0),
            Block.box(4.0, 9.0, 5.0, 6.0, 10.0, 7.0),
            Block.box(4.5, 4.4, 5.5, 5.5, 9.0, 6.5),
            Block.box(0.0, 10.0, 1.0, 10.0, 15.0, 11.0),
            Block.box(10.0, 3.0, 7.0, 12.0, 5.0, 9.0),
            Block.box(10.7, 5.0, 7.7, 11.3, 17.0, 8.3));
    private static final VoxelShape WEST_RIGHT_UPPER = Shapes.or(
            Block.box(10.0, 1.0, 7.0, 12.0, 3.0, 9.0),
            Block.box(10.7, 3.0, 7.7, 11.3, 10.0, 8.3),
            Block.box(9.0, 8.5, 6.0, 13.0, 9.5, 10.0),
            Block.box(5.0, 8.9, 2.0, 17.0, 9.0, 14.0));
    @Override
    protected MapCodec<DrumSetBlock> codec() {
        return CODEC;
    }
    public DrumSetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HIT, false)
                .setValue(PART, DrumSetPart.MID_LOWER)
        );
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        DrumSetPart part = pState.getValue(PART);
        return switch (direction) {
            case EAST -> switch (part) {
                case LEFT_LOWER -> EAST_LEFT_LOWER;
                case LEFT_UPPER -> EAST_LEFT_UPPER;
                case MID_UPPER -> EAST_MID_UPPER;
                case RIGHT_LOWER -> EAST_RIGHT_LOWER;
                case RIGHT_UPPER -> EAST_RIGHT_UPPER;
                default -> EAST_MID_LOWER;
            };
            case SOUTH -> switch (part) {
                case LEFT_LOWER -> SOUTH_LEFT_LOWER;
                case LEFT_UPPER -> SOUTH_LEFT_UPPER;
                case MID_UPPER -> SOUTH_MID_UPPER;
                case RIGHT_LOWER -> SOUTH_RIGHT_LOWER;
                case RIGHT_UPPER -> SOUTH_RIGHT_UPPER;
                default -> SOUTH_MID_LOWER;
            };
            case WEST -> switch (part) {
                case LEFT_LOWER -> WEST_LEFT_LOWER;
                case LEFT_UPPER -> WEST_LEFT_UPPER;
                case MID_UPPER -> WEST_MID_UPPER;
                case RIGHT_LOWER -> WEST_RIGHT_LOWER;
                case RIGHT_UPPER -> WEST_RIGHT_UPPER;
                default -> WEST_MID_LOWER;
            };
            default -> switch (part) {
                case LEFT_LOWER -> NORTH_LEFT_LOWER;
                case LEFT_UPPER -> NORTH_LEFT_UPPER;
                case MID_UPPER -> NORTH_MID_UPPER;
                case RIGHT_LOWER -> NORTH_RIGHT_LOWER;
                case RIGHT_UPPER -> NORTH_RIGHT_UPPER;
                default -> NORTH_MID_LOWER;
            };
        };
    }

    @Override
    public InteractionResult useWithoutItem (BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if ((pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != ModItems.DRUMSTICK.get() ||
                pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() != ModItems.DRUMSTICK.get())) {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_drumsticks"), true);
            return InteractionResult.FAIL;
        } else {
            if (pLevel.isClientSide) {
                DrumSetScreen.openScreen(pPlayer, pPos);
                PacketDistributor.sendToServer(new DrumSetPacket(false, false, false, false, pPos, pPlayer.getId()));
            }
            pPlayer.awardStat(ModStats.INTERACT_WITH_DRUM_SET.get());
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        DrumSetPart part = pState.getValue(PART);
        return (part == DrumSetPart.LEFT_LOWER || part == DrumSetPart.MID_LOWER || part == DrumSetPart.RIGHT_LOWER)
                ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }
    @Override
    protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if ((Direction.DOWN == pDirection && !this.canSurvive(pState, pLevel, pPos))) {
            return Blocks.AIR.defaultBlockState();
        }
        if (pDirection == getNeighbourDirectionLeft(pState.getValue(PART), pState.getValue(FACING))) {
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else if (pDirection == getNeighbourDirectionRight(pState.getValue(PART), pState.getValue(FACING))) {
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else if (pDirection == getNeighbourDirection(pState.getValue(PART))) {
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
        }
    }
    private static Direction getNeighbourDirectionLeft(DrumSetPart pPart, Direction pDirection) {
        return (pPart == DrumSetPart.LEFT_LOWER || pPart == DrumSetPart.LEFT_UPPER) ? pDirection.getCounterClockWise() : pDirection.getClockWise();
    }
    private static Direction getNeighbourDirectionRight(DrumSetPart pPart, Direction pDirection) {
        return (pPart == DrumSetPart.RIGHT_LOWER || pPart == DrumSetPart.RIGHT_UPPER) ? pDirection.getClockWise() : pDirection.getCounterClockWise();
    }
    private static Direction getNeighbourDirection(DrumSetPart pPart) {
        return (pPart == DrumSetPart.LEFT_LOWER || pPart == DrumSetPart.MID_LOWER || pPart == DrumSetPart.RIGHT_LOWER) ? Direction.UP : Direction.DOWN;
    }
    @Override
    public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        ItemStack stack = pPlayer.getMainHandItem();
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        BlockPos blockpos = getBassDrumPos(pPos, pState);
        boolean hasSilkTouch = (server != null && stack.getEnchantmentLevel(server.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH)) > 0);
        if (!pLevel.isClientSide && (pPlayer.isCreative() || hasSilkTouch)) {
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(this) && blockstate.getValue(PART) == DrumSetPart.MID_LOWER) {
                pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
                if (!pPlayer.isCreative()) {
                    popResource(pLevel, blockpos, ModItems.DRUM_SET.get().getDefaultInstance());
                }
            }
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
    private BlockPos getBassDrumPos(BlockPos pPos, BlockState pState) {
        DrumSetPart part = pState.getValue(PART);
        Direction direction = pState.getValue(FACING);
        return switch (part) {
            case MID_LOWER -> pPos;
            case MID_UPPER -> pPos.below();
            case LEFT_LOWER -> pPos.relative(direction.getCounterClockWise());
            case LEFT_UPPER -> pPos.relative(direction.getCounterClockWise()).below();
            case RIGHT_LOWER -> pPos.relative(direction.getClockWise());
            case RIGHT_UPPER -> pPos.relative(direction.getClockWise()).below();
        };
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getClockWise());
        BlockPos blockpos2 = blockpos.relative(direction.getCounterClockWise());
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos1.above()).canBeReplaced(pContext) && level.getBlockState(blockpos2.above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos1.below()).isFaceSturdy(level, blockpos1.below(), Direction.UP) && level.getBlockState(blockpos2.below()).isFaceSturdy(level, blockpos2.below(), Direction.UP)) {
            return level.getBlockState(blockpos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos1)
                    && level.getBlockState(blockpos2).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos2)
                    ? this.defaultBlockState().setValue(FACING, direction.getOpposite())
                    : null;
        } else {
            return null;
        }
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(PART, DrumSetPart.MID_UPPER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getClockWise()), pState.setValue(PART, DrumSetPart.LEFT_LOWER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getClockWise()).above(), pState.setValue(PART, DrumSetPart.LEFT_UPPER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()), pState.setValue(PART, DrumSetPart.RIGHT_LOWER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()).above(), pState.setValue(PART, DrumSetPart.RIGHT_UPPER), 3);
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
        pBuilder.add(HIT, FACING, PART);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DrumSetBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.DRUM_SET.get(), DrumSetBlockEntity::tick);
    }
}
