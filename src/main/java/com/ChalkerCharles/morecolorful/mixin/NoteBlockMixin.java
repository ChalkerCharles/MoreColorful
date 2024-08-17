package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension;
import com.ChalkerCharles.morecolorful.common.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin extends Block {

    public NoteBlockMixin(Properties properties) {
        super(properties);
    }
    @Shadow
    public static final EnumProperty<NoteBlockInstrument> INSTRUMENT = BlockStateProperties.NOTEBLOCK_INSTRUMENT;

    @Shadow
    abstract BlockState setInstrument(LevelAccessor pLevel, BlockPos pPos, BlockState pState);

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
    public BlockState moreColorful$getStateForPlacement(BlockPlaceContext pContext, CallbackInfoReturnable<BlockState> cir) {
        BlockPos pos = pContext.getClickedPos().below();
        if (pContext.getLevel().getBlockState(pos).is(ModTags.QUARTZ_BLOCKS)) {
            cir.setReturnValue(this.setInstrument(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState()).setValue(INSTRUMENT, NoteBlockInstrumentExtension.PIANO));
        } else if (pContext.getLevel().getBlockState(pos).is(Blocks.MOSS_BLOCK)) {
            cir.setReturnValue(this.setInstrument(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState()).setValue(INSTRUMENT, NoteBlockInstrumentExtension.VIOLIN));
        } else if (pContext.getLevel().getBlockState(pos).is(BlockTags.WART_BLOCKS)) {
            cir.setReturnValue(this.setInstrument(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState()).setValue(INSTRUMENT, NoteBlockInstrumentExtension.CELLO));
        } else if (pContext.getLevel().getBlockState(pos).is(ModTags.NETHER_FUNGUS_WOODEN_BLOCKS)) {
            cir.setReturnValue(this.setInstrument(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState()).setValue(INSTRUMENT, NoteBlockInstrumentExtension.ELECTRIC_GUITAR));
        } else if (pContext.getLevel().getBlockState(pos).is(ModTags.COPPER_BLOCKS)) {
            cir.setReturnValue(this.setInstrument(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState()).setValue(INSTRUMENT, NoteBlockInstrumentExtension.TRUMPET));
        }
        return this.setInstrument(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState());
    }
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("HEAD"), cancellable = true)
    public BlockState moreColorful$updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos, CallbackInfoReturnable<BlockState> cir) {
        boolean flag = pFacing.getAxis() == Direction.Axis.Y;
        if (flag && pFacing == Direction.DOWN && !(pLevel.getBlockState(pCurrentPos.above()).getBlock() instanceof AbstractSkullBlock)) {
            if (pLevel.getBlockState(pFacingPos).is(ModTags.QUARTZ_BLOCKS)) {
                cir.setReturnValue(this.setInstrument(pLevel, pCurrentPos, pState).setValue(INSTRUMENT, NoteBlockInstrumentExtension.PIANO));
            } else if (pLevel.getBlockState(pFacingPos).is(Blocks.MOSS_BLOCK)) {
                cir.setReturnValue(this.setInstrument(pLevel, pCurrentPos, pState).setValue(INSTRUMENT, NoteBlockInstrumentExtension.VIOLIN));
            } else if (pLevel.getBlockState(pFacingPos).is(BlockTags.WART_BLOCKS)) {
                cir.setReturnValue(this.setInstrument(pLevel, pCurrentPos, pState).setValue(INSTRUMENT, NoteBlockInstrumentExtension.CELLO));
            } else if (pLevel.getBlockState(pFacingPos).is(ModTags.NETHER_FUNGUS_WOODEN_BLOCKS)) {
                cir.setReturnValue(this.setInstrument(pLevel, pCurrentPos, pState).setValue(INSTRUMENT, NoteBlockInstrumentExtension.ELECTRIC_GUITAR));
            } else if (pLevel.getBlockState(pFacingPos).is(ModTags.COPPER_BLOCKS)) {
                cir.setReturnValue(this.setInstrument(pLevel, pCurrentPos, pState).setValue(INSTRUMENT, NoteBlockInstrumentExtension.TRUMPET));
            }
        }
        return flag ? this.setInstrument(pLevel, pCurrentPos, pState) : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
}
