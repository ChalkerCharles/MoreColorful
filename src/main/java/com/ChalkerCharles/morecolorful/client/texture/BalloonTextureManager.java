package com.ChalkerCharles.morecolorful.client.texture;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class BalloonTextureManager extends TextureAtlasHolder {
    private static final ResourceLocation[] LOCATIONS;
    public static final BalloonTextureManager INSTANCE = new BalloonTextureManager();

    public BalloonTextureManager() {
        super(Minecraft.getInstance().getTextureManager(), Atlases.BALLOON_SHEET, Atlases.BALLOONS_ATLAS);
    }

    public VertexConsumer buffer(Balloon.Variant variant, MultiBufferSource buffer, Function<ResourceLocation, RenderType> renderType) {
        return this.getSprite(LOCATIONS[variant.getIndex()]).wrap(buffer.getBuffer(renderType.apply(Atlases.BALLOON_SHEET)));
    }

    static {
        Balloon.Variant[] values = Balloon.Variant.values();
        int size = values.length;
        ResourceLocation[] arr = new ResourceLocation[size];
        for (int i = 0; i < size; i++) {
            Balloon.Variant variant = values[i];
            arr[i] = MoreColorful.location("entity/balloons/" + variant.getName());
        }
        LOCATIONS = arr;
    }
}
