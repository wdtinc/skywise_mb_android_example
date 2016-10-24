package com.wdtinc.mapbox_platform_client;

import android.os.SystemClock;

import java.util.concurrent.atomic.AtomicBoolean;

public final class AnimTimerLoop implements Runnable {

    private static final long MAX_WAIT_MS = 1000L / 120L;
    private static final long TIME_SCALE = 1_000L;


    private final AtomicBoolean isShutdown;
    private MainActivity mainActivity;

    public AnimTimerLoop(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.isShutdown = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        long et0, etf, etDelta, etDeltaScaled;

        //noinspection InfiniteLoopStatement
        while(!isShutdown.get()) {
            et0 = SystemClock.elapsedRealtime();

            SystemClock.sleep(MAX_WAIT_MS);

            // Compute elapsed time delta in milliseconds
            etf = SystemClock.elapsedRealtime();
            etDelta = etf - et0;
            etDeltaScaled = etDelta * TIME_SCALE;

            mainActivity.frameAnim.act(etDeltaScaled);
        }
    }

    public void shutdown() {
        isShutdown.set(true);
    }
}
