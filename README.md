
# SkyWise Tiles Integration with Mapbox on Android

![Vector Tile Radar Loop](imgs/mvt_reflect_base.png?raw=true "Vector Tile Radar Loop")

This project contains example code for integrating the *BETA* 'v2 SkyWise Tiles API' with the 
Mapbox Native SDK for Android. The project will get a list of the latest contoured radar mosaic 
frames and animate them in the Mapbox widget.

**A Mapbox API Key and WDT SkyWise Tiles credentials are required.**

### BETA Disclaimer

The API used in this demo is in BETA and may change in the future.

### Prerequisites

- WDT SkyWise Tiles Credentials
- Mapbox API Key
- [Android Studio](https://developer.android.com/studio/index.html) or other IDE
- Android Device with 'Ice Cream Sandwich' (API 15+)

### Setup

1) Open the file: [MainActivity.java](app/src/main/java/com/wdtinc/mapbox_skywise_tiles_client/MainActivity.java)

2) Modify the constants to include your API keys:

```java
private static final String MAPBOX_API_KEY = "";
private static final String SKYWISE_TILES_HOST = "skywise-tiles.api.wdtinc.com";
private static final String SKYWISE_TILES_PORT = "80";
private static final String SKYWISE_TILES_USERNAME = "";
private static final String SKYWISE_TILES_PW = "";
```

3) Use [Android Studio](https://developer.android.com/studio/index.html) to launch the application on your Android Device. 

NOTE 1: See the [Mapbox Android SDK Github](https://github.com/mapbox/mapbox-gl-native/tree/master/platform/android) for details on emulator support. Often works best on physical devices.

NOTE 2: This example code uses a beta snapshot of the Mapbox Android SDK for latest fixes and the dynamic styling API.

# Links

- [WDT SkyWise Tiles API Overview - v2 BETA](http://docs.api.wdtinc.com/skywise-tiles/en/2.0/)
- [Mapbox Android SDK](https://www.mapbox.com/android-sdk/)
- [Mapbox Android SDK Github](https://github.com/mapbox/mapbox-gl-native/tree/master/platform/android)
