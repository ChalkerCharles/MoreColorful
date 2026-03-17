package com.ChalkerCharles.morecolorful.mixin.mixins.entity.behavior;

import com.ChalkerCharles.morecolorful.common.entity.ai.behavior.FlyKite;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerGoalPackages.class)
public abstract class VillagerGoalPackagesMixin {
    @ModifyExpressionValue(method = "getPlayPackage", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;"))
    private static ImmutableList<Pair<? extends BehaviorControl<?>, Integer>> getPlayPackage(ImmutableList<Pair<? extends BehaviorControl<?>, Integer>> original) {
        return ImmutableList.<Pair<? extends BehaviorControl<?>, Integer>>builder()
                .addAll(original)
                .add(Pair.of(new FlyKite(), 2))
                .build();
    }
}
