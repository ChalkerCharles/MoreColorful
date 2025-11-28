package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.common.block.entity.RideCymbalBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.RideCymbalBlock;
import com.ChalkerCharles.morecolorful.util.client.AnimationUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class RideCymbalRenderer implements BlockEntityRenderer<RideCymbalBlockEntity> {
    private final ModelPart cymbal;

    public RideCymbalRenderer(BlockEntityRendererProvider.Context context) {
        this.cymbal = context.bakeLayer(ModModelLayers.RIDE_CYMBAL).getChild("ride_cymbal");
    }

    public static LayerDefinition create() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot().addOrReplaceChild("ride_cymbal", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -6.0F, 12.0F, 0.0F, 12.0F),
                PartPose.offset(8.0F, 9.0F, 8.0F)
        );
        partdefinition.addOrReplaceChild("ride_center", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void render(RideCymbalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState state = pBlockEntity.getBlockState();
        if (state.getValue(RideCymbalBlock.HALF) == DoubleBlockHalf.UPPER) {
            AnimationUtils.animateCymbalShaking(
                    pBlockEntity.ticks,
                    pBlockEntity.ticksAfterStop,
                    pBlockEntity.shaking,
                    this.cymbal,
                    pPartialTick,
                    pPoseStack,
                    pBufferSource,
                    pPackedLight,
                    pPackedOverlay
            );
        }
    }
}
