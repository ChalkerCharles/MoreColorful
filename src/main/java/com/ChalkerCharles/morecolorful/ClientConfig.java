package com.ChalkerCharles.morecolorful;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    private static final String prefix = MoreColorful.MODID + ".config.client.";
    public final ModConfigSpec.BooleanValue wavyBlocks;
    public final ModConfigSpec.BooleanValue wavyParticles;
    public final ModConfigSpec.BooleanValue windAndRain;
    public final ModConfigSpec.BooleanValue windAndCloud;
    public final ModConfigSpec.BooleanValue windParticles;
    public final ModConfigSpec.BooleanValue windSounds;
    public final ModConfigSpec.BooleanValue leavesOnGround;
    public final ModConfigSpec.BooleanValue confettiOnGround;

    public ClientConfig(ModConfigSpec.Builder builder) {
        wavyBlocks = builder
                .worldRestart()
                .translation(prefix + "wavy_blocks")
                .comment("Allow blocks to wave in the wind.")
                .define("wavyBlocks", true);
        wavyParticles = builder
                .worldRestart()
                .translation(prefix + "wavy_particles")
                .comment("Allow wind to affect particle motion.")
                .define("wavyParticles", true);
        windAndRain = builder
                .worldRestart()
                .translation(prefix + "wind_and_rain")
                .comment("Allow wind to affect rain and snow.")
                .define("windAndRain", true);
        windAndCloud = builder
                .worldRestart()
                .translation(prefix + "wind_and_cloud")
                .comment("Allow wind to affect cloud motion.")
                .define("windAndCloud", true);
        windParticles = builder
                .worldRestart()
                .translation(prefix + "wind_particles")
                .comment("Wind can generate particles.")
                .define("windParticles", true);
        windSounds = builder
                .worldRestart()
                .translation(prefix + "wind_sounds")
                .comment("Wind can make sound effects. It also allows the leaves rustling.")
                .define("windSounds", true);
        leavesOnGround = builder
                .translation(prefix + "leaves_on_ground")
                .comment("Allow falling leaves and petal particles to stay on the ground for a while, instead of disappearing immediately.")
                .define("leavesOnGround", true);
        confettiOnGround = builder
                .translation(prefix + "confetti_on_ground")
                .comment("Allow confetti particles to stay on the ground for a while, instead of disappearing immediately.")
                .define("confettiOnGround", true);
    }
}
