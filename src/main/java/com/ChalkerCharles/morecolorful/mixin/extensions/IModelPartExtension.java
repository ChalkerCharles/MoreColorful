package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;

public interface IModelPartExtension {
    void moreColorful$renderWithout(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color, ModelPart part);

    private static IModelPartExtension self(ModelPart part) {
        return (IModelPartExtension) (Object) part;
    }

    static void renderWithout(ModelPart self, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color, ModelPart part) {
        self(self).moreColorful$renderWithout(poseStack, buffer, packedLight, packedOverlay, color, part);
    }
}
