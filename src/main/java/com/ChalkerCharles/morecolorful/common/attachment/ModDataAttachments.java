package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MoreColorful.MODID);

    public static final Supplier<AttachmentType<InstrumentData>> INSTRUMENT_DATA = register("instrument_data", () -> AttachmentType.builder(InstrumentData::new).build());
    public static final Supplier<AttachmentType<ChunkData>> CHUNK_DATA = register("chunk_data", () -> AttachmentType.serializable(ChunkData::new).build());
    public static final Supplier<AttachmentType<LevelSavedData>> LEVEL_DATA = register("level_data", () -> AttachmentType.serializable(LevelSavedData::create).build());

    private static <T> Supplier<AttachmentType<T>> register(String name, Supplier<AttachmentType<T>> supplier) {
        return ATTACHMENT_TYPES.register(name, supplier);
    }

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
