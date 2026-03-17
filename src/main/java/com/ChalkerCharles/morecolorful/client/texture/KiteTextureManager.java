package com.ChalkerCharles.morecolorful.client.texture;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KiteTextureManager extends TextureAtlasHolder {
    private static final ResourceLocation[] LOCATIONS;
    private static final ResourceLocation FRAME = MoreColorful.location("colored/kite_entity/frame");
    private static final ResourceLocation ROPE = MoreColorful.location("colored/kite_entity/rope");
    public static final KiteTextureManager INSTANCE = new KiteTextureManager();

    public KiteTextureManager() {
        super(Minecraft.getInstance().getTextureManager(), Atlases.KITE_SHEET, Atlases.KITES_ATLAS);
    }

    public TextureAtlasSprite getSprite(DyeColor color) {
        return this.getSprite(LOCATIONS[color.getId()]);
    }

    public TextureAtlasSprite frameSprite() {
        return this.getSprite(FRAME);
    }

    public TextureAtlasSprite ropeSprite() {
        return this.getSprite(ROPE);
    }

    static {
        DyeColor[] colors = DyeColor.values();
        int size = colors.length;
        ResourceLocation[] locations = new ResourceLocation[size];
        for (DyeColor color : colors) {
            locations[color.getId()] = MoreColorful.location("colored/kite_entity/kite_" + color.getName());
        }
        LOCATIONS = locations;
    }
}
