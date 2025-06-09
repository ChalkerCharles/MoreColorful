package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.common.block.entity.AbstractCymbalBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.CrashCymbalBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.DrumSetBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.RideCymbalBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import com.ChalkerCharles.morecolorful.util.AnimationUtils;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.PART;

@OnlyIn(Dist.CLIENT)
public class CymbalRenderer implements BlockEntityRenderer<AbstractCymbalBlockEntity> {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final ResourceLocation CYMBAL_TEXTURE = MoreColorful.location("textures/entity/cymbal.png");
    private final ModelPart rideCymbal;
    private final ModelPart crashCymbal;
    private final ModelPart drumSetRide;
    private final ModelPart drumSetCrash;

    public CymbalRenderer(BlockEntityRendererProvider.Context context) {
        this.rideCymbal = context.bakeLayer(ModModelLayers.RIDE_CYMBAL).getChild("ride_cymbal");
        this.crashCymbal = context.bakeLayer(ModModelLayers.CRASH_CYMBAL).getChild("crash_cymbal");
        this.drumSetRide = context.bakeLayer(ModModelLayers.DRUM_SET_RIDE).getChild("drum_set_ride");
        this.drumSetCrash = context.bakeLayer(ModModelLayers.DRUM_SET_CRASH).getChild("drum_set_crash");
    }

    public static LayerDefinition createRide() {
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
    public static LayerDefinition createCrash() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot().addOrReplaceChild("crash_cymbal", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -6.0F, 12.0F, 0.0F, 12.0F),
                PartPose.offset(8.0F, 10.0F, 6.0F)
        );
        partdefinition.addOrReplaceChild("crash_center", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
    public static LayerDefinition createDrumSetRide() {
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
    public static LayerDefinition createDrumSetCrash() {
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
    public void render(AbstractCymbalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        switch (pBlockEntity) {
            case RideCymbalBlockEntity blockEntity when blockEntity.getBlockState().getValue(HALF) == DoubleBlockHalf.UPPER ->
                    AnimationUtils.animateCymbalShaking(
                            blockEntity.ticks,
                            blockEntity.ticksAfterStop,
                            blockEntity.shaking,
                            rideCymbal,
                            pPartialTick,
                            pPoseStack,
                            pBufferSource,
                            pPackedLight,
                            pPackedOverlay
                    );
            case CrashCymbalBlockEntity blockEntity when blockEntity.getBlockState().getValue(HALF) == DoubleBlockHalf.UPPER ->
                    AnimationUtils.animateCymbalShakingWithOffset(
                            blockEntity,
                            blockEntity.ticks,
                            blockEntity.ticksAfterStop,
                            blockEntity.shaking,
                            crashCymbal,
                            pPartialTick,
                            pPoseStack,
                            pBufferSource,
                            pPackedLight,
                            pPackedOverlay
                    );
            case DrumSetBlockEntity blockEntity when blockEntity.getBlockState().getValue(PART) == DrumSetPart.RIGHT_UPPER ->
                    AnimationUtils.animateCymbalShakingWithOffset(
                            blockEntity,
                            blockEntity.ticksRide,
                            blockEntity.ticksAfterStopRide,
                            blockEntity.shakingRide,
                            drumSetRide,
                            pPartialTick,
                            pPoseStack,
                            pBufferSource,
                            pPackedLight,
                            pPackedOverlay
                    );
            case DrumSetBlockEntity blockEntity when blockEntity.getBlockState().getValue(PART) == DrumSetPart.LEFT_UPPER ->
                    AnimationUtils.animateCymbalShakingWithOffset(
                            blockEntity,
                            blockEntity.ticksCrash,
                            blockEntity.ticksAfterStopCrash,
                            blockEntity.shakingCrash,
                            drumSetCrash,
                            pPartialTick,
                            pPoseStack,
                            pBufferSource,
                            pPackedLight,
                            pPackedOverlay
                    );
            default -> {}
        }
    }
}
