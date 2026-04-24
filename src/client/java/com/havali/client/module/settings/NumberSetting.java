package com.havali.client.module.settings;

public class NumberSetting extends Setting {
    public double value, min, max, increment;
    public NumberSetting(String name, double value, double min, double max, double increment) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }
    public double getValue() { return value; }
    public float getValueFloat() { return (float) value; }
    public void setValue(double value) {
        double precision = 1.0 / increment;
        this.value = Math.round(Math.max(min, Math.min(max, value)) * precision) / precision;
    }
}
