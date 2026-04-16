package com.ChalkerCharles.morecolorful.common.item.utility;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.item.misc.CritterItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;
import java.util.Objects;

public class BugNetItem extends Item {
    private static final List<String> IGNORED_TAGS = List.of(
            "Air",
            "ArmorDropChances",
            "ArmorItems",
            "Brain",
            "CanPickUpLoot",
            "DeathTime",
            "FallDistance",
            "FallFlying",
            "Fire",
            "HandDropChances",
            "HandItems",
            "HurtByTimestamp",
            "HurtTime",
            "LeftHanded",
            "Motion",
            "OnGround",
            "PortalCooldown",
            "Pos",
            "Rotation",
            "SleepingX",
            "SleepingY",
            "SleepingZ",
            "Passengers",
            "leash",
            "UUID",
            "flower_pos",
            "Resting",
            "CropsGrownSincePollination",
            "CannotEnterHiveTicks",
            "TicksSincePollination",
            "hive_pos"
    );

    public BugNetItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand pUsedHand) {
        if (cannotCatch(target)) return InteractionResult.PASS;
        EntityType<?> type = target.getType();
        Item item = CritterItem.ITEM_BY_TYPE.get(type);
        if (item != null) {
            boolean clientside = player.level().isClientSide;
            if (!clientside) {
                target.unRide();
                ItemStack stack1 = new ItemStack(item);
                stack1.set(DataComponents.CUSTOM_NAME, target.getCustomName());
                CompoundTag tag = target.saveWithoutId(new CompoundTag());
                tag.putString("id", Objects.requireNonNullElse(target.getEncodeId(), ""));
                IGNORED_TAGS.forEach(tag::remove);
                stack1.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
                target.spawnAtLocation(stack1);
                target.discard();
                player.awardStat(Stats.ITEM_USED.get(this));
                player.level().playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        ModSounds.BUG_NET_CATCH.get(),
                        SoundSource.PLAYERS,
                        0.5F,
                        0.4F / (player.level().getRandom().nextFloat() * 0.4F + 0.8F)
                );
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(pUsedHand));
            }
            return InteractionResult.sidedSuccess(clientside);
        }
        return InteractionResult.PASS;
    }

    private static boolean cannotCatch(LivingEntity target) {
        if (target instanceof Bee bee && (bee.hasStung() || bee.isAngry())) {
            return true;
        }
        return !target.isAlive();
    }
}
