package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerRenderer.class)
public abstract class BannerRendererMixin {
    @Shadow
    @Final
    private ModelPart flag;

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/BannerRenderer;renderPatterns(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/resources/model/Material;ZLnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/level/block/entity/BannerPatternLayers;)V"))
    private void applyWind(BannerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, CallbackInfo ci) {
        if (!RenderUtils.isClientWindOn) return;
        Level level = pBlockEntity.getLevel();
        if (level == null) return;
        BlockPos pos = pBlockEntity.getBlockPos();
        BlockState state = pBlockEntity.getBlockState();
        boolean onWall = state.getBlock() instanceof WallBannerBlock;
        if (!RenderUtils.isWindyAt(level, pos)
                && !RenderUtils.isWindyAt(level, onWall ? pos.below() : pos.above()))
            return;
        Vector3f wind = RenderUtils.getWindSpeedAt(level, pos);
        Vector3f facing;
        if (onWall) {
            Direction direction = state.getValue(WallBannerBlock.FACING);
            facing = direction.step();
        } else {
            float degree = RotationSegment.convertToDegrees(state.getValue(BannerBlock.ROTATION));
            facing = new Vector3f(-Mth.sin(degree * Mth.DEG_TO_RAD), 0.0F, Mth.cos(degree * Mth.DEG_TO_RAD));
        }
        float f = Mth.sin(RenderUtils.anim * Math.round(wind.length()) * 1.5F) * 0.5F + 0.5F;
        float angle = wind.dot(facing) * Maths.INV24;
        float rot = angle * -Mth.rotLerp(f, 0.75F, 1.25F);
        if (onWall) {
            Direction direction = state.getValue(WallBannerBlock.FACING);
            BlockPos pos1 = pos.relative(direction.getOpposite());
            if (MultifaceBlock.canAttachTo(level, direction, pos1, level.getBlockState(pos1))) {
                rot = Math.min(rot, 0.075F);
            }
        }
        if (!onWall) {
            rot = Math.min(rot, 0.025F);
        }
        this.flag.xRot += rot;
    }
}
