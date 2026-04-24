package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.NumberSetting;

public class HitColorModule extends Module {
    public static NumberSetting red = new NumberSetting("Red", 1.0, 0.0, 1.0, 0.1);
    public static NumberSetting green = new NumberSetting("Green", 0.0, 0.0, 1.0, 0.1);
    public static NumberSetting blue = new NumberSetting("Blue", 0.0, 0.0, 1.0, 0.1);

    public HitColorModule() {
        super("HitColor");
        addSettings(red, green, blue);
    }
}
