package com.ChalkerCharles.morecolorful.common.block.utility;

import com.ChalkerCharles.morecolorful.common.entity.misc.PrimedUnderwaterTnt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class UnderwaterTntBlock extends TntBlock {
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            Level level = source.level();
            BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            PrimedUnderwaterTnt tnt = new PrimedUnderwaterTnt(level, blockpos.getX() + 0.5, blockpos.getY(), blockpos.getZ() + 0.5, null);
            level.addFreshEntity(tnt);
            level.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
            stack.shrink(1);
            return stack;
        }
    };

    public UnderwaterTntBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity living) {
        if (!level.isClientSide) {
            PrimedUnderwaterTnt tnt = new PrimedUnderwaterTnt(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, living);
            level.addFreshEntity(tnt);
            level.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(living, GameEvent.PRIME_FUSE, pos);
        }
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        if (!pLevel.isClientSide) {
            PrimedUnderwaterTnt tnt = new PrimedUnderwaterTnt(
                    pLevel, pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5, pExplosion.getIndirectSourceEntity()
            );
            int i = tnt.getFuse();
            tnt.setFuse((short)(pLevel.random.nextInt(i / 4) + i / 8));
            pLevel.addFreshEntity(tnt);
        }
    }
}
