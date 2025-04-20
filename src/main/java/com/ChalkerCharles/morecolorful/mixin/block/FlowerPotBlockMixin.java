package com.ChalkerCharles.morecolorful.mixin.block;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin extends Block {
    @Shadow
    @Final
    private Supplier<? extends Block> flowerDelegate;

    private FlowerPotBlockMixin(Properties properties) {
        super(properties);
    }

    @Unique
    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.isRandomlyTicking(pState) && pLevel.dimensionType().natural()) {
            boolean flag = this.flowerDelegate == ModBlocks.OPEN_DAYBLOOM;
            boolean flag1 = pLevel.isNight();
            if (flag == flag1) {
                pLevel.setBlock(pPos, this.moreColorful$opposite(pState), 3);
            }
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    @Unique
    private BlockState moreColorful$opposite(BlockState state) {
        if (state.is(ModBlocks.POTTED_OPEN_DAYBLOOM)) {
            return ModBlocks.POTTED_CLOSED_DAYBLOOM.get().defaultBlockState();
        } else {
            return state.is(ModBlocks.POTTED_CLOSED_DAYBLOOM) ? ModBlocks.POTTED_OPEN_DAYBLOOM.get().defaultBlockState() : state;
        }
    }
}
