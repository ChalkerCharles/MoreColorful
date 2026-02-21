package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.entity.misc.PaperPlane;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class PaperPlaneItem extends Item implements ProjectileItem {
    public static final DispenseConfig CONFIG = DispenseConfig.builder().power(0.8F).build();

    public PaperPlaneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(
                null,
                pPlayer.getX(),
                pPlayer.getY(),
                pPlayer.getZ(),
                ModSounds.PAPER_PLANE_THROW.get(),
                SoundSource.PLAYERS,
                0.5F,
                0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (!pLevel.isClientSide) {
            PaperPlane paperPlane = new PaperPlane(pLevel, pPlayer);
            paperPlane.setItem(itemstack);
            paperPlane.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.9F, 1.0F);
            pLevel.addFreshEntity(paperPlane);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, pPlayer);
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        PaperPlane paperPlane = new PaperPlane(pLevel, pPos.x(), pPos.y(), pPos.z());
        paperPlane.setItem(pStack);
        return paperPlane;
    }

    @Override
    public DispenseConfig createDispenseConfig() {
        return CONFIG;
    }
}
