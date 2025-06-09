package com.ChalkerCharles.morecolorful.mixin.mixins.accessor;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.class)
public interface IBlockStateBaseMixin {
    @Accessor("mapColor")
    @Mutable
    void setMapColor(MapColor mapColor);

    @Accessor("instrument")
    @Mutable
    void setInstrument(NoteBlockInstrument instrument);
}
