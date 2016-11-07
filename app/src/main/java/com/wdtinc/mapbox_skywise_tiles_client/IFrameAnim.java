package com.wdtinc.mapbox_skywise_tiles_client;


import com.mapbox.mapboxsdk.maps.MapboxMap;

public interface IFrameAnim {

    /**
     * Run animation for the provided milliseconds.
     *
     * @param delta simulation time change in milliseconds
     */
    void act(long delta);

    /**
     * Show a frame index on the map.
     *
     * @param mapboxMap map containing animation frames
     * @param nextFrameIndex frame index to show
     */
    void showFrame(MapboxMap mapboxMap, int nextFrameIndex);

    /**
     * Remove animation from map. It is not longer considered valid.
     *
     * @param mapboxMap remove animation from this map
     */
    void remove(MapboxMap mapboxMap);
}
