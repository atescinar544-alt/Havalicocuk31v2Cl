package com.havali.client.gui;

import com.havali.client.Config;
import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import com.havali.client.module.settings.BooleanSetting;
import com.havali.client.module.settings.ColorSetting;
import com.havali.client.module.settings.NumberSetting;
import com.havali.client.module.settings.Setting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGUI extends Screen {
    private Module selectedModule = null;
    private Setting draggingSetting = null;
    private int colorDragType = 0;

    public ClickGUI() {
        super(Text.literal("Havalicocuk31v2 Premium"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x90000000);
        int modY = 30;
        context.fill(20, 20, 160, height - 40, 0x90101010);
        context.drawTextWithShadow(textRenderer, "Moduller", 30, modY, 0x00FFCC);
        modY += 20;

        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            int color = mod.toggled ? 0x00FF00 : 0xAAAAAA;
            context.fill(25, modY, 155, modY + 16, mod.toggled ? 0x4000FF00 : 0x40333333);
            context.drawTextWithShadow(textRenderer, mod.name, 30, modY + 4, color);
            modY += 20;
        }

        if (selectedModule != null) {
            int sY = 30;
            context.fill(170, 20, 360, height - 40, 0x90101010);
            context.drawTextWithShadow(textRenderer, selectedModule.name + " Ayarlari", 180, sY, 0x00FFCC);
            sY += 25;

            for (Setting s : selectedModule.settings) {
                context.drawTextWithShadow(textRenderer, s.name, 180, sY, 0xFFFFFF);
                
                if (s instanceof BooleanSetting bs) {
                    context.drawTextWithShadow(textRenderer, bs.isEnabled() ? "ACIK" : "KAPALI", 310, sY, bs.isEnabled() ? 0x00FF00 : 0xFF0000);
                    sY += 20;
                } else if (s instanceof NumberSetting ns) {
                    float percent = (float) ((ns.value - ns.min) / (ns.max - ns.min));
                    context.fill(180, sY + 12, 340, sY + 16, 0xFF444444);
                    context.fill(180, sY + 12, 180 + (int)(160 * percent), sY + 16, 0xFF00FFCC);
                    context.drawTextWithShadow(textRenderer, String.format("%.2f", ns.value), 310, sY, 0xAAAAAA);
                    sY += 25;
                } else if (s instanceof ColorSetting cs) {
                    context.fill(325, sY, 340, sY + 10, 0xFF888888);
                    context.fill(326, sY + 1, 339, sY + 9, cs.getColor());
                    sY += 15;
                    
                    if (cs.expanded) {
                        int boxSize = 60;
                        int hueWidth = 10;
                        int step = 3;
                        
                        for (int i = 0; i < boxSize; i += step) {
                            for (int j = 0; j < boxSize; j += step) {
                                float sat = i / (float) boxSize;
                                float bri = 1.0f - (j / (float) boxSize);
                                ColorSetting temp = new ColorSetting("", cs.hue, sat, bri);
                                context.fill(180 + i, sY + j, 180 + i + step, sY + j + step, temp.getColor());
                            }
                        }
                        
                        context.fill(180 + (int)(cs.saturation * boxSize) - 1, sY + (int)((1.0f - cs.brightness) * boxSize) - 1, 180 + (int)(cs.saturation * boxSize) + 1, sY + (int)((1.0f - cs.brightness) * boxSize) + 1, 0xFFFFFFFF);

                        for (int j = 0; j < boxSize; j += 2) {
                            float h = j / (float) boxSize;
                            ColorSetting temp = new ColorSetting("", h, 1.0f, 1.0f);
                            context.fill(245, sY + j, 245 + hueWidth, sY + j + 2, temp.getColor());
                        }
                        
                        context.fill(243, sY + (int)(cs.hue * boxSize) - 1, 245 + hueWidth + 2, sY + (int)(cs.hue * boxSize) + 1, 0xFFFFFFFF);
                        sY += boxSize + 10;
                    }
                }
            }
        }

        context.fill(width - 110, height - 35, width - 20, height - 15, 0xFF00FFCC);
        context.drawTextWithShadow(textRenderer, "HUD Ayarla", width - 95, height - 26, 0xFF000000);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int modY = 50;
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            if (mouseX >= 25 && mouseX <= 155 && mouseY >= modY && mouseY <= modY + 16) {
                if (button == 0) mod.toggle();
                if (button == 1) selectedModule = mod;
                Config.save();
                return true;
            }
            modY += 20;
        }

        if (mouseX >= width - 110 && mouseX <= width - 20 && mouseY >= height - 35 && mouseY <= height - 15) {
            client.setScreen(new HudEditorScreen());
            return true;
        }

        if (selectedModule != null) {
            int sY = 55;
            for (Setting s : selectedModule.settings) {
                if (s instanceof BooleanSetting bs) {
                    if (mouseX >= 180 && mouseX <= 340 && mouseY >= sY && mouseY <= sY + 15) {
                        bs.setEnabled(!bs.isEnabled());
                        Config.save();
                    }
                    sY += 20;
                } else if (s instanceof NumberSetting) {
                    if (mouseX >= 180 && mouseX <= 340 && mouseY >= sY + 10 && mouseY <= sY + 20) {
                        draggingSetting = s;
                    }
                    sY += 25;
                } else if (s instanceof ColorSetting cs) {
                    if (mouseX >= 325 && mouseX <= 340 && mouseY >= sY && mouseY <= sY + 10) {
                        cs.expanded = !cs.expanded;
                    }
                    sY += 15;
                    if (cs.expanded) {
                        if (mouseX >= 180 && mouseX <= 240 && mouseY >= sY && mouseY <= sY + 60) {
                            draggingSetting = cs;
                            colorDragType = 1;
                        } else if (mouseX >= 245 && mouseX <= 255 && mouseY >= sY && mouseY <= sY + 60) {
                            draggingSetting = cs;
                            colorDragType = 2;
                        }
                        sY += 70;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingSetting instanceof NumberSetting ns) {
            float percent = (float) Math.max(0, Math.min(1, (mouseX - 180) / 160.0f));
            ns.setValue(ns.min + (ns.max - ns.min) * percent);
        } else if (draggingSetting instanceof ColorSetting cs) {
            int sY = 55;
            for (Setting s : selectedModule.settings) {
                if (s instanceof BooleanSetting) {
                    sY += 20;
                } else if (s instanceof NumberSetting) {
                    sY += 25;
                } else if (s instanceof ColorSetting currentCs) {
                    sY += 15;
                    if (currentCs.expanded) {
                        if (currentCs == cs) {
                            if (colorDragType == 1) {
                                cs.saturation = (float) Math.max(0, Math.min(1, (mouseX - 180) / 60.0f));
                                cs.brightness = 1.0f - (float) Math.max(0, Math.min(1, (mouseY - sY) / 60.0f));
                                cs.brightness = Math.max(0.01f, cs.brightness);
                            } else if (colorDragType == 2) {
                                cs.hue = (float) Math.max(0, Math.min(1, (mouseY - sY) / 60.0f));
                            }
                        }
                        sY += 70;
                    }
                }
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (draggingSetting != null) Config.save();
        draggingSetting = null;
        colorDragType = 0;
        return super.mouseReleased(mouseX, mouseY, button);
    }
                                                                 }
                                    
