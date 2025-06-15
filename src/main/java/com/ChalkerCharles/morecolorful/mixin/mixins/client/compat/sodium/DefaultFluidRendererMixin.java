package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IVertexExtension;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.api.util.NormI8;
import net.caffeinemc.mods.sodium.client.model.light.data.QuadLightData;
import net.caffeinemc.mods.sodium.client.model.quad.ModelQuadView;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.builder.ChunkMeshBufferBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer", remap = false)
public abstract class DefaultFluidRendererMixin {
    @Shadow
    @Final
    private int[] quadColors;
    @Shadow
    @Final
    private float[] brightness;
    @Shadow
    @Final
    private QuadLightData quadLightData;
    @Shadow
    @Final
    private ChunkVertexEncoder.Vertex[] vertices;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/DefaultFluidRenderer;writeQuad(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/buffers/ChunkModelBuilder;Lnet/caffeinemc/mods/sodium/client/render/chunk/translucent_sorting/TranslucentGeometryCollector;Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/material/Material;Lnet/minecraft/core/BlockPos;Lnet/caffeinemc/mods/sodium/client/model/quad/ModelQuadView;Lnet/caffeinemc/mods/sodium/client/model/quad/properties/ModelQuadFacing;Z)V"))
    public void render(DefaultFluidRenderer instance, ChunkModelBuilder builder, TranslucentGeometryCollector collector, Material material, BlockPos offset, ModelQuadView quad, ModelQuadFacing facing, boolean flip, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) BlockPos blockPos) {
        if (Config.WIND_EFFECT_CLIENT.isTrue() && material.isTranslucent()) {
            this.moreColorful$writeQuad(builder, collector, material, offset, quad, facing, flip, blockPos);
        } else {
            original.call(instance, builder, collector, material, offset, quad, facing, flip);
        }
    }

    @Unique
    private void moreColorful$writeQuad(ChunkModelBuilder builder, TranslucentGeometryCollector collector, Material material, BlockPos offset, ModelQuadView quad, ModelQuadFacing facing, boolean flip, BlockPos pos) {
        ChunkVertexEncoder.Vertex[] vertices = this.vertices;

        for(int i = 0; i < 4; ++i) {
            ChunkVertexEncoder.Vertex out = vertices[flip ? 3 - i + 1 & 3 : i];
            out.x = (float)offset.getX() + quad.getX(i);
            out.y = (float)offset.getY() + quad.getY(i);
            out.z = (float)offset.getZ() + quad.getZ(i);
            out.color = this.quadColors[i];
            out.ao = this.brightness[i];
            out.u = quad.getTexU(i);
            out.v = quad.getTexV(i);
            out.light = this.quadLightData.lm[i];
            Vector4f wave = RenderUtils.getFluidWaveData(pos, out.x, out.y, out.z);
            ((IVertexExtension) out).moreColorful$setWave(wave);
        }

        TextureAtlasSprite sprite = quad.getSprite();
        if (sprite != null) {
            builder.addSprite(sprite);
        }

        if (material.isTranslucent() && collector != null) {
            int normal;
            if (facing.isAligned()) {
                normal = facing.getPackedAlignedNormal();
            } else {
                normal = quad.getFaceNormal();
            }
            if (flip) {
                normal = NormI8.flipPacked(normal);
            }
            collector.appendQuad(normal, vertices, facing);
        }

        ChunkMeshBufferBuilder vertexBuffer = builder.getVertexBuffer(facing);
        vertexBuffer.push(vertices, material);
    }
}
