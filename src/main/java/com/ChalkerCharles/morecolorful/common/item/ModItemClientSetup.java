package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.util.EnumExtensions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModItemClientSetup {

    @SubscribeEvent
    public static void setupUseAnim(RegisterClientExtensionsEvent event) {
        // Flute
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return EnumExtensions.ArmPose.FLUTE.getValue();
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.FLUTE.get());
        // Guitar-Like
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
                        && livingEntity.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)) {
                    return EnumExtensions.ArmPose.GUITAR_PLAYING.getValue();
                }
                return EnumExtensions.ArmPose.GUITAR_HOLD.getValue();
            }
        }, ModItems.GUITAR.get(), ModItems.BASS.get(), ModItems.BANJO.get(), ModItems.ELECTRIC_GUITAR.get());
        // Cow Bell
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return EnumExtensions.ArmPose.COW_BELL.getValue();
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.COW_BELL.get());
        // Didgeridoo
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return EnumExtensions.ArmPose.DIDGERIDOO.getValue();
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.DIDGERIDOO.get());
        // Violin
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
                        && livingEntity.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)) {
                    return EnumExtensions.ArmPose.VIOLIN_PLAYING.getValue();
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return EnumExtensions.ArmPose.VIOLIN_HOLD.getValue();
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
                if (Minecraft.getInstance().screen instanceof PlayingScreen pScreen && pScreen.isPressing) {
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
                    } else if (player.getUseItem().getItem() == ModItems.ERHU.get()) {
                        poseStack.mulPose(Axis.YP.rotationDegrees(f1 * 2));
                        if (arm == HumanoidArm.RIGHT) {
                            poseStack.mulPose(Axis.YP.rotationDegrees(15.0F));
                            poseStack.translate(-0.4, 0.0, -0.07);
                        } else {
                            poseStack.translate(0.32, 0.0, -0.07);
                        }
                    }
                }
                return false;
            }
        }, ModItems.FIDDLE_BOW.get());
        // Cello
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
                        && livingEntity.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)) {
                    return EnumExtensions.ArmPose.CELLO_PLAYING.getValue();
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return EnumExtensions.ArmPose.CELLO_HOLD.getValue();
                }
                return EnumExtensions.ArmPose.CELLO.getValue();
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
        // Saxophone, Ocarina & Harmonica
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return EnumExtensions.ArmPose.SAXOPHONE.getValue();
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.SAXOPHONE.get(), ModItems.OCARINA.get(), ModItems.HARMONICA.get());
        // Pipa
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
                        && livingEntity.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)) {
                    return EnumExtensions.ArmPose.PIPA_PLAYING.getValue();
                }
                return EnumExtensions.ArmPose.PIPA_HOLD.getValue();
            }
        }, ModItems.PIPA.get());
        // Erhu
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity livingEntity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
                        && livingEntity.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)) {
                    return EnumExtensions.ArmPose.ERHU_PLAYING.getValue();
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return EnumExtensions.ArmPose.ERHU_HOLD.getValue();
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.ERHU.get());
    }

    @SubscribeEvent
    public static void registerModelPredicate(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.FLUTE.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.COW_BELL.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.DIDGERIDOO.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.VIOLIN.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_violin"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem().getItem() == ModItems.VIOLIN.get() ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_cello"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem().getItem() == ModItems.CELLO.get() ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_erhu"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem().getItem() == ModItems.ERHU.get() ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.TRUMPET.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.SAXOPHONE.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.OCARINA.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.HARMONICA.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.ERHU.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
        });
    }

}
