package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.common.ReedBlock;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandlersRegistry {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((pState, pLevel, pPos, pTintIndex) -> {
            if (pTintIndex != 0) {
                return pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : GrassColor.getDefaultColor();
            } else {
                return -1;
            }}, ModBlocks.BEGONIAS.get(),
                ModBlocks.WHITE_PETALS.get(),
                ModBlocks.FROSTY_PETALS.get(), 
                ModBlocks.VIOLETS.get(),
                ModBlocks.BUTTERCUPS.get(),
                ModBlocks.FORGET_ME_NOTS.get(),
                ModBlocks.BABY_BLUE_EYES.get(),
                ModBlocks.SPEEDWELLS.get(),
                ModBlocks.WOOD_SORRELS.get()
        );
        event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null && pPos != null
                        ? BiomeColors.getAverageGrassColor(pLevel, pPos)
                        : GrassColor.getDefaultColor(),
                ModBlocks.DUCKWEEDS.get()
        );
        event.register((pState, pLevel, pPos, pTintIndex) ->
                pLevel != null && pPos != null
                        ? BiomeColors.getAverageGrassColor(
                                pLevel, pState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER ? pPos : pPos.below())
                        : GrassColor.getDefaultColor(),
                ModBlocks.SHORT_WATER_GRASS.get(),
                ModBlocks.TALL_WATER_GRASS.get()
        );
        event.register((pState, pLevel, pPos, pTintIndex) -> {
            if (pTintIndex == 0) {
                return pLevel != null && pPos != null
                        ? BiomeColors.getAverageGrassColor(pLevel, ReedBlock.getBottomPos(pPos, pState))
                        : GrassColor.getDefaultColor();
            } else {
                return pLevel != null && pPos != null ? getReedColor(pLevel, pPos) : 0xe8e8e1;
            }}, ModBlocks.REED.get()
        );
        event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null && pPos != null
                        ? BiomeColors.getAverageFoliageColor(pLevel, pPos)
                        : FoliageColor.getDefaultColor(),
                ModBlocks.WILLOW_LEAVES.get(),
                ModBlocks.WILLOW_BRANCHES.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((pStack, pTintIndex) -> 0x7cbd6b,
                ModItems.SHORT_WATER_GRASS,
                ModItems.TALL_WATER_GRASS
        );
        event.register((pStack, pTintIndex) -> 0x71c35c,
                ModItems.DUCKWEEDS
        );
        event.register((pStack, pTintIndex) -> 0x64b34f,
                ModItems.WILLOW_LEAVES,
                ModItems.WILLOW_BRANCHES
        );
    }

    private static int getReedColor(BlockAndTintGetter pLevel, BlockPos pPos) {
        Color color = new Color(BiomeColors.getAverageGrassColor(pLevel, pPos));
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float hue = hsb[0];
        Color newColor = Color.getHSBColor((hue + 64) / 2, 0.1F, 0.85F);
        return newColor.getRGB();
    }
}
