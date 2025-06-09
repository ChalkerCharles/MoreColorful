package com.ChalkerCharles.morecolorful.common.loot;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static final ResourceKey<LootTable> TRAIL_RUINS_ARCHAEOLOGY_COMMON_ADD = register("archaeology/trail_ruins_common_add");

    private static ResourceKey<LootTable> register(String pName) {
        return ResourceKey.create(Registries.LOOT_TABLE, MoreColorful.location(pName));
    }
}
