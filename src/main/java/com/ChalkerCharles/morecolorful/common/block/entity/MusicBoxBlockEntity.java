package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.musical.MusicBoxBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.EditableMelody;
import com.ChalkerCharles.morecolorful.common.item.component.Melody;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem;

import java.util.List;

public class MusicBoxBlockEntity extends BlockEntity implements ContainerSingleItem.BlockContainerSingleItem {
    private float rot;
    private float oRot;
    private int progress;
    private int lastSignal = 1;
    public ItemStack sheet = ItemStack.EMPTY;
    private String melody = "";

    public MusicBoxBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MUSIC_BOX.get(), pPos, pBlockState);
    }

    private boolean notEmpty() {
        return !this.melody.isEmpty();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MusicBoxBlockEntity musicBox) {
        musicBox.oRot = musicBox.rot;
        if (!state.getValue(MusicBoxBlock.POWERED)) return;
        musicBox.rot = Mth.wrapDegrees(musicBox.rot + 12);
        if (!level.isClientSide && musicBox.notEmpty()) {
            if (musicBox.shouldEmitPlayEvent()) {
                level.gameEvent(GameEvent.NOTE_BLOCK_PLAY, pos, GameEvent.Context.of(state));
                spawnMusicParticles(level, pos);
            }
            musicBox.parseAndPlay(level, pos, state);
            musicBox.calculateSignal();
        }
    }

    private void parseAndPlay(Level level, BlockPos pos, BlockState state) {
        Holder<SoundEvent> sound = MusicBoxBlock.getSound(state.getValue(MusicBoxBlock.INSTRUMENT));
        while (this.notEnded()) {
            char a = this.melody.charAt(this.progress++);
            if (a == 'z') break;
            int pitch = a - 'm';
            level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, SoundSource.RECORDS, 3.0F, (float) Math.pow(2, pitch / 12.0));
        }
        if (!this.notEnded()) {
            this.progress++;
        }
        if (this.progress - this.melody.length() > 8) {
            this.melody = "";
            this.setChanged();
        }
    }

    private boolean notEnded() {
        return this.melody.length() > this.progress;
    }

    private boolean shouldEmitPlayEvent() {
        return this.notEnded() && this.progress % 24 == 0;
    }

    private static void spawnMusicParticles(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverlevel) {
            Vec3 vec3 = Vec3.atBottomCenterOf(pos).add(0.0, 1.0F, 0.0);
            float f = (float)level.getRandom().nextInt(4) / 24.0F;
            serverlevel.sendParticles(ParticleTypes.NOTE, vec3.x(), vec3.y(), vec3.z(), 0, f, 0.0, 0.0, 1.0);
        }
    }

    private void notifySheetChange() {
        BlockPos pos = this.getBlockPos();
        BlockState state = this.getBlockState();
        if (this.level != null && this.level.getBlockState(pos) == state) {
            BlockState newState = state.setValue(MusicBoxBlock.SHEET, !this.sheet.isEmpty());
            this.level.setBlock(pos, newState, 2);
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        }
    }

    public float getRot(float partialTick) {
        return Mth.rotLerp(partialTick, this.oRot, this.rot);
    }

    public void setSheet(ItemStack stack) {
        this.sheet = stack;
        this.setMelody();
        this.progress = 0;
        this.lastSignal = 1;
        this.setChanged();
    }

    private void setMelody() {
        String melody;
        if (this.sheet.is(ModItems.WRITABLE_SHEET_MUSIC)) {
            melody = composeMelody(this.sheet.getOrDefault(ModDataComponents.EDITABLE_MELODY, EditableMelody.EMPTY).pages());
        } else if (this.sheet.is(ModItems.SHEET_MUSIC)) {
            melody = composeMelody(this.sheet.getOrDefault(ModDataComponents.MELODY, Melody.EMPTY).pages());
        } else {
            melody = "";
        }
        this.melody = melody;
    }

    private static String composeMelody(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s);
        }
        return builder.toString();
    }

    private void calculateSignal() {
        float delta = Mth.clamp((float) this.progress / this.melody.length(), 0, 1);
        int signal = Mth.lerpInt(delta, 1, 15);
        if (this.lastSignal != signal) {
            this.setChanged();
            this.lastSignal = signal;
        }
    }

    public int signalOutput() {
        if (this.notEmpty()) {
            return this.lastSignal;
        }
        return 0;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("Sheet", 10)) {
            this.sheet = ItemStack.parse(pRegistries, pTag.getCompound("Sheet")).orElse(ItemStack.EMPTY);
        } else {
            this.sheet = ItemStack.EMPTY;
        }
        this.setMelody();
        this.progress = pTag.getInt("Progress");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (!this.sheet.isEmpty()) {
            pTag.put("Sheet", this.sheet.save(pRegistries));
            pTag.putInt("Progress", this.progress);
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public boolean canPlaceItem(int pSlot, ItemStack pStack) {
        return MusicBoxBlock.isSheetMusic(pStack) && this.getItem(pSlot).isEmpty();
    }

    @Override
    public boolean canTakeItem(Container pTarget, int pSlot, ItemStack pStack) {
        return pTarget.hasAnyMatching(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getTheItem() {
        return this.sheet;
    }

    @Override
    public void setTheItem(ItemStack pItem) {
        this.setSheet(pItem);
        this.notifySheetChange();
    }

    @Override
    public ItemStack splitTheItem(int pAmount) {
        ItemStack item = this.sheet;
        this.setTheItem(ItemStack.EMPTY);
        return item;
    }
}
