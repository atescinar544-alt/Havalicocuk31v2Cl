package com.havali.client.module;

import com.havali.client.module.impl.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Modülleri buraya kaydediyoruz
        modules.add(new FpsHudModule());
        modules.add(new CpsHudModule());
        modules.add(new MotionBlurModule());
        modules.add(new ArmorHudModule());
        // Diğerleri eklenecek...
    }

    public List<Module> getModules() {
        return modules;
    }
}
