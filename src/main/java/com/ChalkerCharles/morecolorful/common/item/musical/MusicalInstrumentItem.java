package com.ChalkerCharles.morecolorful.common.item.musical;

import com.ChalkerCharles.morecolorful.common.block.musical.DrumSetBlock;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.ChalkerCharles.morecolorful.util.MusicalInstrument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MusicalInstrumentItem extends Item implements MusicalInstrument {
    protected InstrumentsType type;

    public MusicalInstrumentItem(InstrumentsType type, Item.Properties pProperties) {
        super(pProperties);
        this.type = type;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (player != null && !player.isCrouching() && isMusicalInstrumentBlock(blockstate)) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    private static boolean isMusicalInstrumentBlock(BlockState state) {
        Block block = state.getBlock();
        return block instanceof MusicalInstrument || block instanceof DrumSetBlock;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 72000;
    }

    @Override
    public InstrumentsType getType() {
        return this.type;
    }
}
