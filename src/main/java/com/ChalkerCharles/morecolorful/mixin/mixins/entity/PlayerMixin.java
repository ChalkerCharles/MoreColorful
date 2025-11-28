package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntityMixin {
    @Shadow
    @Final
    private Abilities abilities;

    @Override
    public void moreColorful$applyWind() {
        if (!this.abilities.flying) {
            super.moreColorful$applyWind();
        }
    }
}
