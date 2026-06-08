package com.ChalkerCharles.morecolorful.mixin.mixins.accessor;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(InventoryChangeTrigger.TriggerInstance.class)
public interface IInventoryChangeTriggerInstanceMixin {
    @Accessor("items")
    @Mutable
    void setItems(List<ItemPredicate> items);
}
