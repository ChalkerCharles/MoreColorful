package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.ChalkerCharles.morecolorful.common.entity.misc.SandbagEntity;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.HashMap;
import java.util.Map;

public class BalloonItem extends Item {
    public static final Map<Balloon.Variant, Item> BY_VARIANT = new HashMap<>();
    public static final ItemLike[] COMMON = new ItemLike[] {
            ModItems.BALLOON,
            ModItems.WHITE_BALLOON,
            ModItems.LIGHT_GRAY_BALLOON,
            ModItems.GRAY_BALLOON,
            ModItems.BLACK_BALLOON,
            ModItems.BROWN_BALLOON,
            ModItems.RED_BALLOON,
            ModItems.ORANGE_BALLOON,
            ModItems.YELLOW_BALLOON,
            ModItems.LIME_BALLOON,
            ModItems.GREEN_BALLOON,
            ModItems.CYAN_BALLOON,
            ModItems.LIGHT_BLUE_BALLOON,
            ModItems.BLUE_BALLOON,
            ModItems.PURPLE_BALLOON,
            ModItems.MAGENTA_BALLOON,
            ModItems.PINK_BALLOON
    };
    public static final ItemLike[] SPECIAL = new ItemLike[] {};
    public static final ItemLike[] ALL_TYPES = Maths.concatArray(ItemLike[]::new, COMMON, SPECIAL);
    public final Balloon.Variant variant;

    public BalloonItem(Properties pProperties, Balloon.Variant variant) {
        super(pProperties);
        this.variant = variant;
        BY_VARIANT.put(variant, this);
    }

    public static ItemLike byColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_BALLOON;
            case ORANGE -> ModItems.ORANGE_BALLOON;
            case MAGENTA -> ModItems.MAGENTA_BALLOON;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_BALLOON;
            case YELLOW -> ModItems.YELLOW_BALLOON;
            case LIME -> ModItems.LIME_BALLOON;
            case PINK -> ModItems.PINK_BALLOON;
            case GRAY -> ModItems.GRAY_BALLOON;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_BALLOON;
            case CYAN -> ModItems.CYAN_BALLOON;
            case PURPLE -> ModItems.PURPLE_BALLOON;
            case BLUE -> ModItems.BLUE_BALLOON;
            case BROWN -> ModItems.BROWN_BALLOON;
            case GREEN -> ModItems.GREEN_BALLOON;
            case RED -> ModItems.RED_BALLOON;
            case BLACK -> ModItems.BLACK_BALLOON;
        };
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            pLevel.addFreshEntity(new Balloon(pLevel, pPlayer, pPlayer, this.variant));
            pLevel.gameEvent(GameEvent.ENTITY_PLACE, pPlayer.position(), GameEvent.Context.of(pPlayer));
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, pPlayer);
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = pContext.getPlayer();
        if (player != null) {
            boolean success = false;
            if (state.is(BlockTags.FENCES)) {
                success = true;
                if (!level.isClientSide) {
                    LeashFenceKnotEntity leashKnot = LeashFenceKnotEntity.getOrCreateKnot(level, pos);
                    leashKnot.playPlacementSound();
                    level.addFreshEntity(new Balloon(level, player, leashKnot, this.variant));
                }
            } else if (state.is(ModBlocks.SANDBAG)) {
                success = true;
                if (!level.isClientSide) {
                    SandbagEntity sandbag = SandbagEntity.getOrCreate(level, pos, state);
                    sandbag.playSound(ModSounds.LEAD_TIED.get());
                    level.addFreshEntity(new Balloon(level, player, sandbag, this.variant));
                }
            }
            if (success) {
                if (!level.isClientSide) {
                    level.gameEvent(GameEvent.ENTITY_PLACE, pos, GameEvent.Context.of(player));
                    level.gameEvent(GameEvent.BLOCK_ATTACH, pos, GameEvent.Context.of(player));
                }
                ItemStack itemStack = pContext.getItemInHand();
                player.awardStat(Stats.ITEM_USED.get(this));
                itemStack.consume(1, player);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }
}
