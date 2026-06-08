package com.ChalkerCharles.morecolorful.common.item.utility;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.entity.animal.Pigeon;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.MailContent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.List;

public class MailItem extends Item {
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource pBlockSource, ItemStack pItem) {
            Direction direction = pBlockSource.state().getValue(DispenserBlock.FACING);
            Position position = DispenserBlock.getDispensePosition(pBlockSource);
            ItemStack itemstack = pItem.split(1);
            MailContent content = itemstack.get(ModDataComponents.MAIL_CONTENT);
            if (content != null && dispenserPermission(content)) {
                for (ItemStack item : content.getItems()) {
                    spawnItem(pBlockSource.level(), item, 6, direction, position);
                }
            } else {
                spawnItem(pBlockSource.level(), itemstack, 6, direction, position);
            }
            return pItem;
        }
    };

    public MailItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        MailContent content = itemstack.get(ModDataComponents.MAIL_CONTENT);
        if (content != null) {
            if (permissionCheck(content, pPlayer)) {
                pPlayer.displayClientMessage(Component.translatable("info.morecolorful.mail.permission"), true);
                return InteractionResultHolder.fail(itemstack);
            }
            itemstack.consume(1, pPlayer);
            if (!pLevel.isClientSide) {
                for (ItemStack item : content.getItems()) {
                    pPlayer.getInventory().placeItemBackInInventory(item);
                }
            }
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.MAIL_UNSEAL.get(), SoundSource.PLAYERS);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof Pigeon pigeon && pigeon.isAlive()) {
            return InteractionResult.sidedSuccess(pPlayer.level().isClientSide);
        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }

    private static boolean permissionCheck(MailContent content, Player player) {
        String name = player.getScoreboardName();
        String sender = content.sender();
        String recipient = content.recipient();
        if (StringUtil.isBlank(sender) || StringUtil.isBlank(recipient)) {
            return false;
        }
        return !name.equals(sender) && !name.equals(recipient);
    }

    private static boolean dispenserPermission(MailContent content) {
        return StringUtil.isBlank(content.sender()) || StringUtil.isBlank(content.recipient());
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        MailContent content = itemEntity.getItem().get(ModDataComponents.MAIL_CONTENT);
        if (content != null) {
            itemEntity.getItem().set(ModDataComponents.MAIL_CONTENT, MailContent.DEFAULT);
            ItemUtils.onContainerDestroyed(itemEntity, content.getItems());
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        MailContent content = pStack.getOrDefault(ModDataComponents.MAIL_CONTENT, MailContent.DEFAULT);
        String sender = content.sender();
        if (!StringUtil.isBlank(sender)) {
            pTooltipComponents.add(Component.translatable("info.morecolorful.mail.sender", sender).withStyle(ChatFormatting.GRAY));
        }
        String recipient = content.recipient();
        if (!StringUtil.isBlank(recipient)) {
            pTooltipComponents.add(Component.translatable("info.morecolorful.mail.recipient", recipient).withStyle(ChatFormatting.GRAY));
        }
    }
}
