package com.ChalkerCharles.morecolorful.common.block.properties;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType CRABAPPLE = registerCherry("crabapple", ModBlockSetTypes.CRABAPPLE);
    public static final WoodType EBONY = registerRareWood("ebony", ModBlockSetTypes.EBONY);
    public static final WoodType GINKGO = register("ginkgo", ModBlockSetTypes.GINKGO);
    public static final WoodType MAPLE = register("maple", ModBlockSetTypes.MAPLE);
    public static final WoodType FROST = registerCherry("frost", ModBlockSetTypes.FROST);
    public static final WoodType DAWN_REDWOOD = register("dawn_redwood", ModBlockSetTypes.DAWN_REDWOOD);
    public static final WoodType JACARANDA = registerCherry("jacaranda", ModBlockSetTypes.JACARANDA);
    public static final WoodType WILLOW = register("willow", ModBlockSetTypes.WILLOW);

    private static WoodType register(String name, BlockSetType type) {
        return WoodType.register(new WoodType(MoreColorful.key(name), type));
    }

    private static WoodType registerCherry(String name, BlockSetType type) {
        return WoodType.register(new WoodType(
                MoreColorful.key(name),
                type,
                SoundType.CHERRY_WOOD,
                SoundType.CHERRY_WOOD_HANGING_SIGN,
                SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE,
                SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));
    }
    @SuppressWarnings("SameParameterValue")
    private static WoodType registerRareWood(String name, BlockSetType type) {
        return WoodType.register(new WoodType(
                MoreColorful.key(name),
                type,
                ModSoundTypes.RARE_WOOD,
                ModSoundTypes.RARE_WOOD_HANGING_SIGN,
                ModSounds.RARE_WOOD_FENCE_GATE_CLOSE.get(),
                ModSounds.RARE_WOOD_FENCE_GATE_OPEN.get()));
    }
}
