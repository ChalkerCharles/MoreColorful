package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.Util;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModAtlasProvider extends SpriteSourceProvider {
    private static final String[] COLORS = new String[] {
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
            "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    };

    public ModAtlasProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    protected void gather() {
        atlas(BLOCKS_ATLAS).addSource(new PalettedPermutations(
                List.of(MoreColorful.location("colored/umbrella_block/canopy")),
                MoreColorful.location("colored/palettes/cloth_block/palette"),
                Util.make(new HashMap<>(), map -> {
                    map.put("default", MoreColorful.location("colored/palettes/cloth_block/default"));
                    for (String color : COLORS) {
                        map.put(color, MoreColorful.location("colored/palettes/cloth_block/" + color));
                    }
                })
        )).addSource(new PalettedPermutations(
                List.of(
                        MoreColorful.location("colored/umbrella_item/open_0"),
                        MoreColorful.location("colored/umbrella_item/open_1"),
                        MoreColorful.location("colored/umbrella_item/open_2"),
                        MoreColorful.location("colored/umbrella_item/closed_0"),
                        MoreColorful.location("colored/umbrella_item/closed_1"),
                        MoreColorful.location("colored/umbrella_item/closed_2")
                ),
                MoreColorful.location("colored/palettes/cloth_item/palette"),
                Util.make(new HashMap<>(), map -> {
                    map.put("default", MoreColorful.location("colored/palettes/cloth_item/default"));
                    for (String color : COLORS) {
                        map.put(color, MoreColorful.location("colored/palettes/cloth_item/" + color));
                    }
                })
        )).addSource(new PalettedPermutations(
                List.of(
                        MoreColorful.location("colored/pinwheel/0"),
                        MoreColorful.location("colored/pinwheel/1"),
                        MoreColorful.location("colored/pinwheel/2"),
                        MoreColorful.location("colored/pinwheel/3"),
                        MoreColorful.location("colored/pinwheel/4"),
                        MoreColorful.location("colored/pinwheel/5"),
                        MoreColorful.location("colored/pinwheel/6"),
                        MoreColorful.location("colored/pinwheel/7"),
                        MoreColorful.location("colored/pinwheel/8"),
                        MoreColorful.location("colored/pinwheel/9"),
                        MoreColorful.location("colored/pinwheel/10"),
                        MoreColorful.location("colored/pinwheel/11"),
                        MoreColorful.location("colored/pinwheel/12"),
                        MoreColorful.location("colored/pinwheel/13"),
                        MoreColorful.location("colored/pinwheel/14"),
                        MoreColorful.location("colored/pinwheel/15")
                ),
                MoreColorful.location("colored/palettes/paper/palette"),
                Util.make(new HashMap<>(), map -> {
                    for (String color : COLORS) {
                        map.put(color, MoreColorful.location("colored/palettes/paper/" + color));
                    }
                })
        ));
        atlas(PARTICLES_ATLAS).addSource(new PalettedPermutations(
                List.of(
                        MoreColorful.location("colored/balloon/balloon_0"),
                        MoreColorful.location("colored/balloon/balloon_1"),
                        MoreColorful.location("colored/balloon/balloon_2"),
                        MoreColorful.location("colored/balloon/balloon_3")
                ),
                MoreColorful.location("colored/palettes/balloon/palette"),
                Util.make(new HashMap<>(), map -> {
                    map.put("default", MoreColorful.location("colored/palettes/balloon/default"));
                    for (String color : COLORS) {
                        map.put(color, MoreColorful.location("colored/palettes/balloon/" + color));
                    }
                })
        ));
    }
}
