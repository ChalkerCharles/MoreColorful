package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

public class PartyPopperItem extends Item {
    public static final ItemLike[] ALL_COLORS = new ItemLike[] {
            ModItems.WHITE_PARTY_POPPER,
            ModItems.LIGHT_GRAY_PARTY_POPPER,
            ModItems.GRAY_PARTY_POPPER,
            ModItems.BLACK_PARTY_POPPER,
            ModItems.BROWN_PARTY_POPPER,
            ModItems.RED_PARTY_POPPER,
            ModItems.ORANGE_PARTY_POPPER,
            ModItems.YELLOW_PARTY_POPPER,
            ModItems.LIME_PARTY_POPPER,
            ModItems.GREEN_PARTY_POPPER,
            ModItems.CYAN_PARTY_POPPER,
            ModItems.LIGHT_BLUE_PARTY_POPPER,
            ModItems.BLUE_PARTY_POPPER,
            ModItems.PURPLE_PARTY_POPPER,
            ModItems.MAGENTA_PARTY_POPPER,
            ModItems.PINK_PARTY_POPPER
    };
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource pBlockSource, ItemStack pItem) {
            Direction direction = pBlockSource.state().getValue(DispenserBlock.FACING);
            Position position = DispenserBlock.getDispensePosition(pBlockSource);
            float xRot = switch (direction) {
                case UP -> -90.0F;
                case DOWN -> 90.0F;
                default -> 0.0F;
            };
            float yRot = direction.toYRot();
            ServerLevel level = pBlockSource.level();
            level.playSound(
                    null,
                    position.x(), position.y(), position.z(),
                    ModSounds.PARTY_POPPER_POP.get(),
                    SoundSource.NEUTRAL,
                    0.8F,
                    level.random.nextFloat() * 0.2F + 0.9F
            );
            createConfettiParticles(level, position.x(), position.y(), position.z(), xRot, yRot);
            pItem.shrink(1);
            level.gameEvent(null, GameEvent.EXPLODE, pBlockSource.pos());
            return pItem;
        }
    };

    public PartyPopperItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        Vec3 pos = pPlayer.position();
        pLevel.playSound(
                null,
                pos.x, pos.y, pos.z,
                ModSounds.PARTY_POPPER_POP.get(),
                SoundSource.NEUTRAL,
                0.8F,
                pLevel.random.nextFloat() * 0.2F + 0.9F
        );
        if (!pLevel.isClientSide) {
            createConfettiParticles((ServerLevel) pLevel, pos.x, pPlayer.getEyeY() - 0.1, pos.z, pPlayer.getXRot(), pPlayer.getYRot());
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        item.consume(1, pPlayer);
        pPlayer.gameEvent(GameEvent.EXPLODE);
        return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
    }

    public static ItemLike byColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_PARTY_POPPER;
            case ORANGE -> ModItems.ORANGE_PARTY_POPPER;
            case MAGENTA -> ModItems.MAGENTA_PARTY_POPPER;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_PARTY_POPPER;
            case YELLOW -> ModItems.YELLOW_PARTY_POPPER;
            case LIME -> ModItems.LIME_PARTY_POPPER;
            case PINK -> ModItems.PINK_PARTY_POPPER;
            case GRAY -> ModItems.GRAY_PARTY_POPPER;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_PARTY_POPPER;
            case CYAN -> ModItems.CYAN_PARTY_POPPER;
            case PURPLE -> ModItems.PURPLE_PARTY_POPPER;
            case BLUE -> ModItems.BLUE_PARTY_POPPER;
            case BROWN -> ModItems.BROWN_PARTY_POPPER;
            case GREEN -> ModItems.GREEN_PARTY_POPPER;
            case RED -> ModItems.RED_PARTY_POPPER;
            case BLACK -> ModItems.BLACK_PARTY_POPPER;
        };
    }

    private static void createConfettiParticles(ServerLevel level, double x, double y, double z, float xRot, float yRot) {
        float xd = -Mth.sin(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
        float yd = -Mth.sin(xRot * Mth.DEG_TO_RAD);
        float zd = Mth.cos(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
        RandomSource random = level.random;
        int count = random.nextInt(80, 120);
        for (int i = 0; i < count; i++) {
            double d0 = x + random.nextDouble() * xd;
            double d1 = y + random.nextDouble() * yd;
            double d2 = z + random.nextDouble() * zd;
            Vector3d vec = new Vector3d(
                    random.nextGaussian() * 0.4,
                    random.nextGaussian() * 0.4,
                    random.nextGaussian() * 0.4
            ).cross(xd, yd, zd).add(xd, yd, zd).mul(0.3);
            level.sendParticles(ModParticles.CONFETTI.get(), d0, d1, d2, 0, vec.x, vec.y, vec.z, 1);
        }
    }
}
