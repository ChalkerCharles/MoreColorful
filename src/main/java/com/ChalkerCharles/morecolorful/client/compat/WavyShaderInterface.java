package com.ChalkerCharles.morecolorful.client.compat;

import net.caffeinemc.mods.sodium.client.gl.shader.uniform.GlUniformFloat;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.DefaultShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ShaderBindingContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WavyShaderInterface extends DefaultShaderInterface {
    public final GlUniformFloat gameTime;

    public WavyShaderInterface(ShaderBindingContext shader, ChunkShaderOptions options) {
        super(shader, options);
        this.gameTime = shader.bindUniform("u_GameTime", GlUniformFloat::new);
    }
}
