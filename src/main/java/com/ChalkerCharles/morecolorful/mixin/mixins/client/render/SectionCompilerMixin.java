package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SectionCompiler.class)
public abstract class SectionCompilerMixin {
    @ModifyExpressionValue(method = "getOrBeginLayer", at = @At(value = "FIELD", target = "Lcom/mojang/blaze3d/vertex/DefaultVertexFormat;BLOCK:Lcom/mojang/blaze3d/vertex/VertexFormat;", opcode = Opcodes.GETSTATIC))
    private VertexFormat getOrBeginLayer(VertexFormat original, @Local(argsOnly = true) RenderType renderType) {
        return renderType.format();
    }
}
