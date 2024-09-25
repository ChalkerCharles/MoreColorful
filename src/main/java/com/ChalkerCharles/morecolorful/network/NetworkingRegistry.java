package com.ChalkerCharles.morecolorful.network;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.packets.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkingRegistry {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MoreColorful.MODID).versioned("1.0.0").optional();
        registrar.playToServer(
                NotePlayingPacket.TYPE,
                NotePlayingPacket.STREAM_CODEC,
                NotePlayingPacket::handle);
        registrar.playBidirectional(
                PlayingScreenPacket.TYPE,
                PlayingScreenPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        PlayingScreenPacket::handleClient,
                        PlayingScreenPacket::handleServer));
        registrar.playBidirectional(
                InstrumentPressingPacket.TYPE,
                InstrumentPressingPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        InstrumentPressingPacket::handleClient,
                        InstrumentPressingPacket::handleServer));
        registrar.playBidirectional(
                InstrumentTickingPacket.TYPE,
                InstrumentTickingPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        InstrumentTickingPacket::handleClient,
                        InstrumentTickingPacket::handleServer));
        registrar.playBidirectional(
                DrumSetPacket.TYPE,
                DrumSetPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        DrumSetPacket::handleClient,
                        DrumSetPacket::handleServer));
    }
}
