package com.havali.client.module.settings;

public class NumberSetting extends Setting {
    public double value, min, max;
    
    public NumberSetting(String name, double value, double min, double max) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
    }
    
    public float getValueFloat() { return (float) value; }
    
    public void setValue(double value) {
        this.value = Math.max(min, Math.min(max, value));
    }
}
