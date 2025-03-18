package com.ChalkerCharles.morecolorful.common.item.common;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

public class DuckweedsItem extends BlockItem {
    public DuckweedsItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_220229_) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        BlockHitResult result = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos pos = result.getBlockPos();
        BlockHitResult result1 = pLevel.getBlockState(pos).is(ModBlocks.DUCKWEEDS) ? result.withPosition(pos) : result.withPosition(pos.above());
        InteractionResult interactionresult = super.useOn(new UseOnContext(pPlayer, pHand, result1));
        return new InteractionResultHolder<>(interactionresult, pPlayer.getItemInHand(pHand));
    }
}
