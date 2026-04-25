package com.havali.client.module;

import com.havali.client.module.impl.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new FpsHudModule());
        modules.add(new CpsHudModule());
        modules.add(new ArmorHudModule());
        modules.add(new KeystrokesModule());
        modules.add(new HitColorModule());
        modules.add(new FogModule());
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
}
