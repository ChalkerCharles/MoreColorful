package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModModelLayers {
    public static final ModelLayerLocation RIDE_CYMBAL = register("ride_cymbal");
    public static final ModelLayerLocation CRASH_CYMBAL = register("crash_cymbal");
    public static final ModelLayerLocation DRUM_SET_RIDE = register("drum_set_ride");
    public static final ModelLayerLocation DRUM_SET_CRASH = register("drum_set_crash");
    public static final ModelLayerLocation BALLOON = register("balloon");

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }
    @SuppressWarnings("SameParameterValue")
    private static ModelLayerLocation register(String path, String model) {
        return new ModelLayerLocation(MoreColorful.location(path), model);
    }
}
