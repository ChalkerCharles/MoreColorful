package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.common.level.MailCallback;
import com.ChalkerCharles.morecolorful.common.level.wind.WindManager;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZoneManager;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerLevelData extends LevelSavedData {
    private final ServerLevel level;
    private final WindManager.Server windManager;
    private final WindZoneManager windZoneManager = new WindZoneManager();
    private final Map<String, LongSet> mailboxes = new HashMap<>();
    private final Long2ObjectMap<List<MailCallback>> mailCallbacks = new Long2ObjectOpenHashMap<>();

    public ServerLevelData(ServerLevel level) {
        this.level = level;
        this.windManager = new WindManager.Server(level);
    }

    private static ServerLevelData get(ServerLevel level) {
        return (ServerLevelData) LevelSavedData.get(level);
    }

    @Override
    protected Level level() {
        return this.level;
    }

    @Override
    protected WindManager.Server windManager() {
        return this.windManager;
    }

    @Override
    protected WindZoneManager windZoneManager() {
        return this.windZoneManager;
    }

    public static void setWindSpeedByCommand(ServerLevel level, float x, float z) {
        get(level).windManager.setWindSpeedByCommand(x, z);
    }

    public static void resetWindSpeed(ServerLevel level) {
        get(level).windManager.resetWindSpeed();
    }

    public static void freezeWindSpeed(ServerLevel level, boolean freeze) {
        get(level).windManager.setWindFrozen(freeze);
    }

    @Override
    public Map<String, LongSet> getMailboxes() {
        return this.mailboxes;
    }

    public static void addMailCallback(ServerLevel level, long chunkPos, MailCallback callback) {
        get(level).mailCallbacks.computeIfAbsent(chunkPos, l -> new ArrayList<>()).add(callback);
    }

    private void runMailCallbacks(long chunkPos) {
        List<MailCallback> callbacks = this.mailCallbacks.get(chunkPos);
        if (callbacks == null) return;
        for (MailCallback callback : callbacks) {
            callback.run(this.level);
        }
        callbacks.clear();
        this.mailCallbacks.remove(chunkPos);
    }

    public static void runMailCallbacks(ServerLevel level, long chunkPos) {
        get(level).runMailCallbacks(chunkPos);
    }

    @Override
    @UnknownNullability
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = super.serializeNBT(provider);
        CompoundTag tag = new CompoundTag();
        this.mailboxes.forEach((s, l) -> {
            if (l.isEmpty()) return;
            tag.putLongArray(s, l.toLongArray());
        });
        nbt.put("mailboxes", tag);
        this.serializeMailCallbacks(nbt, provider);
        return nbt;
    }

    private void serializeMailCallbacks(CompoundTag nbt, HolderLookup.Provider provider) {
        if (this.mailCallbacks.isEmpty()) return;
        CompoundTag tag = new CompoundTag();
        for (Long2ObjectMap.Entry<List<MailCallback>> entry : this.mailCallbacks.long2ObjectEntrySet()) {
            String chunkPos = String.valueOf(entry.getLongKey());
            ListTag listTag = new ListTag();
            for (MailCallback callback : entry.getValue()) {
                listTag.add(callback.serialize(provider));
            }
            if (!listTag.isEmpty()) {
                tag.put(chunkPos, listTag);
            }
        }
        nbt.put("mailCallbacks", tag);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider, nbt);
        CompoundTag tag = nbt.getCompound("mailboxes");
        this.mailboxes.clear();
        for (String key : tag.getAllKeys()) {
            LongSet set = new LongOpenHashSet(tag.getLongArray(key));
            this.mailboxes.put(key, set);
        }
        this.deserializeMailCallbacks(nbt, provider);
    }

    private void deserializeMailCallbacks(CompoundTag nbt, HolderLookup.Provider provider) {
        this.mailCallbacks.clear();
        CompoundTag tag = nbt.getCompound("mailCallbacks");
        for (String key : tag.getAllKeys()) {
            try {
                long chunkPos = Long.parseLong(key);
                ListTag list = tag.getList(key, Tag.TAG_COMPOUND);
                for (int i = 0; i < list.size(); i++) {
                    CompoundTag compound = list.getCompound(i);
                    MailCallback callback = MailCallback.deserialize(compound, provider);
                    if (callback != null) {
                        this.mailCallbacks.computeIfAbsent(chunkPos, l -> new ArrayList<>()).add(callback);
                    }
                }
            } catch (NumberFormatException ignored) {}
        }
    }
}
