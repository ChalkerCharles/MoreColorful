package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.common.block.natural.WindFlowerBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelContext;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class PinwheelItem extends BlockItem {
    public static final ItemLike[] ALL_DYE_COLORS = new ItemLike[] {
            ModItems.WHITE_PINWHEEL,
            ModItems.LIGHT_GRAY_PINWHEEL,
            ModItems.GRAY_PINWHEEL,
            ModItems.BLACK_PINWHEEL,
            ModItems.BROWN_PINWHEEL,
            ModItems.RED_PINWHEEL,
            ModItems.ORANGE_PINWHEEL,
            ModItems.YELLOW_PINWHEEL,
            ModItems.LIME_PINWHEEL,
            ModItems.GREEN_PINWHEEL,
            ModItems.CYAN_PINWHEEL,
            ModItems.LIGHT_BLUE_PINWHEEL,
            ModItems.BLUE_PINWHEEL,
            ModItems.PURPLE_PINWHEEL,
            ModItems.MAGENTA_PINWHEEL,
            ModItems.PINK_PINWHEEL
    };

    public PinwheelItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public static boolean isMulticolor(ItemStack stack) {
        return stack.is(ModItems.MULTICOLORED_PINWHEEL);
    }

    public static ItemLike itemByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_PINWHEEL;
            case ORANGE -> ModItems.ORANGE_PINWHEEL;
            case MAGENTA -> ModItems.MAGENTA_PINWHEEL;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_PINWHEEL;
            case YELLOW -> ModItems.YELLOW_PINWHEEL;
            case LIME -> ModItems.LIME_PINWHEEL;
            case PINK -> ModItems.PINK_PINWHEEL;
            case GRAY -> ModItems.GRAY_PINWHEEL;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_PINWHEEL;
            case CYAN -> ModItems.CYAN_PINWHEEL;
            case PURPLE -> ModItems.PURPLE_PINWHEEL;
            case BLUE -> ModItems.BLUE_PINWHEEL;
            case BROWN -> ModItems.BROWN_PINWHEEL;
            case GREEN -> ModItems.GREEN_PINWHEEL;
            case RED -> ModItems.RED_PINWHEEL;
            case BLACK -> ModItems.BLACK_PINWHEEL;
        };
    }

    public static void applyWind(Entity entity) {
        Level level = entity.level();
        if (level.isClientSide && level.tickRateManager().runsNormally() && entity instanceof LivingEntity living) {
            ItemStack main = living.getMainHandItem();
            ItemStack off = living.getOffhandItem();
            ItemStack head = living.getItemBySlot(EquipmentSlot.HEAD);
            if (hasPinwheel(main, off, head)) {
                Vec3 pos = living.position();
                Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos.x, pos.y + 0.5, pos.z);
                int windLevel = 0;
                if (wind != null) {
                    Vector3f facing = entity.getLookAngle().toVector3f();
                    windLevel = WindFlowerBlock.getWindLevel(-wind.dot(facing));
                }
                updateContext(main, windLevel);
                updateContext(off, windLevel);
                updateContext(head, windLevel);
            }
        }
    }

    private static boolean hasPinwheel(ItemStack main, ItemStack off, ItemStack head) {
        return main.getItem() instanceof PinwheelItem
                || off.getItem() instanceof PinwheelItem
                || head.getItem() instanceof PinwheelItem;
    }

    private static void updateContext(ItemStack stack, int windLevel) {
        if (stack.getItem() instanceof PinwheelItem) {
            stack.update(ModDataComponents.PINWHEEL_CONTEXT, PinwheelContext.DEFAULT, windLevel, PinwheelContext::update);
        }
    }

    public static void merge(ItemStack carried, ItemStack stackedOn) {
        if (ItemStack.isSameItem(carried, stackedOn)) {
            PinwheelContext context = stackedOn.getOrDefault(ModDataComponents.PINWHEEL_CONTEXT, PinwheelContext.DEFAULT);
            carried.set(ModDataComponents.PINWHEEL_CONTEXT, context);
        }
    }
}
