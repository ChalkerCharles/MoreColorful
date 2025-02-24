package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.client.Minecraft;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class MidiHandler {
    private final ArrayList<MidiDevice> devices = new ArrayList<>();
    private final Consumer<Integer> noteOnHandler;
    private final Consumer<Integer> noteOffHandler;

    public MidiHandler(Consumer<Integer> noteOnHandler, Consumer<Integer> noteOffHandler) {
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
        for(MidiDevice device : devices){
            if(device.isOpen()){
                device.close();
            }
        }
    }

    public class MidiInputReceiver implements Receiver {
        public final String name;
        public MidiInputReceiver(String name) {
            this.name = name;
        }
        @Override
            public void send(MidiMessage message, long timeStamp) {
                if (message instanceof ShortMessage msg) {
                    int keyId = msg.getData1() - 54;
                    if(keyId < 0 || keyId > 24){
                        return;
                    }
                    if (msg.getCommand() == ShortMessage.NOTE_ON) {
                        try {
                            Minecraft.getInstance().submit(() -> noteOnHandler.accept(keyId)).get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (msg.getCommand() == ShortMessage.NOTE_OFF) {
                        try {
                            Minecraft.getInstance().submit(() -> noteOffHandler.accept(keyId)).get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void close() {}
    }
}
