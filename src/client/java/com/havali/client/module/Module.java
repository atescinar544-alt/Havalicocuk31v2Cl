package com.havali.client.module;

import com.havali.client.module.settings.Setting;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    public String name;
    public boolean toggled;
    public int x = 10, y = 10;
    public List<Setting> settings = new ArrayList<>();

    public Module(String name) { this.name = name; }
    public void addSettings(Setting... settings) { this.settings.addAll(List.of(settings)); }
    public void toggle() {
        toggled = !toggled;
        if (toggled) onEnable(); else onDisable();
    }
    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public void onRender(DrawContext context, float tickDelta) {}
}
