package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.packets.DrumSetPacket;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MoreColorful.MODID);

    public static final Supplier<AttachmentType<Boolean>> IS_PLAYING_INSTRUMENT = ATTACHMENT_TYPES.register("is_playing_instrument", () -> AttachmentType.builder(() -> false).build());
    public static final Supplier<AttachmentType<PlayingScreenPacket>> PLAYING_SCREEN_DATA = ATTACHMENT_TYPES.register("playing_screen_data", () -> AttachmentType.builder(PlayingScreenPacket::new).build());
    public static final Supplier<AttachmentType<Float>> PLAYING_SCREEN_TICK = ATTACHMENT_TYPES.register("playing_screen_tick", () -> AttachmentType.builder(() -> 0F).build());
    public static final Supplier<AttachmentType<DrumSetPacket>> DRUM_SET_DATA = ATTACHMENT_TYPES.register("drum_set_data", () -> AttachmentType.builder(DrumSetPacket::new).build());
    public static final Supplier<AttachmentType<ChunkData>> CHUNK_DATA = ATTACHMENT_TYPES.register("chunk_data", () -> AttachmentType.serializable(ChunkData::new).build());
    public static final Supplier<AttachmentType<LevelSavedData>> LEVEL_DATA = ATTACHMENT_TYPES.register("level_data", () -> AttachmentType.serializable(LevelSavedData::new).build());

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
