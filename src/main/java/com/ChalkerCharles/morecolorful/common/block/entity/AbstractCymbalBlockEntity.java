package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractCymbalBlockEntity extends BlockEntity {
    public AbstractCymbalBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    public final ArrayList<Integer> pressingPlayers(Level level, BlockPos pos) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Player player : level.players()) {
            var pos1 = (BlockPos) player.getData(ModDataAttachments.PLAYING_SCREEN_DATA)[1];
            boolean isPressing = player.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT);
            if (isPressing && pos.asLong() == pos1.asLong()) {
                list.add(player.getId());
            }
        }
        return list;
    }
}
