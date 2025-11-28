package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelChunkExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionManagerExtension;
import com.ChalkerCharles.morecolorful.util.client.MultiBlockGroup;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import net.caffeinemc.mods.sodium.client.render.chunk.ChunkUpdateType;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager", remap = false)
public abstract class RenderSectionManagerMixin implements IRenderSectionManagerExtension {
    @Shadow
    @Final
    private Long2ReferenceMap<RenderSection> sectionByPosition;
    @Shadow
    @Final
    private ClientLevel level;
    @Shadow
    protected abstract RenderSection getRenderSection(int x, int y, int z);

    @Inject(method = "scheduleRebuild", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/RenderSection;setPendingUpdate(Lnet/caffeinemc/mods/sodium/client/render/chunk/ChunkUpdateType;)V"))
    private void scheduleRebuild(int x, int y, int z, boolean important, CallbackInfo ci) {
        if (RenderUtils.isClientWindOn) {
            this.moreColorful$setGroupDirty(x, y, z);
        }
    }

    @Override
    public void moreColorful$clearWindCache() {
        for (RenderSection section : this.sectionByPosition.values()) {
            IRenderSectionExtension.getWavyTask(section).windMap.clear();
        }
    }

    @Nullable
    @Override
    public WavyDataTask<?> moreColorful$getWavyDataTask(int sectionX, int sectionY, int sectionZ) {
        RenderSection section = this.getRenderSection(sectionX, sectionY, sectionZ);
        if (section == null) return null;
        return IRenderSectionExtension.getWavyTask(section);
    }

    @Unique
    private void moreColorful$setGroupDirty(int sectionX, int sectionY, int sectionZ) {
        LevelChunk chunk = this.level.getChunk(sectionX, sectionZ);
        MultiBlockGroup mbg = ILevelChunkExtension.getMultiBlockGroup(chunk);
        MultiBlockGroup.Groups groups = mbg.getGroupsNullableInSection(sectionY);
        if (groups == null) return;
        for (MultiBlockGroup.Group group : groups) {
            int a = group.getLowSection(), b = group.getHighSection();
            for (int y = a; y <= b; y++) {
                if (y == sectionY) continue;
                RenderSection section = this.getRenderSection(sectionX, y, sectionZ);
                if (section == null || !section.isBuilt()) continue;
                section.setPendingUpdate(ChunkUpdateType.REBUILD);
            }
        }
    }
}
