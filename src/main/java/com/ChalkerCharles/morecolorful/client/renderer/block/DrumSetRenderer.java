package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.common.block.entity.DrumSetBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DrumSetRenderer implements BlockEntityRenderer<DrumSetBlockEntity> {
    public static final ResourceLocation CYMBAL_TEXTURE = MoreColorful.location("textures/entity/cymbal.png");
    private final ModelPart ride;
    private final ModelPart crash;

    public DrumSetRenderer(BlockEntityRendererProvider.Context context) {
        this.ride = context.bakeLayer(ModModelLayers.DRUM_SET_RIDE).getChild("drum_set_ride");
        this.crash = context.bakeLayer(ModModelLayers.DRUM_SET_CRASH).getChild("drum_set_crash");
    }

    public static LayerDefinition createRide() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot().addOrReplaceChild("drum_set_ride", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -6.0F, 12.0F, 0.0F, 12.0F),
                PartPose.offset(8.0F, 9.0F, 11.0F)
        );
        partdefinition.addOrReplaceChild("drum_set_ride_center", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createCrash() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot().addOrReplaceChild("drum_set_crash", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -6.0F, 12.0F, 0.0F, 12.0F),
                PartPose.offset(8.0F, 10.0F, 11.0F)
        );
        partdefinition.addOrReplaceChild("drum_set_crash_center", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void render(DrumSetBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState state = pBlockEntity.getBlockState();
        DrumSetPart part = state.getValue(DrumSetBlock.PART);
        float rot = state.getValue(HorizontalDirectionalBlock.FACING).getOpposite().toYRot();
        if (part == DrumSetPart.RIGHT_UPPER) {
            AnimationUtils.animateCymbalShakingWithOffset(
                    rot,
                    pBlockEntity.ticksRide,
                    pBlockEntity.ticksAfterStopRide,
                    pBlockEntity.shakingRide,
                    this.ride,
                    pPartialTick,
                    pPoseStack,
                    pBufferSource,
                    pPackedLight,
                    pPackedOverlay
            );
        } else if (part == DrumSetPart.LEFT_UPPER) {
            AnimationUtils.animateCymbalShakingWithOffset(
                    rot,
                    pBlockEntity.ticksCrash,
                    pBlockEntity.ticksAfterStopCrash,
                    pBlockEntity.shakingCrash,
                    this.crash,
                    pPartialTick,
                    pPoseStack,
                    pBufferSource,
                    pPackedLight,
                    pPackedOverlay
            );
        }
    }
}
