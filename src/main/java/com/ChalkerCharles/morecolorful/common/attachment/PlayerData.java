package com.ChalkerCharles.morecolorful.common.attachment;

import net.minecraft.world.entity.player.Player;

public class PlayerData {
    private final InstrumentData instrumentData = new InstrumentData();

    private static PlayerData get(Player player) {
        return player.getData(ModDataAttachments.PLAYER_DATA);
    }

    public static InstrumentData getInstrumentData(Player player) {
        return get(player).instrumentData;
    }
}
