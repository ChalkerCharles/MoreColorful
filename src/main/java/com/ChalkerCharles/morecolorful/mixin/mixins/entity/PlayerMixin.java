package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntityMixin {
    @Shadow
    @Final
    private Abilities abilities;

    protected PlayerMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract ItemCooldowns getCooldowns();

    @Override
    public boolean moreColorful$isWindSensitive() {
        return !this.abilities.flying && super.moreColorful$isWindSensitive();
    }

    @Override
    public void moreColorful$blockUsingUmbrella(LivingEntity attacker) {
        super.moreColorful$blockUsingUmbrella(attacker);
        if (attacker.canDisableShield()) {
            this.getCooldowns().addCooldown(ModItems.UMBRELLA.get(), 100);
            this.getCooldowns().addCooldown(ModItems.DRIPLEAF_UMBRELLA.get(), 100);
            this.level().broadcastEntityEvent(moreColorful$self(), (byte) 30);
        }
    }
}
