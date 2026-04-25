package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.ColorSetting;

public class HitColorModule extends Module {
    public static ColorSetting color = new ColorSetting("Hasar Rengi", 0.0f, 1.0f, 1.0f);

    public HitColorModule() {
        super("HitColor");
        addSettings(color);
    }
}

