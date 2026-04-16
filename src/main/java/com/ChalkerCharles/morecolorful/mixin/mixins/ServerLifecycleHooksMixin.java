package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.worldgen.ModBiomeModifiers;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;
import java.util.stream.Stream;

@Mixin(ServerLifecycleHooks.class)
public abstract class ServerLifecycleHooksMixin {
    @ModifyReceiver(method = "runModifiers", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;map(Ljava/util/function/Function;)Ljava/util/stream/Stream;", ordinal = 0))
    private static Stream<Holder.Reference<BiomeModifier>> runModifiers(Stream<Holder.Reference<BiomeModifier>> original, Function<?, ?> function) {
        if (Config.allowAddingFeatures && Config.allowAddingSpawns) {
            return original;
        }
        return original.filter(ModBiomeModifiers::disableBiomeModifiers);
    }
}
