package com.ChalkerCharles.morecolorful.common.level;

import com.ChalkerCharles.morecolorful.common.block.entity.MailboxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

public record MailCallback(long pos, ItemStack mail) {
    public CompoundTag serialize(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putLong("pos", this.pos);
        tag.put("mail", this.mail.saveOptional(provider));
        return tag;
    }

    public static MailCallback deserialize(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag.contains("pos") && tag.contains("mail")) {
            long pos = tag.getLong("pos");
            ItemStack mail = ItemStack.parseOptional(provider, tag);
            return new MailCallback(pos, mail);
        }
        return null;
    }

    public void run(BlockGetter level) {
        BlockPos pos = BlockPos.of(this.pos);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MailboxBlockEntity mailbox) {
            mailbox.tryInsertIn(this.mail);
        }
    }
}
