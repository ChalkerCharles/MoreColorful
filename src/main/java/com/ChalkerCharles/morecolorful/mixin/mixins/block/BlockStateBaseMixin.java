package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateExtension;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin implements IBlockStateExtension {
    @Shadow
    @Final
    private int lightEmission;
    @Unique
    private int moreColorful$temperature;
    @Unique
    private int moreColorful$thermalResistance;
    @Unique
    private int moreColorful$vertexType;
    @Unique
    private int moreColorful$airBlock;
    @Unique
    private boolean moreColorful$isGroupBlock;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(Block pOwner, Reference2ObjectArrayMap<Property<?>, Comparable<?>> pValues, MapCodec<BlockState> pPropertiesCodec, CallbackInfo ci) {
        this.moreColorful$temperature = Math.max((this.lightEmission - 11) * 3, 0);
        this.moreColorful$thermalResistance = 3;
    }

    @Override
    public int moreColorful$getTemperature() {
        return this.moreColorful$temperature;
    }

    @Override
    public void moreColorful$setTemperature(int temperature) {
        this.moreColorful$temperature = temperature;
    }

    @Override
    public int moreColorful$getThermalResistance() {
        return this.moreColorful$thermalResistance;
    }

    @Override
    public void moreColorful$setThermalResistance(int thermalResistance) {
        this.moreColorful$thermalResistance = thermalResistance;
    }

    @Override
    public int moreColorful$getVertexType() {
        return this.moreColorful$vertexType;
    }

    @Override
    public void moreColorful$setVertexType(int type) {
        this.moreColorful$vertexType = type;
    }

    @Override
    public int moreColorful$getAirBlock() {
        return this.moreColorful$airBlock;
    }

    @Override
    public void moreColorful$setAirBlock(int airBlock) {
        this.moreColorful$airBlock = airBlock;
    }

    @Override
    public boolean moreColorful$isGroupBlock() {
        return this.moreColorful$isGroupBlock;
    }

    @Override
    public void moreColorful$setGroupBlock(boolean groupBlock) {
        this.moreColorful$isGroupBlock = groupBlock;
    }
}
