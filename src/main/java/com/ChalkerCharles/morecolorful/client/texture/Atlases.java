package com.ChalkerCharles.morecolorful.client.texture;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Atlases {
    public static final ResourceLocation BLOCK_SHEET = InventoryMenu.BLOCK_ATLAS;
    public static final ResourceLocation KITES_ATLAS = MoreColorful.location("kites");
    public static final ResourceLocation KITE_SHEET = MoreColorful.location("textures/atlas/kites.png");
    public static final ResourceLocation BALLOONS_ATLAS = MoreColorful.location("balloons");
    public static final ResourceLocation BALLOON_SHEET = MoreColorful.location("textures/atlas/balloons.png");
    public static final ResourceLocation PAPERCUTTING_SHEET = MoreColorful.location("textures/atlas/papercutting.png");
    public static final ResourceLocation MOTHS_ATLAS = MoreColorful.location("moths");
    public static final ResourceLocation MOTH_SHEET = MoreColorful.location("textures/atlas/moths.png");
}
