package com.ChalkerCharles.morecolorful.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;

public record MailContent(ItemStack letter, ItemStack attachment, String sender, String recipient) {
    public static final MailContent DEFAULT = new MailContent(ItemStack.EMPTY, ItemStack.EMPTY, "", "");
    public static final Codec<MailContent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ItemStack.CODEC.optionalFieldOf("letter", ItemStack.EMPTY).forGetter(MailContent::letter),
                    ItemStack.CODEC.optionalFieldOf("attachment", ItemStack.EMPTY).forGetter(MailContent::attachment),
                    Codec.STRING.fieldOf("sender").forGetter(MailContent::sender),
                    Codec.STRING.fieldOf("recipient").forGetter(MailContent::recipient)
            ).apply(instance, MailContent::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, MailContent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC,
            MailContent::letter,
            ItemStack.OPTIONAL_STREAM_CODEC,
            MailContent::attachment,
            ByteBufCodecs.STRING_UTF8,
            MailContent::sender,
            ByteBufCodecs.STRING_UTF8,
            MailContent::recipient,
            MailContent::new
    );

    public List<ItemStack> getItems() {
        return List.of(this.letter.copy(), this.attachment.copy());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof MailContent(ItemStack letter1, ItemStack attachment1, String sender1, String recipient1)) {
            return ItemStack.matches(this.letter, letter1)
                    && ItemStack.matches(this.attachment, attachment1)
                    && Objects.equals(this.sender, sender1)
                    && Objects.equals(this.recipient, recipient1);
        }
        return false;
    }
}
