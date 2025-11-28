package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock;
import com.ChalkerCharles.morecolorful.util.MusicalInstrument;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DrumstickItem extends Item {
    public DrumstickItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Player player = pContext.getPlayer();
        if (isPercussionInstrument(blockstate.getBlock())) {
            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static boolean isPercussionInstrument(Block block) {
        if (block instanceof DrumSetBlock) return true;
        return block instanceof MusicalInstrument m && m.getType().isPercussion();
    }
}
