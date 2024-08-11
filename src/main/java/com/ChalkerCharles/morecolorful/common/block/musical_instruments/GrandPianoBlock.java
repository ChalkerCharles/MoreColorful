package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.block.properties.GrandPianoPart;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class GrandPianoBlock extends MusicalInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<GrandPianoPart> PART = ModBlockStateProperties.GRAND_PIANO_PART;
    private static final VoxelShape NORTH_FRONT_LEFT_LOWER = Shapes.or(
            Block.box(12.0, 0.0, 3.0, 15.0, 12.0, 6.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(0.0, 15.0, 0.0, 14.0, 16.0, 6.0),
            Block.box(14.0, 15.0, 0.0, 16.0, 18.0, 6.0),
            Block.box(-2.0, 1.0, 2.0, 2.0, 3.0, 4.0),
            Block.box(1.0, 3.0, 3.0, 2.0, 12.0, 4.0));
    private static final VoxelShape NORTH_FRONT_LEFT_UPPER = Shapes.or(
            Block.box(0.0, -1.0, 6.0, 16.0, 5.0, 10.0),
            Block.box(14.0, -1.0, 10.0, 16.0, 5.0, 16.0),
            Block.box(0.0, -1.0, 10.0, 14.0, 2.0, 16.0),
            Block.box(12.0, 6.0, 8.0, 16.0, 7.5, 16.0),
            Block.box(8.0, 7.5, 8.0, 12.0, 9.0, 16.0),
            Block.box(4.0, 9.0, 8.0, 8.0, 10.5, 16.0),
            Block.box(0.0, 10.5, 8.0, 4.0, 12.0, 16.0));
    private static final VoxelShape NORTH_FRONT_RIGHT_LOWER = Shapes.or(
            Block.box(1.0, 0.0, 4.0, 4.0, 12.0, 7.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(2.0, 15.0, 0.0, 16.0, 16.0, 6.0),
            Block.box(0.0, 15.0, 0.0, 2.0, 18.0, 6.0),
            Block.box(14.0, 3.0, 3.0, 15.0, 12.0, 4.0));
    private static final VoxelShape NORTH_FRONT_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, -1.0, 6.0, 16.0, 5.0, 10.0),
            Block.box(0.0, -1.0, 10.0, 2.0, 5.0, 16.0),
            Block.box(2.0, -1.0, 10.0, 16.0, 2.0, 16.0),
            Block.box(10.0, 5.0, 7.0, 22.0, 10.0, 8.0),
            Block.box(12.0, 12.0, 8.0, 16.0, 13.5, 16.0),
            Block.box(8.0, 13.5, 8.0, 12.0, 15.0, 16.0),
            Block.box(4.0, 15.0, 8.0, 8.0, 16.5, 16.0),
            Block.box(2.5, 2.0, 14.0, 3.5, 5.0, 15.0),
            Block.box(3.5, 5.0, 14.0, 4.5, 8.0, 15.0),
            Block.box(4.5, 8.0, 14.0, 5.5, 11.0, 15.0),
            Block.box(5.5, 11.0, 14.0, 6.5, 14.0, 15.0));
    private static final VoxelShape NORTH_BACK_LEFT_LOWER = Shapes.or(
            Block.box(2.0, 0.0, 10.0, 5.0, 12.0, 13.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 18.0, 16.0));
    private static final VoxelShape NORTH_BACK_LEFT_UPPER = Shapes.or(
            Block.box(14.0, 2.0, 0.0, 16.0, 5.0, 16.0),
            Block.box(0.0, 2.0, 15.0, 14.0, 5.0, 16.0),
            Block.box(12.0, 6.0, 0.0, 16.0, 7.5, 16.0),
            Block.box(8.0, 7.5, 0.0, 12.0, 9.0, 16.0),
            Block.box(4.0, 9.0, 0.0, 8.0, 10.5, 16.0),
            Block.box(0.0, 10.5, 0.0, 4.0, 12.0, 16.0));
    private static final VoxelShape NORTH_BACK_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, -4.0, 0.0, 16.0, 2.0, 6.0),
            Block.box(10.0, -4.0, 6.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 2.0, 0.0, 2.0, 5.0, 6.0),
            Block.box(2.0, 2.0, 5.0, 11.0, 5.0, 6.0),
            Block.box(10.0, 2.0, 6.0, 11.0, 5.0, 16.0),
            Block.box(11.0, 2.0, 15.0, 16.0, 5.0, 16.0),
            Block.box(12.0, 12.0, 0.0, 16.0, 13.5, 16.0),
            Block.box(8.0, 13.5, 0.0, 12.0, 15.0, 6.0),
            Block.box(4.0, 15.0, 0.0, 8.0, 16.5, 6.0));
    private static final VoxelShape EAST_FRONT_LEFT_LOWER = Shapes.or(
            Block.box(10.0, 0.0, 12.0, 13.0, 12.0, 15.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(10.0, 15.0, 0.0, 16.0, 16.0, 14.0),
            Block.box(10.0, 15.0, 14.0, 16.0, 18.0, 16.0),
            Block.box(12.0, 1.0, -2.0, 14.0, 3.0, 2.0),
            Block.box(12.0, 3.0, 1.0, 13.0, 12.0, 2.0));
    private static final VoxelShape EAST_FRONT_LEFT_UPPER = Shapes.or(
            Block.box(6.0, -1.0, 0.0, 10.0, 5.0, 16.0),
            Block.box(0.0, -1.0, 14.0, 6.0, 5.0, 16.0),
            Block.box(0.0, -1.0, 0.0, 6.0, 2.0, 14.0),
            Block.box(0.0, 6.0, 12.0, 8.0, 7.5, 16.0),
            Block.box(0.0, 7.5, 8.0, 8.0, 9.0, 12.0),
            Block.box(0.0, 9.0, 4.0, 8.0, 10.5, 8.0),
            Block.box(0.0, 10.5, 0.0, 8.0, 12.0, 4.0));
    private static final VoxelShape EAST_FRONT_RIGHT_LOWER = Shapes.or(
            Block.box(9.0, 0.0, 1.0, 12.0, 12.0, 4.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(10.0, 15.0, 2.0, 16.0, 16.0, 16.0),
            Block.box(10.0, 15.0, 0.0, 16.0, 18.0, 2.0),
            Block.box(12.0, 3.0, 14.0, 13.0, 12.0, 15.0));
    private static final VoxelShape EAST_FRONT_RIGHT_UPPER = Shapes.or(
            Block.box(6.0, -1.0, 0.0, 10.0, 5.0, 16.0),
            Block.box(0.0, -1.0, 0.0, 6.0, 5.0, 2.0),
            Block.box(0.0, -1.0, 2.0, 6.0, 2.0, 16.0),
            Block.box(8.0, 5.0, 10.0, 9.0, 10.0, 22.0),
            Block.box(0.0, 12.0, 12.0, 8.0, 13.5, 16.0),
            Block.box(0.0, 13.5, 8.0, 8.0, 15.0, 12.0),
            Block.box(0.0, 15.0, 4.0, 8.0, 16.5, 8.0),
            Block.box(1.0, 2.0, 2.5, 2.0, 5.0, 3.5),
            Block.box(1.0, 5.0, 3.5, 2.0, 8.0, 4.5),
            Block.box(1.0, 8.0, 4.5, 2.0, 11.0, 5.5),
            Block.box(1.0, 11.0, 5.5, 2.0, 14.0, 6.5));
    private static final VoxelShape EAST_BACK_LEFT_LOWER = Shapes.or(
            Block.box(3.0, 0.0, 2.0, 6.0, 12.0, 5.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 18.0, 16.0));
    private static final VoxelShape EAST_BACK_LEFT_UPPER = Shapes.or(
            Block.box(0.0, 2.0, 14.0, 16.0, 5.0, 16.0),
            Block.box(0.0, 2.0, 0.0, 1.0, 5.0, 14.0),
            Block.box(0.0, 6.0, 12.0, 16.0, 7.5, 16.0),
            Block.box(0.0, 7.5, 8.0, 16.0, 9.0, 12.0),
            Block.box(0.0, 9.0, 4.0, 16.0, 10.5, 8.0),
            Block.box(0.0, 10.5, 0.0, 16.0, 12.0, 4.0));
    private static final VoxelShape EAST_BACK_RIGHT_UPPER = Shapes.or(
            Block.box(10.0, -4.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, -4.0, 10.0, 10.0, 2.0, 16.0),
            Block.box(10.0, 2.0, 0.0, 16.0, 5.0, 2.0),
            Block.box(10.0, 2.0, 2.0, 11.0, 5.0, 11.0),
            Block.box(0.0, 2.0, 10.0, 10.0, 5.0, 11.0),
            Block.box(0.0, 2.0, 11.0, 1.0, 5.0, 16.0),
            Block.box(0.0, 12.0, 12.0, 16.0, 13.5, 16.0),
            Block.box(10.0, 13.5, 8.0, 16.0, 15.0, 12.0),
            Block.box(10.0, 15.0, 4.0, 16.0, 16.5, 8.0));
    private static final VoxelShape SOUTH_FRONT_LEFT_LOWER = Shapes.or(
            Block.box(1.0, 0.0, 10.0, 4.0, 12.0, 13.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(2.0, 15.0, 10.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 15.0, 10.0, 2.0, 18.0, 16.0),
            Block.box(14.0, 1.0, 12.0, 18.0, 3.0, 14.0),
            Block.box(14.0, 3.0, 12.0, 15.0, 12.0, 13.0));
    private static final VoxelShape SOUTH_FRONT_LEFT_UPPER = Shapes.or(
            Block.box(0.0, -1.0, 6.0, 16.0, 5.0, 10.0),
            Block.box(0.0, -1.0, 0.0, 2.0, 5.0, 6.0),
            Block.box(2.0, -1.0, 0.0, 16.0, 2.0, 6.0),
            Block.box(0.0, 6.0, 0.0, 4.0, 7.5, 8.0),
            Block.box(4.0, 7.5, 0.0, 8.0, 9.0, 8.0),
            Block.box(8.0, 9.0, 0.0, 12.0, 10.5, 8.0),
            Block.box(12.0, 10.5, 0.0, 16.0, 12.0, 8.0));
    private static final VoxelShape SOUTH_FRONT_RIGHT_LOWER = Shapes.or(
            Block.box(12.0, 0.0, 9.0, 15.0, 12.0, 12.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(0.0, 15.0, 10.0, 14.0, 16.0, 16.0),
            Block.box(14.0, 15.0, 10.0, 16.0, 18.0, 16.0),
            Block.box(1.0, 3.0, 12.0, 2.0, 12.0, 13.0));
    private static final VoxelShape SOUTH_FRONT_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, -1.0, 6.0, 16.0, 5.0, 10.0),
            Block.box(14.0, -1.0, 0.0, 16.0, 5.0, 6.0),
            Block.box(0.0, -1.0, 0.0, 14.0, 2.0, 6.0),
            Block.box(-6.0, 5.0, 8.0, 6.0, 10.0, 9.0),
            Block.box(0.0, 12.0, 0.0, 4.0, 13.5, 8.0),
            Block.box(4.0, 13.5, 0.0, 8.0, 15.0, 8.0),
            Block.box(8.0, 15.0, 0.0, 12.0, 16.5, 8.0),
            Block.box(12.5, 2.0, 1.0, 13.5, 5.0, 2.0),
            Block.box(11.5, 5.0, 1.0, 12.5, 8.0, 2.0),
            Block.box(10.5, 8.0, 1.0, 11.5, 11.0, 2.0),
            Block.box(9.5, 11.0, 1.0, 10.5, 14.0, 2.0));
    private static final VoxelShape SOUTH_BACK_LEFT_LOWER = Shapes.or(
            Block.box(11.0, 0.0, 3.0, 14.0, 12.0, 6.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 18.0, 16.0));
    private static final VoxelShape SOUTH_BACK_LEFT_UPPER = Shapes.or(
            Block.box(0.0, 2.0, 0.0, 2.0, 5.0, 16.0),
            Block.box(2.0, 2.0, 0.0, 16.0, 5.0, 1.0),
            Block.box(0.0, 6.0, 0.0, 4.0, 7.5, 16.0),
            Block.box(4.0, 7.5, 0.0, 8.0, 9.0, 16.0),
            Block.box(8.0, 9.0, 0.0, 12.0, 10.5, 16.0),
            Block.box(12.0, 10.5, 0.0, 16.0, 12.0, 16.0));
    private static final VoxelShape SOUTH_BACK_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, -4.0, 10.0, 16.0, 2.0, 16.0),
            Block.box(0.0, -4.0, 0.0, 6.0, 2.0, 10.0),
            Block.box(14.0, 2.0, 10.0, 16.0, 5.0, 16.0),
            Block.box(5.0, 2.0, 10.0, 14.0, 5.0, 11.0),
            Block.box(5.0, 2.0, 0.0, 6.0, 5.0, 10.0),
            Block.box(0.0, 2.0, 0.0, 5.0, 5.0, 1.0),
            Block.box(0.0, 12.0, 0.0, 4.0, 13.5, 16.0),
            Block.box(4.0, 13.5, 10.0, 8.0, 15.0, 16.0),
            Block.box(8.0, 15.0, 10.0, 12.0, 16.5, 16.0));
    private static final VoxelShape WEST_FRONT_LEFT_LOWER = Shapes.or(
            Block.box(3.0, 0.0, 1.0, 6.0, 12.0, 4.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(0.0, 15.0, 2.0, 6.0, 16.0, 16.0),
            Block.box(0.0, 15.0, 0.0, 6.0, 18.0, 2.0),
            Block.box(2.0, 1.0, 14.0, 4.0, 3.0, 18.0),
            Block.box(3.0, 3.0, 14.0, 4.0, 12.0, 15.0));
    private static final VoxelShape WEST_FRONT_LEFT_UPPER = Shapes.or(
            Block.box(6.0, -1.0, 0.0, 10.0, 5.0, 16.0),
            Block.box(10.0, -1.0, 0.0, 16.0, 5.0, 2.0),
            Block.box(10.0, -1.0, 2.0, 16.0, 2.0, 16.0),
            Block.box(8.0, 6.0, 0.0, 16.0, 7.5, 4.0),
            Block.box(8.0, 7.5, 4.0, 16.0, 9.0, 8.0),
            Block.box(8.0, 9.0, 8.0, 16.0, 10.5, 12.0),
            Block.box(8.0, 10.5, 12.0, 16.0, 12.0, 16.0));
    private static final VoxelShape WEST_FRONT_RIGHT_LOWER = Shapes.or(
            Block.box(4.0, 0.0, 12.0, 7.0, 12.0, 15.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(0.0, 15.0, 0.0, 6.0, 16.0, 14.0),
            Block.box(0.0, 15.0, 14.0, 6.0, 18.0, 16.0),
            Block.box(3.0, 3.0, 1.0, 4.0, 12.0, 2.0));
    private static final VoxelShape WEST_FRONT_RIGHT_UPPER = Shapes.or(
            Block.box(6.0, -1.0, 0.0, 10.0, 5.0, 16.0),
            Block.box(10.0, -1.0, 14.0, 16.0, 5.0, 16.0),
            Block.box(10.0, -1.0, 0.0, 16.0, 2.0, 14.0),
            Block.box(7.0, 5.0, -6.0, 8.0, 10.0, 6.0),
            Block.box(8.0, 12.0, 0.0, 16.0, 13.5, 4.0),
            Block.box(8.0, 13.5, 4.0, 16.0, 15.0, 8.0),
            Block.box(8.0, 15.0, 8.0, 16.0, 16.5, 12.0),
            Block.box(14.0, 2.0, 12.5, 15.0, 5.0, 13.5),
            Block.box(14.0, 5.0, 11.5, 15.0, 8.0, 12.5),
            Block.box(14.0, 8.0, 10.5, 15.0, 11.0, 11.5),
            Block.box(14.0, 11.0, 9.5, 15.0, 14.0, 10.5));
    private static final VoxelShape WEST_BACK_LEFT_LOWER = Shapes.or(
            Block.box(10.0, 0.0, 11.0, 13.0, 12.0, 14.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 18.0, 16.0));
    private static final VoxelShape WEST_BACK_LEFT_UPPER = Shapes.or(
            Block.box(0.0, 2.0, 0.0, 16.0, 5.0, 2.0),
            Block.box(15.0, 2.0, 2.0, 16.0, 5.0, 16.0),
            Block.box(0.0, 6.0, 0.0, 16.0, 7.5, 4.0),
            Block.box(0.0, 7.5, 4.0, 16.0, 9.0, 8.0),
            Block.box(0.0, 9.0, 8.0, 16.0, 10.5, 12.0),
            Block.box(0.0, 10.5, 12.0, 16.0, 12.0, 16.0));
    private static final VoxelShape WEST_BACK_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, -4.0, 0.0, 6.0, 2.0, 16.0),
            Block.box(6.0, -4.0, 0.0, 16.0, 2.0, 6.0),
            Block.box(0.0, 2.0, 14.0, 6.0, 5.0, 16.0),
            Block.box(5.0, 2.0, 5.0, 6.0, 5.0, 14.0),
            Block.box(6.0, 2.0, 5.0, 16.0, 5.0, 6.0),
            Block.box(15.0, 2.0, 0.0, 16.0, 5.0, 5.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 13.5, 4.0),
            Block.box(0.0, 13.5, 4.0, 6.0, 15.0, 8.0),
            Block.box(0.0, 15.0, 8.0, 6.0, 16.5, 12.0));

    public GrandPianoBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, GrandPianoPart.FRONT_LEFT_LOWER)
        );
    }

    @Override
    public @NotNull InteractionResult useWithoutItem (@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull BlockHitResult pHitResult) {
        if (pLevel.isClientSide){
            PlayingScreen.openPlayingScreen(pPlayer, pType, pPos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        GrandPianoPart part = pState.getValue(PART);
        return switch (direction){
            case WEST -> switch (part){
                case FRONT_LEFT_UPPER -> WEST_FRONT_LEFT_UPPER;
                case FRONT_RIGHT_LOWER -> WEST_FRONT_RIGHT_LOWER;
                case FRONT_RIGHT_UPPER -> WEST_FRONT_RIGHT_UPPER;
                case BACK_LEFT_LOWER -> WEST_BACK_LEFT_LOWER;
                case BACK_LEFT_UPPER -> WEST_BACK_LEFT_UPPER;
                case BACK_RIGHT_UPPER -> WEST_BACK_RIGHT_UPPER;
                default -> WEST_FRONT_LEFT_LOWER;
            };
            case SOUTH -> switch (part){
                case FRONT_LEFT_UPPER -> SOUTH_FRONT_LEFT_UPPER;
                case FRONT_RIGHT_LOWER -> SOUTH_FRONT_RIGHT_LOWER;
                case FRONT_RIGHT_UPPER -> SOUTH_FRONT_RIGHT_UPPER;
                case BACK_LEFT_LOWER -> SOUTH_BACK_LEFT_LOWER;
                case BACK_LEFT_UPPER -> SOUTH_BACK_LEFT_UPPER;
                case BACK_RIGHT_UPPER -> SOUTH_BACK_RIGHT_UPPER;
                default -> SOUTH_FRONT_LEFT_LOWER;
            };
            case EAST -> switch (part){
                case FRONT_LEFT_UPPER -> EAST_FRONT_LEFT_UPPER;
                case FRONT_RIGHT_LOWER -> EAST_FRONT_RIGHT_LOWER;
                case FRONT_RIGHT_UPPER -> EAST_FRONT_RIGHT_UPPER;
                case BACK_LEFT_LOWER -> EAST_BACK_LEFT_LOWER;
                case BACK_LEFT_UPPER -> EAST_BACK_LEFT_UPPER;
                case BACK_RIGHT_UPPER -> EAST_BACK_RIGHT_UPPER;
                default -> EAST_FRONT_LEFT_LOWER;
            };
            default -> switch (part){
                case FRONT_LEFT_UPPER -> NORTH_FRONT_LEFT_UPPER;
                case FRONT_RIGHT_LOWER -> NORTH_FRONT_RIGHT_LOWER;
                case FRONT_RIGHT_UPPER -> NORTH_FRONT_RIGHT_UPPER;
                case BACK_LEFT_LOWER -> NORTH_BACK_LEFT_LOWER;
                case BACK_LEFT_UPPER -> NORTH_BACK_LEFT_UPPER;
                case BACK_RIGHT_UPPER -> NORTH_BACK_RIGHT_UPPER;
                default -> NORTH_FRONT_LEFT_LOWER;
            };
        };
    }
    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
        if (pDirection == getNeighbourDirection(pState.getValue(PART), pState.getValue(FACING)) && pState.getValue(PART) != GrandPianoPart.BACK_LEFT_LOWER) {
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else if (pDirection == getNeighbourDirectionAlt(pState.getValue(PART), pState.getValue(FACING)) && pState.getValue(PART) != GrandPianoPart.FRONT_RIGHT_LOWER) {
                return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                        ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                        : Blocks.AIR.defaultBlockState();
        } else if (pDirection == getNeighbourDirection(pState.getValue(PART)) && pState.getValue(PART) != GrandPianoPart.BACK_RIGHT_UPPER) {
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
        }
    }
    private static Direction getNeighbourDirection(GrandPianoPart pPart, Direction pDirection) {
        return (pPart == GrandPianoPart.FRONT_LEFT_LOWER || pPart == GrandPianoPart.FRONT_LEFT_UPPER || pPart == GrandPianoPart.BACK_LEFT_UPPER) ? pDirection.getCounterClockWise() : pDirection.getClockWise();
    }
    private static Direction getNeighbourDirectionAlt(GrandPianoPart pPart, Direction pDirection) {
        return (pPart == GrandPianoPart.FRONT_LEFT_LOWER || pPart == GrandPianoPart.FRONT_LEFT_UPPER || pPart == GrandPianoPart.FRONT_RIGHT_UPPER) ? pDirection.getOpposite() : pDirection;
    }
    private static Direction getNeighbourDirection(GrandPianoPart pPart) {
        return (pPart == GrandPianoPart.FRONT_LEFT_LOWER || pPart == GrandPianoPart.FRONT_RIGHT_LOWER || pPart == GrandPianoPart.BACK_LEFT_LOWER) ? Direction.UP : Direction.DOWN;
    }
    @Override
    public @NotNull BlockState playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
        if (!pLevel.isClientSide && (pPlayer.isCreative() || !pPlayer.hasCorrectToolForDrops(pState, pLevel, pPos))) {
            GrandPianoPart part = pState.getValue(PART);
            if (part != GrandPianoPart.FRONT_RIGHT_LOWER) {
                BlockPos blockpos = switch (part) {
                    case FRONT_LEFT_LOWER ->  pPos.relative(getNeighbourDirection(part, pState.getValue(FACING)));
                    case FRONT_LEFT_UPPER -> pPos.relative(getNeighbourDirection(part, pState.getValue(FACING))).below();
                    case FRONT_RIGHT_UPPER -> pPos.below();
                    case BACK_LEFT_LOWER -> pPos.relative(getNeighbourDirectionAlt(part, pState.getValue(FACING))).relative(getNeighbourDirection(part, pState.getValue(FACING)));
                    case BACK_LEFT_UPPER -> pPos.relative(getNeighbourDirectionAlt(part, pState.getValue(FACING))).relative(getNeighbourDirection(part, pState.getValue(FACING))).below();
                    case BACK_RIGHT_UPPER -> pPos.relative(getNeighbourDirectionAlt(part, pState.getValue(FACING))).below();
                    default -> null;
                };
                BlockState blockstate = pLevel.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(PART) == GrandPianoPart.FRONT_RIGHT_LOWER) {
                    pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getClockWise());
        BlockPos blockpos2 = blockpos.relative(direction);
        BlockPos blockpos3 = blockpos1.relative(direction);
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext) && level.getBlockState(blockpos1.above()).canBeReplaced(pContext)
        && level.getBlockState(blockpos2.above()).canBeReplaced(pContext) && level.getBlockState(blockpos3.above()).canBeReplaced(pContext)) {
            return level.getBlockState(blockpos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos1)
                    && level.getBlockState(blockpos2).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos2)
                    ? this.defaultBlockState().setValue(FACING, direction.getOpposite())
                    : null;
        } else {
            return null;
        }
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, @NotNull ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(PART, GrandPianoPart.FRONT_LEFT_UPPER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()), pState.setValue(PART, GrandPianoPart.FRONT_RIGHT_LOWER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()).above(), pState.setValue(PART, GrandPianoPart.FRONT_RIGHT_UPPER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getOpposite()), pState.setValue(PART, GrandPianoPart.BACK_LEFT_LOWER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getOpposite()).above(), pState.setValue(PART, GrandPianoPart.BACK_LEFT_UPPER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()).relative(pState.getValue(FACING).getOpposite()).above(), pState.setValue(PART, GrandPianoPart.BACK_RIGHT_UPPER), 3);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, PART);
    }
}
