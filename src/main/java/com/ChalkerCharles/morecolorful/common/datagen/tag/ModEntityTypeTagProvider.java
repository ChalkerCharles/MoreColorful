package com.ChalkerCharles.morecolorful.common.datagen.tag;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(EntityTypeTags.ARTHROPOD).add(
                ModEntities.BUTTERFLY.get(),
                ModEntities.MOTH.get()
        );

        tag(ModTags.EntityTypes.CAN_SPAWN_WITH_UMBRELLA).add(
                EntityType.ZOMBIE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.HUSK,
                EntityType.SKELETON,
                EntityType.STRAY,
                EntityType.BOGGED
        );
        tag(ModTags.EntityTypes.CAN_SPAWN_WITH_BALLOON).add(
                EntityType.ZOMBIE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.HUSK,
                EntityType.SKELETON,
                EntityType.STRAY,
                EntityType.BOGGED,
                EntityType.CREEPER
        );
    }
}
