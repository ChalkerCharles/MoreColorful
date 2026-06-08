package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractBird;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class BirdModel extends HierarchicalModel<AbstractBird> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    public final ModelPart head;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public BirdModel(Function<ResourceLocation, RenderType> renderType, ModelPart root) {
        super(renderType);
        this.root = root;
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.leftWing = this.body.getChild("left_wing");
        this.rightWing = this.body.getChild("right_wing");
        this.head = root.getChild("head");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public BirdModel(ModelPart root) {
        this(RenderType::entityCutoutNoCull, root);
    }

    public static LayerDefinition createSmall() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", 
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 3.0F, 3.0F)
                        .texOffs(12, 0).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 19.0F, -2.5F)
        );
        head.addOrReplaceChild("feather",
                CubeListBuilder.create().texOffs(24, -4).addBox(0.0F, -13.1F, -4.9F, 0.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 9.0F, 3.5F)
        );
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, -2.5F));

        body.addOrReplaceChild("body_main",
                CubeListBuilder.create().texOffs(0, 6).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.02F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F)
        );
        body.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(12, 9).addBox(-0.5F, -0.1F, -1.4F, 1.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(1.5F, 0.6F, 0.5F, 0.7854F, 0.0F, 0.0F)
        );
        body.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(20, 9).addBox(-0.5F, -0.1F, -1.4F, 1.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(-1.5F, 0.6F, 0.5F, 0.7854F, 0.0F, 0.0F)
        );

        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(17, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(-1.0F, 22.5F, -0.5F)
        );
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(13, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(1.0F, 22.5F, -0.5F)
        );
        root.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(24, 4).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.5F, 1.0F, 1.1345F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    public static LayerDefinition createBig() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", 
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 3.0F, 3.0F)
                        .texOffs(26, 4).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 17.5F, -3.5F)
        );
        head.addOrReplaceChild("feather",
                CubeListBuilder.create().texOffs(24, -4).addBox(0.0F, -11.95F, -4.9F, 0.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 8.95F, 3.5F)
        );

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create(), 
                PartPose.offset(0.0F, 17.5F, -3.5F)
        );
        body.addOrReplaceChild("body_main", 
                CubeListBuilder.create().texOffs(0, 6).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.02F)), 
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F)
        );
        body.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(12, 8).addBox(-0.5F, -0.1F, -1.4F, 1.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(1.5F, 0.6F, 0.5F, 0.7854F, 0.0F, 0.0F)
        );

        body.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(20, 8).addBox(-0.5F, -0.1F, -1.4F, 1.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(-1.5F, 0.6F, 0.5F, 0.7854F, 0.0F, 0.0F)
        );

        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(1.0F, 22.5F, -0.5F)
        );
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(17, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(-1.0F, 22.5F, -0.5F)
        );
        root.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(13, 3).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.5F, 1.0F, 1.1345F, 0.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(AbstractBird pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.setupAnim(getState(pEntity), pEntity.getVariant().isBig(), pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

    @Override
    public void prepareMobModel(AbstractBird pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.prepare(getState(pEntity), pEntity.getVariant().isBig());
    }

    public void renderOnShoulder(
            PoseStack pPoseStack,
            VertexConsumer pBuffer,
            int pPackedLight,
            int pPackedOverlay,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pNetHeadYaw,
            float pHeadPitch,
            boolean isBig) {
        this.prepare(State.ON_SHOULDER, isBig);
        this.setupAnim(State.ON_SHOULDER, isBig, pLimbSwing, pLimbSwingAmount, 0.0F, pNetHeadYaw, pHeadPitch);
        this.root.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
    }

    private void setupAnim(State state, boolean isBig, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.head.zRot = 0.0F;
        this.head.x = 0.0F;
        this.body.x = 0.0F;
        this.tail.x = 0.0F;
        this.rightWing.x = -1.5F;
        this.leftWing.x = 1.5F;
        switch (state) {
            case STANDING:
                this.leftLeg.xRot = this.leftLeg.xRot + Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
                this.rightLeg.xRot = this.rightLeg.xRot + Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            case FLYING:
            case ON_SHOULDER:
                float f2 = ageInTicks * 0.3F;
                this.head.y = (isBig ? 17.5F : 19.0F) + f2;
                this.tail.xRot = 1.1345F + Mth.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
                this.tail.y = 21.5F + f2;
                this.body.y = (isBig ? 17.5F : 19.0F) + f2;
                this.leftWing.zRot = -0.0873F - ageInTicks;
                this.rightWing.zRot = 0.0873F + ageInTicks;
                this.leftLeg.y = 22.0F + f2;
                this.rightLeg.y = 22.0F + f2;
            case SITTING:
                break;
        }
    }

    private void prepare(State state, boolean isBig) {
        this.leftWing.xRot = -0.7854F;
        this.leftWing.yRot = (float) -Math.PI;
        this.rightWing.xRot = -0.7854F;
        this.rightWing.yRot = (float) -Math.PI;
        this.leftLeg.xRot = -0.0299F;
        this.rightLeg.xRot = -0.0299F;
        this.leftLeg.y = 22.5F;
        this.rightLeg.y = 22.5F;
        this.leftLeg.zRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        if (state == State.FLYING) {
            this.leftLeg.xRot += (float) (Math.PI * 2.0 / 9.0);
            this.rightLeg.xRot += (float) (Math.PI * 2.0 / 9.0);
        } else if (state == State.SITTING) {
            this.head.y = isBig ? 18.9F : 20.4F;
            this.tail.xRot = 1.5388988F;
            this.tail.y = 22.9F;
            this.body.y = isBig ? 18.9F : 20.4F;
            this.leftWing.zRot = -0.0873F;
            this.rightWing.zRot = 0.0873F;
            this.leftLeg.y++;
            this.rightLeg.y++;
            this.leftLeg.xRot++;
            this.rightLeg.xRot++;
        }
    }

    private static State getState(AbstractBird bird) {
        if (bird.isInSittingPose()) {
            return State.SITTING;
        } else {
            return bird.isFlying() ? State.FLYING : State.STANDING;
        }
    }

    private enum State {
        FLYING,
        STANDING,
        SITTING,
        ON_SHOULDER
    }
}
