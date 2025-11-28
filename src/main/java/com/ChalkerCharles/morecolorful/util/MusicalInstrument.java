package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.world.entity.player.Player;

public interface MusicalInstrument {
    InstrumentsType getType();

    static boolean isSameInstrument(Object obj, InstrumentsType type) {
        return obj instanceof MusicalInstrument m && m.getType() == type;
    }

    static boolean withDrumstick(Player player) {
        return player.getMainHandItem().getItem() == ModItems.DRUMSTICK.get() ||
                player.getOffhandItem().getItem() == ModItems.DRUMSTICK.get();
    }

    static boolean withDrumsticks(Player player) {
        return player.getMainHandItem().getItem() == ModItems.DRUMSTICK.get() &&
                player.getOffhandItem().getItem() == ModItems.DRUMSTICK.get();
    }
}
