package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.NumberSetting;

public class ViewmodelModule extends Module {
    public static NumberSetting posX = new NumberSetting("PosX", 0.0, -2.0, 2.0, 0.1);
    public static NumberSetting posY = new NumberSetting("PosY", 0.0, -2.0, 2.0, 0.1);
    public static NumberSetting posZ = new NumberSetting("PosZ", 0.0, -2.0, 2.0, 0.1);
    public static NumberSetting scale = new NumberSetting("Scale", 1.0, 0.1, 2.0, 0.1);

    public ViewmodelModule() {
        super("Viewmodel");
        addSettings(posX, posY, posZ, scale);
    }
}
