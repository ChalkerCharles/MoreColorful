package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IVertexExtension;
import com.ChalkerCharles.morecolorful.util.Constants;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import net.caffeinemc.mods.sodium.client.render.frapi.mesh.MutableQuadViewImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.frapi.render.AbstractBlockRenderContext", remap = false)
public abstract class AbstractBlockRenderContextMixin {
    @Shadow
    protected BlockPos pos;
    @Shadow
    protected BlockState state;

    @Pseudo
    @Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer", remap = false)
    private abstract static class BlockRendererMixin extends AbstractBlockRenderContextMixin {
        @Inject(method = "bufferQuad", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;light:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
        private void bufferQuad(MutableQuadViewImpl quad, float[] brightnesses, Material material, CallbackInfo ci, @Local ChunkVertexEncoder.Vertex out, @Local Vector3f offset) {
            if (Config.WIND_EFFECT_CLIENT.isFalse()) return;
            Block block = this.state.getBlock();
            if (WeatherUtils.isWindSensitiveBlock(block)) {
                int type = RenderUtils.VERTEX_TYPE.get().getOrDefault(block, 0);
                float x = out.x - offset.x;
                float y = out.y - offset.y;
                float z = out.z - offset.z;
                Vector4f wave = RenderUtils.getWaveData(this.pos, this.state, x, y, z, type);
                ((IVertexExtension) out).moreColorful$setWave(wave);
            } else {
                ((IVertexExtension) out).moreColorful$setWave(Constants.ZERO_VEC4);
            }
        }
    }
}
