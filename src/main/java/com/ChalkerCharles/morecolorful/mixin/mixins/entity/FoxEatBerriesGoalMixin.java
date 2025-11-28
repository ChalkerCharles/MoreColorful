package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.block.nature.BerryBushBlock;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.FoxEatBerriesGoal.class)
public abstract class FoxEatBerriesGoalMixin extends MoveToBlockGoal {
    @Shadow
    @Final
    Fox this$0;

    private FoxEatBerriesGoalMixin(PathfinderMob pMob, double pSpeedModifier, int pSearchRange) {
        super(pMob, pSpeedModifier, pSearchRange);
    }

    @ModifyReturnValue(method = "isValidTarget", at = @At("TAIL"))
    private boolean isValidTarget(boolean original, @Local BlockState blockstate) {
        boolean berry = blockstate.getBlock() instanceof BerryBushBlock && blockstate.getValue(BerryBushBlock.AGE) == 4;
        return original || berry;
    }

    @Inject(method = "onReachedTarget()V", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", shift = At.Shift.AFTER))
    private void onReachedTarget(CallbackInfo ci) {
        BlockState blockstate = this$0.level().getBlockState(this.blockPos);
        if (blockstate.getBlock() instanceof BerryBushBlock bushBlock) {
            this.moreColorful$pickBerries(blockstate, bushBlock);
        }
    }

    @Unique
    private void moreColorful$pickBerries(BlockState pState, BerryBushBlock bushBlock) {
        Item berry = bushBlock.getBerry(bushBlock);
        pState.setValue(BerryBushBlock.AGE, 2);
        int j = 1 + this$0.level().random.nextInt(2);
        ItemStack itemstack = this$0.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemstack.isEmpty()) {
            this$0.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(berry));
            j--;
        }

        if (j > 0) {
            Block.popResource(this$0.level(), this.blockPos, new ItemStack(berry, j));
        }

        this$0.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        this$0.level().setBlock(this.blockPos, pState.setValue(BerryBushBlock.AGE, 2), 2);
        this$0.level().gameEvent(GameEvent.BLOCK_CHANGE, this.blockPos, GameEvent.Context.of(this$0));
    }
}
