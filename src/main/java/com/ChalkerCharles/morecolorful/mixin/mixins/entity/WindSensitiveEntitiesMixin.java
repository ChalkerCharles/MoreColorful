package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
        ExperienceOrb.class,
        FallingBlockEntity.class,
        ItemEntity.class,
        PrimedTnt.class,
        VehicleEntity.class
})
public abstract class WindSensitiveEntitiesMixin implements WindSensitive {
}
