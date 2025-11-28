package com.ChalkerCharles.morecolorful.common.command;

import com.ChalkerCharles.morecolorful.common.attachment.ServerLevelData;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class ModWeatherCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("weather")
                .requires(stack -> stack.hasPermission(2))
                .then(Commands.literal("wind")
                        .then(Commands.argument("speedX, speedZ", Vec2Argument.vec2(false))
                                .executes(context -> setWindSpeed(context.getSource(), Vec2Argument.getVec2(context, "speedX, speedZ")))
                        )
                        .then(Commands.literal("reset")
                                .executes(context -> resetWindSpeed(context.getSource()))
                        )
                        .then(Commands.literal("freeze")
                                .executes(context -> freezeWindSpeed(context.getSource(), true))
                        )
                        .then(Commands.literal("unfreeze")
                                .executes(context -> freezeWindSpeed(context.getSource(), false))
                        )
                )
        );
    }

    private static int setWindSpeed(CommandSourceStack pSource, Vec2 vec) {
        float x = Mth.clamp(vec.x, -17.5F, 17.5F);
        float z = Mth.clamp(vec.y, -17.5F, 17.5F);
        if (WeatherUtils.isWindy(pSource.getLevel())) {
            ServerLevelData.setWindSpeedByCommand(pSource.getLevel(), x, z);
            pSource.sendSuccess(() -> Component.translatable("commands.morecolorful.weather.wind.set", x, z), true);
            return Mth.floor(Maths.length(x, z));
        } else {
            pSource.sendFailure(Component.translatable("commands.morecolorful.weather.wind.set.fail"));
            return 0;
        }
    }

    private static int resetWindSpeed(CommandSourceStack pSource) {
        if (WeatherUtils.isWindy(pSource.getLevel())) {
            ServerLevelData.resetWindSpeed(pSource.getLevel());
            pSource.sendSuccess(() -> Component.translatable("commands.morecolorful.weather.wind.reset"), true);
            return 1;
        } else {
            pSource.sendFailure(Component.translatable("commands.morecolorful.weather.wind.reset.fail"));
            return 0;
        }
    }

    private static int freezeWindSpeed(CommandSourceStack pSource, boolean freeze) {
        if (WeatherUtils.isWindy(pSource.getLevel())) {
            ServerLevelData.freezeWindSpeed(pSource.getLevel(), freeze);
            pSource.sendSuccess(() -> Component.translatable(freeze ? "commands.morecolorful.weather.wind.freeze" : "commands.morecolorful.weather.wind.unfreeze"), true);
            return 1;
        } else {
            pSource.sendFailure(Component.translatable(freeze ? "commands.morecolorful.weather.wind.freeze.fail" : "commands.morecolorful.weather.wind.unfreeze.fail"));
            return 0;
        }
    }
}
