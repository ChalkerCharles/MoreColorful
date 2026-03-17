package com.ChalkerCharles.morecolorful.common.item.utility;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.entity.ModAttributes;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.UmbrellaColor;
import com.ChalkerCharles.morecolorful.util.Colour;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class UmbrellaItem extends Item {
    public static final ResourceLocation UMBRELLA = MoreColorful.location("umbrella");
    public static final ItemAttributeModifiers.Entry FALL_DAMAGE_DECREASE = new ItemAttributeModifiers.Entry(
            Attributes.FALL_DAMAGE_MULTIPLIER, new AttributeModifier(UMBRELLA, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.HAND
    );
    public static final ItemAttributeModifiers.Entry SAFE_FALL_DISTANCE_INCREASE = new ItemAttributeModifiers.Entry(
            Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(UMBRELLA, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND
    );
    public static final ItemAttributeModifiers.Entry HORIZONTAL_WINDAGE_INCREASE = new ItemAttributeModifiers.Entry(
            ModAttributes.HORIZONTAL_WINDAGE, new AttributeModifier(UMBRELLA, 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.HAND
    );
    public static final ItemAttributeModifiers.Entry VERTICAL_WINDAGE_INCREASE = new ItemAttributeModifiers.Entry(
            ModAttributes.VERTICAL_WINDAGE, new AttributeModifier(UMBRELLA, 6.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.HAND
    );
    public static final ItemAttributeModifiers.Entry[] UMBRELLA_ATTRIBUTE_MODIFIERS = new ItemAttributeModifiers.Entry[] {
            FALL_DAMAGE_DECREASE, SAFE_FALL_DISTANCE_INCREASE, HORIZONTAL_WINDAGE_INCREASE, VERTICAL_WINDAGE_INCREASE
    };
    public static final CauldronInteraction CAULDRON_INTERACTION = (state, level, pos, player, hand, stack) -> {
        UmbrellaColor color = stack.get(ModDataComponents.UMBRELLA_COLOR);
        if (color == null || color.isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            if (!level.isClientSide) {
                stack.set(ModDataComponents.UMBRELLA_COLOR, UmbrellaColor.DEFAULT);
                player.awardStat(ModStats.CLEAN_UMBRELLA.get());
                LayeredCauldronBlock.lowerFillLevel(state, level, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
    };
    public static final ProjectileDeflection DEFLECTION = (projectile, entity, random) -> {
        if (entity == null) return;
        Vec3 vec3 = projectile.getDeltaMovement();
        double x = Math.copySign(entity.getX() - projectile.getX(), vec3.x);
        double z = Math.copySign(entity.getZ() - projectile.getZ(), vec3.z);
        if (x * x + z * z < 1.0E-5F) {
            x = (Math.random() - Math.random()) * 0.01;
            z = (Math.random() - Math.random()) * 0.01;
        }
        Vec3 vec = new Vec3(x, 0.0, z).normalize();
        projectile.setDeltaMovement((vec3.x + vec.x) * 0.8, Math.abs(vec3.y) * 0.6, (vec3.z + vec.z) * 0.8);
        projectile.hasImpulse = true;
    };

    public UmbrellaItem(Properties pProperties) {
        super(pProperties);
    }

    public static boolean isOpen(ItemStack stack) {
        return stack.getItem() instanceof UmbrellaItem && stack.has(ModDataComponents.OPEN);
    }

    public static boolean isHolding(LivingEntity entity) {
        return isOpen(entity.getMainHandItem()) || isOpen(entity.getOffhandItem());
    }

    public static boolean isHolding(Entity entity) {
        return entity instanceof LivingEntity living && isHolding(living);
    }

    private static boolean onCooldown(LivingEntity living) {
        return living instanceof Player player && player.getCooldowns().isOnCooldown(ModItems.UMBRELLA.get());
    }

    public static boolean canBlockDamage(LivingEntity living, DamageSource source) {
        if (onCooldown(living)) return false;
        boolean isHolding = isHolding(living);
        if (source.is(DamageTypeTags.DAMAGES_HELMET) && isHolding) return true;
        Entity entity = source.getDirectEntity();
        boolean flag = entity instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0;
        if (!source.is(DamageTypeTags.BYPASSES_SHIELD) && isHolding && !flag) {
            Vec3 vec = source.getSourcePosition();
            if (vec != null) {
                return vec.y > living.getY(1.0);
            }
        }
        return false;
    }

    public static boolean canBlock(LivingEntity living, Projectile projectile) {
        if (onCooldown(living)) return false;
        boolean flag = projectile instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0;
        if (isHolding(living) && !flag) {
            Vec3 vec = projectile.position();
            return vec.y > living.getY(1.0);
        }
        return false;
    }

    public static void playBlockingSound(Entity entity) {
        Level level = entity.level();
        if (!level.isClientSide) {
            level.playSound(
                    null,
                    entity.getX(), entity.getY(), entity.getZ(),
                    ModSounds.UMBRELLA_BLOCK.get(),
                    entity.getSoundSource(),
                    1.0F,
                    0.8F + level.random.nextFloat() * 0.4F
            );
        }
    }

    public static void addCanopy(Entity entity) {
        if (isHolding(entity)) {
            LevelSavedData.setCanopy(
                    entity.level(),
                    entity.getBlockX(),
                    entity.getY(1.2),
                    entity.getBlockZ()
            );
        }
    }

    public static void applyAirResistance(Entity entity) {
        Vec3 vec = entity.getDeltaMovement();
        if (vec.y <= 0.0) {
            if (entity.getSelfAndPassengers().anyMatch(UmbrellaItem::isHolding)) {
                entity.setDeltaMovement(vec.x, vec.y * 0.89795918, vec.z);
            }
        }
    }

    public static void hurtUmbrella(Player player, float damage) {
        if (damage >= 3.0F) {
            int i = 1 + Mth.floor(damage);
            if (!tryHurtUmbrella(player, InteractionHand.OFF_HAND, i)) {
                tryHurtUmbrella(player, InteractionHand.MAIN_HAND, i);
            }
        }
    }

    private static boolean tryHurtUmbrella(Player player, InteractionHand hand, int damage) {
        ItemStack stack = player.getItemInHand(hand);
        if (!isOpen(stack)) return false;
        if (player.level() instanceof ServerLevel level && !player.hasInfiniteMaterials()) {
            stack.hurtAndBreak(damage, level, player, item -> {
                player.onEquippedItemBroken(item, LivingEntity.getSlotForHand(hand));
                EventHooks.onPlayerDestroyItem(player, stack, hand);
            });
        }
        return true;
    }

    public static UmbrellaColor chooseColor(float chance, RandomSource random) {
        return chance < 0.001F ? UmbrellaColor.RAINBOW : Util.getRandom(UmbrellaColor.COMMON_COLORS, random);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        boolean used = false;
        if (!item.has(ModDataComponents.OPEN)) {
            open(item);
            pLevel.playSound(null, pPlayer, ModSounds.UMBRELLA_OPEN.get(), SoundSource.PLAYERS, 0.7F, pLevel.random.nextFloat() * 0.2F + 0.9F);
            used = true;
        } else if (pPlayer.isSecondaryUseActive()) {
            item.remove(ModDataComponents.OPEN);
            ItemUtils.removeAttributeModifiers(item, UMBRELLA_ATTRIBUTE_MODIFIERS);
            pLevel.playSound(null, pPlayer, ModSounds.UMBRELLA_CLOSE.get(), SoundSource.PLAYERS, 0.7F, pLevel.random.nextFloat() * 0.2F + 0.9F);
            used = true;
        }
        if (used) {
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.pass(item);
        }
    }

    public static void open(ItemStack item) {
        item.set(ModDataComponents.OPEN, Unit.INSTANCE);
        ItemUtils.addAttributeModifiers(item, UMBRELLA_ATTRIBUTE_MODIFIERS);
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        if (super.isValidRepairItem(pStack, pRepairCandidate)) return true;
        if (pStack.is(ModItems.DRIPLEAF_UMBRELLA)) {
            return pRepairCandidate.is(Items.BIG_DRIPLEAF);
        }
        return pRepairCandidate.is(Tags.Items.LEATHERS);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        UmbrellaColor umbrellaColor = pStack.get(ModDataComponents.UMBRELLA_COLOR);
        if (umbrellaColor == null || umbrellaColor.isEmpty()) return;
        List<Colour> colors = umbrellaColor.colors();
        pTooltipComponents.add(getColorName(colors.getFirst())
                .append(", ").append(getColorName(colors.get(1)))
                .append(", ").append(getColorName(colors.get(2)))
                .append(", ").append(getColorName(colors.get(3)))
                .withStyle(ChatFormatting.GRAY)
        );
        pTooltipComponents.add(getColorName(colors.get(4))
                .append(", ").append(getColorName(colors.get(5)))
                .append(", ").append(getColorName(colors.get(6)))
                .append(", ").append(getColorName(colors.get(7)))
                .withStyle(ChatFormatting.GRAY)
        );
    }

    private static MutableComponent getColorName(Colour color) {
        String name = color.isDefault() ? "empty" : color.getName();
        return Component.translatable("info.morecolorful.dye." + name);
    }
}
