package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.world.level.block.state.BlockState;

public interface IBlockStateExtension {
    int moreColorful$getTemperature();

    void moreColorful$setTemperature(int temperature);

    int moreColorful$getThermalResistance();

    void moreColorful$setThermalResistance(int thermalResistance);

    int moreColorful$getVertexType();

    void moreColorful$setVertexType(int type);

    int moreColorful$getAirBlock();

    void moreColorful$setAirBlock(int airBlock);

    boolean moreColorful$isGroupBlock();

    void moreColorful$setGroupBlock(boolean groupBlock);

    private static IBlockStateExtension self(BlockState state) {
        return (IBlockStateExtension) state;
    }

    static int getTemperature(BlockState state) {
        return self(state).moreColorful$getTemperature();
    }

    static void setTemperature(BlockState state, int temperature) {
        self(state).moreColorful$setTemperature(temperature);
    }

    static int getThermalResistance(BlockState state) {
        return self(state).moreColorful$getThermalResistance();
    }

    static void setThermalResistance(BlockState state, int thermalResistance) {
        self(state).moreColorful$setThermalResistance(thermalResistance);
    }

    static int getVertexType(BlockState state) {
        return self(state).moreColorful$getVertexType();
    }

    static void setVertexType(BlockState state, int type) {
        self(state).moreColorful$setVertexType(type);
    }

    static int getAirBlock(BlockState state) {
        return self(state).moreColorful$getAirBlock();
    }

    static void setAirBlock(BlockState state, int airBlock) {
        self(state).moreColorful$setAirBlock(airBlock);
    }

    static boolean isGroupBlock(BlockState state) {
        return self(state).moreColorful$isGroupBlock();
    }

    static void setGroupBlock(BlockState state, boolean groupBlock) {
        self(state).moreColorful$setGroupBlock(groupBlock);
    }
}
