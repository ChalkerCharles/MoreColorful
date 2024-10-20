package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.MusicalInstrumentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MusicalInstrumentItem extends Item{
    protected InstrumentsType pType;
    public MusicalInstrumentItem(InstrumentsType pType, Item.Properties pProperties) {
        super(pProperties);
        this.pType = pType;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (player != null && !player.isCrouching() && (blockstate.getBlock() instanceof MusicalInstrumentBlock || blockstate.getBlock() instanceof DrumSetBlock)) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 72000;
    }

    public InstrumentsType getType() {
        return pType;
    }
}
