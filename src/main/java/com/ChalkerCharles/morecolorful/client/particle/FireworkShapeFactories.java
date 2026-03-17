package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.util.Maths;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public final class FireworkShapeFactories {
    private static final Vector3f[] CUBE_VERTICES = new Vector3f[] {
            new Vector3f(1, 1, 1), new Vector3f(1, 1, -1),
            new Vector3f(-1, 1, -1), new Vector3f(-1, 1, 1),
            new Vector3f(1, -1, 1), new Vector3f(1, -1, -1),
            new Vector3f(-1, -1, -1), new Vector3f(-1, -1, 1)
    };
    private static final int[][] CUBE_EDGES = new int[][] {
            {0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}, {5, 6}, {6, 7}, {7, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };
    private static final double[][] HEART_SHAPE = initHeart();
    private static final Vector3f[] RING = initRing();
    private static final Vector3f[] TETRAHEDRON_VERTICES = new Vector3f[] {
            new Vector3f(-1, 1, -1),
            new Vector3f(1, 1, 1),
            new Vector3f(-1, -1, 1),
            new Vector3f(1, -1, -1)
    };
    private static final int[][] TETRAHEDRON_EDGES = new int[][] {
            {0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}
    };
    private static final Vector3f[][] HYPERBOLOID = initHyperboloid();

    public static void cube(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        polyhedron(starter, trail, flicker, colors, fadeColors, CUBE_VERTICES, CUBE_EDGES);
    }

    public static void heart(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        double speed = 0.25;
        IntList colorList = IntList.of(colors);
        IntList fadeColorList = IntList.of(fadeColors);
        starter.createParticle(starter.x, starter.y, starter.z, 0.0, speed, 0.0, colorList, fadeColorList, trail, flicker);
        starter.createParticle(starter.x, starter.y, starter.z, 0.0, -speed, 0.0, colorList, fadeColorList, trail, flicker);
        double f = starter.random.nextFloat() * Math.PI;
        for (int i = 0; i < 3; i++) {
            double d = f + i * Math.PI * 0.34;
            for (double[] coord : HEART_SHAPE) {
                double xd = coord[0] * speed;
                double yd = coord[1] * speed;
                double zd = xd * Math.sin(d);
                xd *= Math.cos(d);
                starter.createParticle(starter.x, starter.y, starter.z, xd, yd, zd, colorList, fadeColorList, trail, flicker);
                starter.createParticle(starter.x, starter.y, starter.z, -xd, yd, -zd, colorList, fadeColorList, trail, flicker);
            }
        }
    }

    public static void planet(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        IntList colorList = IntList.of(colors);
        IntList fadeColorList = IntList.of(fadeColors);
        starter.createParticleBall(0.25, 2, colorList, fadeColorList, trail, flicker);
        Quaternionf quaternion = Maths.randomQuaternion(starter.random);
        Vector3f[] vertices = Maths.transform(RING, quaternion);
        for (Vector3f pos : vertices) {
            double v = 0.4;
            starter.createParticle(starter.x, starter.y, starter.z, pos.x * v, pos.y * v, pos.z * v, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, -pos.x * v, -pos.y * v, -pos.z * v, colorList, fadeColorList, trail, flicker);
            double v1 = 0.45;
            starter.createParticle(starter.x, starter.y, starter.z, pos.x * v1, pos.y * v1, pos.z * v1, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, -pos.x * v1, -pos.y * v1, -pos.z * v1, colorList, fadeColorList, trail, flicker);
        }
    }

    public static void jellyfish(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        IntList colorList = IntList.of(colors);
        IntList fadeColorList = IntList.of(fadeColors);
        RandomSource random = starter.random;
        double x = starter.x, y = starter.y, z = starter.z;
        int radius = 2;
        double speed = 0.3;
        for (int i = 0; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    double d3 = j + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double d4 = i + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double d5 = k + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + random.nextGaussian() * 0.05;
                    starter.createParticle(x, y, z, d3 / d6, d4 / d6, d5 / d6, colorList, fadeColorList, trail, flicker);
                    if (i != 0 && i != radius && j != -radius && j != radius) {
                        k += radius * 2 - 1;
                    }
                }
            }
        }
        for (int i = 0; i < 30; i++) {
            double yd = random.nextFloat() * 0.8;
            double xd = (random.nextFloat() - 0.5) * yd;
            double zd = (random.nextFloat() - 0.5) * yd;
            starter.createParticle(x, y, z, xd, -yd, zd, colorList, fadeColorList, trail, flicker);
        }
    }

    public static void clock(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        IntList colorList = IntList.of(colors);
        IntList fadeColorList = IntList.of(fadeColors);
        double f = starter.random.nextFloat() * Math.PI;
        double d = Math.sin(f), e = Math.cos(f);
        double g = d * 0.02, h = e * 0.02;
        for (int i = 0; i < 18; i ++) {
            float angle = i * 10 * Mth.DEG_TO_RAD;
            double dx = Mth.cos(angle) * 0.5;
            double dy = Mth.sin(angle) * 0.5;
            double dz = dx * d;
            dx *= e;
            starter.createParticle(starter.x, starter.y, starter.z, dx + g, dy, dz - h, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, -dx - g, -dy, -dz + h, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, dx + g, -dy, dz - h, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, -dx - g, dy, -dz + h, colorList, fadeColorList, trail, flicker);
        }
        for (int i = 0; i < 6; i++) {
            float angle = i * 30 * Mth.DEG_TO_RAD;
            double dx = Mth.cos(angle) * 0.45;
            double dy = Mth.sin(angle) * 0.45;
            double dz = dx * d;
            dx *= e;
            starter.createParticle(starter.x, starter.y, starter.z, dx, dy, dz, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, -dx, -dy, -dz, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, dx * 0.9, dy * 0.9, dz * 0.9, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, -dx * 0.9, -dy * 0.9, -dz * 0.9, colorList, fadeColorList, trail, flicker);
        }
        Level level = Minecraft.getInstance().level;
        int time;
        if (level != null && level.dimensionType().natural()) {
            time = (int) ((level.getDayTime() + 6000) % 24000);
        } else {
            time = starter.random.nextInt(24000);
        }
        float hourAngle = (time % 12000) / 12000.0F * Mth.TWO_PI;
        float minuteAngle = (time % 1000) / 1000.0F * Mth.TWO_PI;
        double sinHa = Mth.sin(hourAngle);
        double cosHa = Mth.cos(hourAngle);
        double sinMa = Mth.sin(minuteAngle);
        double cosMa = Mth.cos(minuteAngle);
        for (int i = 0; i < 4; i++) {
            double m = i * 0.05;
            double dx = sinHa * m;
            double dy = cosHa * m;
            double dz = dx * d;
            dx *= e;
            starter.createParticle(starter.x, starter.y, starter.z, dx, dy, dz, colorList, fadeColorList, trail, flicker);
        }
        for (int i = 0; i < 8; i++) {
            double m = i * 0.05;
            double dx = sinMa * m;
            double dy = cosMa * m;
            double dz = dx * d;
            dx *= e;
            starter.createParticle(starter.x, starter.y, starter.z, dx, dy, dz, colorList, fadeColorList, trail, flicker);
        }
    }

    public static void axis(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        IntList colorList = IntList.of(colors);
        IntList fadeColorList = IntList.of(fadeColors);
        starter.createParticle(starter.x, starter.y, starter.z, 0.0, 0.0, 0.0, colorList, fadeColorList, trail, flicker);
        for (int i = 0; i < 10; i++) {
            double d = i * 0.05;
            starter.createParticle(starter.x, starter.y, starter.z, d, 0.0, 0.0, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, -d, 0.0, 0.0, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, 0.0, d, 0.0, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, 0.0, -d, 0.0, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, 0.0, 0.0, d, colorList, fadeColorList, trail, flicker);
            starter.createParticle(starter.x, starter.y, starter.z, 0.0, 0.0, -d, colorList, fadeColorList, trail, flicker);
        }
    }

    public static void tetrahedron(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        polyhedron(starter, trail, flicker, colors, fadeColors, TETRAHEDRON_VERTICES, TETRAHEDRON_EDGES);
    }

    public static void hyperboloid(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) {
        IntList colorList = IntList.of(colors);
        IntList fadeColorList = IntList.of(fadeColors);
        Quaternionf quaternion = Maths.randomQuaternion(starter.random);
        Vector3f[] up = Maths.transform(HYPERBOLOID[0], quaternion);
        Vector3f[] down = Maths.transform(HYPERBOLOID[1], quaternion);
        double speed = 0.25;
        for (int i = 0; i < 30; i++) {
            Vector3f start = up[i], end = down[i];
            for (int j = 0; j <= 16; j++) {
                double f = j * 0.0625;
                double xd = Mth.lerp(f, start.x, end.x) * speed;
                double yd = Mth.lerp(f, start.y, end.y) * speed;
                double zd = Mth.lerp(f, start.z, end.z) * speed;
                starter.createParticle(starter.x, starter.y, starter.z, xd, yd, zd, colorList, fadeColorList, trail, flicker);
            }
        }
    }

    private static void polyhedron(FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors, Vector3f[] vertices, int[][] edges) {
        Quaternionf quaternion = Maths.randomQuaternion(starter.random);
        Vector3f[] transformed = Maths.transform(vertices, quaternion);
        IntList colorList = IntList.of(colors);
        IntList fadeColorList = IntList.of(fadeColors);
        double speed = 0.25;
        for (Vector3f pos : transformed) {
            starter.createParticle(starter.x, starter.y, starter.z, pos.x * speed, pos.y * speed, pos.z * speed, colorList, fadeColorList, trail, flicker);
        }
        for (int[] index : edges) {
            Vector3f start = transformed[index[0]];
            Vector3f end = transformed[index[1]];
            for (float f = 0.125F; f < 1.0F; f += 0.125F) {
                double xd = Mth.lerp(f, start.x, end.x) * speed;
                double yd = Mth.lerp(f, start.y, end.y) * speed;
                double zd = Mth.lerp(f, start.z, end.z) * speed;
                starter.createParticle(starter.x, starter.y, starter.z, xd, yd, zd, colorList, fadeColorList, trail, flicker);
            }
        }
    }

    private static double[][] initHeart() {
        double[][] heart = new double[15][];
        int i = 0;
        double p = Math.PI / 16.0;
        for (int j = 1; j < 8; j++) {
            double d = p * j;
            double d1 = Math.sqrt(Math.abs(Math.sin(d))) + Math.sqrt(Math.abs(Math.cos(d)));
            heart[i] = new double[] {d, d1};
            i++;
        }
        heart[i] = new double[] {Mth.HALF_PI, 1.0};
        i++;
        for (int j = 1; j < 8; j++) {
            double d = p * j;
            double d1 = Math.sqrt(Math.abs(Math.sin(d))) - Math.sqrt(Math.abs(Math.cos(d)));
            heart[i] = new double[] {d, d1};
            i++;
        }
        return heart;
    }

    private static Vector3f[] initRing() {
        Vector3f[] ring = new Vector3f[15];
        for (int i = 0; i < 15; i ++) {
            float angle = i * 12 * Mth.DEG_TO_RAD;
            ring[i] = new Vector3f(Mth.cos(angle), 0, Mth.sin(angle));
        }
        return ring;
    }

    private static Vector3f[][] initHyperboloid() {
        int size = 30;
        Vector3f[] up = new Vector3f[size];
        Vector3f[] down = new Vector3f[size];
        float a = Mth.TWO_PI / 3.0F;
        for (int i = 0; i < size; i++) {
            float angle = i * 12 * Mth.DEG_TO_RAD;
            up[i] = new Vector3f(Mth.cos(angle), 1, Mth.sin(angle));
            down[i] = new Vector3f(Mth.cos(angle + a), -1, Mth.sin(angle + a));
        }
        return new Vector3f[][] {up, down};
    }
}
