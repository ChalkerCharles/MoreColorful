package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.world.level.block.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
        AttachedStemBlock.class,
        AzaleaBlock.class,
        BambooStalkBlock.class,
        BigDripleafBlock.class,
        BigDripleafStemBlock.class,
        ChainBlock.class,
        CropBlock.class,
        DeadBushBlock.class,
        DoublePlantBlock.class,
        FlowerBlock.class,
        HangingRootsBlock.class,
        LanternBlock.class,
        LeavesBlock.class,
        NetherSproutsBlock.class,
        NetherWartBlock.class,
        PinkPetalsBlock.class,
        RootsBlock.class,
        SaplingBlock.class,
        SmallDripleafBlock.class,
        SporeBlossomBlock.class,
        StemBlock.class,
        SugarCaneBlock.class,
        SweetBerryBushBlock.class,
        TallGrassBlock.class,
        TwistingVinesBlock.class,
        TwistingVinesPlantBlock.class,
        VineBlock.class,
        WaterlilyBlock.class,
        WebBlock.class,
        WeepingVinesBlock.class,
        WeepingVinesPlantBlock.class
})
public abstract class WindSensitiveBlocksMixin implements WindSensitive {
    @Mixin(CaveVines.class)
    private interface Interface extends WindSensitive {}
}
