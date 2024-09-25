package com.ChalkerCharles.morecolorful.common.block.properties;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class ModSoundTypes {
    public static final DeferredSoundType RARE_WOOD = new DeferredSoundType(
            1.0F,
            1.0F,
            ModSounds.RARE_WOOD_BREAK,
            ModSounds.RARE_WOOD_STEP,
            ModSounds.RARE_WOOD_PLACE,
            ModSounds.RARE_WOOD_HIT,
            ModSounds.RARE_WOOD_FALL
    );
    public static final DeferredSoundType RARE_WOOD_HANGING_SIGN = new DeferredSoundType(
            1.0F,
            1.0F,
            ModSounds.RARE_WOOD_HANGING_SIGN_BREAK,
            ModSounds.RARE_WOOD_HANGING_SIGN_STEP,
            ModSounds.RARE_WOOD_HANGING_SIGN_PLACE,
            ModSounds.RARE_WOOD_HANGING_SIGN_HIT,
            ModSounds.RARE_WOOD_HANGING_SIGN_FALL
    );
}
