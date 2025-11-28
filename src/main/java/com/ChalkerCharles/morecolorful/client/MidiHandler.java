package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

@OnlyIn(Dist.CLIENT)
public class MidiHandler {
    private final List<MidiDevice> devices = new ArrayList<>();
    private final IntConsumer noteOnHandler;
    private final IntConsumer noteOffHandler;

    public MidiHandler(IntConsumer noteOnHandler, IntConsumer noteOffHandler) {
        this.noteOnHandler = noteOnHandler;
        this.noteOffHandler = noteOffHandler;

        MidiDevice device;
        MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : deviceInfo) {
            try {
                device = MidiSystem.getMidiDevice(info);
                MoreColorful.LOGGER.debug(String.valueOf(info));
                List<Transmitter> transmitters = device.getTransmitters();
                for (Transmitter transmitter : transmitters) {
                    transmitter.setReceiver(new MidiInputReceiver(device.getDeviceInfo().toString()));
                }
                Transmitter trans = device.getTransmitter();
                trans.setReceiver(new MidiInputReceiver(device.getDeviceInfo().toString()));
                device.open();
                devices.add(device);
                MoreColorful.LOGGER.debug("{} was opened", device.getDeviceInfo());
            } catch (MidiUnavailableException exception) {
                MoreColorful.LOGGER.debug("Found Midi unavailable: {}", exception.getMessage());
            }
        }
    }

    public void closeDevices() {
        for (MidiDevice device : devices) {
            if (device.isOpen()) {
                device.close();
            }
        }
    }

    private class MidiInputReceiver implements Receiver {
        public final String name;

        public MidiInputReceiver(String name) {
            this.name = name;
        }

        @Override
        public void send(MidiMessage message, long timeStamp) {
            if (message instanceof ShortMessage msg) {
                int keyId = msg.getData1() - 54;
                if (keyId < 0 || keyId > 24) {
                    return;
                }
                Minecraft minecraft = Minecraft.getInstance();
                if (msg.getCommand() == ShortMessage.NOTE_ON) {
                    var ignore = minecraft.submit(() -> MidiHandler.this.noteOnHandler.accept(keyId));
                }
                if (msg.getCommand() == ShortMessage.NOTE_OFF) {
                    var ignore = minecraft.submit(() -> MidiHandler.this.noteOffHandler.accept(keyId));
                }
            }
        }

        @Override
        public void close() {}
    }
}
