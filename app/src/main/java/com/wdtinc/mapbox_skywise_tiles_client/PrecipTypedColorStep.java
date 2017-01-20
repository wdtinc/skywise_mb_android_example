package com.wdtinc.mapbox_skywise_tiles_client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Defines value range and associated fill color.
 */
public final class PrecipTypedColorStep {

    private static final HashSet<PrecipType> ALL_SET = new HashSet<>(4);
    private static final HashSet<PrecipType> RAIN_SET = new HashSet<>(1);
    private static final HashSet<PrecipType> FREEZING_SET = new HashSet<>(3);
    private static final HashSet<PrecipType> SNOW_SET = new HashSet<>(1);
    static {
        RAIN_SET.add(PrecipType.rain);
        FREEZING_SET.add(PrecipType.freezing_rain);
        FREEZING_SET.add(PrecipType.sleet);
        SNOW_SET.add(PrecipType.snow);

        ALL_SET.add(PrecipType.rain);
        ALL_SET.add(PrecipType.freezing_rain);
        ALL_SET.add(PrecipType.sleet);
        ALL_SET.add(PrecipType.snow);
    }

    /** Default color steps for radar reflectivity */
    public static final List<PrecipTypedColorStep> DEFAULT_DBZ_COLOR_STEPS = Arrays.asList(

            new PrecipTypedColorStep(0f, 5f, "rgba(0, 236, 236, 50)", ALL_SET),
            new PrecipTypedColorStep(5f, 10f, "rgba(0, 236, 236, 100)", ALL_SET),
            new PrecipTypedColorStep(10f, 14f, "rgba(0, 211, 239, 255)", ALL_SET),
            new PrecipTypedColorStep(14f, 16f, "rgba(0, 185, 243, 255)", ALL_SET),
            new PrecipTypedColorStep(16f, 18f, "rgba(0, 107, 246, 255)", ALL_SET),
            new PrecipTypedColorStep(18f, 20f, "rgba(0, 53, 246, 255)", ALL_SET),
            new PrecipTypedColorStep(20f, 22f, "rgba(0, 85, 164, 255)", ALL_SET),
            new PrecipTypedColorStep(22f, 24f, "rgba(0, 191, 62, 255)", ALL_SET),
            new PrecipTypedColorStep(24f, 26f, "rgba(0, 246, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(26f, 28f, "rgba(0, 255, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(28f, 30f, "rgba(0, 229, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(30f, 32f, "rgba(0, 186, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(32f, 34f, "rgba(0, 158, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(34f, 36f, "rgba(43, 162, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(36f, 38f, "rgba(128, 199, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(38f, 40f, "rgba(212, 237, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(40f, 42f, "rgba(249, 239, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(42f, 44f, "rgba(237, 208, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(44f, 46f, "rgba(235, 184, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(46f, 48f, "rgba(243, 168, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(48f, 50f, "rgba(251, 152, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(50f, 52f, "rgba(255, 108, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(52f, 54f, "rgba(255, 36, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(54f, 56f, "rgba(245, 0, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(56f, 58f, "rgba(234, 0, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(58f, 60f, "rgba(224, 0, 0, 255)", ALL_SET),
            new PrecipTypedColorStep(60f, 62f, "rgba(209, 0, 64, 255)", ALL_SET),
            new PrecipTypedColorStep(62f, 64f, "rgba(199, 0, 170, 255)", ALL_SET),
            new PrecipTypedColorStep(64f, 66f, "rgba(202, 0, 255, 255)", ALL_SET),
            new PrecipTypedColorStep(66f, 68f, "rgba(224, 0, 255, 255)", ALL_SET),
            new PrecipTypedColorStep(68f, 70f, "rgba(244, 0, 255, 255)", ALL_SET),
            new PrecipTypedColorStep(70f, 200f, "rgba(255, 255, 255, 255)", ALL_SET)
    );

    /** Default color steps for radar reflectivity with precip-typed colors */
    public static final List<PrecipTypedColorStep> DEFAULT_PRECIP_TYPED_COLOR_STEPS = Arrays.asList(

            new PrecipTypedColorStep(0f, 5f, "rgba(0, 238, 0, 50)", RAIN_SET),
            new PrecipTypedColorStep(5f, 10f, "rgba(0, 238, 0, 100)", RAIN_SET),
            new PrecipTypedColorStep(10f, 15f, "rgba(0, 221, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(15f, 20f, "rgba(0, 204, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(20f, 25f, "rgba(0, 170, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(25f, 30f, "rgba(0, 153, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(30f, 35f, "rgba(0, 102, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(35f, 40f, "rgba(200, 222, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(40f, 45f, "rgba(255, 255, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(45f, 50f, "rgba(255, 170, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(50f, 55f, "rgba(255, 0, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(55f, 60f, "rgba(204, 0, 0, 255)", RAIN_SET),
            new PrecipTypedColorStep(65f, 70f, "rgba(255, 0, 255, 255)", RAIN_SET),
            new PrecipTypedColorStep(70f, 75f, "rgba(255, 153, 255, 255)", RAIN_SET),
            new PrecipTypedColorStep(75f, 90f, "rgba(255, 136, 255, 255)", RAIN_SET),
            new PrecipTypedColorStep(95f, 200f, "rgba(255, 197, 255, 255)", RAIN_SET),

            new PrecipTypedColorStep(0f, 5f, "rgba(229, 182, 172, 50)", FREEZING_SET),
            new PrecipTypedColorStep(5f, 10f, "rgba(229, 182, 172, 100)", FREEZING_SET),
            new PrecipTypedColorStep(10f, 15f, "rgba(229, 149, 138, 255)", FREEZING_SET),
            new PrecipTypedColorStep(15f, 20f, "rgba(229, 115, 103, 255)", FREEZING_SET),
            new PrecipTypedColorStep(20f, 25f, "rgba(229, 82, 69, 255)", FREEZING_SET),
            new PrecipTypedColorStep(25f, 30f, "rgba(210, 77, 67, 255)", FREEZING_SET),
            new PrecipTypedColorStep(30f, 35f, "rgba(191, 72, 65, 255)", FREEZING_SET),
            new PrecipTypedColorStep(35f, 40f, "rgba(172, 66, 63, 255)", FREEZING_SET),
            new PrecipTypedColorStep(40f, 45f, "rgba(153, 61, 61, 255)", FREEZING_SET),
            new PrecipTypedColorStep(45f, 50f, "rgba(140, 59, 61, 255)", FREEZING_SET),
            new PrecipTypedColorStep(50f, 55f, "rgba(128, 56, 60, 255)", FREEZING_SET),
            new PrecipTypedColorStep(55f, 60f, "rgba(115, 54, 60, 255)", FREEZING_SET),
            new PrecipTypedColorStep(60f, 65f, "rgba(102, 51, 59, 255)", FREEZING_SET),
            new PrecipTypedColorStep(65f, 70f, "rgba(68, 34, 39, 255)", FREEZING_SET),
            new PrecipTypedColorStep(70f, 200f, "rgba(34, 17, 20, 255)", FREEZING_SET),

            new PrecipTypedColorStep(0f, 5f, "rgba(0, 255, 255, 50)", SNOW_SET),
            new PrecipTypedColorStep(5f, 10f, "rgba(0, 255, 255, 100)", SNOW_SET),
            new PrecipTypedColorStep(10f, 15f, "rgba(0, 213, 255, 255)", SNOW_SET),
            new PrecipTypedColorStep(15f, 20f, "rgba(0, 170, 255, 255)", SNOW_SET),
            new PrecipTypedColorStep(20f, 25f, "rgba(0, 128, 255, 255)", SNOW_SET),
            new PrecipTypedColorStep(25f, 30f, "rgba(0, 85, 255, 255)", SNOW_SET),
            new PrecipTypedColorStep(30f, 35f, "rgba(0, 42, 255, 255)", SNOW_SET),
            new PrecipTypedColorStep(35f, 40f, "rgba(0, 0, 255, 255)", SNOW_SET),
            new PrecipTypedColorStep(40f, 50f, "rgba(0, 0, 200, 255)", SNOW_SET),
            new PrecipTypedColorStep(50f, 60f, "rgba(0, 0, 150, 255)", SNOW_SET),
            new PrecipTypedColorStep(60f, 70f, "rgba(0, 0, 100, 255)", SNOW_SET),
            new PrecipTypedColorStep(70f, 200f, "rgba(0, 0, 50, 255)", SNOW_SET)
    );

    /** Default debug color steps for precip type display */
    public static final List<PrecipTypedColorStep> DEFAULT_DEBUG_PRECIP_TYPED_COLOR_STEPS = Arrays.asList(
            new PrecipTypedColorStep(0f, 200f, "rgba(255, 255, 255, 50)", ALL_SET)
//            ,
//            new PrecipTypedColorStep(0f, 200f, "rgba(0, 255, 0, 255)", RAIN_SET),
//            new PrecipTypedColorStep(0f, 200f, "rgba(255, 0, 0, 255)", FREEZING_SET),
//            new PrecipTypedColorStep(0f, 200f, "rgba(0, 0, 255, 255)", SNOW_SET)
    );

    public final float min;
    public final float max;
    public final String rgbaColor;
    public final Set<PrecipType> pTypes;

    public PrecipTypedColorStep(float min, float max, String rgbaColor, Set<PrecipType> pTypes) {
        this.min = min;
        this.max = max;
        this.rgbaColor = rgbaColor;
        this.pTypes = pTypes;
    }
}
