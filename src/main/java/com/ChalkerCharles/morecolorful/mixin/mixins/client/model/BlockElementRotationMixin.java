package com.ChalkerCharles.morecolorful.mixin.mixins.client.model;

import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockElementRotationExtension;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockElementRotation.class)
public abstract class BlockElementRotationMixin implements IBlockElementRotationExtension {
    @Unique
    private Vector3f moreColorful$rotation;

    @Override
    public Vector3f moreColorful$rotation() {
        return this.moreColorful$rotation;
    }

    @Override
    public void moreColorful$setRotation(Vector3f rotation) {
        this.moreColorful$rotation = rotation;
    }
}
