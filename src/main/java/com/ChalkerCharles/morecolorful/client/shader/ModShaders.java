package com.ChalkerCharles.morecolorful.client.shader;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import javax.annotation.Nullable;
import java.io.IOException;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModShaders {
    @Nullable
    public static ShaderInstance wavyCutoutMippedShader;
    @Nullable
    public static ShaderInstance wavyCutoutShader;
    @Nullable
    public static ShaderInstance wavyTranslucentShader;

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();
        event.registerShader(
                new ShaderInstance(resourceProvider, MoreColorful.location("rendertype_wavy_cutout_mipped"), ModVertexFormat.WAVY_BLOCK.get()),
                shaderInstance -> wavyCutoutMippedShader = shaderInstance
        );
        event.registerShader(
                new ShaderInstance(resourceProvider, MoreColorful.location("rendertype_wavy_cutout"), ModVertexFormat.WAVY_BLOCK.get()),
                shaderInstance -> wavyCutoutShader = shaderInstance
        );
        event.registerShader(
                new ShaderInstance(resourceProvider, MoreColorful.location("rendertype_wavy_translucent"), ModVertexFormat.WAVY_BLOCK.get()),
                shaderInstance -> wavyTranslucentShader = shaderInstance
        );
    }
}
