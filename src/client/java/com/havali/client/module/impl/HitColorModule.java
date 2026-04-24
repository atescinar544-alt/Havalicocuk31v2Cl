package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.ColorSetting;

public class HitColorModule extends Module {
    public static ColorSetting color = new ColorSetting("Hasar Rengi", 255, 0, 0);

    public HitColorModule() {
        super("HitColor");
        addSettings(color);
    }
}
