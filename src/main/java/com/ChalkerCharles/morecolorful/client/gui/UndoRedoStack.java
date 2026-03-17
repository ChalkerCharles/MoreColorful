package com.ChalkerCharles.morecolorful.client.gui;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class UndoRedoStack<T> {
    private static final int MAX_STEP = 64;
    private final Deque<T> undoStack = new ArrayDeque<>();
    private final Deque<T> redoStack = new ArrayDeque<>();
    private final Consumer<T> callback;
    private boolean changed;
    private boolean stepEnded = true;

    public UndoRedoStack(Consumer<T> callback) {
        this.callback = callback;
    }

    private void undo() {
        if (this.undoStack.size() > 1) {
            T t = this.undoStack.pollFirst();
            this.redoStack.offerFirst(t);
            T t1 = this.undoStack.peekFirst();
            this.callback.accept(t1);
        }
    }

    private void redo() {
        T t = this.redoStack.pollFirst();
        if (t != null) {
            if (this.undoStack.size() >= MAX_STEP) {
                this.undoStack.pollLast();
            }
            this.undoStack.offerFirst(t);
            this.callback.accept(t);
        }
    }

    public void checkNewOperation(Supplier<T> supplier) {
        if (this.changed && this.stepEnded) {
            this.redoStack.clear();
            if (this.undoStack.size() > MAX_STEP) {
                this.undoStack.pollLast();
            }
            this.undoStack.offerFirst(supplier.get());
            this.changed = false;
            this.stepEnded = false;
        }
    }

    public void changed() {
        this.changed = true;
    }

    public void endStep() {
        this.stepEnded = true;
    }

    public void clear() {
        this.undoStack.clear();
        this.redoStack.clear();
    }

    public void setInitialState(T t) {
        this.undoStack.offerFirst(t);
    }

    public boolean keyPressed(int keyCode) {
        if (isUndo(keyCode)) {
            this.undo();
            return true;
        } else if (isRedo(keyCode)) {
            this.redo();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUndo(int keyCode) {
        return keyCode == GLFW.GLFW_KEY_Z && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static boolean isRedo(int keyCode) {
        return keyCode == GLFW.GLFW_KEY_Y && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }
}
