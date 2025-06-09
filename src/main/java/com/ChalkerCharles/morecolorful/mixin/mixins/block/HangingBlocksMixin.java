package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.common.block.properties.HangingBlock;
import net.minecraft.world.level.block.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
        ChainBlock.class,
        HangingRootsBlock.class,
        LanternBlock.class,
        SporeBlossomBlock.class,
        WeepingVinesBlock.class,
        WeepingVinesPlantBlock.class
})
public abstract class HangingBlocksMixin implements HangingBlock {
    @Mixin(CaveVines.class)
    private interface Interface extends HangingBlock {}
}
