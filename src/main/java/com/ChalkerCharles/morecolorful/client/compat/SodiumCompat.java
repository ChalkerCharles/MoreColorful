package com.ChalkerCharles.morecolorful.client.compat;

import com.ChalkerCharles.morecolorful.client.shader.ModRenderTypes;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SodiumCompat {
    public static final TerrainRenderPass WAVY_CUTOUT_PASS = new TerrainRenderPass(ModRenderTypes.WAVY_CUTOUT_MIPPED, false, true);
    public static final Material WAVY_CUTOUT = new Material(WAVY_CUTOUT_PASS, AlphaCutoffParameter.ONE_TENTH, false);
    public static final Material WAVY_CUTOUT_MIPPED = new Material(WAVY_CUTOUT_PASS, AlphaCutoffParameter.HALF, true);
    public static final ChunkVertexType WAVY = new WavyChunkVertex();
}
