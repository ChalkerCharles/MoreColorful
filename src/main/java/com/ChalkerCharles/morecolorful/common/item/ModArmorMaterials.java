package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MoreColorful.MODID);
    private static final List<ArmorMaterial.Layer> EMPTY_LAYER = List.of(new ArmorMaterial.Layer(MoreColorful.location("empty")));

    public static final Holder<ArmorMaterial> CLOTH = ARMOR_MATERIALS.register("cloth", () -> new ArmorMaterial(
            defense(1, 3, 2, 1, 3), 9, SoundEvents.ARMOR_EQUIP_GENERIC, Ingredient::of, EMPTY_LAYER, 0.0F, 0.0F
    ));

    @SuppressWarnings("SameParameterValue")
    private static EnumMap<ArmorItem.Type, Integer> defense(int helmet, int chestplate, int leggings, int boots, int body) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.HELMET, helmet);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.BODY, body);
        return map;
    }

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
