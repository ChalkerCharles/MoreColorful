package com.ChalkerCharles.morecolorful.client.compat;

import com.ChalkerCharles.morecolorful.MoreColorful;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.caffeinemc.mods.sodium.client.gl.attribute.GlVertexFormat;
import net.caffeinemc.mods.sodium.client.gl.device.CommandList;
import net.caffeinemc.mods.sodium.client.gl.device.RenderDevice;
import net.caffeinemc.mods.sodium.client.gl.shader.*;
import net.caffeinemc.mods.sodium.client.render.chunk.ChunkRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkFogMode;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public abstract class WavyShaderChunkRenderer implements ChunkRenderer {
    private final Map<ChunkShaderOptions, GlProgram<ChunkShaderInterface>> programs = new Object2ObjectOpenHashMap<>();
    protected final ChunkVertexType vertexType;
    protected final GlVertexFormat vertexFormat;
    protected final RenderDevice device;
    protected GlProgram<ChunkShaderInterface> activeProgram;

    public WavyShaderChunkRenderer(RenderDevice device, ChunkVertexType vertexType) {
        this.device = device;
        this.vertexType = vertexType;
        this.vertexFormat = vertexType.getVertexFormat();
    }

    protected GlProgram<ChunkShaderInterface> compileProgram(ChunkShaderOptions options) {
        GlProgram<ChunkShaderInterface> program = this.programs.get(options);
        if (program == null) {
            this.programs.put(options, program = this.createShader(options));
        }

        return program;
    }

    private GlProgram<ChunkShaderInterface> createShader(ChunkShaderOptions options) {
        ShaderConstants constants = options.constants();
        GlShader vertShader = ShaderLoader.loadShader(ShaderType.VERTEX,
                MoreColorful.location("sodium/sodium_block_layer.vsh"),
                constants);
        GlShader fragShader = ShaderLoader.loadShader(ShaderType.FRAGMENT,
                ResourceLocation.fromNamespaceAndPath("sodium", "blocks/block_layer_opaque.fsh"),
                constants);

        try {
            return GlProgram.builder(MoreColorful.location("wavy_chunk_shader"))
                    .attachShader(vertShader)
                    .attachShader(fragShader)
                    .bindAttribute("a_Position", 0)
                    .bindAttribute("a_Color", 1)
                    .bindAttribute("a_TexCoord", 2)
                    .bindAttribute("a_LightAndData", 3)
                    .bindAttribute("a_Wave", 4)
                    .bindFragmentData("fragColor", 0)
                    .link(shader -> new WavyShaderInterface(shader, options));
        } finally {
            vertShader.delete();
            fragShader.delete();
        }
    }

    @SuppressWarnings("deprecation")
    protected void begin(TerrainRenderPass pass) {
        pass.startDrawing();

        ChunkShaderOptions options = new ChunkShaderOptions(ChunkFogMode.SMOOTH, pass, this.vertexType);

        this.activeProgram = this.compileProgram(options);
        this.activeProgram.bind();
        this.activeProgram.getInterface().setupState();
    }

    @SuppressWarnings("deprecation")
    protected void end(TerrainRenderPass pass) {
        this.activeProgram.getInterface().resetState();
        this.activeProgram.unbind();
        this.activeProgram = null;

        pass.endDrawing();
    }

    @Override
    public void delete(CommandList commandList) {
        this.programs.values().forEach(GlProgram::delete);
    }
}
