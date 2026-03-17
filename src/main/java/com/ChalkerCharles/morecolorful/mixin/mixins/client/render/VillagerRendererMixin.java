package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.Config;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerRenderer.class)
public abstract class VillagerRendererMixin {
    @ModifyExpressionValue(method = "scale(Lnet/minecraft/world/entity/npc/Villager;Lcom/mojang/blaze3d/vertex/PoseStack;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;getAgeScale()F"))
    private float scale(float original) {
        return Config.babyVillagerWithBigHead ? 1.0F : original;
    }
}
