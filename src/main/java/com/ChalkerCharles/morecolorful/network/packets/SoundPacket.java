package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SoundPacket(Holder<SoundEvent> sound, SoundSource source, double x, double y, double z, float volume, float pitch) implements CustomPacketPayload {
    public static final Type<SoundPacket> TYPE = new Type<>(MoreColorful.location("sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SoundPacket> STREAM_CODEC = StreamCodec.ofMember(
            SoundPacket::write, SoundPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public SoundPacket(SoundEvent sound, SoundSource source, BlockPos pos, float volume, float pitch) {
        this(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound), source, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, volume, pitch);
    }

    private SoundPacket(RegistryFriendlyByteBuf buf) {
        this(SoundEvent.STREAM_CODEC.decode(buf),
                buf.readEnum(SoundSource.class),
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble(),
                buf.readFloat(),
                buf.readFloat()
        );
    }

    private void write(RegistryFriendlyByteBuf buf) {
        SoundEvent.STREAM_CODEC.encode(buf, this.sound);
        buf.writeEnum(this.source);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.volume);
        buf.writeFloat(this.pitch);
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            level.playSound(null, this.x, this.y, this.z, this.sound, this.source, this.volume, this.pitch);
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
