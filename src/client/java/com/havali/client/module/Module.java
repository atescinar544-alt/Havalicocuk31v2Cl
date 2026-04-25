package com.havali.client.module;

import com.havali.client.module.settings.NumberSetting;
import com.havali.client.module.settings.Setting;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module {
    public String name;
    public boolean toggled;
    public int x = 10;
    public int y = 10;
    public NumberSetting scale = new NumberSetting("Boyut", 1.0, 0.5, 3.0);
    public List<Setting> settings = new ArrayList<>();

    public Module(String name) {
        this.name = name;
        this.settings.add(scale);
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public void onRender(DrawContext context, float tickDelta) {}
}
