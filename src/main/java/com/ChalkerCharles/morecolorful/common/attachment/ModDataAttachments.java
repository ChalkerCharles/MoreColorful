package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MoreColorful.MODID);

    public static final Supplier<AttachmentType<PlayerData>> PLAYER_DATA = ATTACHMENT_TYPES.register("player_data", () -> AttachmentType.builder(PlayerData::new).build());
    public static final Supplier<AttachmentType<ChunkData>> CHUNK_DATA = ATTACHMENT_TYPES.register("chunk_data", () -> AttachmentType.serializable(ChunkData::new).build());
    public static final Supplier<AttachmentType<LevelSavedData>> LEVEL_DATA = ATTACHMENT_TYPES.register("level_data", () -> AttachmentType.serializable(LevelSavedData::create).build());

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
