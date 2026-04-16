package com.ChalkerCharles.morecolorful.client.texture;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.common.entity.animal.Butterfly;
import com.ChalkerCharles.morecolorful.common.entity.animal.Moth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MothTextureManager extends TextureAtlasHolder {
    public static final Map<AbstractMoth.Variant, ResourceLocation[]> TEXTURE_MAP = new HashMap<>();
    public static final MothTextureManager INSTANCE = new MothTextureManager();

    public MothTextureManager() {
        super(Minecraft.getInstance().getTextureManager(), Atlases.MOTH_SHEET, Atlases.MOTHS_ATLAS);
    }

    public static TextureAtlasSprite getSprite(AbstractMoth.Variant variant, boolean caterpillar) {
        ResourceLocation location = TEXTURE_MAP.get(variant)[caterpillar ? 1 : 0];
        return INSTANCE.getSprite(location);
    }

    static {
        for (Butterfly.Variant variant : Butterfly.Variant.VALUES) {
            ResourceLocation[] locations = new ResourceLocation[] {
                    MoreColorful.location("entity/butterfly/" + variant.getName()),
                    MoreColorful.location("entity/caterpillar/" + variant.getName())
            };
            TEXTURE_MAP.put(variant, locations);
        }
        for (Moth.Variant variant : Moth.Variant.VALUES) {
            ResourceLocation[] locations = new ResourceLocation[] {
                    MoreColorful.location("entity/moth/" + variant.getName()),
                    MoreColorful.location("entity/caterpillar/" + variant.getName())
            };
            TEXTURE_MAP.put(variant, locations);
        }
    }
}
