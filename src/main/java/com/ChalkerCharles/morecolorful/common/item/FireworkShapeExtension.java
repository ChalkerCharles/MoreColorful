package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.world.item.component.FireworkExplosion;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class FireworkShapeExtension {
    public static final FireworkExplosion.Shape CUBE = Proxy.CUBE.getValue();
    public static final FireworkExplosion.Shape HEART = Proxy.HEART.getValue();
    public static final FireworkExplosion.Shape PLANET = Proxy.PLANET.getValue();
    public static final FireworkExplosion.Shape JELLYFISH = Proxy.JELLYFISH.getValue();
    public static final FireworkExplosion.Shape CLOCK = Proxy.CLOCK.getValue();
    public static final FireworkExplosion.Shape AXIS = Proxy.AXIS.getValue();
    public static final FireworkExplosion.Shape TETRAHEDRON = Proxy.TETRAHEDRON.getValue();
    public static final FireworkExplosion.Shape HYPERBOLOID = Proxy.HYPERBOLOID.getValue();

    public static class Proxy {
        public static final EnumProxy<FireworkExplosion.Shape> CUBE = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("cube")
        );
        public static final EnumProxy<FireworkExplosion.Shape> HEART = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("heart")
        );
        public static final EnumProxy<FireworkExplosion.Shape> PLANET = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("planet")
        );
        public static final EnumProxy<FireworkExplosion.Shape> JELLYFISH = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("jellyfish")
        );
        public static final EnumProxy<FireworkExplosion.Shape> CLOCK = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("clock")
        );
        public static final EnumProxy<FireworkExplosion.Shape> AXIS = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("axis")
        );
        public static final EnumProxy<FireworkExplosion.Shape> TETRAHEDRON = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("tetrahedron")
        );
        public static final EnumProxy<FireworkExplosion.Shape> HYPERBOLOID = new EnumProxy<>(
                FireworkExplosion.Shape.class,
                -1,
                MoreColorful.key("hyperboloid")
        );
    }
}
