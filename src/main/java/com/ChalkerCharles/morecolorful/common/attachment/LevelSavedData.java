package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.common.level.WindManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;
import org.joml.Vector2f;

public final class LevelSavedData implements INBTSerializable<CompoundTag> {
    private final WindManager windManager;

    public LevelSavedData(IAttachmentHolder holder) {
        Level level = (Level) holder;
        this.windManager = new WindManager(level);
    }

    private static LevelSavedData get(Level level) {
        return level.getData(ModDataAttachments.LEVEL_DATA);
    }

    public static Vector2f getGlobalWindSpeed(Level level) {
        return get(level).windManager.getGlobalWindSpeed();
    }

    public static void setGlobalWindSpeed(Level level, float x, float z) {
        get(level).windManager.setGlobalWindSpeed(x, z);
    }

    public static void setWindSpeedByCommand(Level level, float x, float z) {
        get(level).windManager.setWindSpeedByCommand(x, z);
    }

    public static void resetWindSpeed(Level level) {
        get(level).windManager.resetWindSpeed();
    }

    public static void update(Level level) {
        get(level).windManager.update();
    }

    @Override
    @UnknownNullability
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        this.windManager.serialize(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.windManager.deserialize(nbt);
    }
}
