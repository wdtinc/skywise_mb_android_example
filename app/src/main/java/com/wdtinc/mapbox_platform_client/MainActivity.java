package com.wdtinc.mapbox_platform_client;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private static final String MAPBOX_API_KEY = "";
    private static final String PLATFORM_HOST = "platform-api.dev.us-east-1.wdtinc.com";
    private static final String PLATFORM_PORT = "80";
    private static final String PLATFORM_USERNAME = "";
    private static final String PLATFORM_PW = "";


    private MapView mapView;
    public IFrameAnim frameAnim;
    private MapboxMap mapboxMap;
    private AnimTimerLoop animTimerLoop;
    private Lock advanceFrameLock;
    private boolean stopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token only needs to be configured once in your app
        MapboxAccountManager.start(this, MAPBOX_API_KEY);

        // This contains the MapView in XML and needs to be called after the account manager
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setStyleUrl("mapbox://styles/mapbox/dark-v9");
        mapView.onCreate(savedInstanceState);

        advanceFrameLock = new ReentrantLock(true);

        frameAnim = FrameAnims.create();

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.getUiSettings().setTiltGesturesEnabled(false);
                mapboxMap.getUiSettings().setRotateGesturesEnabled(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final MainActivity self = this;

        PlatformClient platformClient = new PlatformClient("http://" + PLATFORM_HOST,
                getApplicationContext(), PLATFORM_USERNAME, PLATFORM_PW);
        platformClient.getAnalysisFrames(12, new PlatformClient.FramesCallback() {
            @Override
            public void onResult(List<MapFrame.Meta> frameMetas) {
                self.resetAnimLoopTo(frameMetas);
            }
        });

        // Animation timer
        if(self.animTimerLoop == null) {
            self.animTimerLoop = new AnimTimerLoop(self);
            new Thread(self.animTimerLoop, "Anim Timer").start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            advanceFrameLock.tryLock(1L, TimeUnit.SECONDS);
            stopped = true;
        } catch (InterruptedException ignored) {
        } finally {
            advanceFrameLock.unlock();
        }

        if(animTimerLoop != null) {
            animTimerLoop.shutdown();
            animTimerLoop = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void advanceFrame(int nextFrameIndex) {
        if(mapboxMap == null || mapView == null) {
            return;
        }

        // Avoid race condition for advancing frame and activity being stopped
        try {
            if(advanceFrameLock.tryLock(1L, TimeUnit.SECONDS) && !stopped) {
                frameAnim.showFrame(mapboxMap, nextFrameIndex);
            }
        } catch (InterruptedException ignored) {
        } finally {
            advanceFrameLock.unlock();
        }
    }

    public void resetAnimLoopTo(final Collection<MapFrame.Meta> framesMeta) {
        final MainActivity self = this;
        final Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                self.mapboxMap = mapboxMap;

                // Remove / clear old animation
                self.frameAnim.remove(mapboxMap);

                // Build map frames from metadata
                List<MapFrame> mapFrames = new ArrayList<>(framesMeta.size());
                for(MapFrame.Meta fm : framesMeta) {
                    mapFrames.add(MapFrame.createMapFrame(mapboxMap, PLATFORM_HOST, PLATFORM_PORT, fm));
                }

                // Set built frames as new animation
                self.frameAnim = FrameAnims.create(mapFrames, mainHandler, self, mapboxMap);
            }
        });
    }
}
