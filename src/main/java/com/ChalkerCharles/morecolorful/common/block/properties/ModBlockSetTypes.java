package com.ChalkerCharles.morecolorful.common.block.properties;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ModBlockSetTypes {
    public static final BlockSetType CRABAPPLE = registerCherry("crabapple");
    public static final BlockSetType EBONY = registerRareWood("ebony");
    public static final BlockSetType GINKGO = register("ginkgo");
    public static final BlockSetType MAPLE = register("maple");
    public static final BlockSetType FROST = registerCherry("frost");
    public static final BlockSetType DAWN_REDWOOD = register("dawn_redwood");
    public static final BlockSetType JACARANDA = registerCherry("jacaranda");
    public static final BlockSetType WILLOW = register("willow");

    private static BlockSetType register(String name) {
        return BlockSetType.register(new BlockSetType(MoreColorful.key(name)));
    }

    private static BlockSetType registerCherry(String name) {
        return BlockSetType.register(new BlockSetType(
                MoreColorful.key(name),
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
        return BlockSetType.register(new BlockSetType(
                MoreColorful.key(name),
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
