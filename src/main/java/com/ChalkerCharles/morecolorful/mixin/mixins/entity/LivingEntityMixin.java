package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.entity.ModAttributes;
import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.util.Self;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IEntityExtension, WindSensitive, Self<LivingEntity> {
    protected LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    @Final
    private AttributeMap attributes;
    @Shadow
    public abstract double getAttributeValue(Holder<Attribute> pAttribute);

    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/damagesource/DamageContainer;getNewDamage()F", ordinal = 0, shift = At.Shift.AFTER))
    private void hurt$checkUmbrellaBlock(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir,
                                         @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella) {
        if (UmbrellaItem.canBlockDamage(moreColorful$self(), source)) {
            blockedByUmbrella.set(true);
        }
    }

    @WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isDamageSourceBlocked(Lnet/minecraft/world/damagesource/DamageSource;)Z"))
    private boolean hurt$cancelVanillaBlock(LivingEntity instance, DamageSource source, Operation<Boolean> original,
                                            @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella) {
        if (blockedByUmbrella.get()) return false;
        return original.call(instance, source);
    }

    @ModifyExpressionValue(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/CommonHooks;onDamageBlock(Lnet/minecraft/world/entity/LivingEntity;Lnet/neoforged/neoforge/common/damagesource/DamageContainer;Z)Lnet/neoforged/neoforge/event/entity/living/LivingShieldBlockEvent;"))
    private LivingShieldBlockEvent hurt$onDamageBlock(LivingShieldBlockEvent original, @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella,
                                                      @Local(argsOnly = true) LocalFloatRef amount) {
        if (blockedByUmbrella.get()) {
            original.setBlocked(false);
            LivingEntity entity = original.getEntity();
            DamageSource source = original.getDamageSource();
            float damage = amount.get();
            if (damage > 0 && entity instanceof Player player) {
                UmbrellaItem.hurtUmbrella(player, damage);
            }
            if (!source.is(DamageTypeTags.IS_PROJECTILE) && source.getDirectEntity() instanceof LivingEntity living) {
                this.moreColorful$blockUsingUmbrella(living);
            }
            amount.set(0);
            if (entity instanceof ServerPlayer player) {
                if (damage > 0.0F && damage < 3.4028235E37F) {
                    player.awardStat(ModStats.DAMAGE_BLOCKED_BY_UMBRELLA.get(), Math.round(damage * 10.0F));
                }
            }
            UmbrellaItem.playBlockingSound(entity);
        }
        return original;
    }

    @Inject(method = "hurt", at = @At(value = "FIELD", target = "Lnet/minecraft/tags/DamageTypeTags;IS_FREEZING:Lnet/minecraft/tags/TagKey;", shift = At.Shift.BEFORE))
    private void hurt$setBlocked(CallbackInfoReturnable<Boolean> cir, @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella, @Local LocalBooleanRef flag) {
        if (blockedByUmbrella.get()) flag.set(true);
    }

    @WrapWithCondition(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V"))
    private boolean hurt$broadcastEvent(Level instance, Entity pEntity, byte pState, @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella) {
        return !blockedByUmbrella.get();
    }

    @ModifyExpressionValue(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/tags/DamageTypeTags;NO_KNOCKBACK:Lnet/minecraft/tags/TagKey;")))
    private boolean hurt$cancelKnockback(boolean original, @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella) {
        if (blockedByUmbrella.get()) return true;
        return original;
    }

    @WrapWithCondition(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playHurtSound(Lnet/minecraft/world/damagesource/DamageSource;)V"))
    private boolean hurt$playHurtSound(LivingEntity instance, DamageSource pSource, @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella) {
        return !blockedByUmbrella.get();
    }

    @WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V"))
    private void hurt$triggerEntityHurtPlayer(EntityHurtPlayerTrigger instance, ServerPlayer player, DamageSource source, float dealtDamage, float takenDamage, boolean blocked, Operation<Void> original,
                                              @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella) {
        blocked = !blockedByUmbrella.get() && blocked;
        original.call(instance, player, source, dealtDamage, takenDamage, blocked);
    }

    @WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V"))
    private void hurt$triggerPlayerHurtEntity(PlayerHurtEntityTrigger instance, ServerPlayer player, Entity entity, DamageSource source, float amountDealt, float amountTaken, boolean blocked, Operation<Void> original,
                                              @Share("blockedByUmbrella")LocalBooleanRef blockedByUmbrella) {
        blocked = !blockedByUmbrella.get() && blocked;
        original.call(instance, player, entity, source, amountDealt, amountTaken, blocked);
    }

//    @ModifyExpressionValue(method = "travel", at = @At(value = "CONSTANT", args = "doubleValue=0.9800000190734863"))
//    private double travel$modifyAirResistance(double original, @Local boolean flag) {
//        if (flag && UmbrellaItem.isHolding(moreColorful$self())) {
//            return original - 0.1;
//        }
//        return original;
//    }

    @Inject(method = "collectEquipmentChanges", at = @At("TAIL"))
    private void collectEquipmentChanges(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir) {
        if (cir.getReturnValue() != null && UmbrellaItem.isHolding(moreColorful$self())) {
            for (ItemAttributeModifiers.Entry entry : UmbrellaItem.UMBRELLA_ATTRIBUTE_MODIFIERS) {
                Holder<Attribute> attribute = entry.attribute();
                AttributeInstance instance = this.attributes.getInstance(attribute);
                if (instance != null) {
                    instance.addOrUpdateTransientModifier(entry.modifier());
                }
            }
        }
    }

    @Inject(method = "updateFallFlying", at = @At("HEAD"))
    private void updateFallFlying(CallbackInfo ci) {
        this.checkSlowFallDistance();
    }

    @Override
    public boolean moreColorful$isWindSensitive() {
        return this.moreColorful$horizontalWindage() > 0.0
                && this.moreColorful$verticalWindage() > 0.0;
    }

    @Override
    public double moreColorful$horizontalWindage() {
        return this.getAttributeValue(ModAttributes.HORIZONTAL_WINDAGE);
    }

    @Override
    public double moreColorful$verticalWindage() {
        return this.getAttributeValue(ModAttributes.VERTICAL_WINDAGE);
    }

    @Override
    public double moreColorful$weight() {
        double d = this.getAttributeValue(ModAttributes.WEIGHT);
        if (moreColorful$self() instanceof Slime slime) {
            d *= slime.getSize();
        }
        return d;
    }

    @Override
    public double moreColorful$weightFactor() {
        double d = this.moreColorful$weight();
        return d == 0.0 ? Double.MAX_VALUE : 1.0 / d;
    }

    @Unique
    protected void moreColorful$blockUsingUmbrella(LivingEntity attacker) {
        double strength = 1.0 - attacker.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        if (strength <= 0.0) return;
        attacker.hasImpulse = true;
        Vec3 vec3 = attacker.getDeltaMovement();
        double x = Math.copySign(this.getX() - attacker.getX(), vec3.x);
        double z = Math.copySign(this.getZ() - attacker.getZ(), vec3.z);
        if (x * x + z * z < 1.0E-5F) {
            x = (Math.random() - Math.random()) * 0.01;
            z = (Math.random() - Math.random()) * 0.01;
        }
        Vec3 vec = new Vec3(x, 0.0, z).normalize().scale(strength);
        attacker.setDeltaMovement((vec3.x + vec.x) * 0.8, (Math.abs(vec3.y) + strength) * 0.4, (vec3.z + vec.z) * 0.8);
    }
}
