package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.NumberSetting;

public class FogModule extends Module {
    public static NumberSetting density = new NumberSetting("Density", 0.5, 0.1, 1.0, 0.05);
    public static NumberSetting red = new NumberSetting("Red", 1.0, 0.0, 1.0, 0.01);
    public static NumberSetting green = new NumberSetting("Green", 1.0, 0.0, 1.0, 0.01);
    public static NumberSetting blue = new NumberSetting("Blue", 1.0, 0.0, 1.0, 0.01);

    public FogModule() {
        super("Fog");
        addSettings(density, red, green, blue);
    }
}
