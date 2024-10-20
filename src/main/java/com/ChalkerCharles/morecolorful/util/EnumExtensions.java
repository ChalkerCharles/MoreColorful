package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public final class EnumExtensions {
    public static class ArmPose {
        public static final EnumProxy<HumanoidModel.ArmPose> FLUTE = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -0.364023F - model.head.xRot / 2 + model.head.yRot / 2;
                model.rightArm.yRot = 0.36029F - Math.abs(model.head.yRot / 2) - model.head.xRot / 2;
                model.rightArm.zRot = 1.888971F - model.head.xRot / 4 + model.head.yRot / 4;
                model.rightArm.y -= 2;
                model.leftArm.xRot = -1.600489F + model.head.xRot / 2 - model.head.yRot / 4;
                model.leftArm.yRot = 1.030029F + model.head.yRot / 2 - model.head.xRot / 4;
                model.leftArm.zRot = 0.172297F;
                model.leftArm.x -= 1;
                model.leftArm.y -= 1;
                model.leftArm.z -= 1;
            } else {
                model.leftArm.xRot = -0.364023F - model.head.xRot / 2 - model.head.yRot / 2;
                model.leftArm.yRot = -0.36029F + Math.abs(model.head.yRot / 2) + model.head.xRot / 2;
                model.leftArm.zRot = -1.888971F + model.head.xRot / 4 + model.head.yRot / 4;
                model.leftArm.y -= 2;
                model.rightArm.xRot = -1.600489F + model.head.xRot / 2 + model.head.yRot / 4;
                model.rightArm.yRot = -1.030029F + model.head.yRot / 2 + model.head.xRot / 4;
                model.rightArm.zRot = -0.172297F;
                model.rightArm.x += 1;
                model.rightArm.y -= 1;
                model.rightArm.z -= 1;
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> GUITAR_HOLD = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -0.26179938F; // (-pi/12)
                model.leftArm.xRot = -0.62831852F; // (-pi/5)
            } else {
                model.rightArm.xRot = -0.62831852F;
                model.leftArm.xRot = -0.26179938F;
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> GUITAR_PLAYING = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -0.26179938F;
                model.leftArm.xRot = -0.62831852F;
                AnimationUtils.animateGuitarPlaying(model.rightArm, model.leftArm, entity, true);
            } else {
                model.rightArm.xRot = -0.62831852F;
                model.leftArm.xRot = -0.26179938F;
                AnimationUtils.animateGuitarPlaying(model.rightArm, model.leftArm, entity, false);
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> COW_BELL = new EnumProxy<>(HumanoidModel.ArmPose.class, false, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.308998F;
                model.rightArm.yRot = -0.174533F;
                model.leftArm.xRot = model.leftArm.xRot * 0.5F - (float) (Math.PI / 10);
            } else {
                model.leftArm.xRot = -1.308998F;
                model.leftArm.yRot = 0.174533F;
                model.rightArm.xRot = model.rightArm.xRot * 0.5F - (float) (Math.PI / 10);
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> DIDGERIDOO = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            model.rightArm.xRot = -1.221731F;
            model.leftArm.xRot = -1.221731F;
            model.rightArm.yRot = -0.218166F;
            model.leftArm.yRot = 0.218166F;
            model.rightArm.zRot = -0.174533F;
            model.leftArm.zRot = 0.174533F;
            model.rightArm.x += 0.5F;
            model.leftArm.x -= 0.5F;
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> VIOLIN_HOLD = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.178098F;
                model.rightArm.yRot = 0.087267F;
                model.rightArm.zRot = 0.392699F;
                model.leftArm.xRot = -1.570797F;
            } else {
                model.leftArm.xRot = -1.178098F;
                model.leftArm.yRot = -0.087267F;
                model.leftArm.zRot = -0.392699F;
                model.rightArm.xRot = -1.570797F;
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> VIOLIN_PLAYING = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.178098F;
                model.rightArm.yRot = 0.087267F;
                model.rightArm.zRot = 0.392699F;
                model.leftArm.xRot = -1.570797F;
                AnimationUtils.animateViolinPlaying(model.rightArm, model.leftArm, entity, true);
            } else {
                model.leftArm.xRot = -1.178098F;
                model.leftArm.yRot = -0.087267F;
                model.leftArm.zRot = -0.392699F;
                model.rightArm.xRot = -1.570797F;
                AnimationUtils.animateViolinPlaying(model.rightArm, model.leftArm, entity, false);
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> CELLO = new EnumProxy<>(HumanoidModel.ArmPose.class, false, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.483531F;
                model.rightArm.yRot = -0.043633F;
                model.rightArm.zRot = -0.087267F;
                model.rightArm.y += 1;
            } else {
                model.leftArm.xRot = -1.483531F;
                model.leftArm.yRot = 0.043633F;
                model.leftArm.zRot = 0.087267F;
                model.leftArm.y += 1;
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> CELLO_HOLD = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.483531F;
                model.rightArm.yRot = -0.043633F;
                model.rightArm.zRot = -0.087267F;
                model.rightArm.y += 1;
                model.leftArm.xRot = -0.872665F;
                model.leftArm.y -= 1;
            } else {
                model.leftArm.xRot = -1.483531F;
                model.leftArm.yRot = 0.043633F;
                model.leftArm.zRot = 0.087267F;
                model.leftArm.y += 1;
                model.rightArm.xRot = -0.872665F;
                model.rightArm.y -= 1;
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> CELLO_PLAYING = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.483531F;
                model.rightArm.yRot = -0.043633F;
                model.rightArm.zRot = -0.087267F;
                model.rightArm.y += 1;
                model.leftArm.xRot = -0.872665F;
                model.leftArm.y -= 1;
                AnimationUtils.animateCelloPlaying(model.rightArm, model.leftArm, entity, true);
            } else {
                model.leftArm.xRot = -1.483531F;
                model.leftArm.yRot = 0.043633F;
                model.leftArm.zRot = 0.087267F;
                model.leftArm.y += 1;
                model.rightArm.xRot = -0.872665F;
                model.rightArm.y -= 1;
                AnimationUtils.animateCelloPlaying(model.rightArm, model.leftArm, entity, false);
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> SAXOPHONE = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            model.rightArm.xRot = -1.047198F;
            model.leftArm.xRot = -1.047198F;
            model.rightArm.yRot = -0.218166F;
            model.leftArm.yRot = 0.218166F;
            model.rightArm.zRot = -0.174533F;
            model.leftArm.zRot = 0.174533F;
            model.rightArm.x += 0.5F;
            model.leftArm.x -= 0.5F;
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> PIPA_HOLD = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -2.094396F;
                model.rightArm.yRot = -0.349066F;
                model.rightArm.zRot = -0.2618F;
                model.rightArm.z += 0.5F;
                model.leftArm.xRot = -0.610866F;
                model.leftArm.yRot = 0.218166F;
                model.leftArm.z -= 1F;
            } else {
                model.rightArm.xRot = -0.610866F;
                model.rightArm.yRot = -0.218166F;
                model.rightArm.z -= 1F;
                model.leftArm.xRot = -2.094396F;
                model.leftArm.yRot = 0.349066F;
                model.leftArm.zRot = 0.2618F;
                model.leftArm.z += 0.5F;
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> PIPA_PLAYING = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -2.094396F;
                model.rightArm.yRot = -0.349066F;
                model.rightArm.zRot = -0.2618F;
                model.rightArm.z += 0.5F;
                model.leftArm.xRot = -0.610866F;
                model.leftArm.yRot = 0.218166F;
                model.leftArm.z -= 1F;
                AnimationUtils.animatePipaPlaying(model.rightArm, model.leftArm, entity, true);
            } else {
                model.rightArm.xRot = -0.610866F;
                model.rightArm.yRot = -0.218166F;
                model.rightArm.z -= 1F;
                model.leftArm.xRot = -2.094396F;
                model.leftArm.yRot = 0.349066F;
                model.leftArm.zRot = 0.2618F;
                model.leftArm.z += 0.5F;
                AnimationUtils.animatePipaPlaying(model.rightArm, model.leftArm, entity, false);
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> ERHU_HOLD = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.047198F;
                model.rightArm.yRot = -0.087267F;
                model.leftArm.xRot = -0.523599F;
            } else {
                model.leftArm.xRot = -1.047198F;
                model.leftArm.yRot = 0.087267F;
                model.rightArm.xRot = -0.523599F;
            }
        }));
        public static final EnumProxy<HumanoidModel.ArmPose> ERHU_PLAYING = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) ((model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -1.047198F;
                model.rightArm.yRot = -0.087267F;
                model.leftArm.xRot = -0.523599F;
                AnimationUtils.animateErhuPlaying(model.rightArm, model.leftArm, entity, true);
            } else {
                model.leftArm.xRot = -1.047198F;
                model.leftArm.yRot = 0.087267F;
                model.rightArm.xRot = -0.523599F;
                AnimationUtils.animateErhuPlaying(model.rightArm, model.leftArm, entity, false);
            }
        }));
    }

    public static class BoatType {
        public static final EnumProxy<Boat.Type> CRABAPPLE = new EnumProxy<>(Boat.Type.class,
                ModBlocks.CRABAPPLE_PLANKS,
                "morecolorful:crabapple",
                ModItems.CRABAPPLE_BOAT,
                ModItems.CRABAPPLE_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> EBONY = new EnumProxy<>(Boat.Type.class,
                ModBlocks.EBONY_PLANKS,
                "morecolorful:ebony",
                ModItems.EBONY_BOAT,
                ModItems.EBONY_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> GINKGO = new EnumProxy<>(Boat.Type.class,
                ModBlocks.GINKGO_PLANKS,
                "morecolorful:ginkgo",
                ModItems.GINKGO_BOAT,
                ModItems.GINKGO_CHEST_BOAT,
                Items.STICK,
                false);
        public static final EnumProxy<Boat.Type> MAPLE = new EnumProxy<>(Boat.Type.class,
                ModBlocks.MAPLE_PLANKS,
                "morecolorful:maple",
                ModItems.MAPLE_BOAT,
                ModItems.MAPLE_CHEST_BOAT,
                Items.STICK,
                false);
    }
}