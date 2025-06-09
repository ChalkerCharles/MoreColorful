package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.common.block.nature.BerryBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fox.FoxEatBerriesGoal.class)
public abstract class FoxEatBerriesGoalMixin extends MoveToBlockGoal {
    private FoxEatBerriesGoalMixin(PathfinderMob pMob, double pSpeedModifier, int pSearchRange) {
        super(pMob, pSpeedModifier, pSearchRange);
    }

    @Inject(method = "isValidTarget", at = @At("HEAD"), cancellable = true)
    private void isValidTarget(LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (blockstate.getBlock() instanceof BerryBushBlock && blockstate.getValue(BerryBushBlock.AGE) == 4) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onReachedTarget()V", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", shift = At.Shift.AFTER))
    private void onReachedTarget(CallbackInfo ci) {
        BlockState blockstate = this.mob.level().getBlockState(this.blockPos);
        if (blockstate.getBlock() instanceof BerryBushBlock bushBlock) {
            this.moreColorful$pickBerries(blockstate, bushBlock);
        }
    }

    @Unique
    private void moreColorful$pickBerries(BlockState pState, BerryBushBlock bushBlock) {
        Item berry = bushBlock.getBerry(bushBlock);
        pState.setValue(BerryBushBlock.AGE, 2);
        int j = 1 + this.mob.level().random.nextInt(2);
        ItemStack itemstack = this.mob.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemstack.isEmpty()) {
            this.mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(berry));
            j--;
        }

        if (j > 0) {
            Block.popResource(this.mob.level(), this.blockPos, new ItemStack(berry, j));
        }

        this.mob.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        this.mob.level().setBlock(this.blockPos, pState.setValue(BerryBushBlock.AGE, 2), 2);
        this.mob.level().gameEvent(GameEvent.BLOCK_CHANGE, this.blockPos, GameEvent.Context.of(this.mob));
    }
}
