package com.ChalkerCharles.morecolorful.common.entity.villager;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModVillagerProfessions {
    private static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(Registries.VILLAGER_PROFESSION, MoreColorful.MODID);

    public static final Holder<VillagerProfession> BEEKEEPER = register("beekeeper", p -> p.is(PoiTypes.BEEHIVE), ModSounds.VILLAGER_WORK_BEEKEEPER);
    public static final Holder<VillagerProfession> PYROTECHNICIAN = register("pyrotechnician", is(ModPoiTypes.PYROTECHNICIAN), ModSounds.VILLAGER_WORK_PYROTECHNICIAN);
    public static final Holder<VillagerProfession> PAPER_ARTISAN = register("paper_artisan", is(ModPoiTypes.PAPER_ARTISAN), ModSounds.VILLAGER_WORK_PAPER_ARTISAN);

    private static Holder<VillagerProfession> register(String name, Predicate<Holder<PoiType>> jobSite, Supplier<SoundEvent> workSound) {
        return VILLAGER_PROFESSIONS.register(name, () -> new VillagerProfession(name, jobSite, jobSite, ImmutableSet.of(), ImmutableSet.of(), workSound.get()));
    }

    private static Predicate<Holder<PoiType>> is(Holder<PoiType> poiType) {
        return p -> p.equals(poiType);
    }

    public static void register(IEventBus eventBus) {
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
