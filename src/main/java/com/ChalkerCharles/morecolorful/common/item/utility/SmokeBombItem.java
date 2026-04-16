package com.ChalkerCharles.morecolorful.common.item.utility;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.entity.misc.SmokeBomb;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class SmokeBombItem extends Item implements ProjectileItem {
    public static final ItemLike[] ALL_ITEMS = new ItemLike[] {
            ModItems.WHITE_SMOKE_BOMB,
            ModItems.LIGHT_GRAY_SMOKE_BOMB,
            ModItems.GRAY_SMOKE_BOMB,
            ModItems.BLACK_SMOKE_BOMB,
            ModItems.BROWN_SMOKE_BOMB,
            ModItems.RED_SMOKE_BOMB,
            ModItems.ORANGE_SMOKE_BOMB,
            ModItems.YELLOW_SMOKE_BOMB,
            ModItems.LIME_SMOKE_BOMB,
            ModItems.GREEN_SMOKE_BOMB,
            ModItems.CYAN_SMOKE_BOMB,
            ModItems.LIGHT_BLUE_SMOKE_BOMB,
            ModItems.BLUE_SMOKE_BOMB,
            ModItems.PURPLE_SMOKE_BOMB,
            ModItems.MAGENTA_SMOKE_BOMB,
            ModItems.PINK_SMOKE_BOMB
    };
    private final DyeColor color;

    public SmokeBombItem(DyeColor color, Properties pProperties) {
        super(pProperties);
        this.color = color;
        DispenserBlock.registerProjectileBehavior(this);
    }

    public static DyeColor getColor(Item item) {
        if (item instanceof SmokeBombItem bombItem) {
            return bombItem.color;
        }
        return DyeColor.WHITE;
    }

    public static ItemLike itemByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_SMOKE_BOMB;
            case ORANGE -> ModItems.ORANGE_SMOKE_BOMB;
            case MAGENTA -> ModItems.MAGENTA_SMOKE_BOMB;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_SMOKE_BOMB;
            case YELLOW -> ModItems.YELLOW_SMOKE_BOMB;
            case LIME -> ModItems.LIME_SMOKE_BOMB;
            case PINK -> ModItems.PINK_SMOKE_BOMB;
            case GRAY -> ModItems.GRAY_SMOKE_BOMB;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_SMOKE_BOMB;
            case CYAN -> ModItems.CYAN_SMOKE_BOMB;
            case PURPLE -> ModItems.PURPLE_SMOKE_BOMB;
            case BLUE -> ModItems.BLUE_SMOKE_BOMB;
            case BROWN -> ModItems.BROWN_SMOKE_BOMB;
            case GREEN -> ModItems.GREEN_SMOKE_BOMB;
            case RED -> ModItems.RED_SMOKE_BOMB;
            case BLACK -> ModItems.BLACK_SMOKE_BOMB;
        };
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(
                null,
                pPlayer.getX(),
                pPlayer.getY(),
                pPlayer.getZ(),
                ModSounds.SMOKE_BOMB_THROW.get(),
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        for (ItemLike item : ALL_ITEMS) {
            pPlayer.getCooldowns().addCooldown(item.asItem(), 20);
        }
        if (!pLevel.isClientSide) {
            SmokeBomb bomb = new SmokeBomb(pPlayer, pLevel);
            bomb.setItem(itemstack);
            bomb.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(bomb);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, pPlayer);
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        SmokeBomb bomb = new SmokeBomb(pPos.x(), pPos.y(), pPos.z(), pLevel);
        bomb.setItem(pStack);
        return bomb;
    }
}
