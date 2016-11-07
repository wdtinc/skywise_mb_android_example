package com.wdtinc.mapbox_platform_client;


import com.mapbox.mapboxsdk.maps.MapboxMap;

/**
 * Empty case for frame animation.
 */
public final class FrameAnimEmpty implements IFrameAnim {

    @Override
    public void showFrame(MapboxMap mapboxMap, int nextFrameIndex) {}

    @Override
    public void act(long delta) {}

    @Override
    public void remove(MapboxMap mapboxMap) {}
}
