package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.common.advancement.VanillaAdvancementModifier;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerAdvancementManager.class)
public abstract class ServerAdvancementManagerMixin {
    @Inject(method = "lambda$apply$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ServerAdvancementManager;validate(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/Advancement;)V"))
    private void onApply(RegistryOps<?> registryops, ImmutableMap.Builder<?, ?> builder, ResourceLocation location, JsonElement element, CallbackInfo ci, @Local Advancement advancement) {
        VanillaAdvancementModifier.modify(location, advancement);
    }
}
