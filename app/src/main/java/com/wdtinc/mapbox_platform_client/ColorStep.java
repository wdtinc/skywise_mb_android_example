package com.wdtinc.mapbox_platform_client;

import java.util.Arrays;
import java.util.List;

public final class ColorStep {

    public static final List<ColorStep> DEFAULT_COLOR_STEPS = Arrays.asList(
            new ColorStep(0f, 5f, "rgba(0, 236, 236, 0)"),
            new ColorStep(5f, 10f, "rgba(0, 236, 236, 15)"),
            new ColorStep(12f, 14f, "rgba(0, 211, 239, 255)"),
            new ColorStep(14f, 16f, "rgba(0, 185, 243, 255)"),
            new ColorStep(16f, 18f, "rgba(0, 107, 246, 255)"),
            new ColorStep(18f, 20f, "rgba(0, 53, 246, 255)"),
            new ColorStep(20f, 22f, "rgba(0, 85, 164, 255)"),
            new ColorStep(22f, 24f, "rgba(0, 191, 62, 255)"),
            new ColorStep(24f, 26f, "rgba(0, 246, 0, 255)"),
            new ColorStep(26f, 28f, "rgba(0, 246, 0, 255)"),
            new ColorStep(28f, 30f, "rgba(0, 209, 0, 255)"),
            new ColorStep(30f, 32f, "rgba(0, 186, 0, 255)"),
            new ColorStep(32f, 34f, "rgba(0, 158, 0, 255)"),
            new ColorStep(34f, 36f, "rgba(43, 162, 0, 255)"),
            new ColorStep(36f, 38f, "rgba(128, 199, 0, 255)"),
            new ColorStep(38f, 40f, "rgba(212, 237, 0, 255)"),
            new ColorStep(40f, 42f, "rgba(249, 239, 0, 255)"),
            new ColorStep(42f, 44f, "rgba(237, 208, 0, 255)"),
            new ColorStep(44f, 46f, "rgba(235, 184, 0, 255)"),
            new ColorStep(46f, 48f, "rgba(243, 168, 0, 255)"),
            new ColorStep(48f, 50f, "rgba(251, 152, 0, 255)"),
            new ColorStep(50f, 52f, "rgba(255, 108, 0, 255)"),
            new ColorStep(52f, 54f, "rgba(255, 36, 0, 255)"),
            new ColorStep(54f, 56f, "rgba(245, 0, 0, 255)"),
            new ColorStep(56f, 58f, "rgba(234, 0, 0, 255)"),
            new ColorStep(58f, 60f, "rgba(224, 0, 0, 255)"),
            new ColorStep(60f, 62f, "rgba(209, 0, 64, 255)"),
            new ColorStep(62f, 64f, "rgba(199, 0, 170, 255)"),
            new ColorStep(64f, 66f, "rgba(202, 0, 255, 255)"),
            new ColorStep(66f, 68f, "rgba(224, 0, 255, 255)"),
            new ColorStep(68f, 70f, "rgba(244, 0, 255, 255)"),
            new ColorStep(70f, 200f, "rgba(255, 255, 255, 255)")
    );


    public final float min;
    public final float max;
    public final String rgbaColor;

    public ColorStep(float min, float max, String rgbaColor) {
        this.min = min;
        this.max = max;
        this.rgbaColor = rgbaColor;
    }
}
