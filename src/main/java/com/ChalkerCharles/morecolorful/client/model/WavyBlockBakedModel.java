package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.client.shader.ModRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WavyBlockBakedModel extends BakedModelWrapper<BakedModel> {
    public WavyBlockBakedModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        return super.getQuads(state, side, rand, extraData, null);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        Block block = state.getBlock();
        if (block instanceof LeavesBlock) {
            return ChunkRenderTypeSet.of(Minecraft.useFancyGraphics() ? ModRenderTypes.WAVY_CUTOUT_MIPPED : RenderType.solid());
        } else if (block instanceof ChainBlock) {
            return ChunkRenderTypeSet.of(ModRenderTypes.WAVY_CUTOUT_MIPPED);
        } else {
            return ChunkRenderTypeSet.of(ModRenderTypes.WAVY_CUTOUT);
        }
    }
}
