
# SkyWise Tiles Integration with Mapbox on Android

![Vector Tile Radar Loop](imgs/mvt_reflect_base.png?raw=true "Vector Tile Radar Loop")

This project contains example code for integrating the *BETA* 'v2 SkyWise Tiles API' with the 
Mapbox Native SDK for Android. The project will get a list of the latest contoured radar mosaic 
frames and animate them in the Mapbox widget.

**A Mapbox API Key and WDT SkyWise Tiles credentials are required.**

Open the file:

```java
com.wdtinc.mapbox_platform_client.MainActivity.java
```

Modify the constants to include your API keys:

```java
private static final String MAPBOX_API_KEY = "";
private static final String PLATFORM_HOST = "skywise-tiles.api.wdtinc.com";
private static final String PLATFORM_PORT = "80";
private static final String PLATFORM_USERNAME = "";
private static final String PLATFORM_PW = "";
```

Run the application on your Android Device.

# Links

[WDT Platform API Overview - v2 BETA](http://docs.api.wdtinc.com/skywise-tiles/en/2.0/)
[Mapbox Android SDK](https://www.mapbox.com/android-sdk/)
[Mapbox Android SDK Github](https://github.com/mapbox/mapbox-gl-native/tree/master/platform/android)
