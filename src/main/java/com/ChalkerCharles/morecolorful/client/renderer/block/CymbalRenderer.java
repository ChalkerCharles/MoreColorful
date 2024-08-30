package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.common.block.entity.AbstractCymbalBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.CrashCymbalBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.RideCymbalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CymbalRenderer<T extends AbstractCymbalBlockEntity> implements BlockEntityRenderer<T> {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final ResourceLocation CYMBAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "textures/entity/cymbal.png");
    private final ModelPart rideCymbal;
    private final ModelPart crashCymbal;

    public CymbalRenderer(BlockEntityRendererProvider.Context context) {
        this.rideCymbal = context.bakeLayer(ModModelLayers.RIDE_CYMBAL).getChild("ride_cymbal");
        this.crashCymbal = context.bakeLayer(ModModelLayers.CRASH_CYMBAL).getChild("crash_cymbal");
    }

    public static LayerDefinition createRide() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("ride_cymbal", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -6.0F, 12.0F, 0.0F, 12.0F),
                PartPose.offset(8.0F, 9.0F, 8.0F)
        );
        partdefinition1.addOrReplaceChild("ride_center", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
    public static LayerDefinition createCrash() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("crash_cymbal", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -8.0F, 12.0F, 0.0F, 12.0F),
                PartPose.offset(8.0F, 10.0F, 8.0F)
        );
        partdefinition1.addOrReplaceChild("crash_center", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-2.0F, -0.5F, -4.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity instanceof RideCymbalBlockEntity blockEntity && blockEntity.getBlockState().getValue(HALF) == DoubleBlockHalf.UPPER) {
            float f = blockEntity.ticks + pPartialTick;
            float f0 = blockEntity.ticksAfterStop + pPartialTick;
            float f1 = 0.0F;
            float f2 = 0.0F;
            if (blockEntity.shaking) {
                float f3 = Mth.sin(f / (float) Math.PI) / (4.0F + f0 / 2.0F);
                float f4 = Mth.cos(f / (float) Math.PI) / (4.0F + f0 / 2.0F);
                f1 = f3;
                f2 = f4;
            }
            this.rideCymbal.xRot = f1;
            this.rideCymbal.zRot = f2;
            VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.entitySolid(CYMBAL_TEXTURE));
            this.rideCymbal.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
        } else if (pBlockEntity instanceof CrashCymbalBlockEntity blockEntity && blockEntity.getBlockState().getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = blockEntity.getBlockState();
            float rot = blockState.getValue(FACING).getOpposite().toYRot();
            pPoseStack.pushPose();
            pPoseStack.translate(0.5F, 0.5F, 0.5F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-rot));
            pPoseStack.translate(-0.5F, -0.5F, -0.5F);
            float f = blockEntity.ticks + pPartialTick;
            float f0 = blockEntity.ticksAfterStop + pPartialTick;
            float f1 = 0.0F;
            float f2 = 0.0F;
            if (blockEntity.shaking) {
                float f3 = Mth.sin(f / (float) Math.PI) / (4.0F + f0 / 2.0F);
                float f4 = Mth.cos(f / (float) Math.PI) / (4.0F + f0 / 2.0F);
                f1 = f3;
                f2 = f4;
            }
            this.crashCymbal.xRot = f1;
            this.crashCymbal.zRot = f2;
            VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.entitySolid(CYMBAL_TEXTURE));
            this.crashCymbal.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
            pPoseStack.popPose();
        }
    }
}
