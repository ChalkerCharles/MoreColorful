package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IVertexExtension;
import com.ChalkerCharles.morecolorful.util.Constants;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder$Vertex", remap = false)
public abstract class SodiumVertexMixin implements IVertexExtension {
    @Unique
    @Nullable
    private Vector4f moreColorful$wave;

    @Override
    public void moreColorful$setWave(Vector4f vector4f) {
        this.moreColorful$wave = vector4f;
    }

    @Override
    public Vector4f moreColorful$getWave() {
        return Objects.requireNonNullElse(this.moreColorful$wave, Constants.ZERO_VEC4);
    }
}
