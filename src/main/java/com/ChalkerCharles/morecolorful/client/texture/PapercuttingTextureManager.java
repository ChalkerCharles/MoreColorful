package com.ChalkerCharles.morecolorful.client.texture;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.item.component.PapercuttingStencil;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PapercuttingTextureManager implements ResourceManagerReloadListener {
    public static final DynamicTextureAtlas ATLAS = new DynamicTextureAtlas(Atlases.PAPERCUTTING_SHEET);
    public static final RenderType RENDER_TYPE = RenderType.entityCutout(Atlases.PAPERCUTTING_SHEET);
    private static Map<Block, Map<PapercuttingStencil, ResourceLocation>> locations = Map.of();
    private static Map<Block, NativeImage> originalImages = Map.of();
    public static final PapercuttingTextureManager INSTANCE = new PapercuttingTextureManager();

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        reset();
    }

    public static void reset() {
        ATLAS.clear();
        ImmutableMap.Builder<Block, Map<PapercuttingStencil, ResourceLocation>> builder = ImmutableMap.builder();
        ImmutableMap.Builder<Block, NativeImage> builder1 = ImmutableMap.builder();
        for (Block block : PapercuttingBlock.ALL_BLOCKS.get()) {
            Map<PapercuttingStencil, ResourceLocation> map = new HashMap<>();
            String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(Atlases.BLOCK_SHEET).apply(MoreColorful.location("block/" + name));
            ResourceLocation location = MoreColorful.location("papercutting/" + name + PapercuttingStencil.DEFAULT.name());
            map.put(PapercuttingStencil.DEFAULT, location);
            NativeImage image = sprite.contents().getOriginalImage();
            ATLAS.addSprite(location, image, false);
            builder.put(block, map);
            builder1.put(block, image);
        }
        locations = builder.build();
        originalImages = builder1.build();
    }

    public static NativeImage carve(NativeImage image, PapercuttingStencil stencil) {
        int h = image.getHeight(), w = image.getWidth();
        NativeImage newImage = new NativeImage(w, h, true);
        newImage.copyFrom(image);
        int xScale = w >> 4, yScale = h >> 4;
        long[] longs = stencil.stencil();
        for (int i = 0; i < 4; i++) {
            long l = longs[i];
            for (int j = 0; j < 4; j++) {
                int y = (i << 2) + j;
                for (int k = 0; k < 16; k++) {
                    int idx = (j << 4) + k;
                    if ((l & (1L << idx)) != 0) {
                        newImage.fillRect(k * xScale, y * yScale, xScale, yScale, 0);
                    }
                }
            }
        }
        return newImage;
    }

    public static DynamicTextureAtlas.Sprite get(Block block, PapercuttingStencil stencil) {
        Map<PapercuttingStencil, ResourceLocation> map = locations.get(block);
        ResourceLocation location = map.computeIfAbsent(stencil, s -> {
            String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
            return MoreColorful.location("papercutting/" + name + s.name());
        });
        return ATLAS.getSprite(location, () -> carve(originalImages.get(block), stencil));
    }

    public static void close() {
        ATLAS.close();
    }
}
