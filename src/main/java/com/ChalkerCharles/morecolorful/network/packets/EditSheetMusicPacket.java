package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.EditableMelody;
import com.ChalkerCharles.morecolorful.common.item.component.Melody;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public record EditSheetMusicPacket(int slot, List<BitSet> pages, Optional<String> title) implements CustomPacketPayload {
    public static final Type<EditSheetMusicPacket> TYPE = new Type<>(MoreColorful.location("edit_sheet_music"));

    public static final StreamCodec<FriendlyByteBuf, EditSheetMusicPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            EditSheetMusicPacket::slot,
            NetworkUtils.SHEET_MUSIC_PAGES,
            EditSheetMusicPacket::pages,
            ByteBufCodecs.stringUtf8(128).apply(ByteBufCodecs::optional),
            EditSheetMusicPacket::title,
            EditSheetMusicPacket::new
    );

    public EditSheetMusicPacket {
        pages = List.copyOf(pages);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            int i = this.slot;
            if (Inventory.isHotbarSlot(i) || i == 40) {
                ServerPlayer player = (ServerPlayer) context.player();
                Consumer<FilteredText> consumer = this.title.isPresent()
                        ? text -> this.signSheet(text, player)
                        : text -> this.updateSheetContents(player);
                this.filterTextPacket(player).thenAcceptAsync(consumer, player.server);
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }

    private void updateSheetContents(Player player) {
        ItemStack itemstack = player.getInventory().getItem(this.slot);
        if (itemstack.is(ModItems.WRITABLE_SHEET_MUSIC)) {
            itemstack.set(ModDataComponents.EDITABLE_MELODY, EditableMelody.of(this.pages));
        }
    }

    private void signSheet(FilteredText title, Player player) {
        ItemStack item = player.getInventory().getItem(this.slot);
        if (item.is(ModItems.WRITABLE_SHEET_MUSIC)) {
            ItemStack item1 = item.transmuteCopy(ModItems.SHEET_MUSIC);
            item1.remove(ModDataComponents.EDITABLE_MELODY);
            List<String> list = Melody.compose(this.pages);
            item1.set(
                    ModDataComponents.MELODY,
                    new Melody(filterableFromOutgoing(player, title), player.getName().getString(), 0, list)
            );
            player.getInventory().setItem(this.slot, item1);
        }
    }

    private static Filterable<String> filterableFromOutgoing(Player player, FilteredText text) {
        return player.isTextFilteringEnabled() ? Filterable.passThrough(text.filteredOrEmpty()) : Filterable.from(text);
    }

    private CompletableFuture<FilteredText> filterTextPacket(ServerPlayer player) {
        return this.title.map(s -> player.connection.filterTextPacket(s))
                .orElseGet(() -> CompletableFuture.completedFuture(null));
    }
}
