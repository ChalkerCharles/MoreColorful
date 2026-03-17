package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.level.ItemLike;

import java.util.List;

public class FireworkShapeTemplateItem extends Item {
    public static final ItemLike[] ALL_TYPES = new ItemLike[] {
            ModItems.FIREWORK_SHAPE_TEMPLATE_LARGE_BALL,
            ModItems.FIREWORK_SHAPE_TEMPLATE_STAR,
            ModItems.FIREWORK_SHAPE_TEMPLATE_CREEPER,
            ModItems.FIREWORK_SHAPE_TEMPLATE_BURST,
            ModItems.FIREWORK_SHAPE_TEMPLATE_CUBE,
            ModItems.FIREWORK_SHAPE_TEMPLATE_HEART,
            ModItems.FIREWORK_SHAPE_TEMPLATE_PLANET,
            ModItems.FIREWORK_SHAPE_TEMPLATE_JELLYFISH,
            ModItems.FIREWORK_SHAPE_TEMPLATE_CLOCK,
            ModItems.FIREWORK_SHAPE_TEMPLATE_AXIS,
            ModItems.FIREWORK_SHAPE_TEMPLATE_TETRAHEDRON,
            ModItems.FIREWORK_SHAPE_TEMPLATE_HYPERBOLOID
    };
    private final FireworkExplosion.Shape shape;

    public FireworkShapeTemplateItem(Properties pProperties, FireworkExplosion.Shape shape) {
        super(pProperties);
        this.shape = shape;
    }

    public static FireworkExplosion.Shape getShape(ItemStack stack) {
        if (stack.getItem() instanceof FireworkShapeTemplateItem item) {
            return item.shape;
        }
        return FireworkExplosion.Shape.SMALL_BALL;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(this.shape.getName().withStyle(ChatFormatting.GRAY));
    }
}
