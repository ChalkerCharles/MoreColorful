package com.ChalkerCharles.morecolorful.mixin.mixins.entity.behavior;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.item.misc.PartyPopperItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.CelebrateVillagersSurvivedRaid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CelebrateVillagersSurvivedRaid.class)
public abstract class CelebrateVillagersSurvivedRaidMixin {
    @Inject(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;J)V", at = @At("HEAD"))
    private void tick(ServerLevel level, Villager owner, long gameTime, CallbackInfo ci) {
        if (owner.getRandom().nextInt(200) == 0) {
            Vec3 pos = owner.position();
            level.playSound(
                    null,
                    pos.x(), pos.y(), pos.z(),
                    ModSounds.PARTY_POPPER_POP.get(),
                    SoundSource.NEUTRAL,
                    0.8F,
                    level.random.nextFloat() * 0.2F + 0.9F
            );
            PartyPopperItem.createConfettiParticles(level, pos.x, owner.getEyeY() - 0.1, pos.z, owner.getXRot(), owner.getYRot());
        }
    }
}
