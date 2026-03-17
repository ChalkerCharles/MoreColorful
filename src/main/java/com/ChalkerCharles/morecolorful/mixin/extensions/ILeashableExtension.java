package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ILeashableExtension {
    Vec3 AXIS_SPECIFIC_ELASTICITY = new Vec3(0.8, 0.2, 0.8);
    List<Vec3> ENTITY_ATTACHMENT_POINT = ImmutableList.of(new Vec3(0.0, 0.5, 0.5));
    List<Vec3> LEASHER_ATTACHMENT_POINT = ImmutableList.of(new Vec3(0.0F, 0.5F, 0.0F));
    List<Vec3> SHARED_QUAD_ATTACHMENT_POINTS = ImmutableList.of(
            new Vec3(-0.5F, 0.5F, 0.5F),
            new Vec3(-0.5F, 0.5F, -0.5F),
            new Vec3(0.5F, 0.5F, -0.5F),
            new Vec3(0.5F, 0.5F, 0.5F)
    );

    private Leashable self() {
        return (Leashable) this;
    }
    
    private Entity entity() {
        return (Entity) this;
    }

    default boolean moreColorful$canDropLeash() {
        return true;
    }

    default boolean moreColorful$canHaveALeashAttachedTo(Entity entity) {
        if (this == entity) {
            return false;
        } else if (this.moreColorful$leashDistanceTo(entity) > this.moreColorful$leashSnapDistance()) {
            return false;
        } else {
            return self().canBeLeashed();
        }
    }

    default double moreColorful$leashDistanceTo(Entity entity) {
        return entity.getBoundingBox().getCenter().distanceTo((entity()).getBoundingBox().getCenter());
    }

    default void moreColorful$onElasticLeashPull() {
        entity().checkSlowFallDistance();
        if (this instanceof AbstractHorse horse) {
            if (horse.isEating()) {
                horse.setEating(false);
            }
            if (this instanceof Camel camel) {
                if (camel.isCamelSitting() && !camel.isInPoseTransition() && camel.canCamelChangePose()) {
                    camel.standUp();
                }
            }
        }
    }

    default double moreColorful$leashSnapDistance() {
        if (this instanceof Ghast) return 16.0;
        return 12.0;
    }

    default double moreColorful$leashElasticDistance() {
        if (this instanceof Ghast) return 10.0;
        return 6.0;
    }

    static <E extends Entity & Leashable> float angularFriction(E entity) {
        if (entity.onGround()) {
            return entity.level().getBlockState(entity.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
        } else {
            return entity.isInLiquid() ? 0.8F : 0.91F;
        }
    }

    default void moreColorful$whenLeashedTo(Entity leashHolder) {
        if (this instanceof PathfinderMob mob) {
            mob.restrictTo(leashHolder.blockPosition(), (int)this.moreColorful$leashElasticDistance() - 1);
        }
    }

    default boolean moreColorful$checkElasticInteractions(Entity leashHolder, Leashable.LeashData leashData) {
        boolean quadConnection = IEntityExtension.supportQuadLeashAsHolder(leashHolder) && this.moreColorful$supportQuadLeash();
        List<Wrench> wrenches = computeElasticInteraction(
                (Entity & Leashable) this, leashHolder,
                quadConnection ? SHARED_QUAD_ATTACHMENT_POINTS : ENTITY_ATTACHMENT_POINT,
                quadConnection ? SHARED_QUAD_ATTACHMENT_POINTS : LEASHER_ATTACHMENT_POINT
        );
        if (wrenches.isEmpty()) {
            return false;
        } else {
            Wrench result = Wrench.accumulate(wrenches).scale(quadConnection ? 0.25 : 1.0);
            LeashData.setAngularMomentum(leashData, LeashData.angularMomentum(leashData) + 10.0 * result.torque());
            Vec3 vec3 = getHolderMovement(leashHolder).subtract(entity().getKnownMovement());
            entity().addDeltaMovement(result.force().multiply(AXIS_SPECIFIC_ELASTICITY).add(vec3.scale(0.11)));
            return true;
        }
    }

    private static Vec3 getHolderMovement(Entity leashHolder) {
        if (leashHolder instanceof Mob mob) {
            if (mob.isNoAi()) {
                return Vec3.ZERO;
            }
        }

        return leashHolder.getKnownMovement();
    }

    private static <E extends Entity & Leashable> List<Wrench> computeElasticInteraction(E entity, Entity leashHolder, List<Vec3> entityAttachmentPoints, List<Vec3> leasherAttachmentPoints) {
        double slackDistance = self(entity).moreColorful$leashElasticDistance();
        Vec3 currentMovement = getHolderMovement(entity);
        float entityYRot = entity.getYRot() * Mth.DEG_TO_RAD;
        Vec3 entityDimensions = new Vec3(entity.getBbWidth(), entity.getBbHeight(), entity.getBbWidth());
        float leashHolderYRot = leashHolder.getYRot() * Mth.DEG_TO_RAD;
        Vec3 leasherDimensions = new Vec3(leashHolder.getBbWidth(), leashHolder.getBbHeight(), leashHolder.getBbWidth());
        List<Wrench> wrenches = new ArrayList<>();

        for(int i = 0; i < entityAttachmentPoints.size(); ++i) {
            Vec3 entityAttachVector = entityAttachmentPoints.get(i).multiply(entityDimensions).yRot(-entityYRot);
            Vec3 entityAttachPos = entity.position().add(entityAttachVector);
            Vec3 leasherAttachVector = leasherAttachmentPoints.get(i).multiply(leasherDimensions).yRot(-leashHolderYRot);
            Vec3 leasherAttachPos = leashHolder.position().add(leasherAttachVector);
            computeDampenedSpringInteraction(leasherAttachPos, entityAttachPos, slackDistance, currentMovement, entityAttachVector).ifPresent(wrenches::add);
        }

        return wrenches;
    }

    private static Optional<Wrench> computeDampenedSpringInteraction(Vec3 pivotPoint, Vec3 objectPosition, double springSlack, Vec3 objectMotion, Vec3 leverArm) {
        double distance = objectPosition.distanceTo(pivotPoint);
        if (distance < springSlack) {
            return Optional.empty();
        } else {
            Vec3 displacement = pivotPoint.subtract(objectPosition).normalize().scale(distance - springSlack);
            double torque = Wrench.torqueFromForce(leverArm, displacement);
            boolean sameDirectionToMovement = objectMotion.dot(displacement) >= 0.0;
            if (sameDirectionToMovement) {
                displacement = displacement.scale(0.3);
            }

            return Optional.of(new Wrench(displacement, torque));
        }
    }

    default boolean moreColorful$supportQuadLeash() {
        if (this instanceof Llama) return false;
        return switch (this) {
            case Boat ignore -> true;
            case AbstractHorse ignore -> true;
            case Ghast ignore -> true;
            case Sniffer ignore -> true;
            default -> false;
        };
    }

    default Vec3[] moreColorful$getQuadLeashOffsets() {
        return switch (this) {
            case Camel camel -> createQuadLeashOffsets(camel, 0.02, 0.48, 0.25, 0.82);
            case AbstractChestedHorse horse -> createQuadLeashOffsets(horse, 0.04, 0.41, 0.18, 0.73);
            case AbstractHorse horse -> createQuadLeashOffsets(horse, 0.04, 0.52, 0.23, 0.87);
            case Boat boat -> createQuadLeashOffsets(boat, 0.0, 0.64, 0.382, 0.88);
            case Sniffer sniffer -> createQuadLeashOffsets(sniffer, -0.01, 0.63, 0.38, 1.15);
            default -> createQuadLeashOffsets(entity(), 0.0, 0.5, 0.5, 0.5);
        };
    }

    static Vec3[] createQuadLeashOffsets(Entity entity, double frontOffset, double frontBack, double leftRight, double height) {
        float width = entity.getBbWidth();
        double frontOffsetScaled = frontOffset * width;
        double frontBackScaled = frontBack * width;
        double leftRightScaled = leftRight * width;
        double heightScaled = height * entity.getBbHeight();
        return new Vec3[] {
                new Vec3(-leftRightScaled, heightScaled, frontBackScaled + frontOffsetScaled),
                new Vec3(-leftRightScaled, heightScaled, -frontBackScaled + frontOffsetScaled),
                new Vec3(leftRightScaled, heightScaled, -frontBackScaled + frontOffsetScaled),
                new Vec3(leftRightScaled, heightScaled, frontBackScaled + frontOffsetScaled)
        };
    }

    static List<Leashable> leashableLeashedTo(Entity entity) {
        return leashableInArea(entity, l -> l.getLeashHolder() == entity);
    }

    static List<Leashable> leashableInArea(Entity entity, Predicate<Leashable> test) {
        return leashableInArea(entity.level(), entity.getBoundingBox().getCenter(), test);
    }

    static List<Leashable> leashableInArea(Level level, Vec3 pos, Predicate<Leashable> test) {
        AABB scanArea = AABB.ofSize(pos, 32.0, 32.0, 32.0);
        return level.getEntitiesOfClass(Entity.class, scanArea, e -> {
            if (e instanceof Leashable leashable) {
                return test.test(leashable);
            }
            return false;
        }).stream().map(Leashable.class::cast).toList();
    }

    private static ILeashableExtension self(Leashable leashable) {
        return (ILeashableExtension) leashable;
    }

    static boolean canDropLeash(Leashable leashable) {
        return self(leashable).moreColorful$canDropLeash();
    }

    static boolean canHaveALeashAttachedTo(Leashable leashable, Entity entity) {
        return self(leashable).moreColorful$canHaveALeashAttachedTo(entity);
    }

    static double leashDistanceTo(Leashable leashable, Entity entity) {
        return self(leashable).moreColorful$leashDistanceTo(entity);
    }

    static void onElasticLeashPull(Leashable leashable) {
        self(leashable).moreColorful$onElasticLeashPull();
    }

    static double leashSnapDistance(Leashable leashable) {
        return self(leashable).moreColorful$leashSnapDistance();
    }

    static double leashElasticDistance(Leashable leashable) {
        return self(leashable).moreColorful$leashElasticDistance();
    }

    static void whenLeashedTo(Leashable leashable, Entity entity) {
        self(leashable).moreColorful$whenLeashedTo(entity);
    }

    static boolean checkElasticInteractions(Leashable leashable, Entity leashHolder, Leashable.LeashData leashData) {
        return self(leashable).moreColorful$checkElasticInteractions(leashHolder, leashData);
    }

    static boolean supportQuadLeash(Leashable leashable) {
        return self(leashable).moreColorful$supportQuadLeash();
    }

    static Vec3[] getQuadLeashOffsets(Leashable leashable) {
        return self(leashable).moreColorful$getQuadLeashOffsets();
    }

    interface LeashData {
        double moreColorful$angularMomentum();

        void moreColorful$setAngularMomentum(double momentum);

        private static LeashData self(Leashable.LeashData data) {
            return (LeashData) (Object) data;
        }

        static double angularMomentum(Leashable.LeashData data) {
            return self(data).moreColorful$angularMomentum();
        }

        static void setAngularMomentum(Leashable.LeashData data, double momentum) {
            self(data).moreColorful$setAngularMomentum(momentum);
        }
    }

    record Wrench(Vec3 force, double torque) {
        public static final Wrench ZERO = new Wrench(Vec3.ZERO, 0.0);

        public static double torqueFromForce(Vec3 leverArm, Vec3 force) {
            return leverArm.z * force.x - leverArm.x * force.z;
        }

        public static Wrench accumulate(List<Wrench> wrenches) {
            if (wrenches.isEmpty()) {
                return ZERO;
            } else if (wrenches.size() == 1) {
                return wrenches.getFirst();
            } else {
                double x = 0.0;
                double y = 0.0;
                double z = 0.0;
                double t = 0.0;

                for (Wrench wrench : wrenches) {
                    Vec3 force = wrench.force;
                    x += force.x;
                    y += force.y;
                    z += force.z;
                    t += wrench.torque;
                }

                return new Wrench(new Vec3(x, y, z), t);
            }
        }

        public Wrench scale(double scale) {
            return new Wrench(this.force.scale(scale), this.torque * scale);
        }
    }

    class LeashState {
        public Vec3 offset = Vec3.ZERO;
        public Vec3 start = Vec3.ZERO;
        public Vec3 end = Vec3.ZERO;
        public int startBlockLight = 0;
        public int endBlockLight = 0;
        public int startSkyLight = 15;
        public int endSkyLight = 15;
        public boolean slack = true;
    }
}
