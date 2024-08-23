package com.ChalkerCharles.morecolorful.network;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentPressingPacket;
import com.ChalkerCharles.morecolorful.network.packets.NotePlayingPacket;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentTickingPacket;
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
                PayloadHandler::handleNotePlaying);
        registrar.playBidirectional(
                PlayingScreenPacket.TYPE,
                PlayingScreenPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        PayloadHandler::handlePlayingScreenClient,
                        PayloadHandler::handlePlayingScreenServer));
        registrar.playBidirectional(
                InstrumentPressingPacket.TYPE,
                InstrumentPressingPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        PayloadHandler::handleInstrumentPressingClient,
                        PayloadHandler::handleInstrumentPressingServer));
        registrar.playBidirectional(
                InstrumentTickingPacket.TYPE,
                InstrumentTickingPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        PayloadHandler::handleInstrumentTickingClient,
                        PayloadHandler::handleInstrumentTickingServer));
    }
}
