package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.client.renderer.block.model.BlockElementRotation;
import org.joml.Vector3f;

public interface IBlockElementRotationExtension {
    Vector3f moreColorful$rotation();

    void moreColorful$setRotation(Vector3f rotation);

    private static IBlockElementRotationExtension self(BlockElementRotation rotation) {
        return (IBlockElementRotationExtension) (Object) rotation;
    }

    static Vector3f rotation(BlockElementRotation rotation) {
        return self(rotation).moreColorful$rotation();
    }

    static void setRotation(BlockElementRotation rotation, Vector3f vector3f) {
        self(rotation).moreColorful$setRotation(vector3f);
    }
}
