package com.ChalkerCharles.morecolorful.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BalloonModel extends Model {
    private final ModelPart model;

    public BalloonModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.model = root.getChild("balloon");
    }

    public static LayerDefinition create() {
        MeshDefinition meshdefinition = new MeshDefinition();
        meshdefinition.getRoot().addOrReplaceChild("balloon",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-5.0F, -11.0F, -5.0F, 10.0F, 11.0F, 10.0F),
                PartPose.ZERO
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createHeart() {
        MeshDefinition meshdefinition = new MeshDefinition();
        meshdefinition.getRoot().addOrReplaceChild("balloon",
                CubeListBuilder.create()
                        .texOffs(38, 0)
                        .addBox(6.0F, -6.0F, -4.0F, 5.0F, 6.0F, 8.0F)
                        .texOffs(0, 0)
                        .addBox(0.0F, -11.0F, -4.0F, 6.0F, 11.0F, 8.0F),
                PartPose.rotation(0.0F, 0.0F, -0.7854F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createStar() {
        MeshDefinition meshdefinition = new MeshDefinition();
        meshdefinition.getRoot().addOrReplaceChild("balloon",
                CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-5.0F, -2.0F, -4.0F, 10.0F, 2.0F, 8.0F)
                        .texOffs(40, 16)
                        .addBox(1.0F, 0.0F, -4.0F, 4.0F, 3.0F, 8.0F)
                        .texOffs(40, 16)
                        .addBox(-5.0F, 0.0F, -4.0F, 4.0F, 3.0F, 8.0F)
                        .texOffs(0, 20)
                        .addBox(-7.0F, -6.0F, -4.0F, 14.0F, 4.0F, 8.0F)
                        .texOffs(0, 0)
                        .addBox(-2.0F, -9.0F, -4.0F, 4.0F, 3.0F, 8.0F),
                PartPose.ZERO
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createRabbit() {
        MeshDefinition meshdefinition = new MeshDefinition();
        meshdefinition.getRoot().addOrReplaceChild("balloon",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F)
                        .texOffs(52, 0)
                        .addBox(-5.0F, -16.0F, 1.0F, 4.0F, 8.0F, 2.0F)
                        .texOffs(52, 0)
                        .addBox(1.0F, -16.0F, 1.0F, 4.0F, 8.0F, 2.0F),
                PartPose.ZERO
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
        this.model.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
    }
}
