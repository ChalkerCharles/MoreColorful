package com.ChalkerCharles.morecolorful.common.advancement;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IInventoryChangeTriggerInstanceMixin;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VanillaAdvancementModifier {
    public static void modify(ResourceLocation location, Advancement advancement) {
        if (location.equals(ResourceLocation.withDefaultNamespace("husbandry/froglights"))) {
            Criterion<?> criterion = advancement.criteria().get("froglights");
            if (criterion.triggerInstance() instanceof InventoryChangeTrigger.TriggerInstance instance) {
                List<ItemPredicate> items = new ArrayList<>(instance.items());
                addItemPredicates(items, ModItems.VERMILION_FROGLIGHT, ModItems.CYANINE_FROGLIGHT, ModItems.UMBER_FROGLIGHT);
                ((IInventoryChangeTriggerInstanceMixin) (Object) instance).setItems(items);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void addItemPredicates(List<ItemPredicate> list, ItemLike... items) {
        for (ItemLike item : items) {
            list.add(new ItemPredicate(Optional.of(HolderSet.direct(item.asItem().builtInRegistryHolder())), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, Map.of()));
        }
    }
}
