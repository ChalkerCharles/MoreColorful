package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.CymbalBlock;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class HiHatBlockEntity extends BlockEntity {

    public HiHatBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HIHAT.get(), pPos, pBlockState);
    }

    public static void tick(@NotNull Level pLevel, BlockPos pPos, @NotNull BlockState pState, HiHatBlockEntity pBlockEntity) {
        /*if (!pLevel.isClientSide) {
            if (PlayingScreen.isOpen && PlayingScreen.isPressing && PlayingScreen.pType == InstrumentsType.HAT) {
                pLevel.setBlock(pPos, pState.setValue(CymbalBlock.HIT, true), 3);
                pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pState));
            } else {
                pLevel.setBlock(pPos, pState.setValue(CymbalBlock.HIT, false), 3);
                pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pState));
            }
        }*/
    }
}
