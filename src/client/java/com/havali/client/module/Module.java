package com.havali.client.module;
public abstract class Module {
    private final String name;
    private boolean toggled;
    public Module(String name) {
        this.name = name;
        this.toggled = false;
    }
    public String getName() { return name; }
    public boolean isToggled() { return toggled; }
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) onEnable();
        else onDisable();
    }
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
}