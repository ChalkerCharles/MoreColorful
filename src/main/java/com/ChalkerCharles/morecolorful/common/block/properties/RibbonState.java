package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.function.Predicate;

public enum RibbonState implements StringRepresentable {
    DEFAULT("default", false, false, false, false),
    UP("up", true, false, false, false),
    DOWN("down", false, true, false, false),
    LEFT("left", false, false, true, false),
    RIGHT("right", false, false, false, true),
    VERTICAL("vertical", true, true, false, false),
    HORIZONTAL("horizontal", false, false, true, true),
    UP_LEFT("up_left", true, false, true, false),
    UP_RIGHT("up_right", true, false, false, true),
    DOWN_LEFT("down_left", false, true, true, false),
    DOWN_RIGHT("down_right", false, true, false, true),
    VERTICAL_LEFT("vertical_left", true, true, true, false),
    VERTICAL_RIGHT("vertical_right", true, true, false, true),
    UP_HORIZONTAL("up_horizontal", true, false, true, true),
    DOWN_HORIZONTAL("down_horizontal", false, true, true, true),
    CROSS("cross", true, true, true, true),
    VERTICAL_CONNECT("vertical_connect", true, true, false, false),
    TIP("tip", true, false, false, false),
    HORIZONTAL_CONNECT("horizontal_connect", false, false, true, true),
    CROSS_CONNECT("cross_connect", true, true, true, true);

    private static final RibbonState[] VALUES_LESS_BOWS = new RibbonState[] {
            DEFAULT, // 0000
            RIGHT, // 0001
            LEFT, // 0010
            HORIZONTAL_CONNECT, // 0011
            DOWN, // 0100
            DOWN_RIGHT, // 0101
            DOWN_LEFT, // 0110
            DOWN_HORIZONTAL, // 0111
            TIP, // 1000
            UP_RIGHT, // 1001
            UP_LEFT, // 1010
            UP_HORIZONTAL, // 1011
            VERTICAL_CONNECT, // 1100
            VERTICAL_RIGHT, // 1101
            VERTICAL_LEFT, // 1110
            CROSS_CONNECT, // 1111
    };

    private final String name;
    public final boolean connectUp;
    public final boolean connectDown;
    public final boolean connectLeft;
    public final boolean connectRight;

    RibbonState(String name, boolean up, boolean down, boolean left, boolean right) {
        this.name = name;
        this.connectUp = up;
        this.connectDown = down;
        this.connectLeft = left;
        this.connectRight = right;
    }

    public boolean noBow() {
        return this == VERTICAL_CONNECT || this == TIP || this == HORIZONTAL_CONNECT || this == CROSS_CONNECT;
    }

    public boolean hasBow() {
        return !this.noBow();
    }

