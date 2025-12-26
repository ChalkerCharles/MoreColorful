package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BellRenderer.class)
public abstract class BellRendererMixin {
    @Shadow
    @Final
    private ModelPart bellBody;

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BellBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private void applyWind(BellBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, CallbackInfo ci) {
        if (!RenderUtils.wavyBlocks) return;
        Level level = pBlockEntity.getLevel();
        if (level == null) return;
        BlockPos pos = pBlockEntity.getBlockPos();
        if (!RenderUtils.isWindyAt(level, pos)) return;
        BlockState state = pBlockEntity.getBlockState();
        Vector3f wind = RenderUtils.getWindSpeedAt(level, pos);
        float f = Mth.sin(RenderUtils.anim * Math.round(wind.length()) * 1.5F) * 0.5F + 0.5F;
        float angleX = wind.x * Maths.INV48, angleZ = wind.z * Maths.INV48;
        float xRot = angleX * Mth.rotLerp(f, 0.75F, 1.25F);
        float zRot = angleZ * Mth.rotLerp(f, 0.75F, 1.25F);
        BellAttachType type = state.getValue(BellBlock.ATTACHMENT);
        Direction direction = state.getValue(BellBlock.FACING);
        switch (type) {
            case FLOOR -> {
                if (direction.getAxis() == Direction.Axis.X)
                    this.bellBody.zRot += xRot;
                else this.bellBody.xRot -= zRot;
            }
            case SINGLE_WALL, DOUBLE_WALL -> {
                if (direction.getAxis() != Direction.Axis.X)
                    this.bellBody.zRot += xRot;
                else this.bellBody.xRot -= zRot;
            }
            case CEILING -> {
                this.bellBody.zRot += xRot;
                this.bellBody.xRot -= zRot;
            }
        }
    }
}
