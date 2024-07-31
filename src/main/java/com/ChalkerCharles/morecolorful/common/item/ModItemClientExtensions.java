package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModItemClientExtensions {

    @SubscribeEvent
    public static void setupUseAnim(RegisterClientExtensionsEvent event) {
        // Flute
        event.registerItem(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose FLUTE = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_FLUTE");

            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return FLUTE;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.FLUTE.get());
        // Guitar-Like
        event.registerItem(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose GUITAR_HOLD = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_GUITAR_HOLD");
            private static final HumanoidModel.ArmPose GUITAR_PLAYING = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_GUITAR_PLAYING");

            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && PlayingScreen.isPressing) {
                    return GUITAR_PLAYING;
                }
                return GUITAR_HOLD;
            }
        }, ModItems.GUITAR.get(), ModItems.BASS.get(), ModItems.BANJO.get(), ModItems.ELECTRIC_GUITAR.get());
        // Cow Bell
        event.registerItem(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose COW_BELL = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_COW_BELL");

            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return COW_BELL;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.COW_BELL.get());
        // Didgeridoo
        event.registerItem(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose DIDGERIDOO = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_DIDGERIDOO");

            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return DIDGERIDOO;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.DIDGERIDOO.get());
        // Violin
        event.registerItem(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose VIOLIN_PLAYING = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_VIOLIN_PLAYING");
            private static final HumanoidModel.ArmPose VIOLIN_HOLD = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_VIOLIN_HOLD");

            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && PlayingScreen.isPressing) {
                    return VIOLIN_PLAYING;
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return VIOLIN_HOLD;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.VIOLIN.get());
        // Fiddle Bow
        event.registerItem(new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(@NotNull PoseStack poseStack, @NotNull LocalPlayer player, @NotNull HumanoidArm arm, @NotNull ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                float f = player.getTicksUsingItem();
                float f1 = f % 20 >= 10 ? -(f % 20) + 10 : (f % 20) - 10;
                if (PlayingScreen.isPressing) {
                    if (player.getUseItem().getItem() == ModItems.VIOLIN.get()) {
                        poseStack.mulPose(Axis.YP.rotationDegrees(f1 * 2));
                        if (arm == HumanoidArm.RIGHT) {
                            poseStack.mulPose(Axis.YP.rotationDegrees(15.0F));
                            poseStack.translate(-0.4, 0.12, 0.02);
                        } else {
                            poseStack.translate(0.32, 0.12, 0.02);
                        }
                    } else if (player.getUseItem().getItem() == ModItems.CELLO.get()) {
                        poseStack.mulPose(Axis.YP.rotationDegrees(f1 * 2));
                        poseStack.mulPose(Axis.XP.rotationDegrees(f1 / 2));
                        if (arm == HumanoidArm.RIGHT) {
                            poseStack.mulPose(Axis.YP.rotationDegrees(15.0F));
                            poseStack.translate(-0.4, 0.0, -0.1);
                        } else {
                            poseStack.translate(0.32, 0.0, -0.1);
                        }
                    }
                }
                return false;
            }
        }, ModItems.FIDDLE_BOW.get());
        // Cello
        event.registerItem(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose CELLO = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_CELLO");
            private static final HumanoidModel.ArmPose CELLO_PLAYING = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_CELLO_PLAYING");
            private static final HumanoidModel.ArmPose CELLO_HOLD = HumanoidModel.ArmPose.valueOf("MORECOLORFUL_CELLO_HOLD");

            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && PlayingScreen.isPressing) {
                    return CELLO_PLAYING;
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return CELLO_HOLD;
                }
                return CELLO;
            }
        }, ModItems.CELLO.get());
        // Trumpet
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return HumanoidModel.ArmPose.TOOT_HORN;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.TRUMPET.get());
    }

}
