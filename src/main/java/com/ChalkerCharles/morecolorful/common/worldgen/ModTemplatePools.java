package com.ChalkerCharles.morecolorful.common.worldgen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModTemplatePools {
    public static void addPools(RegistryAccess access) {
        HolderGetter<StructureTemplatePool> pools = access.lookup(Registries.TEMPLATE_POOL).orElseThrow();
        HolderGetter<StructureProcessorList> processors = access.lookup(Registries.PROCESSOR_LIST).orElseThrow();
        Holder<StructureProcessorList> mossify10 = processors.getOrThrow(ProcessorLists.MOSSIFY_10_PERCENT);
        Holder<StructureProcessorList> zombiePlains = processors.getOrThrow(ProcessorLists.ZOMBIE_PLAINS);
        Holder<StructureProcessorList> zombieSnowy = processors.getOrThrow(ProcessorLists.ZOMBIE_SNOWY);
        Holder<StructureProcessorList> zombieSavanna = processors.getOrThrow(ProcessorLists.ZOMBIE_SAVANNA);
        Holder<StructureProcessorList> zombieDesert = processors.getOrThrow(ProcessorLists.ZOMBIE_DESERT);
        Holder<StructureProcessorList> zombieTaiga = processors.getOrThrow(ProcessorLists.ZOMBIE_TAIGA);

        addTemplates(
                pools, "village/plains/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/plains/houses/plains_apiary_1"), mossify10), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/plains/houses/plains_pyrotechnician_house_1"), mossify10), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/plains/houses/plains_paper_mill_1"), mossify10), 2)
        );
        addTemplates(
                pools, "village/plains/zombie/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/plains/houses/plains_apiary_1"), zombiePlains), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/plains/houses/plains_pyrotechnician_house_1"), zombiePlains), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/plains/houses/plains_paper_mill_1"), zombiePlains), 2)
        );
        addTemplates(
                pools, "village/snowy/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/snowy/houses/snowy_apiary_1")), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/snowy/houses/snowy_pyrotechnician_house_1")), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/snowy/houses/snowy_paper_mill_1")), 2)
        );
        addTemplates(
                pools, "village/snowy/zombie/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/snowy/houses/snowy_apiary_1"), zombieSnowy), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/snowy/houses/snowy_pyrotechnician_house_1"), zombieSnowy), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/snowy/houses/snowy_paper_mill_1"), zombieSnowy), 2)
        );
        addTemplates(
                pools, "village/savanna/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/savanna/houses/savanna_apiary_1")), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/savanna/houses/savanna_pyrotechnician_house_1")), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/savanna/houses/savanna_paper_mill_1")), 2)
        );
        addTemplates(
                pools, "village/savanna/zombie/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/savanna/houses/savanna_apiary_1"), zombieSavanna), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/savanna/houses/savanna_pyrotechnician_house_1"), zombieSavanna), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/savanna/houses/savanna_paper_mill_1"), zombieSavanna), 2)
        );
        addTemplates(
                pools, "village/desert/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/desert/houses/desert_apiary_1")), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/desert/houses/desert_pyrotechnician_house_1")), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/desert/houses/desert_paper_mill_1")), 2)
        );
        addTemplates(
                pools, "village/desert/zombie/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/desert/houses/desert_apiary_1"), zombieDesert), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/desert/houses/desert_pyrotechnician_house_1"), zombieDesert), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/desert/houses/desert_paper_mill_1"), zombieDesert), 2)
        );
        addTemplates(
                pools, "village/taiga/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/taiga/houses/taiga_apiary_1"), mossify10), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/taiga/houses/taiga_pyrotechnician_house_1"), mossify10), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/taiga/houses/taiga_paper_mill_1"), mossify10), 2)
        );
        addTemplates(
                pools, "village/taiga/zombie/houses", StructureTemplatePool.Projection.RIGID,
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/taiga/houses/taiga_apiary_1"), zombieTaiga), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/taiga/houses/taiga_pyrotechnician_house_1"),  zombieTaiga), 2),
                Pair.of(StructurePoolElement.legacy(MoreColorful.key("village/taiga/houses/taiga_paper_mill_1"),  zombieTaiga), 2)
        );
    }

    @SafeVarargs
    private static void addTemplates(
            HolderGetter<StructureTemplatePool> pools,
            String key,
            @SuppressWarnings("SameParameterValue") StructureTemplatePool.Projection projection,
            Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>... templates) {
        StructureTemplatePool pool = pools.getOrThrow(Pools.createKey(key)).value();
        List<Pair<StructurePoolElement, Integer>> list = new ArrayList<>(pool.rawTemplates);
        for (Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer> pair : templates) {
            StructurePoolElement element = pair.getFirst().apply(projection);
            int weight = pair.getSecond();
            list.add(Pair.of(element, weight));
            for (int i = 0; i < weight; i++) {
                pool.templates.add(element);
            }
        }
        pool.rawTemplates = list;
    }
}
