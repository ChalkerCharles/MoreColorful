package com.ChalkerCharles.morecolorful.util;

import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.IArmPoseTransformer;

@SuppressWarnings("unused")
public class EnumParameters {
    public static Object flutePose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
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
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object guitarHoldPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = -0.26179938F; // (-pi/12)
                    model.leftArm.xRot = -0.62831852F; // (-pi/5)
                } else {
                    model.rightArm.xRot = -0.62831852F;
                    model.leftArm.xRot = -0.26179938F;
                }
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object guitarPlayingPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = -0.26179938F;
                    model.leftArm.xRot = -0.62831852F;
                    AnimationUtils.animateGuitarPlaying(model.rightArm, model.leftArm, entity, true);
                } else {
                    model.rightArm.xRot = -0.62831852F;
                    model.leftArm.xRot = -0.26179938F;
                    AnimationUtils.animateGuitarPlaying(model.rightArm, model.leftArm, entity, false);
                }
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object cowBellPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> false;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = -1.308998F;
                    model.rightArm.yRot = -0.174533F;
                    model.leftArm.xRot = model.leftArm.xRot * 0.5F - (float) (Math.PI / 10);
                } else {
                    model.leftArm.xRot = -1.308998F;
                    model.leftArm.yRot = 0.174533F;
                    model.rightArm.xRot = model.rightArm.xRot * 0.5F - (float) (Math.PI / 10);
                }
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object didgeridooPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
                model.rightArm.xRot = -1.221731F;
                model.leftArm.xRot = -1.221731F;
                model.rightArm.yRot = -0.218166F;
                model.leftArm.yRot = 0.218166F;
                model.rightArm.zRot = -0.174533F;
                model.leftArm.zRot = 0.174533F;
                model.rightArm.x += 0.5F;
                model.leftArm.x -= 0.5F;
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object violinHoldPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
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
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object violinPlayingPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
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
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object celloPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> false;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
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
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object celloHoldPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
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
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
    public static Object celloPlayingPose(int idx, Class<?> type) {
        return switch (idx) {
            case 0 -> true;
            case 1 -> type.cast((IArmPoseTransformer) (model, entity, arm) -> {
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
            });
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        };
    }
}
git config --global user.email"2130897257@qq.com"
git config --global user.name"ChalkerCharles"