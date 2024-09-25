package com.ChalkerCharles.morecolorful.common.block.properties;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import static net.minecraft.world.level.block.state.properties.BlockSetType.register;

public class ModBlockSetTypes {
    public static final BlockSetType CRABAPPLE = registerCherry((MoreColorful.MODID + ":crabapple"));
    public static final BlockSetType EBONY = registerRareWood((MoreColorful.MODID + ":ebony"));

    private static BlockSetType registerCherry(@SuppressWarnings("SameParameterValue") String name) {
        return register(new BlockSetType(
                name,
                true,
                true,
                true,
                BlockSetType.PressurePlateSensitivity.EVERYTHING,
                SoundType.CHERRY_WOOD,
                SoundEvents.CHERRY_WOOD_DOOR_CLOSE,
                SoundEvents.CHERRY_WOOD_DOOR_OPEN,
                SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE,
                SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN,
                SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF,
                SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON));
    }
    private static BlockSetType registerRareWood(@SuppressWarnings("SameParameterValue") String name) {
        return register(new BlockSetType(
                name,
                true,
                true,
                true,
                BlockSetType.PressurePlateSensitivity.EVERYTHING,
                ModSoundTypes.RARE_WOOD,
                ModSounds.RARE_WOOD_DOOR_CLOSE.get(),
                ModSounds.RARE_WOOD_DOOR_OPEN.get(),
                ModSounds.RARE_WOOD_TRAPDOOR_CLOSE.get(),
                ModSounds.RARE_WOOD_TRAPDOOR_OPEN.get(),
                ModSounds.RARE_WOOD_PRESSURE_PLATE_CLICK_OFF.get(),
                ModSounds.RARE_WOOD_PRESSURE_PLATE_CLICK_ON.get(),
                ModSounds.RARE_WOOD_BUTTON_CLICK_OFF.get(),
                ModSounds.RARE_WOOD_BUTTON_CLICK_ON.get()));
    }
}
