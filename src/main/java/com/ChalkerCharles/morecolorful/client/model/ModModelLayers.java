package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModModelLayers {
    public static final ModelLayerLocation RIDE_CYMBAL = register("ride_cymbal");
    public static final ModelLayerLocation CRASH_CYMBAL = register("crash_cymbal");

    private static ModelLayerLocation register(String pPath) {
        return register(pPath, "main");
    }
    @SuppressWarnings("SameParameterValue")
    private static ModelLayerLocation register(String pPath, String pModel) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, pPath), pModel);
    }
}
