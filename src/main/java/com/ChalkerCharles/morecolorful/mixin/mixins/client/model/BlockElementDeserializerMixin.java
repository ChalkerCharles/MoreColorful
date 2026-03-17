package com.ChalkerCharles.morecolorful.mixin.mixins.client.model;

import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockElementRotationExtension;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockElement.Deserializer.class)
public abstract class BlockElementDeserializerMixin {
    @WrapOperation(method = "getRotation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockElement$Deserializer;getAxis(Lcom/google/gson/JsonObject;)Lnet/minecraft/core/Direction$Axis;"))
    private Direction.Axis getRotation$tryParseRotation(BlockElement.Deserializer instance, JsonObject json, Operation<Direction.Axis> original,
                                       @Local(ordinal = 1) JsonObject object, @Share("rotation") LocalRef<Vector3f> rotation) {
        rotation.set(null);
        if (object.has("x") && object.has("y") && object.has("z")) {
            float x = GsonHelper.getAsFloat(object, "x", 0.0F);
            float y = GsonHelper.getAsFloat(object, "y", 0.0F);
            float z = GsonHelper.getAsFloat(object, "z", 0.0F);
            rotation.set(new Vector3f(x, y, z));
            return Direction.Axis.Y;
        }
        return original.call(instance, json);
    }

    @WrapOperation(method = "getRotation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockElement$Deserializer;getAngle(Lcom/google/gson/JsonObject;)F"))
    private float getRotation$cancelVanillaAngle(BlockElement.Deserializer instance, JsonObject json, Operation<Float> original,
                                                 @Share("rotation") LocalRef<Vector3f> rotation) {
        if (rotation.get() != null) {
            return 0;
        }
        return original.call(instance, json);
    }

    @ModifyExpressionValue(method = "getRotation", at = @At(value = "NEW", target = "Lnet/minecraft/client/renderer/block/model/BlockElementRotation;"))
    private BlockElementRotation getRotation$setRotation(BlockElementRotation original, @Share("rotation") LocalRef<Vector3f> rotation) {
        Vector3f vec = rotation.get();
        if (vec != null) {
            IBlockElementRotationExtension.setRotation(original, vec);
        }
        return original;
    }
}
