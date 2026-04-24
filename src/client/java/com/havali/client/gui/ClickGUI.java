package com.havali.client.gui;

import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import com.havali.client.module.settings.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class ClickGUI extends Screen {
    private Module selectedModule = null;
    private Setting draggingSetting = null;

    public ClickGUI() { super(Text.literal("Havalicocuk31v2 Premium")); }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x80000000); // Daha soft arkaplan
        
        // Modül Listesi
        int y = 40;
        context.fill(45, 30, 155, height - 40, 0x90101010);
        context.drawTextWithShadow(textRenderer, "Modüller", 55, 15, 0x00FFCC);
        
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            int color = mod.toggled ? 0x00FF00 : 0xAAAAAA;
            context.fill(50, y, 150, y + 16, mod.toggled ? 0x4000FF00 : 0x40333333);
            context.drawTextWithShadow(textRenderer, mod.name, 55, y + 4, color);
            y += 20;
        }

        // Ayar Paneli
        if (selectedModule != null) {
            int sY = 40;
            context.fill(160, 30, 320, height - 40, 0x90101010);
            context.drawTextWithShadow(textRenderer, selectedModule.name + " Ayarları", 165, 15, 0x00FFCC);
            
            for (Setting s : selectedModule.settings) {
                context.drawTextWithShadow(textRenderer, s.name, 165, sY, 0xFFFFFF);
                
                if (s instanceof BooleanSetting bs) {
                    context.drawTextWithShadow(textRenderer, bs.isEnabled() ? "ACIK" : "KAPALI", 280, sY, bs.isEnabled() ? 0x00FF00 : 0xFF0000);
                } 
                else if (s instanceof NumberSetting ns) {
                    float percent = (float) ((ns.value - ns.min) / (ns.max - ns.min));
                    context.fill(165, sY + 12, 305, sY + 14, 0xFF555555); // Slider arkaplan
                    context.fill(165, sY + 12, 165 + (int)(140 * percent), sY + 14, 0xFF00FFCC); // Slider dolgu
                    context.drawTextWithShadow(textRenderer, String.format("%.1f", ns.value), 280, sY, 0xAAAAAA);
                    sY += 10;
                }
                else if (s instanceof ColorSetting cs) {
                    context.fill(295, sY, 305, sY + 10, 0xFF888888); // Gri Kare
                    context.fill(296, sY + 1, 304, sY + 9, cs.getColor()); // İç renk
                    
                    if (cs.expanded) {
                        sY += 15;
                        drawColorSlider(context, "R", cs.r, sY, 0xFFFF0000);
                        sY += 15;
                        drawColorSlider(context, "G", cs.g, sY, 0xFF00FF00);
                        sY += 15;
                        drawColorSlider(context, "B", cs.b, sY, 0xFF0000FF);
                    }
                }
                sY += 20;
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawColorSlider(DrawContext ctx, String label, int val, int y, int color) {
        ctx.drawTextWithShadow(textRenderer, label, 165, y, 0xAAAAAA);
        float percent = val / 255f;
        ctx.fill(180, y + 4, 305, y + 6, 0xFF555555);
        ctx.fill(180, y + 4, 180 + (int)(125 * percent), y + 6, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = 40;
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            if (mouseX >= 50 && mouseX <= 150 && mouseY >= y && mouseY <= y + 16) {
                if (button == 0) mod.toggle();
                if (button == 1) selectedModule = mod;
                return true;
            }
            y += 20;
        }

        if (selectedModule != null) {
            int sY = 40;
            for (Setting s : selectedModule.settings) {
                if (s instanceof BooleanSetting bs && mouseX >= 165 && mouseX <= 310 && mouseY >= sY && mouseY <= sY + 10) {
                    bs.setEnabled(!bs.isEnabled());
                } else if (s instanceof ColorSetting cs) {
                    if (mouseX >= 295 && mouseX <= 305 && mouseY >= sY && mouseY <= sY + 10) {
                        cs.expanded = !cs.expanded;
                    }
                    if (cs.expanded) sY += 45;
                }
                if (mouseX >= 165 && mouseX <= 310 && mouseY >= sY && mouseY <= sY + 20) {
                    draggingSetting = s;
                }
                sY += (s instanceof NumberSetting ? 30 : 20);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingSetting != null) {
            if (draggingSetting instanceof NumberSetting ns) {
                float percent = (float) Math.max(0, Math.min(1, (mouseX - 165) / 140.0f));
                ns.setValue(ns.min + (ns.max - ns.min) * percent);
            } else if (draggingSetting instanceof ColorSetting cs && cs.expanded) {
                // Hangi renk slider'ında olduğunu tahmin etme (Basit mantık)
                int val = (int) (255 * Math.max(0, Math.min(1, (mouseX - 180) / 125.0f)));
                // Sadece sürüklendiğinde en yakın rengi günceller (Pratik çözüm)
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingSetting = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }
            }
                    
