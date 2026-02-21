package com.ChalkerCharles.morecolorful.common.datagen.tag;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagProvider extends DamageTypeTagsProvider {
    public ModDamageTypeTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.DamageTypes.CAN_POKE_BALLOON).add(
                DamageTypes.ARROW,
                DamageTypes.CACTUS,
                DamageTypes.CRAMMING,
                DamageTypes.FALLING_STALACTITE,
                DamageTypes.FIREBALL,
                DamageTypes.LAVA,
                DamageTypes.ON_FIRE,
                DamageTypes.STALAGMITE,
                DamageTypes.STING,
                DamageTypes.SWEET_BERRY_BUSH,
                DamageTypes.THORNS,
                DamageTypes.TRIDENT,
                DamageTypes.UNATTRIBUTED_FIREBALL
        );
    }
}
