package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.common.entity.animal.Dragonfly;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonflyModel extends HierarchicalModel<Dragonfly> {
    private final ModelPart root;
    private final ModelPart leftFrontWing;
    private final ModelPart leftBackWing;
    private final ModelPart rightFrontWing;
    private final ModelPart rightBackWing;

    public DragonflyModel(ModelPart root) {
        this.root = root.getChild("root");
        ModelPart body = this.root.getChild("body");
        this.leftFrontWing = body.getChild("left_front_wing");
        this.leftBackWing = body.getChild("left_back_wing");
        this.rightFrontWing = body.getChild("right_front_wing");
        this.rightBackWing = body.getChild("right_back_wing");
    }

    public static LayerDefinition create() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 10.0F)
                        .texOffs(20, 10)
                        .addBox(-1.0F, -2.0F, -7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.2F))
                        .texOffs(0, 0)
                        .addBox(-1.5F, -2.1F, -7.2F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)),
                PartPose.ZERO
        );
        body.addOrReplaceChild("a",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -5.0F, 0.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -1.0F, 0.0F, 0.0F, 0.5236F)
        );
        body.addOrReplaceChild("b",
                CubeListBuilder.create().texOffs(0, -2).addBox(0.0F, 0.0F, -5.0F, 0.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.5F, 0.0F, -1.0F, 0.0F, 0.0F, -0.5236F)
        );
        body.addOrReplaceChild("left_front_wing",
                CubeListBuilder.create().texOffs(11, 4).addBox(0.0F, 0.0F, -1.0F, 9.0F, 0.0F, 3.0F),
                PartPose.offset(1.0F, -1.8F, -5.0F)
        );
        body.addOrReplaceChild("left_back_wing",
                CubeListBuilder.create().texOffs(11, 4).addBox(0.0F, 0.0F, -1.0F, 9.0F, 0.0F, 3.0F),
                PartPose.offset(1.0F, -1.5F, -2.0F)
        );
        body.addOrReplaceChild("right_front_wing",
                CubeListBuilder.create().texOffs(11, 0).addBox(-9.0F, 0.0F, -1.0F, 9.0F, 0.0F, 3.0F),
                PartPose.offset(-1.0F, -1.8F, -5.0F)
        );
        body.addOrReplaceChild("right_back_wing",
                CubeListBuilder.create().texOffs(11, 0).addBox(-10.0F, 0.0F, -1.0F, 9.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -1.5F, -2.0F)
        );
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Dragonfly pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pEntity.onGround() && pEntity.getDeltaMovement().lengthSqr() < 1.0E-7) {
            this.leftFrontWing.zRot = 0.0F;
            this.leftBackWing.zRot = 0.0F;
            this.rightFrontWing.zRot = 0.0F;
            this.rightBackWing.zRot = 0.0F;
            this.root.xRot = 0;
            this.root.y = 23;
        } else {
            float f = pAgeInTicks * 120.32113F * Mth.DEG_TO_RAD;
            this.rightFrontWing.zRot = Mth.cos(f) * Mth.PI * 0.15F;
            this.rightBackWing.zRot = Mth.sin(f) * Mth.PI * 0.15F;
            this.leftFrontWing.zRot = -this.rightFrontWing.zRot;
            this.leftBackWing.zRot = -this.rightBackWing.zRot;
            float f1 = Mth.cos(pAgeInTicks * 0.18F);
            this.root.xRot = -0.1F - f1 * Mth.PI * 0.025F;
            this.root.y = 23.0F - Mth.cos(pAgeInTicks * 0.18F) * 0.9F;
        }
    }
}
