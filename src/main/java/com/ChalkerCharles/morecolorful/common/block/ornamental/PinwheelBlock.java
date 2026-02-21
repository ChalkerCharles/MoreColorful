package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.entity.PinwheelBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.natural.WindFlowerBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelColor;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelContext;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class PinwheelBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PinwheelBlock> CODEC = simpleCodec(PinwheelBlock::new);
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

    public PinwheelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ROTATION, 0)
                .setValue(WATERLOGGED, false)
        );
    }

    public static void applyWind(Entity entity) {
        Level level = entity.level();
        if (level.isClientSide && level.tickRateManager().runsNormally() && entity instanceof LivingEntity living) {
            ItemStack main = living.getMainHandItem();
            ItemStack off = living.getOffhandItem();
            ItemStack head = living.getItemBySlot(EquipmentSlot.HEAD);
            if (hasPinwheel(main, off, head)) {
                Vec3 pos = living.position();
                Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos.x, pos.y + 0.5, pos.z);
                int windLevel = 0;
                if (wind != null) {
                    Vector3f facing = entity.getLookAngle().toVector3f();
                    windLevel = WindFlowerBlock.getWindLevel(-wind.dot(facing));
                }
                updateContext(main, windLevel);
                updateContext(off, windLevel);
                updateContext(head, windLevel);
            }
        }
    }

    private static boolean hasPinwheel(ItemStack main, ItemStack off, ItemStack head) {
        return main.is(ModItems.PINWHEEL) || off.is(ModItems.PINWHEEL) || head.is(ModItems.PINWHEEL);
    }

    private static void updateContext(ItemStack stack, int windLevel) {
        if (stack.is(ModItems.PINWHEEL)) {
            stack.update(ModDataComponents.PINWHEEL_CONTEXT, PinwheelContext.DEFAULT, windLevel, PinwheelContext::update);
        }
    }

    public static void merge(ItemStack carried, ItemStack stackedOn) {
        if (ItemStack.isSameItem(carried, stackedOn)) {
            PinwheelContext context = stackedOn.getOrDefault(ModDataComponents.PINWHEEL_CONTEXT, PinwheelContext.DEFAULT);
            carried.set(ModDataComponents.PINWHEEL_CONTEXT, context);
        }
    }

    @Override
    protected MapCodec<PinwheelBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState()
                .setValue(ROTATION, RotationSegment.convertToSegment(pContext.getRotation() + 180.0F))
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isFaceSturdy(pLevel, pPos, Direction.UP, SupportType.CENTER);
    }

    @Override
    protected BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(ROTATION, pRot.rotate(pState.getValue(ROTATION), 16));
    }

    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.setValue(ROTATION, pMirror.mirror(pState.getValue(ROTATION), 16));
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ROTATION, WATERLOGGED);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        PinwheelColor color = stack.getOrDefault(ModDataComponents.PINWHEEL_COLOR, PinwheelColor.DEFAULT);
        if (!color.equals(PinwheelColor.DEFAULT)) {
            List<DyeColor> list = color.colors();
            components.add(getColorName(list.getFirst())
                    .append(", ").append(getColorName(list.get(1)))
                    .append(", ").append(getColorName(list.get(2)))
                    .append(", ").append(getColorName(list.get(3)))
                    .withStyle(ChatFormatting.GRAY));
        }
    }

    private static MutableComponent getColorName(DyeColor color) {
        return Component.translatable("info.morecolorful.dye." + color.getName());
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return pLevel.getBlockEntity(pPos) instanceof PinwheelBlockEntity blockEntity
                ? blockEntity.getItem()
                : super.getCloneItemStack(pLevel, pPos, pState);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PinwheelBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PINWHEEL.get(), PinwheelBlockEntity::tick);
    }
}
