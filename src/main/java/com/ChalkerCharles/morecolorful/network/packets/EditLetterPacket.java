package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.component.WrittenBookContent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record EditLetterPacket(int slot, List<String> pages, Optional<String> title) implements CustomPacketPayload {
    public static final Type<EditLetterPacket> TYPE = new Type<>(MoreColorful.location("edit_letter"));

    public static final StreamCodec<FriendlyByteBuf, EditLetterPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            EditLetterPacket::slot,
            ByteBufCodecs.stringUtf8(8192).apply(ByteBufCodecs.list(200)),
            EditLetterPacket::pages,
            ByteBufCodecs.stringUtf8(128).apply(ByteBufCodecs::optional),
            EditLetterPacket::title,
            EditLetterPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            int i = this.slot;
            if (Inventory.isHotbarSlot(i) || i == 40) {
                ServerPlayer player = (ServerPlayer) context.player();
                List<String> list = new ArrayList<>();
                Optional<String> optional = this.title;
                optional.ifPresent(list::add);
                this.pages.stream().limit(100L).forEach(list::add);
                Consumer<List<FilteredText>> consumer = optional.isPresent()
                        ? text -> this.signSheet(text.getFirst(), text.subList(1, text.size()), player)
                        : text -> this.updateSheetContents(text, player);
                player.connection.filterTextPacket(list).thenAcceptAsync(consumer, player.server);
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }

    private void updateSheetContents(List<FilteredText> pages, Player player) {
        ItemStack itemstack = player.getInventory().getItem(this.slot);
        if (itemstack.is(ModItems.WRITABLE_LETTER)) {
            List<Filterable<String>> list = pages.stream().map(t -> filterableFromOutgoing(player, t)).toList();
            itemstack.set(DataComponents.WRITABLE_BOOK_CONTENT, new WritableBookContent(list));
        }
    }

    private void signSheet(FilteredText title, List<FilteredText> pages, Player player) {
        ItemStack item = player.getInventory().getItem(this.slot);
        if (item.is(ModItems.WRITABLE_LETTER)) {
            ItemStack item1 = item.transmuteCopy(ModItems.LETTER);
            item1.remove(DataComponents.WRITABLE_BOOK_CONTENT);
            List<Filterable<Component>> list = pages.stream().map(text -> filterableFromOutgoing(player, text).<Component>map(Component::literal)).toList();
            item1.set(
                    DataComponents.WRITTEN_BOOK_CONTENT,
                    new WrittenBookContent(filterableFromOutgoing(player, title), player.getName().getString(), 0, list, true)
            );
            player.getInventory().setItem(this.slot, item1);
        }
    }

    private static Filterable<String> filterableFromOutgoing(Player player, FilteredText text) {
        return player.isTextFilteringEnabled() ? Filterable.passThrough(text.filteredOrEmpty()) : Filterable.from(text);
    }
}
