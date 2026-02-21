package com.ChalkerCharles.morecolorful.common.entity;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ObjDoubleConsumer;

public final class EntityUtils {
    public static boolean shearOffAllLeashConnections(Entity entity, @Nullable Player player) {
        boolean dropped = dropAllLeashConnections(entity, player);
        if (dropped && entity.level() instanceof ServerLevel level) {
            level.playSound(null, entity.blockPosition(), ModSounds.SHEARS_SNIP.get(), player != null ? player.getSoundSource() : entity.getSoundSource());
        }
        return dropped;
    }

    public static boolean dropAllLeashConnections(Entity entity, @Nullable Player player) {
        List<Leashable> list = ILeashableExtension.leashableLeashedTo(entity);
        boolean dropped = !list.isEmpty();
        if (entity instanceof Leashable leashable) {
            if (leashable.isLeashed()) {
                leashable.dropLeash(true, true);
                dropped = true;
            }
        }
        for (Leashable leashable : list) {
            leashable.dropLeash(true, true);
        }
        if (dropped) {
            entity.gameEvent(GameEvent.SHEAR, player);
            return true;
        } else {
            return false;
        }
    }

    public static List<Balloon> getTiedBalloons(Entity entity) {
        AABB scanArea = AABB.ofSize(entity.getBoundingBox().getCenter(), 32.0, 32.0, 32.0);
        return entity.level().getEntitiesOfClass(Balloon.class, scanArea, b -> b.getLeashHolder() == entity);
    }

    public static double getBalloonGravityFactor(Entity entity) {
        return getTiedBalloons(entity).stream().mapToDouble(b -> Math.max(0, b.getY() - entity.getY()) * 0.25).sum();
    }

    public static void addVanillaWeightAttribute(ObjDoubleConsumer<EntityType<? extends LivingEntity>> consumer) {
        consumer.accept(EntityType.ALLAY, 0.5);
        consumer.accept(EntityType.ARMADILLO, 0.8);
        consumer.accept(EntityType.AXOLOTL, 0.7);
        consumer.accept(EntityType.BEE, 0.5);
        consumer.accept(EntityType.BAT, 0.5);
        consumer.accept(EntityType.CAT, 0.6);
        consumer.accept(EntityType.CAVE_SPIDER, 0.8);
        consumer.accept(EntityType.CHICKEN, 0.6);
        consumer.accept(EntityType.COD, 0.4);
        consumer.accept(EntityType.ELDER_GUARDIAN, 2.0);
        consumer.accept(EntityType.ENDER_DRAGON, 5.0);
        consumer.accept(EntityType.ENDERMITE, 0.4);
        consumer.accept(EntityType.FOX, 0.6);
        consumer.accept(EntityType.FROG, 0.6);
        consumer.accept(EntityType.GUARDIAN, 1.2);
        consumer.accept(EntityType.GLOW_SQUID, 0.5);
        consumer.accept(EntityType.HOGLIN, 1.5);
        consumer.accept(EntityType.IRON_GOLEM, 2.0);
        consumer.accept(EntityType.MAGMA_CUBE, 0.4);
        consumer.accept(EntityType.OCELOT, 0.7);
        consumer.accept(EntityType.PANDA, 1.2);
        consumer.accept(EntityType.PARROT, 0.4);
        consumer.accept(EntityType.PIG, 0.75);
        consumer.accept(EntityType.POLAR_BEAR, 1.2);
        consumer.accept(EntityType.PUFFERFISH, 0.4);
        consumer.accept(EntityType.RABBIT, 0.4);
        consumer.accept(EntityType.RAVAGER, 1.8);
        consumer.accept(EntityType.SALMON, 0.4);
        consumer.accept(EntityType.SILVERFISH, 0.4);
        consumer.accept(EntityType.SHEEP, 0.75);
        consumer.accept(EntityType.SLIME, 0.4);
        consumer.accept(EntityType.SNIFFER, 1.2);
        consumer.accept(EntityType.SQUID, 0.5);
        consumer.accept(EntityType.TADPOLE, 0.2);
        consumer.accept(EntityType.TROPICAL_FISH, 0.4);
        consumer.accept(EntityType.VEX, 0.5);
        consumer.accept(EntityType.WARDEN, 2.0);
        consumer.accept(EntityType.WITHER, 5.0);
        consumer.accept(EntityType.WOLF, 0.8);
        consumer.accept(EntityType.ZOGLIN, 1.5);
    }
}
