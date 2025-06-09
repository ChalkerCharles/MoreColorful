package com.ChalkerCharles.morecolorful.mixin.mixins.accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GrowingPlantBlock.class)
public interface IGrowingPlantBlockMixin {
    @Invoker("getHeadBlock")
    GrowingPlantHeadBlock invokeGetHeadBlock();

    @Invoker("getBodyBlock")
    Block invokeGetBodyBlock();
}
