package com.ChalkerCharles.morecolorful.common.block.utility;

import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.menu.PapercraftMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class PapercraftTableBlock extends Block {
    public static final MapCodec<PapercraftTableBlock> CODEC = simpleCodec(PapercraftTableBlock::new);
    private static final Component CONTAINER_TITLE = Component.translatable("morecolorful.gui.papercraft_table");

    public PapercraftTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<PapercraftTableBlock> codec() {
        return CODEC;
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos), pPos);
            pPlayer.awardStat(ModStats.INTERACT_WITH_PAPERCRAFT_TABLE.get());
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((id, inventory, player) ->
                new PapercraftMenu(id, inventory, ContainerLevelAccess.create(pLevel, pPos), pPos), CONTAINER_TITLE
        );
    }
}
