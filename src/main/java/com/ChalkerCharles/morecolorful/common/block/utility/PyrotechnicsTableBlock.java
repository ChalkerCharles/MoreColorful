package com.ChalkerCharles.morecolorful.common.block.utility;

import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.menu.PyrotechnicsMenu;
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

public class PyrotechnicsTableBlock extends Block {
    public static final MapCodec<PyrotechnicsTableBlock> CODEC = simpleCodec(PyrotechnicsTableBlock::new);
    private static final Component CONTAINER_TITLE = Component.translatable("morecolorful.gui.pyrotechnics_table");

    public PyrotechnicsTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<PyrotechnicsTableBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            pPlayer.awardStat(ModStats.INTERACT_WITH_PYROTECHNICS_TABLE.get());
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((id, inventory, player) ->
                new PyrotechnicsMenu(id, inventory, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE
        );
    }
}
