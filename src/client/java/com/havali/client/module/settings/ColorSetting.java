package com.havali.client.module.settings;

public class ColorSetting extends Setting {
    public int r, g, b;
    public boolean expanded = false;

    public ColorSetting(String name, int r, int g, int b) {
        super(name);
        this.r = r; this.g = g; this.b = b;
    }

    public int getColor() {
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }
}
