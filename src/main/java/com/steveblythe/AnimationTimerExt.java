package com.steveblythe;

import javafx.animation.AnimationTimer;

public abstract class AnimationTimerExt extends AnimationTimer {
    private long sleepNanoSeconds;
    private long previousTime;

    public AnimationTimerExt(long sleepMs) {
        this.sleepNanoSeconds = sleepMs * 1_000_000;
    }

    @Override
    public void handle(long now) {
        if ((now - previousTime) < sleepNanoSeconds) {
            return;
        }
        previousTime = now;

        handle();
    }

    public abstract void handle();
}
