package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelChunkExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IRenderSectionExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IViewAreaExtension;
import com.ChalkerCharles.morecolorful.util.client.MultiBlockGroup;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ViewArea.class)
public abstract class ViewAreaMixin implements IViewAreaExtension {
    @Shadow
    protected int sectionGridSizeX;
    @Shadow
    protected int sectionGridSizeZ;
    @Shadow
    @Final
    protected Level level;
    @Shadow
    public SectionRenderDispatcher.RenderSection[] sections;
    @Shadow
    protected abstract int getSectionIndex(int pX, int pY, int pZ);

    @Inject(method = "repositionCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection;setOrigin(III)V", shift = At.Shift.AFTER))
    private void repositionCamera(double pViewEntityX, double pViewEntityZ, CallbackInfo ci, @Local SectionRenderDispatcher.RenderSection section) {
        if (RenderUtils.isClientWindOn) {
            IRenderSectionExtension.getWavyTask(section).setWindZones(this.level);
        }
    }

    @Override
    public WavyDataTask<?> moreColorful$getWavyDataTask(int sectionX, int sectionY, int sectionZ) {
        int i = Math.floorMod(sectionX, this.sectionGridSizeX);
        int j = sectionY - this.level.getMinSection();
        int k = Math.floorMod(sectionZ, this.sectionGridSizeZ);
        SectionRenderDispatcher.RenderSection section = this.sections[this.getSectionIndex(i, j, k)];
        return IRenderSectionExtension.getWavyTask(section);
    }

    @Override
    public void moreColorful$setGroupDirty(int sectionX, int sectionY, int sectionZ) {
        LevelChunk chunk = this.level.getChunk(sectionX, sectionZ);
        MultiBlockGroup mbg = ILevelChunkExtension.getMultiBlockGroup(chunk);
        MultiBlockGroup.Groups groups = mbg.getGroupsNullableInSection(sectionY);
        if (groups == null) return;
        int m = this.level.getMinSection();
        int i = Math.floorMod(sectionX, this.sectionGridSizeX);
        int k = Math.floorMod(sectionZ, this.sectionGridSizeZ);
        for (MultiBlockGroup.Group group : groups) {
            int a = group.getLowSection(), b = group.getHighSection();
            for (int j = a; j <= b; j++) {
                if (j == sectionY) continue;
                SectionRenderDispatcher.RenderSection section = this.sections[this.getSectionIndex(i, j - m, k)];
                IRenderSectionExtension.setDirty(section);
            }
        }
    }
}
