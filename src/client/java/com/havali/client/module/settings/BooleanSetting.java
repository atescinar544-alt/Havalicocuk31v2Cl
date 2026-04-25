package com.havali.client.module.settings;

public class BooleanSetting extends Setting {
    public boolean enabled;
    
    public BooleanSetting(String name, boolean enabled) {
        super(name);
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
