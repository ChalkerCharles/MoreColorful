package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.HiHatBlock.HIT;

public class HiHatBlockEntity extends BlockEntity {

    public HiHatBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HIHAT.get(), pPos, pBlockState);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> void tick(Level pLevel, T pBlockEntity) {
        if (pBlockEntity instanceof HiHatBlockEntity) {
            Object[] obj = pBlockEntity.getData(ModDataAttachments.CYMBAL_DATA);
            var pos = (BlockPos) obj[0];
            var state = pLevel.getBlockState(pos);
            var playerList = (ArrayList<Integer>) obj[1];
            if (state.is(ModBlocks.HIHAT.get())) {
                if (!playerList.isEmpty()) {
                    pLevel.setBlock(pos, state.setValue(HIT, true), 3);
                } else {
                    pLevel.setBlock(pos, state.setValue(HIT, false), 3);
                }
            }
        }
    }
    public final ArrayList<Integer> pressingPlayers(BlockEntity blockEntity) {
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();
        ArrayList<Integer> list = new ArrayList<>();
        if (level != null) {
            for (Player player : level.players()) {
                var pos1 = (BlockPos) player.getData(ModDataAttachments.PLAYING_SCREEN_DATA)[1];
                boolean isPressing = player.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT);
                if (isPressing && pos.asLong() == pos1.asLong()) {
                    list.add(player.getId());
                }
            }
        }
        return list;
    }
}
