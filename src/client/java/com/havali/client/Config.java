package com.havali.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import com.havali.client.module.settings.BooleanSetting;
import com.havali.client.module.settings.ColorSetting;
import com.havali.client.module.settings.NumberSetting;
import com.havali.client.module.settings.Setting;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "havaliclient.json");

    public static void save() {
        try {
            JsonObject json = new JsonObject();
            for (Module m : ModuleManager.INSTANCE.getModules()) {
                JsonObject mJ = new JsonObject();
                mJ.addProperty("toggled", m.toggled);
                mJ.addProperty("x", m.x);
                mJ.addProperty("y", m.y);
                JsonObject sJ = new JsonObject();
                for (Setting s : m.settings) {
                    if (s instanceof BooleanSetting bs) {
                        sJ.addProperty(s.name, bs.enabled);
                    } else if (s instanceof NumberSetting ns) {
                        sJ.addProperty(s.name, ns.value);
                    } else if (s instanceof ColorSetting cs) {
                        JsonObject cJ = new JsonObject();
                        cJ.addProperty("h", cs.hue);
                        cJ.addProperty("s", cs.saturation);
                        cJ.addProperty("b", cs.brightness);
                        sJ.add(s.name, cJ);
                    }
                }
                mJ.add("settings", sJ);
                json.add(m.name, mJ);
            }
            FileWriter w = new FileWriter(FILE);
            GSON.toJson(json, w);
            w.close();
        } catch (Exception ignored) {}
    }

    public static void load() {
        try {
            if (!FILE.exists()) return;
            JsonObject json = JsonParser.parseReader(new FileReader(FILE)).getAsJsonObject();
            for (Module m : ModuleManager.INSTANCE.getModules()) {
                if (json.has(m.name)) {
                    JsonObject mJ = json.getAsJsonObject(m.name);
                    m.toggled = mJ.get("toggled").getAsBoolean();
                    m.x = mJ.get("x").getAsInt();
                    m.y = mJ.get("y").getAsInt();
                    JsonObject sJ = mJ.getAsJsonObject("settings");
                    for (Setting s : m.settings) {
                        if (sJ.has(s.name)) {
                            if (s instanceof BooleanSetting bs) {
                                bs.enabled = sJ.get(s.name).getAsBoolean();
                            } else if (s instanceof NumberSetting ns) {
                                ns.value = sJ.get(s.name).getAsDouble();
                            } else if (s instanceof ColorSetting cs) {
                                JsonObject cJ = sJ.getAsJsonObject(s.name);
                                cs.hue = cJ.get("h").getAsFloat();
                                cs.saturation = cJ.get("s").getAsFloat();
                                cs.brightness = cJ.get("b").getAsFloat();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
