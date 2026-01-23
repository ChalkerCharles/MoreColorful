package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.level.wind.SwirlWindZone;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(Breeze.class)
public abstract class BreezeMixin extends Monster {
    @Unique
    private SwirlWindZone moreColorful$windZone;

    private BreezeMixin(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(EntityType<? extends Monster> pEntityType, Level pLevel, CallbackInfo ci) {
        if (!Config.windSystem) return;
        float scale = this.getScale();
        this.moreColorful$windZone = new SwirlWindZone(this.position(), 2.5F * scale, 5 * scale, 16);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        SwirlWindZone zone = this.moreColorful$windZone;
        if (zone == null) return;
        float scale = this.getScale();
        long[] oldSections = zone.sections;
        zone.setSizeAndPos(2.5F * scale, 5 * scale, this.position());
        long[] newSections = zone.getSections();
        if (!Arrays.equals(oldSections, newSections)) {
            LevelSavedData.updateWindZoneSections(level(), zone, oldSections, newSections);
            zone.sections = newSections;
        }
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (moreColorful$windZone == null) return;
        LevelSavedData.addWindZone(level(), moreColorful$windZone);
    }

    @Override
    public void onRemovedFromLevel() {
        super.onRemovedFromLevel();
        if (moreColorful$windZone == null) return;
        this.moreColorful$windZone.remove();
    }
}
