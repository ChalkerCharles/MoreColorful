package com.ChalkerCharles.morecolorful.util;

public record StagedTask(boolean isPre, Runnable task) implements Runnable {
    public boolean isPost() {
        return !this.isPre;
    }

    @Override
    public void run() {
        this.task.run();
    }
}
