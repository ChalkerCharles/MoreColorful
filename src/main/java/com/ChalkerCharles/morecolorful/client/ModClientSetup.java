package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.gui.EnvelopeScreen;
import com.ChalkerCharles.morecolorful.client.gui.PapercraftScreen;
import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.client.gui.PyrotechnicsScreen;
import com.ChalkerCharles.morecolorful.client.model.ArmPoseExtension;
import com.ChalkerCharles.morecolorful.client.particle.FireworkShapeFactories;
import com.ChalkerCharles.morecolorful.client.renderer.item.KiteRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.item.PapercuttingRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.item.PinwheelRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.item.UmbrellaRenderer;
import com.ChalkerCharles.morecolorful.client.texture.BalloonTextureManager;
import com.ChalkerCharles.morecolorful.client.texture.KiteTextureManager;
import com.ChalkerCharles.morecolorful.client.texture.MothTextureManager;
import com.ChalkerCharles.morecolorful.client.texture.PapercuttingTextureManager;
import com.ChalkerCharles.morecolorful.common.attachment.InstrumentData;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.item.FireworkShapeExtension;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.misc.SparklerItem;
import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.ChalkerCharles.morecolorful.common.menu.ModMenuTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.FireworkShapeFactoryRegistry;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@SuppressWarnings("deprecation")
@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientSetup {
    private static final ItemPropertyFunction PROPERTY_PLAYING = (stack, level, entity, seed) ->
            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
    private static ItemPropertyFunction propertyPlaying(Item item) {
        return (stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem().getItem() == item ? 1.0F : 0.0F;
    }
    private static final ItemPropertyFunction PROPERTY_ACTIVATED = (stack, level, entity, seed) ->
            stack.has(ModDataComponents.ACTIVATED) ? 1.0F : 0.0F;
    private static final ItemPropertyFunction PROPERTY_OPEN = (stack, level, entity, seed) ->
            stack.has(ModDataComponents.OPEN) ? 1.0F : 0.0F;
    private static final ItemPropertyFunction PROPERTY_FILLED = (stack, level, entity, seed) ->
            BundleItem.getFullnessDisplay(stack);
    private static ItemPropertyFunction propertyEntityVariantType(float f) {
        return (stack, level, entity, seed) -> {
            CustomData data = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            int i = data.copyTag().getInt("Type");
            return i / f;
        };
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModClientSetup::registerModelPredicate);
        event.enqueueWork(ModClientSetup::registerFireworkShapes);
    }

    private static void registerModelPredicate() {
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
        ResourceLocation activated = MoreColorful.location("activated");
        for (ItemLike item : SparklerItem.ALL_ITEMS) {
            ItemProperties.register(item.asItem(), activated, PROPERTY_ACTIVATED);
        }
        ItemProperties.register(ModItems.BOMB.get(), activated, PROPERTY_ACTIVATED);
        ResourceLocation open = MoreColorful.location("open");
        ItemProperties.register(ModItems.UMBRELLA.get(), open, PROPERTY_OPEN);
        ItemProperties.register(ModItems.DRIPLEAF_UMBRELLA.get(), open, PROPERTY_OPEN);
        ResourceLocation filled = ResourceLocation.withDefaultNamespace("filled");
        for (ItemLike item : ItemUtils.COLORED_BUNDLES) {
            ItemProperties.register(item.asItem(), filled, PROPERTY_FILLED);
        }
        ResourceLocation type = MoreColorful.location("type");
        ItemProperties.register(ModItems.BUTTERFLY.get(), type, propertyEntityVariantType(32));
        ItemProperties.register(ModItems.MOTH.get(), type, propertyEntityVariantType(32));
        ItemProperties.register(ModItems.CATERPILLAR.get(), type, propertyEntityVariantType(64));
        ItemProperties.register(ModItems.DRAGONFLY.get(), type, propertyEntityVariantType(10));
    }

    private static void registerFireworkShapes() {
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.CUBE, FireworkShapeFactories::cube);
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.HEART, FireworkShapeFactories::heart);
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.PLANET, FireworkShapeFactories::planet);
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.JELLYFISH, FireworkShapeFactories::jellyfish);
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.CLOCK, FireworkShapeFactories::clock);
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.AXIS, FireworkShapeFactories::axis);
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.TETRAHEDRON, FireworkShapeFactories::tetrahedron);
        FireworkShapeFactoryRegistry.register(FireworkShapeExtension.HYPERBOLOID, FireworkShapeFactories::hyperboloid);
    }

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
        // Pinwheel
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return PinwheelRenderer.INSTANCE;
            }
        }, ModItems.PINWHEEL.get());
        // Umbrella
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return UmbrellaItem.isOpen(itemStack) ? ArmPoseExtension.UMBRELLA : HumanoidModel.ArmPose.ITEM;
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return UmbrellaRenderer.INSTANCE;
            }
        }, ModItems.UMBRELLA.get());
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return UmbrellaItem.isOpen(itemStack) ? ArmPoseExtension.UMBRELLA : HumanoidModel.ArmPose.ITEM;
            }
        }, ModItems.DRIPLEAF_UMBRELLA.get());
        // Kite
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return KiteRenderer.INSTANCE;
            }
        }, ModItems.KITE.get());
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return PapercuttingRenderer.INSTANCE;
            }
        }, ItemUtils.itemArray(PapercuttingBlock.ALL_ITEMS));
    }

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(UmbrellaRenderer.UMBRELLA_FRAME_MODEL);
        event.register(UmbrellaRenderer.UMBRELLA_FRAME_MIRRORED_MODEL);
        event.register(UmbrellaRenderer.UMBRELLA_OVERLAY_MODEL);
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(UmbrellaRenderer.INSTANCE);
        event.registerReloadListener(PinwheelRenderer.INSTANCE);
        event.registerReloadListener(KiteRenderer.INSTANCE);
        event.registerReloadListener(KiteTextureManager.INSTANCE);
        event.registerReloadListener(BalloonTextureManager.INSTANCE);
        event.registerReloadListener(PapercuttingRenderer.INSTANCE);
        event.registerReloadListener(PapercuttingTextureManager.INSTANCE);
        event.registerReloadListener(MothTextureManager.INSTANCE);
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.PYROTECHNICS.get(), PyrotechnicsScreen::new);
        event.register(ModMenuTypes.PAPERCRAFT.get(), PapercraftScreen::new);
        event.register(ModMenuTypes.ENVELOPE.get(), EnvelopeScreen::new);
    }
}
