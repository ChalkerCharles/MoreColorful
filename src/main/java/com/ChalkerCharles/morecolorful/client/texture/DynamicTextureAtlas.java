package com.ChalkerCharles.morecolorful.client.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class DynamicTextureAtlas extends DynamicTexture {
    private final Map<ResourceLocation, Sprite> textures = new HashMap<>();
    private final Sprite emptySprite = new Sprite(0, 0, 0, 0);
    private final int maxSupportedTextureSize;
    private int currentX;
    private int currentY;
    private int width = 64;
    private int height = 64;

    public DynamicTextureAtlas(ResourceLocation location) {
        super(new NativeImage(64, 64, true));
        Minecraft.getInstance().getTextureManager().register(location, this);
        this.maxSupportedTextureSize = RenderSystem.maxSupportedTextureSize();
    }

    public void addSprite(ResourceLocation location, NativeImage image, boolean closeImage) {
        try {
            NativeImage atlas = this.getPixels();
            if (atlas == null) return;
            int w = image.getWidth(), h = image.getHeight();
            int x1 = this.currentX + w;
            int y1 = this.currentY + h;
            if (isOutsideBounds(atlas, x1 - 1, y1 - 1)) {
                atlas = this.expand(atlas);
            }
            if (isOutsideBounds(atlas, x1 - 1, y1 - 1)) {
                throw new IllegalStateException("Invalid Texture Size");
            }
            image.copyRect(atlas, 0, 0, this.currentX, this.currentY, w, h, false, false);
            this.textures.put(location, new Sprite(this.currentX, this.currentY, x1, y1));
            if (x1 == this.width && y1 == this.height) {
                if (this.width == this.height) {
                    this.currentX = this.width;
                    this.currentY = 0;
                } else {
                    this.currentX = 0;
                    this.currentY = this.height;
                }
            } else if (x1 >= this.width) {
                this.currentX = this.width == this.height ? 0 : this.width >> 1;
                this.currentY += h;
            } else {
                this.currentX += w;
            }
            this.upload();
        } finally {
            if (closeImage) {
                image.close();
            }
        }
    }

    private static boolean isOutsideBounds(NativeImage image, int x, int y) {
        return x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight();
    }

    private NativeImage expand(NativeImage oldImage) {
        if (this.width == this.height) {
            this.width *= 2;
        } else {
            this.height *= 2;
        }
        if (this.width > this.maxSupportedTextureSize || this.height > this.maxSupportedTextureSize) {
            throw new IllegalStateException("Max Texture Size Exceeded");
        }
        NativeImage newImage = new NativeImage(this.width, this.height, true);
        int oldWidth = oldImage.getWidth(), oldHeight = oldImage.getHeight();
        oldImage.copyRect(newImage, 0, 0, 0, 0, oldWidth, oldHeight, false, false);
        this.setPixels(newImage);
        TextureUtil.prepareImage(this.getId(), this.width, this.height);
        return newImage;
    }

    public void clear() {
        this.textures.clear();
        this.currentX = 0;
        this.currentY = 0;
        this.width = 64;
        this.height = 64;
        NativeImage image = new NativeImage(64, 64, true);
        this.setPixels(image);
        TextureUtil.prepareImage(this.getId(), this.width, this.height);
    }

    public Sprite getSprite(ResourceLocation location, Supplier<NativeImage> image) {
        Sprite sprite = this.textures.get(location);
        if (sprite == null) {
            this.addSprite(location, image.get(), true);
            return this.textures.getOrDefault(location, this.emptySprite);
        } else {
            return sprite;
        }
    }

    public class Sprite {
        private final int x0;
        private final int x1;
        private final int y0;
        private final int y1;

        public Sprite(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.x1 = x1;
            this.y0 = y0;
            this.y1 = y1;
        }

        public float u0() {
            return (float) this.x0 / DynamicTextureAtlas.this.width;
        }

        public float v0() {
            return (float) this.y0 / DynamicTextureAtlas.this.height;
        }

        public float u1() {
            return (float) this.x1 / DynamicTextureAtlas.this.width;
        }

        public float v1() {
            return (float) this.y1 / DynamicTextureAtlas.this.height;
        }
    }
}
