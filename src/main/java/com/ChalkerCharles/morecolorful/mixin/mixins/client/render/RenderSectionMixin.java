package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IRenderSectionExtension;
import com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor.IVertexBufferMixin;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(SectionRenderDispatcher.RenderSection.class)
public abstract class RenderSectionMixin implements IRenderSectionExtension {
    @Shadow
    @Final
    BlockPos.MutableBlockPos origin;
    @Shadow
    public abstract VertexBuffer getBuffer(RenderType pRenderType);
    @Shadow
    private boolean dirty;
    @Shadow
    private boolean playerChanged;
    @Unique
    private WavyDataTask.Default moreColorful$wavyTask;

    @ModifyExpressionValue(method = "lambda$new$1", at = @At(value = "FIELD", target = "Lcom/mojang/blaze3d/vertex/VertexBuffer$Usage;STATIC:Lcom/mojang/blaze3d/vertex/VertexBuffer$Usage;"))
    private static VertexBuffer.Usage newVertexBuffer(VertexBuffer.Usage original, @Local(argsOnly = true)RenderType renderType) {
        if (Config.wavyBlocks && RenderUtils.isWavyRenderType(renderType)) {
            return VertexBuffer.Usage.DYNAMIC;
        }
        return original;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(CallbackInfo ci) {
        if (!Config.wavyBlocks) return;
        int id0 = ((IVertexBufferMixin) this.getBuffer(RenderType.cutoutMipped())).getVertexBufferId();
        int id1 = ((IVertexBufferMixin) this.getBuffer(RenderType.cutout())).getVertexBufferId();
        int id2 = ((IVertexBufferMixin) this.getBuffer(RenderType.translucent())).getVertexBufferId();
        this.moreColorful$wavyTask = new WavyDataTask.Default(this.origin, id0, id1, id2);
    }

    @Inject(method = "releaseBuffers", at = @At("TAIL"))
    private void releaseBuffers(CallbackInfo ci) {
        if (this.moreColorful$wavyTask != null) {
            this.moreColorful$wavyTask.close();
        }
    }

    @Inject(method = "setDirty", at = @At("HEAD"))
    private void setDirty(boolean pPlayerChanged, CallbackInfo ci) {
        if (this.moreColorful$wavyTask == null || this.dirty) return;
        int x = this.origin.getX() >> 4, y = this.origin.getY() >> 4, z = this.origin.getZ() >> 4;
        ILevelRendererExtension.setGroupDirty(x, y, z);
    }

    @Override
    public WavyDataTask.Default moreColorful$getWavyTask() {
        return this.moreColorful$wavyTask;
    }

    @Override
    public void moreColorful$setDirty() {
        boolean flag = this.dirty;
        this.dirty = true;
        this.playerChanged = flag && this.playerChanged;
    }

    @Mixin(targets = "net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection$RebuildTask")
    private static abstract class RebuildTaskMixin {
        @Shadow
        @Final
        SectionRenderDispatcher.RenderSection this$1;

        @Inject(method = "doTask", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/SectionPos;of(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/SectionPos;"))
        private void preCompile(CallbackInfoReturnable<CompletableFuture<?>> cir) {
            if (Config.wavyBlocks) {
                RenderUtils.putWavyTask(IRenderSectionExtension.getWavyTask(this$1));
            }
        }
    }
}
