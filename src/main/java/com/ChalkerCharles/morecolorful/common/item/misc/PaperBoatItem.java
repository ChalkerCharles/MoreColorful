package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.entity.misc.PaperBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PaperBoatItem extends Item {
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource pBlockSource, ItemStack pItem) {
            Direction direction = pBlockSource.state().getValue(DispenserBlock.FACING);
            ServerLevel level = pBlockSource.level();
            BlockPos pos = pBlockSource.pos().relative(direction);
            if (!level.getBlockState(pos).isAir()) {
                return super.execute(pBlockSource, pItem);
            }
            Vec3 vec3 = pBlockSource.center();
            double d0 = 0.5625 + ModEntities.PAPER_BOAT.get().getWidth() / 2.0;
            double d1 = vec3.x() + direction.getStepX() * d0;
            double d2 = vec3.y() + (direction.getStepY() * 1.125F);
            double d3 = vec3.z() + direction.getStepZ() * d0;
            PaperBoat paperBoat = new PaperBoat(level, d1, d2, d3);
            paperBoat.setYRot(-direction.toYRot());
            ItemStack itemstack = pItem.split(1);
            paperBoat.setItem(itemstack);
            level.addFreshEntity(paperBoat);
            return pItem;
        }
    };

    public PaperBoatItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        HitResult hitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            if (hitresult.getType() == HitResult.Type.BLOCK) {
                Vec3 location = hitresult.getLocation();
                PaperBoat paperBoat = new PaperBoat(pLevel, location.x, location.y, location.z);
                paperBoat.setItem(itemstack);
                paperBoat.setYRot(-pPlayer.getYRot());
                if (!pLevel.noCollision(paperBoat, paperBoat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!pLevel.isClientSide) {
                        pLevel.addFreshEntity(paperBoat);
                        pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, location);
                        itemstack.consume(1, pPlayer);
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide);
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }
}