    public RibbonState mirror() {
        return switch (this) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP_LEFT -> UP_RIGHT;
            case UP_RIGHT -> UP_LEFT;
            case DOWN_LEFT -> DOWN_RIGHT;
            case DOWN_RIGHT -> DOWN_LEFT;
            case VERTICAL_LEFT -> VERTICAL_RIGHT;
            case VERTICAL_RIGHT -> VERTICAL_LEFT;
            default -> this;
        };
    }

    public RibbonState connectUp() {
        return switch (this) {
            case DEFAULT -> UP;
            case DOWN -> VERTICAL;
            case LEFT -> UP_LEFT;
            case RIGHT -> UP_RIGHT;
            case HORIZONTAL -> UP_HORIZONTAL;
            case DOWN_LEFT -> VERTICAL_LEFT;
            case DOWN_RIGHT -> VERTICAL_RIGHT;
            case DOWN_HORIZONTAL -> CROSS;
            default -> this;
        };
    }

    public RibbonState connectDown() {
        return switch (this) {
            case DEFAULT -> DOWN;
            case UP -> VERTICAL;
            case LEFT -> DOWN_LEFT;
            case RIGHT -> DOWN_RIGHT;
            case HORIZONTAL -> DOWN_HORIZONTAL;
            case UP_LEFT -> VERTICAL_LEFT;
            case UP_RIGHT -> VERTICAL_RIGHT;
            case UP_HORIZONTAL -> CROSS;
            case TIP -> VERTICAL_CONNECT;
            default -> this;
        };
    }

    public RibbonState connectLeft() {
        return switch (this) {
            case DEFAULT -> LEFT;
            case UP -> UP_LEFT;
            case DOWN -> DOWN_LEFT;
            case VERTICAL -> VERTICAL_LEFT;
            case RIGHT -> HORIZONTAL;
            case UP_RIGHT -> UP_HORIZONTAL;
            case DOWN_RIGHT -> DOWN_HORIZONTAL;
            case VERTICAL_RIGHT -> CROSS;
            default -> this;
        };
    }

    public RibbonState connectRight() {
        return switch (this) {
            case DEFAULT -> RIGHT;
            case UP -> UP_RIGHT;
            case DOWN -> DOWN_RIGHT;
            case VERTICAL -> VERTICAL_RIGHT;
            case LEFT -> HORIZONTAL;
            case UP_LEFT -> UP_HORIZONTAL;
            case DOWN_LEFT -> DOWN_HORIZONTAL;
            case VERTICAL_LEFT -> CROSS;
            default -> this;
        };
    }

    public RibbonState withBow() {
        return switch (this) {
            case VERTICAL_CONNECT -> VERTICAL;
            case TIP -> UP;
            case HORIZONTAL_CONNECT -> HORIZONTAL;
            case CROSS_CONNECT -> CROSS;
            default -> this;
        };
    }

    public RibbonState cutUp() {
        return switch (this) {
            case UP -> DEFAULT;
            case VERTICAL -> DOWN;
            case UP_LEFT -> LEFT;
            case UP_RIGHT -> RIGHT;
            case UP_HORIZONTAL -> HORIZONTAL;
            case VERTICAL_LEFT -> DOWN_LEFT;
            case VERTICAL_RIGHT -> DOWN_RIGHT;
            case CROSS -> DOWN_HORIZONTAL;
            default -> this;
        };
    }

    public RibbonState cutDown() {
        return switch (this) {
            case DOWN -> DEFAULT;
            case VERTICAL -> UP;
            case DOWN_LEFT -> LEFT;
            case DOWN_RIGHT -> RIGHT;
            case DOWN_HORIZONTAL -> HORIZONTAL;
            case VERTICAL_LEFT -> UP_LEFT;
            case VERTICAL_RIGHT -> UP_RIGHT;
            case CROSS -> UP_HORIZONTAL;
            case VERTICAL_CONNECT -> TIP;
            default -> this;
        };
    }

    public RibbonState cutLeft() {
        return switch (this) {
            case LEFT -> DEFAULT;
            case UP_LEFT -> UP;
            case DOWN_LEFT -> DOWN;
            case VERTICAL_LEFT -> VERTICAL;
            case HORIZONTAL -> RIGHT;
            case UP_HORIZONTAL -> UP_RIGHT;
            case DOWN_HORIZONTAL -> DOWN_RIGHT;
            case CROSS -> VERTICAL_RIGHT;
            default -> this;
        };
    }

    public RibbonState cutRight() {
        return switch (this) {
            case RIGHT -> DEFAULT;
            case UP_RIGHT -> UP;
            case DOWN_RIGHT -> DOWN;
            case VERTICAL_RIGHT -> VERTICAL;
            case HORIZONTAL -> LEFT;
            case UP_HORIZONTAL -> UP_LEFT;
            case DOWN_HORIZONTAL -> DOWN_LEFT;
            case CROSS -> VERTICAL_LEFT;
            default -> this;
        };
    }

    public RibbonState withoutBow() {
        return switch (this) {
            case VERTICAL -> VERTICAL_CONNECT;
            case UP -> TIP;
            case HORIZONTAL -> HORIZONTAL_CONNECT;
            case CROSS -> CROSS_CONNECT;
            default -> this;
        };
    }

    public static RibbonState getState(boolean up, boolean down, boolean left, boolean right) {
        int index = (up ? 8 : 0) | (down ? 4 : 0) | (left ? 2 : 0) | (right ? 1 : 0);
        return VALUES_LESS_BOWS[index];
    }

    public static RibbonState[] arrayOf(Predicate<RibbonState> predicate) {
        return Arrays.stream(values()).filter(predicate).toArray(RibbonState[]::new);
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
