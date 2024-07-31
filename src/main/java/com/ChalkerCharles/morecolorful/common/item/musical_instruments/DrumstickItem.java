package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.common.block.musical_instruments.PercussionInstrumentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DrumstickItem extends Item {

    public DrumstickItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Player pPlayer = pContext.getPlayer();
        if (blockstate.getBlock() instanceof PercussionInstrumentBlock) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                if (pPlayer != null) {
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
        return InteractionResult.PASS;
    }
}
