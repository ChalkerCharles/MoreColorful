package com.ChalkerCharles.morecolorful.common.loot;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static final ResourceKey<LootTable> TRAIL_RUINS_ARCHAEOLOGY_COMMON_ADD = register("archaeology/trail_ruins_common_add");
    public static final ResourceKey<LootTable> VILLAGE_APIARY = register("chests/village/village_apiary");
    public static final ResourceKey<LootTable> VILLAGE_PYROTECHNICIAN_HOUSE = register("chests/village/village_pyrotechnician_house");
    public static final ResourceKey<LootTable> VILLAGE_PAPER_MILL = register("chests/village/village_paper_mill");
    public static final ResourceKey<LootTable> BEEKEEPER_GIFT = register("gameplay/hero_of_the_village/beekeeper_gift");
    public static final ResourceKey<LootTable> PYROTECHNICIAN_GIFT = register("gameplay/hero_of_the_village/pyrotechnician_gift");
    public static final ResourceKey<LootTable> PAPER_ARTISAN_GIFT = register("gameplay/hero_of_the_village/paper_artisan_gift");
    public static final ResourceKey<LootTable> VILLAGE_PLAINS_HOUSE_ADD = register("chests/village/village_plains_house_add");

    private static ResourceKey<LootTable> register(String pName) {
        return ResourceKey.create(Registries.LOOT_TABLE, MoreColorful.location(pName));
    }
}
