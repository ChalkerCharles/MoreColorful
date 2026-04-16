package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

public class CocoonBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<String> IGNORED_TAGS = List.of(
            "Air",
            "ArmorDropChances",
            "ArmorItems",
            "Brain",
            "CanPickUpLoot",
            "DeathTime",
            "FallDistance",
            "FallFlying",
            "Fire",
            "HandDropChances",
            "HandItems",
            "HurtByTimestamp",
            "HurtTime",
            "LeftHanded",
            "Motion",
            "NoGravity",
            "OnGround",
            "PortalCooldown",
            "Pos",
            "Rotation",
            "SleepingX",
            "SleepingY",
            "SleepingZ",
            "Passengers",
            "leash",
            "UUID",
            "Type"
    );
    private CustomData data = CustomData.EMPTY;

    public CocoonBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COCOON.get(), pPos, pBlockState);
    }

    public void setData(CustomData data) {
        this.data = data;
    }

    public boolean isEmpty() {
        CompoundTag tag = this.data.copyTag();
        return tag.isEmpty() || EntityType.by(tag).isEmpty();
    }

    public void hatch(Level level, BlockPos pos) {
        CompoundTag tag = this.data.copyTag();
        Optional<EntityType<?>> type = EntityType.by(tag);
        if (type.isEmpty()) return;
        if (type.orElseThrow() == ModEntities.CATERPILLAR.get()) {
            int i = tag.getInt("Type");
            AbstractMoth.Variant variant = AbstractMoth.Variant.byIndex(i);
            IGNORED_TAGS.forEach(tag::remove);
            EntityType<? extends AbstractMoth> type1 = variant.isButterfly() ? ModEntities.BUTTERFLY.get() : ModEntities.MOTH.get();
            AbstractMoth abstractMoth = type1.create(level);
            if (abstractMoth != null) {
                abstractMoth.load(tag);
                abstractMoth.setVariant(variant);
                abstractMoth.setPos(pos.getCenter());
                abstractMoth.setYRot(level.random.nextFloat() * 360.0F);
                abstractMoth.setPersistenceRequired();
                level.addFreshEntity(abstractMoth);
            }
        }
    }

    public ItemStack getItem() {
        ItemStack stack = ModItems.COCOON.toStack();
        stack.applyComponents(this.collectComponents());
        return stack;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("CocoonData")) {
            CustomData.CODEC
                    .parse(NbtOps.INSTANCE, pTag.get("CocoonData"))
                    .resultOrPartial(s -> LOGGER.error("Failed to parse cocoon data: '{}'", s))
                    .ifPresent(this::setData);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("CocoonData", CustomData.CODEC.encodeStart(NbtOps.INSTANCE, this.data).getOrThrow());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder pComponents) {
        super.collectImplicitComponents(pComponents);
        pComponents.set(ModDataComponents.COCOON_DATA, this.data);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput pComponentInput) {
        super.applyImplicitComponents(pComponentInput);
        this.data = pComponentInput.getOrDefault(ModDataComponents.COCOON_DATA, CustomData.EMPTY);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void removeComponentsFromTag(CompoundTag pTag) {
        super.removeComponentsFromTag(pTag);
        pTag.remove("CocoonData");
    }
}
