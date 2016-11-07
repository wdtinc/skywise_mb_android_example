package com.wdtinc.mapbox_skywise_tiles_client;

import android.os.SystemClock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Advance simulation time forward using the system clock. Scales time advancement
 * using {@link #TIME_SCALE}. Calls {@link IFrameAnim#act(long)} on main activity's animation.
 */
public final class AnimTimerLoop implements Runnable {

    /** Simulation time step interval */
    private static final long MAX_WAIT_MS = 1000L / 120L;

    /** How much faster than real time */
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

    /**
     * Shutdown the animation timer.
     */
    public void shutdown() {
        isShutdown.set(true);
    }
}
