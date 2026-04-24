package com.havali.client.gui;

import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import com.havali.client.module.settings.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class ClickGUI extends Screen {
    private Module selectedModule = null;

    public ClickGUI() { super(Text.literal("Havalicocuk31v2")); }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x70000000);
        int y = 40;
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            int color = mod.toggled ? 0x00FF00 : 0xFFFFFF;
            context.fill(50, y, 150, y + 14, 0x90202020);
            context.drawTextWithShadow(textRenderer, mod.name, 55, y + 3, color);
            y += 16;
        }

        if (selectedModule != null) {
            int sY = 40;
            context.fill(160, 30, 300, 200, 0x90101010);
            for (Setting s : selectedModule.settings) {
                context.drawTextWithShadow(textRenderer, s.name, 165, sY, 0xFFFFFF);
                if (s instanceof NumberSetting ns) {
                    context.drawTextWithShadow(textRenderer, String.valueOf(ns.getValue()), 260, sY, 0xAAAAAA);
                } else if (s instanceof BooleanSetting bs) {
                    context.drawTextWithShadow(textRenderer, bs.isEnabled() ? "ON" : "OFF", 260, sY, bs.isEnabled() ? 0x00FF00 : 0xFF0000);
                }
                sY += 15;
            }
        }
        
        context.fill(width - 100, height - 30, width - 10, height - 10, 0xFF5555FF);
        context.drawTextWithShadow(textRenderer, "HUD Ayarla", width - 85, height - 22, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = 40;
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            if (mouseX >= 50 && mouseX <= 150 && mouseY >= y && mouseY <= y + 14) {
                if (button == 0) mod.toggle();
                if (button == 1) selectedModule = mod;
                return true;
            }
            y += 16;
        }
        
        if (mouseX >= width - 100 && mouseX <= width - 10 && mouseY >= height - 30 && mouseY <= height - 10) {
            client.setScreen(new HudEditorScreen());
            return true;
        }

        if (selectedModule != null) {
            int sY = 40;
            for (Setting s : selectedModule.settings) {
                if (mouseX >= 160 && mouseX <= 300 && mouseY >= sY && mouseY <= sY + 12) {
                    if (s instanceof BooleanSetting bs) bs.setEnabled(!bs.isEnabled());
                    if (s instanceof NumberSetting ns) {
                        if (button == 0) ns.setValue(ns.getValue() + ns.increment);
                        else ns.setValue(ns.getValue() - ns.increment);
                    }
                }
                sY += 15;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
                            }
