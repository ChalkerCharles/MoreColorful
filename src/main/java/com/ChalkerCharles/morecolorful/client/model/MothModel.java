package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.common.entity.animal.Butterfly;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MothModel extends HierarchicalModel<AbstractMoth> {
    private final ModelPart root;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public MothModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.root = root.getChild("root");
        ModelPart body = this.root.getChild("body");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
    }

    public static LayerDefinition createButterfly() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F),
                PartPose.ZERO
        );
        body.addOrReplaceChild("a",
                CubeListBuilder.create().texOffs(2, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -4.0F, 0.0F, 0.0F, 2.0944F)
        );
        body.addOrReplaceChild("b",
                CubeListBuilder.create().texOffs(3, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.5F, 0.0F, -4.0F, 0.0F, 0.0F, 1.0472F)
        );
        body.addOrReplaceChild("c",
                CubeListBuilder.create().texOffs(0, -3).addBox(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.6F, -2.0F, -5.0F, 0.0F, -0.2618F, 0.0F)
        );
        body.addOrReplaceChild("d",
                CubeListBuilder.create().texOffs(0, -3).addBox(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-0.6F, -2.0F, -5.0F, 0.0F, 0.2618F, 0.0F)
        );
        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.0F, -1.5F, 0.0F));
        left_wing.addOrReplaceChild("e",
                CubeListBuilder.create()
                        .texOffs(24, 0).addBox(-12.0F, 0.01F, -8.0F, 12.0F, 0.0F, 16.0F)
                        .texOffs(12, 0).addBox(-12.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.5F, 0.0F));
        right_wing.addOrReplaceChild("f",
                CubeListBuilder.create()
                        .texOffs(24, 16).addBox(0.0F, 0.01F, -8.0F, 12.0F, 0.0F, 16.0F)
                        .texOffs(12, 16).addBox(0.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createMoth() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild(
                "body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F), PartPose.ZERO);
        body.addOrReplaceChild("a",
                CubeListBuilder.create().texOffs(10, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -4.0F, 0.0F, 0.0F, 2.0944F)
        );
        body.addOrReplaceChild("b",
                CubeListBuilder.create().texOffs(11, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.5F, 0.0F, -4.0F, 0.0F, 0.0F, 1.0472F)
        );
        body.addOrReplaceChild("c",
                CubeListBuilder.create().texOffs(-5, 0).addBox(-2.5F, 0.02F, -2.5F, 5.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -1.5F, -6.5F, 0.0F, 3.1416F, 0.0F)
        );
        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.0F, -1.5F, 0.0F));
        left_wing.addOrReplaceChild("d",
                CubeListBuilder.create()
                        .texOffs(24, 0).addBox(-12.0F, 0.01F, -8.0F, 12.0F, 0.0F, 16.0F)
                        .texOffs(12, 0).addBox(-12.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.5F, 0.0F));
        right_wing.addOrReplaceChild("e",
                CubeListBuilder.create()
                        .texOffs(24, 16).addBox(0.0F, 0.01F, -8.0F, 12.0F, 0.0F, 16.0F)
                        .texOffs(12, 16).addBox(0.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(AbstractMoth pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root.xRot = 0.0F;
        this.root.z = 0.0F;
        byte resting = pEntity.getResting();
        if (resting == 0) {
            float f = pAgeInTicks * 45 * Mth.DEG_TO_RAD;
            this.rightWing.zRot = (Mth.cos(f) * 0.25F + 0.05F) * Mth.PI;
            this.leftWing.zRot = -this.rightWing.zRot;
            float f1 = Mth.cos(pAgeInTicks * 0.18F);
            this.root.xRot = -0.1F - f1 * Mth.PI * 0.025F;
            this.root.y = 23.0F - Mth.cos(pAgeInTicks * 0.18F) * 0.9F;
        } else {
            if (pEntity instanceof Butterfly) {
                this.rightWing.zRot = 85 * Mth.DEG_TO_RAD;
                this.leftWing.zRot = -85 * Mth.DEG_TO_RAD;
            } else {
                this.rightWing.zRot = 0.0F;
                this.leftWing.zRot = 0.0F;
            }
            this.root.y = 23.0F;
            if (resting > 1) {
                this.root.xRot = -Mth.HALF_PI;
                this.root.z = -6;
            }
        }
    }
}
