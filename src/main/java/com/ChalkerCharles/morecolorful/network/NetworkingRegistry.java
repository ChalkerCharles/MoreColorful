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

        // Server Bound
        registrar.playToServer(NotePlayingPacket.TYPE, NotePlayingPacket.STREAM_CODEC, NotePlayingPacket::handle);
        registrar.playToServer(WindInitiationPacket.TYPE, WindInitiationPacket.STREAM_CODEC, WindInitiationPacket::handle);

        // Both
        registrar.playBidirectional(PlayingScreenPacket.TYPE, PlayingScreenPacket.STREAM_CODEC, PlayingScreenPacket.HANDLER);
        registrar.playBidirectional(InstrumentPressingPacket.TYPE, InstrumentPressingPacket.STREAM_CODEC, InstrumentPressingPacket.HANDLER);
        registrar.playBidirectional(InstrumentTickingPacket.TYPE, InstrumentTickingPacket.STREAM_CODEC, InstrumentTickingPacket.HANDLER);
        registrar.playBidirectional(DrumSetPacket.TYPE, DrumSetPacket.STREAM_CODEC, DrumSetPacket.HANDLER);
    }
}
