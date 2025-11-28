package com.ChalkerCharles.morecolorful.client.compat;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataUpdateDispatcher;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegion;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11C;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class SodiumWavyDataDispatcher extends WavyDataUpdateDispatcher<SodiumWavyTask> {
    private final Queue<SodiumWavyTask> tasks = new ConcurrentLinkedQueue<>();
    private final Queue<SodiumWavyTask> toUpload = new ConcurrentLinkedQueue<>();
    private int toUploadCount;

    protected SodiumWavyDataDispatcher(Executor pExecutor) {
        super(pExecutor);
    }

    @Override
    @Nullable
    protected SodiumWavyTask pollTask() {
        return this.tasks.poll();
    }

    @Override
    protected void schedule(SodiumWavyTask task) {
        if (this.closed) return;
        task.setValid();
        task.calculateDist(this.camera);
        this.mailbox.tell(() -> {
            if (!this.closed) {
                this.tasks.offer(task);
                this.runTask();
            }
        });
    }

    @Override
    public void uploadAllPending() {
        List<SodiumWavyTask> list = this.collectWavyTasks();
        if (list.isEmpty()) return;
        uploadTasks(list);
    }

    @Override
    protected void upload(SodiumWavyTask task) {
        if (this.closed || task.cancelled) return;
        for (SodiumWavyVertices vertices : task.vertices) {
            if (vertices.isInvalid()) {
                vertices.invalid = true;
            } else {
                vertices.computeData();
            }
        }
        this.toUpload.offer(task);
    }

    @Override
    public void clearBatchQueue() {
        while (!this.tasks.isEmpty()) {
            SodiumWavyTask task = this.tasks.poll();
            if (task != null) {
                task.cancel();
            }
        }
    }

    @Override
    protected int getPendingTaskCount() {
        return this.tasks.size();
    }

    @Override
    protected int getUploadingTaskCount() {
        int lastUploaded = this.toUploadCount;
        this.toUploadCount = this.toUpload.size();
        return (this.toUploadCount + lastUploaded) >> 1;
    }

    private List<SodiumWavyTask> collectWavyTasks() {
        List<SodiumWavyTask> list = new ArrayList<>();
        for (SodiumWavyTask task;;) {
            task = this.toUpload.poll();
            if (task == null) break;
            if (task.section.isDisposed()) continue;
            list.add(task);
        }
        return list;
    }

    private static void uploadTasks(List<SodiumWavyTask> list) {
        var map = createUploadQueues(list);
        var iterator = map.reference2ReferenceEntrySet().fastIterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            SodiumWavyTask.update(entry.getKey(), entry.getValue());
        }
        GL11C.glFinish();
    }

    private static Reference2ReferenceOpenHashMap<RenderRegion, List<SodiumWavyTask>> createUploadQueues(List<SodiumWavyTask> tasks) {
        var map = new Reference2ReferenceOpenHashMap<RenderRegion, List<SodiumWavyTask>>();
        for (SodiumWavyTask task : tasks) {
            map.computeIfAbsent(task.section.getRegion(), k -> new ArrayList<>()).add(task);
        }
        return map;
    }
}
