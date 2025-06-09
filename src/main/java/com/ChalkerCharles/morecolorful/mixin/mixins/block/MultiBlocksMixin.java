package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.common.block.properties.MultipartBlock;
import net.minecraft.world.level.block.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
        BigDripleafBlock.class,
        BigDripleafStemBlock.class,
        ChainBlock.class,
        DoublePlantBlock.class,
        GrowingPlantBlock.class,
        SmallDripleafBlock.class,
        SugarCaneBlock.class,
        VineBlock.class
})
public abstract class MultiBlocksMixin implements MultipartBlock {
}
