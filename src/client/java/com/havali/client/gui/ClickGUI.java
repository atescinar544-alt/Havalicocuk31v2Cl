package com.havali.client.gui;

import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class ClickGUI extends Screen {
    public ClickGUI() {
        super(Text.literal("Havalicocuk31v2 ClickGUI"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context); // Arka planı karartır
        
        int y = 50;
        context.drawTextWithShadow(this.textRenderer, "=== Moduller ===", 100, y - 20, 0xFFFFFF);
        
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            int color = mod.isToggled() ? 0x00FF00 : 0xFF0000; // Açıkça yeşil, kapalıysa kırmızı
            context.drawTextWithShadow(this.textRenderer, mod.getName() + " [" + (mod.isToggled() ? "AÇIK" : "KAPALI") + "]", 100, y, color);
            y += 15;
        }
        
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Sol tık
            int y = 50;
            for (Module mod : ModuleManager.INSTANCE.getModules()) {
                // Basit bir tıklama kutusu mantığı (X: 100-200 arası, Y: yazının olduğu satır)
                if (mouseX >= 100 && mouseX <= 250 && mouseY >= y && mouseY <= y + 10) {
                    mod.toggle();
                    return true;
                }
                y += 15;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
