package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.block.entity.HiHatBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Deprecated
public abstract class CymbalBlock extends BaseEntityBlock implements EntityBlock {
    public static final BooleanProperty HIT = BooleanProperty.create("hit");
    InstrumentsType pType;
    public CymbalBlock(InstrumentsType pType, Properties properties) {
        super(properties);
        this.pType = pType;
    }
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(null, null);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn (@NotNull ItemStack pStack, @NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHitResult) {
        if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != ModItems.DRUMSTICK.get() &&
                pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() != ModItems.DRUMSTICK.get()) {
            pPlayer.displayClientMessage(Component.translatable("item.morecolorful.instruments.need_drumstick"), true);
        } else {
            if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            } else {
                PlayingScreen.openPlayingScreen(pPlayer, pType, pPos);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.HIHAT.get(), HiHatBlockEntity::tick);
    }
}
