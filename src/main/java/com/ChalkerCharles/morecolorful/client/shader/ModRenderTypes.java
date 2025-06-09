package com.ChalkerCharles.morecolorful.client.shader;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ModRenderTypes extends RenderType {
    private static final RenderStateShard.ShaderStateShard WAVY_CUTOUT_MIPPED_SHADER = new RenderStateShard.ShaderStateShard(
            () -> ModShaders.wavyCutoutMippedShader
    );
    private static final RenderStateShard.ShaderStateShard WAVY_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard(
            () -> ModShaders.wavyCutoutShader
    );
    private static final RenderStateShard.ShaderStateShard WAVY_TRANSLUCENT_SHADER = new RenderStateShard.ShaderStateShard(
            () -> ModShaders.wavyTranslucentShader
    );

    public static final RenderType WAVY_CUTOUT_MIPPED = createModded(
            "wavy_cutout_mipped",
            ModVertexFormat.WAVY_BLOCK.get(),
            VertexFormat.Mode.QUADS,
            4194304,
            true,
            false,
            RenderType.CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(WAVY_CUTOUT_MIPPED_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .createCompositeState(true)
    );
    public static final RenderType WAVY_CUTOUT = createModded(
            "wavy_cutout",
            ModVertexFormat.WAVY_BLOCK.get(),
            VertexFormat.Mode.QUADS,
            786432,
            true,
            false,
            RenderType.CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(WAVY_CUTOUT_SHADER)
                    .setTextureState(BLOCK_SHEET)
                    .createCompositeState(true)
    );
    public static final RenderType WAVY_TRANSLUCENT = createModded(
            "wavy_translucent",
            ModVertexFormat.WAVY_BLOCK.get(),
            VertexFormat.Mode.QUADS,
            786432,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(WAVY_TRANSLUCENT_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .createCompositeState(true)
    );

    public static RenderType createModded(
            String pName,
            VertexFormat pFormat,
            VertexFormat.Mode pMode,
            int pBufferSize,
            boolean pAffectsCrumbling,
            boolean pSortOnUpload,
            RenderType.CompositeState pState) {
        return RenderType.create(MoreColorful.MODID + ":" + pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pState);
    }

    private ModRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
        throw new UnsupportedOperationException();
    }
}
