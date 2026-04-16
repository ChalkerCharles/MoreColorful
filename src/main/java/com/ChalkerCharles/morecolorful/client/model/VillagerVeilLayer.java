package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.entity.villager.ModVillagerProfessions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillagerVeilLayer<T extends LivingEntity & VillagerDataHolder, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
    private static final ResourceLocation VEIL_TEXTURE = MoreColorful.location("textures/entity/villager/veil.png");
    private final ModelPart veil;

    public VillagerVeilLayer(RenderLayerParent<T, M> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.veil = context.bakeLayer(ModModelLayers.VEIL);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.isInvisible()) {
            VillagerProfession profession = pLivingEntity.getVillagerData().getProfession();
            if (profession == ModVillagerProfessions.BEEKEEPER.value()) {
                VertexConsumer consumer = pBufferSource.getBuffer(RenderType.entityCutoutNoCull(VEIL_TEXTURE));
                this.veil.copyFrom(this.getParentModel().getHead());
                this.veil.render(pPoseStack, consumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F));
            }
        }
    }

    public static LayerDefinition createVeil() {
        MeshDefinition meshdefinition = new MeshDefinition();
        meshdefinition.getRoot().addOrReplaceChild("veil",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, -33.9F, -6.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}
