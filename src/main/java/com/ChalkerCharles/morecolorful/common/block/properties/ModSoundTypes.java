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
    public static final DeferredSoundType LEAF_LITTER = new DeferredSoundType(
            1.0F,
            1.0F,
            ModSounds.LEAF_LITTER_BREAK,
            ModSounds.LEAF_LITTER_STEP,
            ModSounds.LEAF_LITTER_PLACE,
            ModSounds.LEAF_LITTER_HIT,
            ModSounds.LEAF_LITTER_FALL
    );
    public static final DeferredSoundType PAPER = new DeferredSoundType(
            0.8F,
            1.0F,
            ModSounds.PAPER_BREAK,
            ModSounds.PAPER_STEP,
            ModSounds.PAPER_PLACE,
            ModSounds.PAPER_HIT,
            ModSounds.PAPER_FALL
    );
    public static final DeferredSoundType CARDBOARD = new DeferredSoundType(
            1.0F,
            1.0F,
            ModSounds.CARDBOARD_BREAK,
            ModSounds.CARDBOARD_STEP,
            ModSounds.CARDBOARD_PLACE,
            ModSounds.CARDBOARD_HIT,
            ModSounds.CARDBOARD_FALL
    );
}
