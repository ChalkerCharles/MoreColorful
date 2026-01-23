package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.client.model.ArmPoseExtension;
import com.ChalkerCharles.morecolorful.common.attachment.InstrumentData;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelContext;
import com.ChalkerCharles.morecolorful.common.item.misc.PinwheelItem;
import com.ChalkerCharles.morecolorful.common.item.misc.SparklerItem;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientSetup {
    @SubscribeEvent
    public static void setupUseAnim(RegisterClientExtensionsEvent event) {
        // Flute
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return ArmPoseExtension.FLUTE;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.FLUTE.get());
        // Guitar-Like
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                InstrumentData data = InstrumentData.get((Player) livingEntity);
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && data.isPlaying) {
                    return ArmPoseExtension.GUITAR_PLAYING;
                }
                return ArmPoseExtension.GUITAR_HOLD;
            }
        }, ModItems.GUITAR.get(), ModItems.BASS.get(), ModItems.BANJO.get(), ModItems.ELECTRIC_GUITAR.get());
        // Cow Bell
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return ArmPoseExtension.COW_BELL;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.COW_BELL.get());
        // Didgeridoo
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return ArmPoseExtension.DIDGERIDOO;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.DIDGERIDOO.get());
        // Violin
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                InstrumentData data = InstrumentData.get((Player) livingEntity);
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && data.isPlaying) {
                    return ArmPoseExtension.VIOLIN_PLAYING;
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return ArmPoseExtension.VIOLIN_HOLD;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.VIOLIN.get());
        // Fiddle Bow
        event.registerItem(new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                float f = player.getTicksUsingItem();
                float f1 = f % 20 >= 10 ? -(f % 20) + 10 : (f % 20) - 10;
                if (Minecraft.getInstance().screen instanceof PlayingScreen screen && screen.isPressing) {
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
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                InstrumentData data = InstrumentData.get((Player) livingEntity);
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && data.isPlaying) {
                    return ArmPoseExtension.CELLO_PLAYING;
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return ArmPoseExtension.CELLO_HOLD;
                }
                return ArmPoseExtension.CELLO;
            }
        }, ModItems.CELLO.get());
        // Trumpet
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return HumanoidModel.ArmPose.TOOT_HORN;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.TRUMPET.get());
        // Saxophone, Ocarina & Harmonica
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return ArmPoseExtension.SAXOPHONE;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.SAXOPHONE.get(), ModItems.OCARINA.get(), ModItems.HARMONICA.get());
        // Pipa
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                InstrumentData data = InstrumentData.get((Player) livingEntity);
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && data.isPlaying) {
                    return ArmPoseExtension.PIPA_PLAYING;
                }
                return ArmPoseExtension.PIPA_HOLD;
            }
        }, ModItems.PIPA.get());
        // Erhu
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity livingEntity, InteractionHand hand, ItemStack itemStack) {
                InstrumentData data = InstrumentData.get((Player) livingEntity);
                if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && data.isPlaying) {
                    return ArmPoseExtension.ERHU_PLAYING;
                } else if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) {
                    return ArmPoseExtension.ERHU_HOLD;
                }
                return HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.ERHU.get());
    }

    private static final ClampedItemPropertyFunction PROPERTY_PLAYING = (stack, level, entity, seed) ->
            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;

    private static ClampedItemPropertyFunction propertyPlaying(Item item) {
        return (stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem().getItem() == item ? 1.0F : 0.0F;
    }

    private static final ClampedItemPropertyFunction PROPERTY_PINWHEEL = (stack, level, entity, seed) -> {
        if (!Config.windSystem) return 0;
        if (entity instanceof LivingEntity living) {
            if (stack == living.getMainHandItem() || stack == living.getOffhandItem() || stack == living.getItemBySlot(EquipmentSlot.HEAD)) {
                int frame = stack.getOrDefault(ModDataComponents.PINWHEEL_CONTEXT, PinwheelContext.DEFAULT).lerpFrame(RenderUtils.partialTick);
                int frames = PinwheelItem.isMulticolor(stack) ? 16 : 4;
                return (frame % frames) / (float) frames;
            }
        }
        return 0;
    };

    private static final ClampedItemPropertyFunction PROPERTY_ACTIVATED = (stack, level, entity, seed) ->
            stack.has(ModDataComponents.ACTIVATED) ? 1.0F : 0.0F;

    @SubscribeEvent
    public static void registerModelPredicate(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ResourceLocation playing = MoreColorful.location("playing");
            ItemProperties.register(ModItems.FLUTE.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.COW_BELL.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.DIDGERIDOO.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.VIOLIN.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), MoreColorful.location("playing_violin"), propertyPlaying(ModItems.VIOLIN.get()));
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), MoreColorful.location("playing_cello"), propertyPlaying(ModItems.CELLO.get()));
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), MoreColorful.location("playing_erhu"), propertyPlaying(ModItems.ERHU.get()));
            ItemProperties.register(ModItems.TRUMPET.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.SAXOPHONE.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.OCARINA.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.HARMONICA.get(), playing, PROPERTY_PLAYING);
            ItemProperties.register(ModItems.ERHU.get(), playing, PROPERTY_PLAYING);
            ResourceLocation spin = MoreColorful.location("spin");
            for (ItemLike item : PinwheelItem.ALL_DYE_COLORS) {
                ItemProperties.register(item.asItem(), spin, PROPERTY_PINWHEEL);
            }
            ItemProperties.register(ModItems.MULTICOLORED_PINWHEEL.get(), spin, PROPERTY_PINWHEEL);
            ResourceLocation activated = MoreColorful.location("activated");
            for (ItemLike item : SparklerItem.ALL_ITEMS) {
                ItemProperties.register(item.asItem(), activated, PROPERTY_ACTIVATED);
            }
        });
    }
}
