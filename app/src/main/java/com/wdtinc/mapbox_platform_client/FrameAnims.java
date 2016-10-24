package com.wdtinc.mapbox_platform_client;


import android.os.Handler;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.Collection;

/**
 * Factory class for creating frame animations.
 */
public final class FrameAnims {

    /**
     * @return an empty animation.
     */
    public static IFrameAnim create() {
        return new FrameAnimEmpty();
    }

    /**
     * @param mapFrames
     * @param mainHandler
     * @param mainActivity
     * @return animation with the provided frames
     */
    public static IFrameAnim create(Collection<MapFrame> mapFrames, Handler mainHandler, MainActivity mainActivity, MapboxMap mapboxMap) {
        return new FrameAnimSimple(mapFrames, mainHandler, mainActivity, mapboxMap);
    }
}
