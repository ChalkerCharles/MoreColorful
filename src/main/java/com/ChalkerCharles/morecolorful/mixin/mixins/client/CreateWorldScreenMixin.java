package com.ChalkerCharles.morecolorful.mixin.mixins.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.worldgen.ModBiomeModifiers;
import com.ChalkerCharles.morecolorful.util.FileUtils;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Shadow
    private Path tempDataPackDir;

    @Inject(method = "getTempDataPackDir()Ljava/nio/file/Path;",
            at = @At(value = "FIELD",
                    target = "net.minecraft.client.gui.screens.worldselection.CreateWorldScreen.tempDataPackDir:Ljava/nio/file/Path;",
                    opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void getTempDataPackDir(CallbackInfoReturnable<Path> cir) {
        moreColorful$checkBiomeModifier(Config.allowAddingFeatures, ModBiomeModifiers.ADD_FEATURE_MODIFIERS);
    }

    @Unique
    private void moreColorful$checkBiomeModifier(boolean config, List<String> modifiers) {
        if (!config) {
            FileUtils.generateDatapack(tempDataPackDir, modifiers, FileUtils.writeFiles());
        }
    }
}
