package com.ChalkerCharles.morecolorful.common.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public final class BoatTypeExtension {
    public static final Boat.Type CRABAPPLE = Proxy.CRABAPPLE.getValue();
    public static final Boat.Type EBONY = Proxy.EBONY.getValue();
    public static final Boat.Type GINKGO = Proxy.GINKGO.getValue();
    public static final Boat.Type MAPLE = Proxy.MAPLE.getValue();
    public static final Boat.Type FROST = Proxy.FROST.getValue();
    public static final Boat.Type DAWN_REDWOOD = Proxy.DAWN_REDWOOD.getValue();
    public static final Boat.Type JACARANDA = Proxy.JACARANDA.getValue();
    public static final Boat.Type WILLOW = Proxy.WILLOW.getValue();

    public static class Proxy {
        public static final EnumProxy<Boat.Type> CRABAPPLE = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.CRABAPPLE_PLANKS,
                "morecolorful:crabapple",
                ModItems.CRABAPPLE_BOAT,
                ModItems.CRABAPPLE_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> EBONY = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.EBONY_PLANKS,
                "morecolorful:ebony",
                ModItems.EBONY_BOAT,
                ModItems.EBONY_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> GINKGO = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.GINKGO_PLANKS,
                "morecolorful:ginkgo",
                ModItems.GINKGO_BOAT,
                ModItems.GINKGO_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> MAPLE = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.MAPLE_PLANKS,
                "morecolorful:maple",
                ModItems.MAPLE_BOAT,
                ModItems.MAPLE_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> FROST = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.FROST_PLANKS,
                "morecolorful:frost",
                ModItems.FROST_BOAT,
                ModItems.FROST_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> DAWN_REDWOOD = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.DAWN_REDWOOD_PLANKS,
                "morecolorful:dawn_redwood",
                ModItems.DAWN_REDWOOD_BOAT,
                ModItems.DAWN_REDWOOD_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> JACARANDA = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.JACARANDA_PLANKS,
                "morecolorful:jacaranda",
                ModItems.JACARANDA_BOAT,
                ModItems.JACARANDA_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> WILLOW = new EnumProxy<>(
                Boat.Type.class,
                ModBlocks.WILLOW_PLANKS,
                "morecolorful:willow",
                ModItems.WILLOW_BOAT,
                ModItems.WILLOW_CHEST_BOAT,
                Items.STICK,
                false);
    }
}
