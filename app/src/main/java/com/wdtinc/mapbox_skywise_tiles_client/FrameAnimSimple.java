package com.wdtinc.mapbox_skywise_tiles_client;


import android.os.Handler;
import android.util.Log;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Base implementation for a radar reflectivity animation loop.
 */
public class FrameAnimSimple implements IFrameAnim {

    private final List<MapFrame> frames;
    private int currFrameIndex;

    private final Handler mainHandler;
    private final MainActivity mainActivity;

    /** Time of first frame in milliseconds */
    private final long baseTimeMs;

    /** Time from first frame to last frame in milliseconds */
    private final long timeLineLenMs;

    /** Average frame time in milliseconds */
    private final long avgFrameTimeMs;

    /** Total time for the animation with start and end buffer times */
    private final long bufferedTimeLineLenMs;

    /** Animation time elapsed in milliseconds */
    private long timeElapsed;

    /** Prevent race conditions between simulation thread and main thread */
    private final Lock frameUpdateLock;

    /** Whether the animation is still valid; removed animations are not valid */
    private boolean valid;

    public FrameAnimSimple(Collection<MapFrame> mapFrames, Handler mainHandler, MainActivity mainActivity, MapboxMap mapboxMap) {

        // Guard vs empty
        if(mapFrames.isEmpty()) {
            throw new IllegalArgumentException("mapFrames must not be empty");
        }

        valid = true;
        frames = new ArrayList<>(mapFrames);
        this.mainHandler = mainHandler;
        this.mainActivity = mainActivity;
        currFrameIndex = 0;

        frameUpdateLock = new ReentrantLock(true);

        baseTimeMs = frames.get(0).getMeta().validTime.getTime();
        timeLineLenMs = frames.get(frames.size()-1).getMeta().validTime.getTime() - baseTimeMs;

        avgFrameTimeMs = timeLineLenMs / mapFrames.size();
        bufferedTimeLineLenMs = timeLineLenMs + (avgFrameTimeMs * 2L);

        timeElapsed = -avgFrameTimeMs;

        // Show first frame
        frames.get(0).setOpacity(mapboxMap, MapFrame.DEFAULT_OPACITY);

        // Hide all animation frames except for first
        for(int i = 1; i < frames.size(); ++i) {
            frames.get(i).hide(mapboxMap);
            frames.get(i).setOpacity(mapboxMap, 0f);
        }
    }

    public void showFrame(MapboxMap mapboxMap, int nextFrameIndex) {
        if(frames.size() < 2) {
            return;
        }

        try {
            frameUpdateLock.tryLock(200L, TimeUnit.MILLISECONDS);

            if(!valid) {
                return;
            }


            // Guard against duplicate call
            if(nextFrameIndex == currFrameIndex) {
                return;
            }


            // Hide current frame
            MapFrame currFrame = getCurrFrame();
            if(currFrame != null) {
                currFrame.hide(mapboxMap);
                currFrame.setOpacity(mapboxMap, 0f);
            }

            // Advance current frame
            ++currFrameIndex;
            currFrame = getCurrFrame();

            // Show current frame
            if(currFrame != null) {
//                currFrame.show(mapboxMap);
                currFrame.setOpacity(mapboxMap, MapFrame.DEFAULT_OPACITY);
            }

            // Prepare next frame
            MapFrame forwardFrame = getFwdFrame(1);
            if(forwardFrame != null) {
                forwardFrame.setOpacity(mapboxMap, 0f);
                forwardFrame.show(mapboxMap);
            }

//            mapboxMap.invalidate();

        } catch (InterruptedException ignored) {
            Log.e(FrameAnimSimple.class.getCanonicalName(), "Interrupted: " + ignored.getMessage());

        } finally { frameUpdateLock.unlock(); }
    }

    private MapFrame getCurrFrame() {

        // Guard: empty frames
        if(frames.isEmpty()) {
            return null;
        }

        if(currFrameIndex >= frames.size()) {
            currFrameIndex = 0;
        }

        return frames.get(currFrameIndex);
    }

    private MapFrame getFwdFrame(int count) {

        // Guard: Invalid count
        if(count < 1) {
            throw new IllegalArgumentException("count must be >= 1");
        }

        // Guard: empty frames
        if(frames.isEmpty() || frames.size() <= count) {
            return null;
        }

        return frames.get(getFwdFrameIndex(count));
    }

    private int getFwdFrameIndex() {
        return getFwdFrameIndex(1);
    }

    private int getFwdFrameIndex(int count) {

        // Guard: Invalid count
        if(count < 1) {
            throw new IllegalArgumentException("count must be >= 1");
        }

        return (currFrameIndex + count) % frames.size();
    }

    public void act(long delta) {
        timeElapsed += delta;

        try {
            frameUpdateLock.tryLock(200L, TimeUnit.MILLISECONDS);

            if(!valid) {
                return;
            }

            boolean advanceFrame = false;

            final int nextFrameIndex = getFwdFrameIndex();
            final MapFrame nextFrame = frames.get(nextFrameIndex);
            final long nextFrameTimeOffset = nextFrame.getMeta().validTime.getTime() - baseTimeMs;

            // Wrap around timeline case
            if(nextFrameIndex < currFrameIndex) {

                if(timeElapsed > timeLineLenMs + avgFrameTimeMs) {
                    timeElapsed -= bufferedTimeLineLenMs;
                    advanceFrame = true;
                }

            } else {

                if(timeElapsed >= nextFrameTimeOffset) {
                    advanceFrame = true;
                }
            }


            if(advanceFrame) {

                // Do work on main UI thread
                mainHandler.post(new ShowFrameRunnable(nextFrameIndex, mainActivity));
            }

        } catch (InterruptedException ignored) {
        } finally { frameUpdateLock.unlock(); }
    }

    @Override
    public void remove(MapboxMap mapboxMap) {
        if(!valid) {
            return;
        }

        for(MapFrame f : frames) {
            f.removeFrom(mapboxMap);
        }

        valid = false;
    }


    /**
     * Class lambda for running {@link IFrameAnim#showFrame(MapboxMap, int)}.
     */
    private static final class ShowFrameRunnable implements Runnable {
        private final int nextFrameIndex;
        private final MainActivity mainActivity;

        private ShowFrameRunnable(int nextFrameIndex, MainActivity mainActivity) {
            this.nextFrameIndex = nextFrameIndex;
            this.mainActivity = mainActivity;
        }

        @Override
        public void run() {
            mainActivity.advanceFrame(nextFrameIndex);
        }
    }

}
