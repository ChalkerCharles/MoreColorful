package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.natural.WindFlowerBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PinwheelBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelColor;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelContext;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.joml.Vector3f;

import java.util.List;

public class PinwheelBlockEntity extends BlockEntity {
    private int currentFrame;
    private int lastFrame;
    private int interval = -1;
    private PinwheelColor color = PinwheelColor.DEFAULT;

    public PinwheelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PINWHEEL.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PinwheelBlockEntity blockEntity) {
        if (!Config.windSystem) return;
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos);
        if (wind != null) {
            float angle = RotationSegment.convertToDegrees(state.getValue(PinwheelBlock.ROTATION));
            Vector3f facing = new Vector3f(-Mth.sin(angle * Mth.DEG_TO_RAD), 0, Mth.cos(angle * Mth.DEG_TO_RAD));
            int windLevel = WindFlowerBlock.getWindLevel(-wind.dot(facing));
            blockEntity.tick(windLevel);
        }
    }

    private void tick(int windLevel) {
        if (windLevel == 0) {
            this.lastFrame = this.currentFrame;
            return;
        }
        if (this.interval == -1) {
            this.interval = PinwheelContext.getInterval(windLevel);
        }
        this.interval--;
        if (this.interval <= 0) {
            this.lastFrame = this.currentFrame;
            this.currentFrame += windLevel == 5 ? 2 : 1;
            this.interval = PinwheelContext.getInterval(windLevel);
        }
    }

    public int lerpFrame(float partialTick) {
        return Mth.lerpInt(partialTick, this.lastFrame, this.currentFrame);
    }

    public ItemStack getItem() {
        ItemStack stack = ModItems.PINWHEEL.toStack();
        stack.applyComponents(this.collectComponents());
        return stack;
    }

    public List<DyeColor> getColors() {
        return this.color.colors();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        this.color.save(pTag);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.color = PinwheelColor.load(pTag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return this.saveCustomOnly(pRegistries);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder pComponents) {
        super.collectImplicitComponents(pComponents);
        pComponents.set(ModDataComponents.PINWHEEL_COLOR, this.color);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput pComponentInput) {
        super.applyImplicitComponents(pComponentInput);
        this.color = pComponentInput.getOrDefault(ModDataComponents.PINWHEEL_COLOR, PinwheelColor.DEFAULT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void removeComponentsFromTag(CompoundTag pTag) {
        super.removeComponentsFromTag(pTag);
        pTag.remove("colors");
    }
}
