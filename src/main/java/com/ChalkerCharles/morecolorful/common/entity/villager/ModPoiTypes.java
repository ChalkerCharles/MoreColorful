package com.ChalkerCharles.morecolorful.common.entity.villager;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IPoiTypeMixin;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class ModPoiTypes {
    private static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, MoreColorful.MODID);

    public static final Holder<PoiType> PYROTECHNICIAN = register("pyrotechnician", ModBlocks.PYROTECHNICS_TABLE, 1, 1);
    public static final Holder<PoiType> PAPER_ARTISAN = register("paper_artisan", ModBlocks.PAPERCRAFT_TABLE, 1, 1);

    @SuppressWarnings("SameParameterValue")
    private static Holder<PoiType> register(String name, Supplier<Block> block, int maxTickets, int validRange) {
        return POI_TYPES.register(name, () -> new PoiType(blockStates(block.get()), maxTickets, validRange));
    }

    private static Set<BlockState> blockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

    public static void modifyVanilla() {
        PoiType beehive = BuiltInRegistries.POINT_OF_INTEREST_TYPE.get(PoiTypes.BEEHIVE);
        if (beehive != null) {
            ((IPoiTypeMixin) (Object) beehive).setMaxTickets(1);
        }
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
    }
}
