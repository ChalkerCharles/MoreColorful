package com.ChalkerCharles.morecolorful.mixin.mixins.client.model;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.IModelPartExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VillagerModel.class)
public abstract class VillagerModelMixin<T extends Entity> extends HierarchicalModel<T> {
    @Shadow
    public abstract ModelPart getHead();
    @Shadow
    public abstract ModelPart root();

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
        if (Config.babyVillagerWithBigHead && this.young) {
            pPoseStack.pushPose();
            pPoseStack.scale(0.75F, 0.75F, 0.75F);
            pPoseStack.translate(0.0F, 1.0F, 0.0F);
            this.getHead().render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
            pPoseStack.popPose();
            pPoseStack.pushPose();
            pPoseStack.scale(0.5F, 0.5F, 0.5F);
            pPoseStack.translate(0.0F, 1.5F, 0.0F);
            IModelPartExtension.renderWithout(this.root(), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor, this.getHead());
            pPoseStack.popPose();
        } else {
            super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
        }
    }
}
