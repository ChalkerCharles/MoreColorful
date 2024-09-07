package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MoreColorful.MODID);
    public static final Supplier<AttachmentType<Boolean>> IS_PLAYING_INSTRUMENT = ATTACHMENT_TYPES.register("is_playing_instrument", () -> AttachmentType.builder(() -> false).build());
    public static final Supplier<AttachmentType<Object[]>> PLAYING_SCREEN_DATA = ATTACHMENT_TYPES.register("playing_screen_data", () -> AttachmentType.builder(() -> new Object[] {InstrumentsType.HARP, new BlockPos(0, -65, 0), 0, false}).build());
    public static final Supplier<AttachmentType<Float>> PLAYING_SCREEN_TICK = ATTACHMENT_TYPES.register("playing_screen_tick", () -> AttachmentType.builder(() -> 0F).build());
    public static final Supplier<AttachmentType<Object[]>> DRUM_SET_DATA = ATTACHMENT_TYPES.register("drum_set_data", () -> AttachmentType.builder(() -> new Object[] {false, false, false, false, new BlockPos(0, -65, 0), 0}).build());
    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
