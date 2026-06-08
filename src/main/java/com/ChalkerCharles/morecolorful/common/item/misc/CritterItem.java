package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.common.entity.animal.Butterfly;
import com.ChalkerCharles.morecolorful.common.entity.animal.Dragonfly;
import com.ChalkerCharles.morecolorful.common.entity.animal.Moth;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.*;

public class CritterItem extends Item {
    private static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        public ItemStack execute(BlockSource blockSource, ItemStack stack) {
            Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
            EntityType<? extends Mob> type = ((CritterItem)stack.getItem()).type;
            BlockPos pos = blockSource.pos();
            ServerLevel level = blockSource.level();
            try {
                Mob mob = type.spawn(level, stack, null, pos.relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                if (mob != null) mob.setPersistenceRequired();
            } catch (Exception exception) {
                LOGGER.error("Error while dispensing spawn egg from dispenser at {}", pos, exception);
                return ItemStack.EMPTY;
            }
            stack.shrink(1);
            level.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
            return stack;
        }
    };
    public static final Map<EntityType<?>, Item> ITEM_BY_TYPE = new HashMap<>();
    private final EntityType<? extends Mob> type;

    public CritterItem(EntityType<? extends Mob> type, Properties pProperties) {
        super(pProperties);
        this.type = type;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
        ITEM_BY_TYPE.put(type, this);
    }

    public static Set<ItemStack> getAllButterflies() {
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndComponentsSet();
        for (Butterfly.Variant variant : Butterfly.Variant.VALUES) {
            if (variant.unavailable()) continue;
            ItemStack stack = ModItems.BUTTERFLY.toStack();
            CompoundTag tag = new CompoundTag();
            tag.putString("id", "morecolorful:butterfly");
            tag.putInt("Type", variant.ordinal());
            stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
            set.add(stack);
        }
        return set;
    }

    public static Set<ItemStack> getAllMoths() {
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndComponentsSet();
        for (Moth.Variant variant : Moth.Variant.VALUES) {
            if (variant.unavailable()) continue;
            ItemStack stack = ModItems.MOTH.toStack();
            CompoundTag tag = new CompoundTag();
            tag.putString("id", "morecolorful:moth");
            tag.putInt("Type", variant.ordinal());
            stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
            set.add(stack);
        }
        return set;
    }

    public static Set<ItemStack> getAllCaterpillars() {
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndComponentsSet();
        for (AbstractMoth.Variant variant : AbstractMoth.Variant.values()) {
            if (variant.unavailable()) continue;
            ItemStack stack = ModItems.CATERPILLAR.toStack();
            CompoundTag tag = new CompoundTag();
            tag.putString("id", "morecolorful:caterpillar");
            tag.putInt("Type", variant.getIndex());
            stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
            set.add(stack);
        }
        return set;
    }

    public static Set<ItemStack> getAllDragonflies() {
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndComponentsSet();
        for (Dragonfly.Variant variant : Dragonfly.Variant.values()) {
            ItemStack stack = ModItems.DRAGONFLY.toStack();
            CompoundTag tag = new CompoundTag();
            tag.putString("id", "morecolorful:dragonfly");
            tag.putInt("Type", variant.ordinal());
            stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
            set.add(stack);
        }
        return set;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        ItemStack stack = pContext.getItemInHand();
        BlockPos pos = pContext.getClickedPos();
        Direction direction = pContext.getClickedFace();
        BlockState state = level.getBlockState(pos);
        BlockPos pos1;
        if (state.getCollisionShape(level, pos).isEmpty()) {
            pos1 = pos;
        } else {
            pos1 = pos.relative(direction);
        }
        Mob mob = this.type.spawn(
                (ServerLevel) level,
                stack,
                pContext.getPlayer(),
                pos1,
                MobSpawnType.SPAWN_EGG,
                true,
                !Objects.equals(pos, pos1) && direction == Direction.UP
        );
        if (mob != null) {
            mob.setPersistenceRequired();
            stack.shrink(1);
            level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, pos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        CustomData data = pStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        int i = data.copyTag().getInt("Type");
        if (pStack.is(ModItems.BUTTERFLY) || pStack.is(ModItems.MOTH) || pStack.is(ModItems.CATERPILLAR)) {
            addMothDescription(pStack, i, pTooltipComponents);
        }
    }

    private static void addMothDescription(ItemStack stack, int index, List<Component> components) {
        AbstractMoth.Variant variant;
        if (stack.is(ModItems.BUTTERFLY)) {
            variant = Butterfly.Variant.byIndex(index);
        } else if (stack.is(ModItems.MOTH)) {
            variant = Moth.Variant.byIndex(index);
        } else {
            variant = AbstractMoth.Variant.byIndex(index);
        }
        components.add(variant.getDescription().withStyle(ChatFormatting.GRAY));
    }
}
