package com.ChalkerCharles.morecolorful.common.block.musical;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.entity.MusicBoxBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.properties.InstrumentExtension;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MusicBoxBlock extends BaseEntityBlock {
    public static final MapCodec<MusicBoxBlock> CODEC = simpleCodec(MusicBoxBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty SHEET = ModBlockStateProperties.SHEET;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<NoteBlockInstrument> INSTRUMENT = BlockStateProperties.NOTEBLOCK_INSTRUMENT;
    private static final VoxelShape SHAPE = Block.box(3.0, 0.0, 4.0, 13.0, 6.0, 12.0);
    private static final VoxelShape SHAPE2 = Block.box(4.0, 0.0, 3.0, 12.0, 6.0, 13.0);
    private static final Map<NoteBlockInstrument, Holder<SoundEvent>> NOTE_BLOCK_TO_MUSIC_BOX = new ImmutableMap.Builder<NoteBlockInstrument, Holder<SoundEvent>>()
            .put(NoteBlockInstrument.HARP, ModSounds.MUSIC_BOX_HARP)
            .put(NoteBlockInstrument.BASEDRUM, ModSounds.MUSIC_BOX_BASEDRUM)
            .put(NoteBlockInstrument.SNARE, ModSounds.MUSIC_BOX_SNARE)
            .put(NoteBlockInstrument.HAT, ModSounds.MUSIC_BOX_HAT)
            .put(NoteBlockInstrument.BASS, ModSounds.MUSIC_BOX_BASS)
            .put(NoteBlockInstrument.CHIME, ModSounds.MUSIC_BOX_CHIME)
            .put(NoteBlockInstrument.BELL, ModSounds.MUSIC_BOX_BELL)
            .put(NoteBlockInstrument.FLUTE, ModSounds.MUSIC_BOX_FLUTE)
            .put(NoteBlockInstrument.GUITAR, ModSounds.MUSIC_BOX_GUITAR)
            .put(NoteBlockInstrument.XYLOPHONE, ModSounds.MUSIC_BOX_XYLOPHONE)
            .put(NoteBlockInstrument.IRON_XYLOPHONE, ModSounds.MUSIC_BOX_IRON_XYLOPHONE)
            .put(NoteBlockInstrument.COW_BELL, ModSounds.MUSIC_BOX_COW_BELL)
            .put(NoteBlockInstrument.DIDGERIDOO, ModSounds.MUSIC_BOX_DIDGERIDOO)
            .put(NoteBlockInstrument.BIT, ModSounds.MUSIC_BOX_BIT)
            .put(NoteBlockInstrument.BANJO, ModSounds.MUSIC_BOX_BANJO)
            .put(NoteBlockInstrument.PLING, ModSounds.MUSIC_BOX_PLING)
            .put(InstrumentExtension.PIANO_LOW, ModSounds.MUSIC_BOX_PIANO_LOW)
            .put(InstrumentExtension.PIANO_HIGH, ModSounds.MUSIC_BOX_PIANO_HIGH)
            .put(InstrumentExtension.TOM, ModSounds.MUSIC_BOX_TOM)
            .put(InstrumentExtension.RIDE, ModSounds.MUSIC_BOX_RIDE)
            .put(InstrumentExtension.CRASH, ModSounds.MUSIC_BOX_CRASH)
            .put(InstrumentExtension.VIOLIN, ModSounds.MUSIC_BOX_VIOLIN)
            .put(InstrumentExtension.CELLO, ModSounds.MUSIC_BOX_CELLO)
            .put(InstrumentExtension.ELECTRIC_GUITAR, ModSounds.MUSIC_BOX_ELECTRIC_GUITAR)
            .put(InstrumentExtension.TRUMPET, ModSounds.MUSIC_BOX_TRUMPET)
            .put(InstrumentExtension.SAXOPHONE, ModSounds.MUSIC_BOX_SAXOPHONE)
            .put(InstrumentExtension.OCARINA, ModSounds.MUSIC_BOX_OCARINA)
            .put(InstrumentExtension.HARMONICA, ModSounds.MUSIC_BOX_HARMONICA)
            .put(InstrumentExtension.SCULK, ModSounds.MUSIC_BOX_SCULK)
            .put(InstrumentExtension.CRYSTAL, ModSounds.MUSIC_BOX_CRYSTAL)
            .put(InstrumentExtension.SAW, ModSounds.MUSIC_BOX_SAW)
            .put(InstrumentExtension.PLUCK, ModSounds.MUSIC_BOX_PLUCK)
            .put(InstrumentExtension.SYNTH_BASS, ModSounds.MUSIC_BOX_SYNTH_BASS)
            .put(InstrumentExtension.PIPA, ModSounds.MUSIC_BOX_PIPA)
            .put(InstrumentExtension.ERHU, ModSounds.MUSIC_BOX_ERHU)
            .put(InstrumentExtension.GUZHENG, ModSounds.MUSIC_BOX_GUZHENG)
            .build();

    public MusicBoxBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(SHEET, false)
                .setValue(POWERED, false)
                .setValue(INSTRUMENT, NoteBlockInstrument.HARP)
        );
    }

    @Override
    protected MapCodec<MusicBoxBlock> codec() {
        return CODEC;
    }

    public static Holder<SoundEvent> getSound(NoteBlockInstrument instrument) {
        return NOTE_BLOCK_TO_MUSIC_BOX.getOrDefault(instrument, ModSounds.MUSIC_BOX_HARP);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case WEST, EAST -> SHAPE2;
            default -> SHAPE;
        };
    }

    private static BlockState setInstrument(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        NoteBlockInstrument instrument = pLevel.getBlockState(pPos.below()).instrument();
        if (instrument.isTunable()) {
            return pState.setValue(INSTRUMENT, instrument);
        } else {
            return pState;
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return setInstrument(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState())
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(POWERED, pContext.getLevel().hasNeighborSignal(pContext.getClickedPos()));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pPlayer.isSecondaryUseActive() || !pState.getValue(SHEET)) {
            pLevel.setBlock(pPos, pState.cycle(OPEN), 3);
        } else {
            removeSheet(pPlayer, pLevel, pPos, pState);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pState.getValue(SHEET)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else if (isSheetMusic(pStack)) {
            return tryInsertSheet(pPlayer, pLevel, pPos, pState, pStack)
                    ? ItemInteractionResult.sidedSuccess(pLevel.isClientSide)
                    : ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        } else {
            return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
        }
    }

    public static boolean isSheetMusic(ItemStack stack) {
        return stack.is(ModItems.WRITABLE_SHEET_MUSIC) || stack.is(ModItems.SHEET_MUSIC);
    }

    private static boolean tryInsertSheet(Player player, Level pLevel, BlockPos pPos, BlockState pState, ItemStack pStack) {
        if (!pState.getValue(SHEET)) {
            if (!pLevel.isClientSide) {
                insertSheet(player, pLevel, pPos, pState, pStack);
            }
            return true;
        } else {
            return false;
        }
    }

    private static void insertSheet(Player player, Level pLevel, BlockPos pPos, BlockState pState, ItemStack pStack) {
        if (pLevel.getBlockEntity(pPos) instanceof MusicBoxBlockEntity blockEntity) {
            blockEntity.setSheet(pStack.consumeAndReturn(1, player));
            resetSheetState(player, pLevel, pPos, pState, true);
        }
    }

    private static void removeSheet(Player player, Level pLevel, BlockPos pPos, BlockState pState) {
        if (!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof MusicBoxBlockEntity blockEntity) {
            ItemStack item = blockEntity.sheet;
            if (!player.addItem(item)) {
                player.drop(item, false);
            }
            blockEntity.setSheet(ItemStack.EMPTY);
            resetSheetState(player, pLevel, pPos, pState, false);
        }
    }

    private static void resetSheetState(Player player, Level pLevel, BlockPos pPos, BlockState pState, boolean hasSheet) {
        BlockState blockstate = pState.setValue(SHEET, hasSheet);
        pLevel.setBlock(pPos, blockstate, 3);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(player, blockstate));
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            if (pState.getValue(SHEET)) {
                popSheet(pLevel, pPos);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        }
    }

    private static void popSheet(Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof MusicBoxBlockEntity blockEntity) {
            ItemEntity itementity = new ItemEntity(
                    level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, blockEntity.sheet
            );
            itementity.setDefaultPickUpDelay();
            level.addFreshEntity(itementity);
        }
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        boolean flag = pFacing.getAxis() == Direction.Axis.Y;
        return flag ? setInstrument(pLevel, pCurrentPos, pState) : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            boolean flag = pState.getValue(POWERED);
            if (flag != pLevel.hasNeighborSignal(pPos)) {
                if (flag) {
                    pLevel.scheduleTick(pPos, this, 4);
                } else {
                    pLevel.setBlock(pPos, pState.cycle(POWERED), 2);
                }
            }
        }
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(POWERED) && !pLevel.hasNeighborSignal(pPos)) {
            pLevel.setBlock(pPos, pState.cycle(POWERED), 2);
        }
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
        pBuilder.add(FACING, OPEN, SHEET, POWERED, INSTRUMENT);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return pLevel.getBlockEntity(pPos) instanceof MusicBoxBlockEntity musicBox ? musicBox.signalOutput() : 0;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MusicBoxBlockEntity(pPos, pState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MUSIC_BOX.get(), MusicBoxBlockEntity::tick);
    }
}
