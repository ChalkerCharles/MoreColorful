package com.ChalkerCharles.morecolorful.network;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.packets.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkingRegistry {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MoreColorful.MODID).optional();
        // Client Bound
        registrar.playToClient(ThermalUpdatePacket.TYPE, ThermalUpdatePacket.STREAM_CODEC, ThermalUpdatePacket::handle);
        registrar.playToClient(ThermalRemovalPacket.TYPE, ThermalRemovalPacket.STREAM_CODEC, ThermalRemovalPacket::handle);
        registrar.playToClient(VentUpdatePacket.TYPE, VentUpdatePacket.STREAM_CODEC, VentUpdatePacket::handle);
        registrar.playToClient(VentRemovalPacket.TYPE, VentRemovalPacket.STREAM_CODEC, VentRemovalPacket::handle);
        registrar.playToClient(WindPacket.TYPE, WindPacket.STREAM_CODEC, WindPacket::handle);
        registrar.playToClient(WindZonePacket.TYPE, WindZonePacket.STREAM_CODEC, WindZonePacket::handle);
        registrar.playToClient(SmokeBombPacket.TYPE, SmokeBombPacket.STREAM_CODEC, SmokeBombPacket::handle);
        registrar.playToClient(OpenLetterPacket.TYPE, OpenLetterPacket.STREAM_CODEC, OpenLetterPacket::handle);

        // Server Bound
        registrar.playToServer(NotePlayingPacket.TYPE, NotePlayingPacket.STREAM_CODEC, NotePlayingPacket::handle);
        registrar.playToServer(EditSheetMusicPacket.TYPE, EditSheetMusicPacket.STREAM_CODEC, EditSheetMusicPacket::handle);
        registrar.playToServer(KiteReelPacket.TYPE, KiteReelPacket.STREAM_CODEC, KiteReelPacket::handle);
        registrar.playToServer(PaperCarvingPacket.TYPE, PaperCarvingPacket.STREAM_CODEC, PaperCarvingPacket::handle);
        registrar.playToServer(SoundPacket.TYPE, SoundPacket.STREAM_CODEC, SoundPacket::handle);
        registrar.playToServer(EditLetterPacket.TYPE, EditLetterPacket.STREAM_CODEC, EditLetterPacket::handle);
        registrar.playToServer(SealMailPacket.TYPE, SealMailPacket.STREAM_CODEC, SealMailPacket::handle);

        // Both
        registrar.playBidirectional(PlayingScreenPacket.TYPE, PlayingScreenPacket.STREAM_CODEC, PlayingScreenPacket.HANDLER);
        registrar.playBidirectional(InstrumentPressingPacket.TYPE, InstrumentPressingPacket.STREAM_CODEC, InstrumentPressingPacket.HANDLER);
        registrar.playBidirectional(InstrumentTickingPacket.TYPE, InstrumentTickingPacket.STREAM_CODEC, InstrumentTickingPacket.HANDLER);
        registrar.playBidirectional(DrumSetPacket.TYPE, DrumSetPacket.STREAM_CODEC, DrumSetPacket.HANDLER);
    }
}
