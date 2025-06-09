package com.ChalkerCharles.morecolorful.common.worldgen.features.trees.treedecorators;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.nature.WillowBranchesBlock;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeDecorators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class WillowBranchesDecorator extends TreeDecorator {
    public static final MapCodec<WillowBranchesDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(d -> d.probability),
                            IntProvider.codec(1, 8).fieldOf("length").forGetter(d -> d.length)
                    )
                    .apply(instance, WillowBranchesDecorator::new)
    );
    private final float probability;
    private final IntProvider length;
    private static final BlockState BRANCHES_TIP = ModBlocks.WILLOW_BRANCHES.get().defaultBlockState();
    private static final BlockState BRANCHES = BRANCHES_TIP.setValue(WillowBranchesBlock.TIP, false);
    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.WILLOW_BRANCHES_DECORATOR.get();
    }

    public WillowBranchesDecorator(float probability, IntProvider length) {
        this.probability = probability;
        this.length = length;
    }

    @Override
    public void place(Context pContext) {
        RandomSource random = pContext.random();
        pContext.leaves().forEach(pos -> {
            if (random.nextFloat() < this.probability) {
                BlockPos blockPos = pos.below();
                if (pContext.isAir(blockPos)) {
                    int length = this.length.sample(random);
                    addBranches(blockPos, length, pContext);
                }
            }
        });
    }

    private static void addBranches(BlockPos pos, int length, Context pContext) {
        for (BlockPos blockpos = pos; pContext.isAir(blockpos) && length > 0; length--) {
            BlockState state = length == 1 ? BRANCHES_TIP : BRANCHES;
            pContext.setBlock(blockpos, state);
            blockpos = blockpos.below();
        }
    }
}
