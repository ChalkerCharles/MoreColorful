package com.ChalkerCharles.morecolorful.client.renderer.wavy;

import com.google.common.collect.Queues;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@OnlyIn(Dist.CLIENT)
public abstract class WavyDataUpdateDispatcher<T extends WavyDataTask<?>> {
    protected volatile boolean closed;
    protected final ProcessorMailbox<Runnable> mailbox;
    private final Executor executor;
    protected Vec3 camera = Vec3.ZERO;

    protected WavyDataUpdateDispatcher(Executor pExecutor) {
        this.executor = pExecutor;
        this.mailbox = ProcessorMailbox.create(pExecutor, "Wavy Data Updater");
        this.mailbox.tell(this::runTask);
    }

    public static Default create(Executor executor) {
        return new Default(executor);
    }

    public static Default ofDefault(WavyDataUpdateDispatcher<?> dispatcher) {
        return (Default) dispatcher;
    }

    protected void runTask() {
        if (this.closed) return;
        T task = this.pollTask();
        if (task == null) return;
        CompletableFuture.runAsync(() -> this.upload(task), this.executor)
                .whenComplete((unused, throwable) -> this.mailbox.tell(this::runTask));
    }

    @Nullable
    protected abstract T pollTask();

    public void setCamera(Vec3 pCamera) {
        this.camera = pCamera;
    }

    public void trySchedule(T task) {
        if (task.cancelled || task.isInvalid()) return;
        if (!task.isHighPriority) {
            boolean skip = task.skip;
            task.skip = !skip;
            if (skip) return;
        }
        this.schedule(task);
    }

    protected abstract void schedule(T task);

    public abstract void uploadAllPending();

    protected abstract void upload(T task);

    public abstract void clearBatchQueue();

    public void dispose() {
        this.closed = true;
        this.clearBatchQueue();
        this.uploadAllPending();
    }

    protected abstract int getPendingTaskCount();

    protected abstract int getUploadingTaskCount();

    public String getStats() {
        return String.format(Locale.ROOT, "pC: %03d, pU: %03d", this.getPendingTaskCount(), this.getUploadingTaskCount());
    }

    public static class Default extends WavyDataUpdateDispatcher<WavyDataTask.Default> {
        private final Queue<WavyDataTask.Default> highPriorityTasks = Queues.newPriorityBlockingQueue();
        private final Queue<WavyDataTask.Default> lowPriorityTasks = Queues.newLinkedBlockingDeque();
        private int highPriorityQuota = 2;
        protected final Queue<Runnable> toUpload = Queues.newConcurrentLinkedQueue();
        private int toUploadCount;

        private Default(Executor pExecutor) {
            super(pExecutor);
        }

        @Override
        @Nullable
        protected WavyDataTask.Default pollTask() {
            if (this.highPriorityQuota <= 0) {
                WavyDataTask.Default task = this.lowPriorityTasks.poll();
                if (task != null) {
                    this.highPriorityQuota = 2;
                    return task;
                }
            }
            WavyDataTask.Default task = this.highPriorityTasks.poll();
            if (task != null) {
                this.highPriorityQuota--;
                return task;
            } else {
                this.highPriorityQuota = 2;
                return this.lowPriorityTasks.poll();
            }
        }

        @Override
        protected void schedule(WavyDataTask.Default task) {
            if (this.closed) return;
            task.calculateDist(this.camera);
            this.mailbox.tell(() -> {
                if (!this.closed) {
                    if (task.isHighPriority) {
                        this.highPriorityTasks.offer(task);
                    } else {
                        this.lowPriorityTasks.offer(task);
                    }
                    this.runTask();
                }
            });
        }

        @Override
        public void uploadAllPending() {
            Runnable runnable;
            while ((runnable = toUpload.poll()) != null) {
                runnable.run();
            }
        }

        @Override
        protected void upload(WavyDataTask.Default task) {
            if (this.closed || task.cancelled) return;
            for (WavyVertices.Default vertices : task.vertices) {
                if (vertices.isInvalid()) continue;
                vertices.computeData();
                CompletableFuture.runAsync(vertices::update, this.toUpload::add);
            }
        }

        @Override
        public void clearBatchQueue() {
            while (!this.highPriorityTasks.isEmpty()) {
                WavyDataTask.Default task = this.highPriorityTasks.poll();
                if (task != null) {
                    task.cancel();
                }
            }
            while (!this.lowPriorityTasks.isEmpty()) {
                WavyDataTask.Default task = this.lowPriorityTasks.poll();
                if (task != null) {
                    task.cancel();
                }
            }
        }

        @Override
        protected int getPendingTaskCount() {
            return this.highPriorityTasks.size() + this.lowPriorityTasks.size();
        }

        @Override
        protected int getUploadingTaskCount() {
            int lastUploaded = this.toUploadCount;
            this.toUploadCount = this.toUpload.size();
            return (this.toUploadCount + lastUploaded) >> 1;
        }
    }
}
