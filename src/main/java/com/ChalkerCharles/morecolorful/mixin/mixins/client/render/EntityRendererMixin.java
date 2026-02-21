package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor.IEntityRendererMixin;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Shadow
    protected abstract int getBlockLightLevel(T pEntity, BlockPos pPos);
    @Shadow
    @Final
    protected EntityRenderDispatcher entityRenderDispatcher;

    @Inject(method = "shouldRender", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/Leashable;getLeashHolder()Lnet/minecraft/world/entity/Entity;", shift = At.Shift.AFTER), cancellable = true)
    private void shouldRender(T entity, Frustum frustum, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir,
                              @Local AABB aabb, @Local(ordinal = 1) Entity leashHolder) {
        if (entity instanceof Balloon && leashHolder == null) {
            AABB aabb1 = aabb.setMinY(aabb.minY - 4);
            cir.setReturnValue(frustum.isVisible(aabb1));
        }
    }

    @WrapOperation(method = "shouldRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/culling/Frustum;isVisible(Lnet/minecraft/world/phys/AABB;)Z", ordinal = 1))
    private boolean shouldRender(Frustum instance, AABB aabb, Operation<Boolean> original, @Local AABB aabb1) {
        boolean b = original.call(instance, aabb);
        if (Config.enhancedLeash) {
            return b || instance.isVisible(aabb1.minmax(aabb));
        } else {
            return b;
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render$renderLeash(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
        if (!Config.enhancedLeash) return;
        List<ILeashableExtension.LeashState> list = moreColorful$getLeashStates(entity, partialTick);
        if (!list.isEmpty()) {
            for (ILeashableExtension.LeashState leashState : list) {
                moreColorful$renderLeash(poseStack, bufferSource, leashState);
            }
        }
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;getLeashHolder()Lnet/minecraft/world/entity/Entity;"))
    private Entity render$cancelVanillaLeashRender(Entity original, T entity) {
        if (Config.enhancedLeash) {
            return null;
        } else if (entity instanceof Balloon && original == null) {
            return entity;
        } else {
            return original;
        }
    }

    @Inject(method = "renderLeash", at = @At("HEAD"))
    private <E extends Entity> void renderLeash(T entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, E leashHolder, CallbackInfo ci,
                                                @Share("untiedBalloon")LocalBooleanRef untiedBalloon) {
        untiedBalloon.set(entity instanceof Balloon && leashHolder == entity);
    }

    @WrapOperation(method = "renderLeash", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getRopeHoldPosition(F)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 renderLeash$ropePos(Entity instance, float partialTicks, Operation<Vec3> original, @Share("untiedBalloon")LocalBooleanRef untiedBalloon) {
        return untiedBalloon.get()
                ? Balloon.getUntiedRopePos(instance, partialTicks)
                : original.call(instance, partialTicks);
    }

    @WrapOperation(method = "renderLeash", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;", ordinal = 1))
    private Vec3 renderLeash$blockPos(Entity instance, float partialTicks, Operation<Vec3> original, @Share("untiedBalloon")LocalBooleanRef untiedBalloon) {
        return untiedBalloon.get()
                ? Balloon.getUntiedRopeBlockPos(instance, partialTicks)
                : original.call(instance, partialTicks);
    }

    @Unique
    private List<ILeashableExtension.LeashState> moreColorful$getLeashStates(T entity, float partialTicks) {
        if (entity instanceof Leashable leashable) {
            Level level = entity.level();
            Entity leashHolder = leashable.getLeashHolder();
            boolean untiedBalloon = entity instanceof Balloon && leashHolder == null;
            if (untiedBalloon) leashHolder = entity;
            if (leashHolder != null) {
                float entityYRot = entity.getPreciseBodyRotation(partialTicks) * Mth.DEG_TO_RAD;
                Vec3 attachOffset = entity.getLeashOffset(partialTicks);
                BlockPos entityEyePos = BlockPos.containing(entity.getEyePosition(partialTicks));
                BlockPos roperEyePos = untiedBalloon
                        ? BlockPos.containing(Balloon.getUntiedRopeBlockPos(entity, partialTicks))
                        : BlockPos.containing(leashHolder.getEyePosition(partialTicks));
                int startBlockLight = this.getBlockLightLevel(entity, entityEyePos);
                int endBlockLight = ((IEntityRendererMixin) this.entityRenderDispatcher.getRenderer(leashHolder)).invokeGetBlockLightLevel(leashHolder, roperEyePos);
                int startSkyLight = level.getBrightness(LightLayer.SKY, entityEyePos);
                int endSkyLight = level.getBrightness(LightLayer.SKY, roperEyePos);
                boolean quadConnection = IEntityExtension.supportQuadLeashAsHolder(leashHolder) && ILeashableExtension.supportQuadLeash(leashable);
                int leashCount = quadConnection ? 4 : 1;
                List<ILeashableExtension.LeashState> leashStates = new ArrayList<>(leashCount);
                for(int i = 0; i < leashCount; ++i) {
                    leashStates.add(new ILeashableExtension.LeashState());
                }

                if (quadConnection) {
                    float roperYRot = leashHolder.getPreciseBodyRotation(partialTicks) * Mth.DEG_TO_RAD;
                    Vec3 holderPos = leashHolder.getPosition(partialTicks);
                    Vec3[] leashableAttachmentPoints = ILeashableExtension.getQuadLeashOffsets(leashable);
                    Vec3[] roperAttachmentPoints = IEntityExtension.getQuadLeashHolderOffsets(leashHolder);
                    for (int i = 0; i < leashCount; i++) {
                        ILeashableExtension.LeashState leashState = leashStates.get(i);
                        leashState.offset = leashableAttachmentPoints[i].yRot(-entityYRot);
                        leashState.start = entity.getPosition(partialTicks).add(leashState.offset);
                        leashState.end = holderPos.add(roperAttachmentPoints[i].yRot(-roperYRot));
                        leashState.startBlockLight = startBlockLight;
                        leashState.endBlockLight = endBlockLight;
                        leashState.startSkyLight = startSkyLight;
                        leashState.endSkyLight = endSkyLight;
                        leashState.slack = false;
                    }
                } else {
                    Vec3 rotatedAttachOffset = attachOffset.yRot(-entityYRot);
                    ILeashableExtension.LeashState leashState = leashStates.getFirst();
                    leashState.offset = rotatedAttachOffset;
                    leashState.start = entity.getPosition(partialTicks).add(rotatedAttachOffset);
                    leashState.end = untiedBalloon
                            ? Balloon.getUntiedRopePos(entity, partialTicks)
                            : leashHolder.getRopeHoldPosition(partialTicks);
                    leashState.startBlockLight = startBlockLight;
                    leashState.endBlockLight = endBlockLight;
                    leashState.startSkyLight = startSkyLight;
                    leashState.endSkyLight = endSkyLight;
                }
                return leashStates;
            }
        }
        return List.of();
    }

    @Unique
    private static void moreColorful$renderLeash(PoseStack poseStack, MultiBufferSource multiBufferSource, ILeashableExtension.LeashState leashState) {
        int n;
        float f = (float)(leashState.end.x - leashState.start.x);
        float f2 = (float)(leashState.end.y - leashState.start.y);
        float f3 = (float)(leashState.end.z - leashState.start.z);
        float f4 = Mth.invSqrt(f * f + f3 * f3) * 0.05f / 2.0f;
        float f5 = f3 * f4;
        float f6 = f * f4;
        poseStack.pushPose();
        Vec3 offset = leashState.offset;
        poseStack.translate(offset.x, offset.y, offset.z);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.leash());
        Matrix4f matrix4f = poseStack.last().pose();
        for (n = 0; n <= 24; ++n) {
            moreColorful$addVertexPair(vertexConsumer, matrix4f, f, f2, f3, 0.05f, 0.05f, f5, f6, n, false, leashState);
        }
        for (n = 24; n >= 0; --n) {
            moreColorful$addVertexPair(vertexConsumer, matrix4f, f, f2, f3, 0.05f, 0.0f, f5, f6, n, true, leashState);
        }
        poseStack.popPose();
    }

    @Unique
    private static void moreColorful$addVertexPair(VertexConsumer vertexConsumer, Matrix4f matrix4f, float startX, float startY, float startZ,
                                                   float yOffset, float dy, float dx, float dz, int index, boolean reverse, ILeashableExtension.LeashState leashState) {
        float f8 = index / 24.0f;
        int n2 = (int)Mth.lerp(f8, leashState.startBlockLight, leashState.endBlockLight);
        int n3 = (int)Mth.lerp(f8, leashState.startSkyLight, leashState.endSkyLight);
        int n4 = LightTexture.pack(n2, n3);
        float f9 = index % 2 == (reverse ? 1 : 0) ? 0.7f : 1.0f;
        float f10 = 0.5f * f9;
        float f11 = 0.4f * f9;
        float f12 = 0.3f * f9;
        float f13 = startX * f8;
        float f14 = leashState.slack ? (startY > 0.0f ? startY * f8 * f8 : startY - startY * (1.0f - f8) * (1.0f - f8)) : startY * f8;
        float f15 = startZ * f8;
        vertexConsumer.addVertex(matrix4f, f13 - dx, f14 + dy, f15 + dz).setColor(f10, f11, f12, 1.0f).setLight(n4);
        vertexConsumer.addVertex(matrix4f, f13 + dx, f14 + yOffset - dy, f15 - dz).setColor(f10, f11, f12, 1.0f).setLight(n4);
    }
}
