package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.common.entity.animal.Caterpillar;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
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
public class CaterpillarModel extends EntityModel<Caterpillar> {
    private final ModelPart root;

    public CaterpillarModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.root = root;
    }

    public static LayerDefinition create() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot().addOrReplaceChild("root",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("a",
                CubeListBuilder.create().texOffs(3, -2).addBox(0.0F, -1.0F, -2.0F, 0.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.6F, -2.0F, -5.0F, 0.0F, -0.2618F, 0.0F));
        root.addOrReplaceChild("b",
                CubeListBuilder.create().texOffs(3, -2).addBox(0.0F, -1.0F, -2.0F, 0.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(-0.6F, -2.0F, -5.0F, 0.0F, 0.2618F, 0.0F));
        root.addOrReplaceChild("c",
                CubeListBuilder.create().texOffs(-10, 0).addBox(0.0F, 0.0F, -5.0F, 1.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(-1.0F, -2.0F, 0.0F, 0.0F, 0.0F, -2.3562F));
        root.addOrReplaceChild("d",
                CubeListBuilder.create().texOffs(-9, 0).addBox(0.0F, 0.0F, -5.0F, 1.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(1.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(Caterpillar pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root.y = 0.0F;
        this.root.xRot = 0.0F;
        float a = pLimbSwingAmount > 0.2F ? Mth.square(pLimbSwingAmount - 1) * 0.3125F : pLimbSwingAmount;
        float f = Mth.cos(pLimbSwing) * a;
        float f1 = Mth.sin(pLimbSwing) * a;
        this.root.zScale = 1 + f;
        this.root.z = -12 * f1;
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
        this.root.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
    }
}
