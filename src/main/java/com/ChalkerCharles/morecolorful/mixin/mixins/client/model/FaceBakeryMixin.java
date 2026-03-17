package com.ChalkerCharles.morecolorful.mixin.mixins.client.model;

import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockElementRotationExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FaceBakery.class)
public abstract class FaceBakeryMixin {
    @WrapOperation(method = "applyElementRotation", at = @At(value = "INVOKE", target = "Lorg/joml/Quaternionf;rotationAxis(FLorg/joml/Vector3fc;)Lorg/joml/Quaternionf;"))
    private Quaternionf applyElementRotation(Quaternionf instance, float angle, Vector3fc axis, Operation<Quaternionf> original,
                                             @Local(argsOnly = true) BlockElementRotation partRotation) {
        Vector3f rotation = IBlockElementRotationExtension.rotation(partRotation);
        if (rotation != null) {
            return instance.rotationZYX(rotation.z * Mth.DEG_TO_RAD, rotation.y * Mth.DEG_TO_RAD, rotation.x * Mth.DEG_TO_RAD);
        }
        return original.call(instance, angle, axis);
    }
}
