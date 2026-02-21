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
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
        this.model.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
    }
}
