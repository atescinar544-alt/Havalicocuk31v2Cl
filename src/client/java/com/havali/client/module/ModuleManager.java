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
        modules.add(new MotionBlurModule());
        modules.add(new ArmorHudModule());
        modules.add(new ColorSaturationModule());
        modules.add(new HitColorModule());
        modules.add(new ViewmodelChangerModule());
    }
    public List<Module> getModules() {
        return modules;
    }
}