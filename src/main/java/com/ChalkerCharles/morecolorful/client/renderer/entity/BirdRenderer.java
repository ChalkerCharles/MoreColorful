package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.model.BirdHeldItemLayer;
import com.ChalkerCharles.morecolorful.client.model.BirdModel;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractBird;
import com.ChalkerCharles.morecolorful.common.entity.animal.Bird;
import com.ChalkerCharles.morecolorful.common.entity.animal.Pigeon;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILivingEntityRendererExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Function;

public class BirdRenderer extends MobRenderer<AbstractBird, BirdModel> implements ILivingEntityRendererExtension<AbstractBird> {
    private static final ResourceLocation[] LOCATIONS;
    private final BirdModel small = this.getModel();
    private final BirdModel big;

    private BirdRenderer(Function<ResourceLocation, RenderType> renderType, EntityRendererProvider.Context pContext) {
        super(pContext, new BirdModel(renderType, pContext.bakeLayer(ModModelLayers.SMALL_BIRD)), 0.3F);
        this.big = new BirdModel(renderType, pContext.bakeLayer(ModModelLayers.BIG_BIRD));
        this.addLayer(new BirdHeldItemLayer(this, pContext.getItemInHandRenderer()));
    }

    public static BirdRenderer create(EntityRendererProvider.Context context) {
        return new BirdRenderer(RenderType::entityCutoutNoCull, context);
    }

    public static BirdRenderer createPigeon(EntityRendererProvider.Context context) {
        return new BirdRenderer(RenderType::entityTranslucent, context);
    }

    @Override
    protected void scale(AbstractBird pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        if (pLivingEntity.getVariant() == Bird.Variant.SEAGULL) {
            pPoseStack.scale(1.2F, 1.2F, 1.2F);
        }
    }

    public static ResourceLocation getTexture(AbstractBird.Variant variant) {
        return LOCATIONS[variant.getIndex()];
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractBird pEntity) {
        return getTexture(pEntity.getVariant());
    }

    @Override
    public void render(AbstractBird pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.getVariant().isBig()) {
            this.model = this.big;
        } else {
            this.model = this.small;
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    public float getBob(AbstractBird pLivingBase, float pPartialTicks) {
        float f = Mth.lerp(pPartialTicks, pLivingBase.oFlap, pLivingBase.flap);
        float f1 = Mth.lerp(pPartialTicks, pLivingBase.oFlapSpeed, pLivingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }

    @Override
    public boolean morecolorful$hasDynamicTransparency(AbstractBird abstractBird) {
        return abstractBird instanceof Pigeon pigeon && pigeon.isFadingInOrFadingOut();
    }

    @Override
    public int morecolorful$getOpacity(AbstractBird entity, float partialTicks) {
        if (entity instanceof Pigeon pigeon) {
            return Mth.lerpInt(pigeon.getOpacityDelta(partialTicks), 0, 255);
        }
        return 0;
    }

    static {
        AbstractBird.Variant[] variants = AbstractBird.Variant.VALUES.get();
        int size = variants.length;
        ResourceLocation[] locations = new ResourceLocation[size];
        for (int i = 0; i < size; i++) {
            locations[i] = MoreColorful.location("textures/entity/bird/" + variants[i].getName() + ".png");
        }
        LOCATIONS = locations;
    }
}
