package com.ChalkerCharles.morecolorful.mixin.mixins.client.model;

import com.ChalkerCharles.morecolorful.mixin.extensions.IModelPartExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements IModelPartExtension {
    @Shadow
    public boolean visible;
    @Shadow
    @Final private List<ModelPart.Cube> cubes;
    @Shadow
    @Final
    private Map<String, ModelPart> children;
    @Shadow
    public boolean skipDraw;
    @Shadow
    public abstract void translateAndRotate(PoseStack pPoseStack);
    @Shadow
    protected abstract void compile(PoseStack.Pose pPose, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor);

    @Override
    public void moreColorful$renderWithout(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color, ModelPart part) {
        if (this.visible) {
            if (!this.cubes.isEmpty() || !this.children.isEmpty()) {
                poseStack.pushPose();
                this.translateAndRotate(poseStack);
                if (!this.skipDraw) {
                    this.compile(poseStack.last(), buffer, packedLight, packedOverlay, color);
                }
                for (ModelPart modelpart : this.children.values()) {
                    if (modelpart == part) continue;
                    modelpart.render(poseStack, buffer, packedLight, packedOverlay, color);
                }
                poseStack.popPose();
            }
        }
    }
}
