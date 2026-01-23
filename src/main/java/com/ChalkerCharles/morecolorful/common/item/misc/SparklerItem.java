package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.Map;

public class SparklerItem extends Item {
    public static final ItemLike[] ALL_ITEMS = new ItemLike[] {
            ModItems.WHITE_SPARKLER,
            ModItems.BROWN_SPARKLER,
            ModItems.RED_SPARKLER,
            ModItems.ORANGE_SPARKLER,
            ModItems.YELLOW_SPARKLER,
            ModItems.LIME_SPARKLER,
            ModItems.GREEN_SPARKLER,
            ModItems.CYAN_SPARKLER,
            ModItems.LIGHT_BLUE_SPARKLER,
            ModItems.BLUE_SPARKLER,
            ModItems.PURPLE_SPARKLER,
            ModItems.MAGENTA_SPARKLER,
            ModItems.PINK_SPARKLER
    };
    public static final Lazy<Map<Item, Pair<ParticleOptions, ParticleOptions>>> COLOR_MAP = Lazy.of(() ->
            ImmutableMap.<Item, Pair<ParticleOptions, ParticleOptions>>builder()
                    .put(ModItems.WHITE_SPARKLER.get(), createPair(0xb4d8ca))
                    .put(ModItems.BROWN_SPARKLER.get(), createPair(0xcc8654))
                    .put(ModItems.RED_SPARKLER.get(), createPair(0xff7777))
                    .put(ModItems.ORANGE_SPARKLER.get(), createPair(0xf3791b))
                    .put(ModItems.YELLOW_SPARKLER.get(), createPair(0xffcb00))
                    .put(ModItems.LIME_SPARKLER.get(), createPair(0xaae42a))
                    .put(ModItems.GREEN_SPARKLER.get(), createPair(0x69b26e))
                    .put(ModItems.CYAN_SPARKLER.get(), createPair(0x00e4e6))
                    .put(ModItems.LIGHT_BLUE_SPARKLER.get(), createPair(0x00abff))
                    .put(ModItems.BLUE_SPARKLER.get(), createPair(0x6b80f6))
                    .put(ModItems.PURPLE_SPARKLER.get(), createPair(0xa678f1))
                    .put(ModItems.MAGENTA_SPARKLER.get(), createPair(0xf86fff))
                    .put(ModItems.PINK_SPARKLER.get(), createPair(0xf78abb))
                    .build()
    );
    
    public SparklerItem(Properties pProperties) {
        super(pProperties);
    }

    private static Pair<ParticleOptions, ParticleOptions> createPair(int color) {
        return Pair.of(
                ColorParticleOption.create(ModParticles.SPARKLER_SPARKLE.get(), color),
                ColorParticleOption.create(ModParticles.SPARKLER_GLITTER.get(), color)
        );
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemUtils.isSameItemSameComponentsExcept(newStack, oldStack, DataComponents.DAMAGE);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack sparkler = pPlayer.getItemInHand(pUsedHand);
        InteractionHand otherHand = ItemUtils.otherHand(pUsedHand);
        ItemStack firestarter = pPlayer.getItemInHand(otherHand);
        if (!sparkler.has(ModDataComponents.ACTIVATED) && firestarter.canPerformAction(ItemAbilities.FIRESTARTER_LIGHT)) {
            sparkler.set(ModDataComponents.ACTIVATED, Unit.INSTANCE);
            if (firestarter.isDamageableItem()) {
                firestarter.hurtAndBreak(1, pPlayer, LivingEntity.getSlotForHand(otherHand));
            } else {
                firestarter.consume(1, pPlayer);
            }
            pLevel.gameEvent(pPlayer, GameEvent.PRIME_FUSE, pPlayer.position());
            pLevel.playSound(null, pPlayer, ModSounds.SPARKLER_LIT.get(), SoundSource.NEUTRAL, 1.0F, pLevel.random.nextFloat() * 0.4F + 0.8F);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pPlayer.awardStat(Stats.ITEM_USED.get(firestarter.getItem()));
            return InteractionResultHolder.sidedSuccess(sparkler, pLevel.isClientSide);
        }
        return InteractionResultHolder.pass(sparkler);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.isClientSide) return;
        if (pEntity instanceof Player player && pStack.has(ModDataComponents.ACTIVATED)) {
            ServerLevel level = (ServerLevel) pLevel;
            long l = level.getGameTime();
            if (player.isUnderWater()) {
                pStack.shrink(1);
                extinguish(level, player);
            } else if (l % 20 == 0) {
                pStack.hurtAndBreak(1, level, player, i -> extinguish(level, player));
            }

            if (pIsSelected || player.getOffhandItem() == pStack) {
                RandomSource random = level.random;
                if (l % 20 == 0) {
                    pLevel.playSound(null, player, ModSounds.SPARKLER_FIZZ.get(), SoundSource.NEUTRAL, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                }
                Vec3 vec = this.getSparklerPos(player);
                Pair<ParticleOptions, ParticleOptions> pair = COLOR_MAP.get().get(this);
                if (random.nextInt(5) == 0) {
                    level.sendParticles(pair.left(), vec.x, vec.y, vec.z, 1, 0, 0, 0, 1);
                }
                if (random.nextBoolean()) {
                    level.sendParticles(pair.right(), vec.x, vec.y, vec.z, 1, 0, 0, 0, 1);
                }
            }
        }
    }

    private Vec3 getSparklerPos(Player player) {
        return player.getHandHoldingItemAngle(this)
                .add(player.calculateViewVector(0.0F,player.getYRot()).scale(0.5))
                .add(player.getX(), player.getY(0.6), player.getZ());
    }

    private static void extinguish(Level level, Entity entity) {
        RandomSource random = level.random;
        level.playSound(
                null,
                entity,
                ModSounds.SPARKLER_EXTINGUISH.get(),
                SoundSource.NEUTRAL,
                0.5F,
                2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F
        );
    }
}
