package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.ColorSetting;
import com.havali.client.module.settings.NumberSetting;

public class FogModule extends Module {
    public static NumberSetting density = new NumberSetting("Yogunluk", 10.0, 0.0, 100.0);
    public static ColorSetting color = new ColorSetting("Sis Rengi", 150, 0, 255);

    public FogModule() {
        super("Fog");
        addSettings(density, color);
    }
}
