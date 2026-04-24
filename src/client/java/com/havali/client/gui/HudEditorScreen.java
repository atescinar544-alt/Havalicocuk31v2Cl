package com.havali.client.gui;

import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {
    private Module dragging = null;
    public HudEditorScreen() { super(Text.literal("HUD Editor")); }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            if (mod.toggled) {
                context.fill(mod.x, mod.y, mod.x + 50, mod.y + 20, 0x70FFFFFF);
                context.drawTextWithShadow(textRenderer, mod.name, mod.x + 2, mod.y + 5, 0xFFFFFF);
                if (dragging == mod) {
                    mod.x = mouseX - 25;
                    mod.y = mouseY - 10;
                }
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            if (mouseX >= mod.x && mouseX <= mod.x + 50 && mouseY >= mod.y && mouseY <= mod.y + 20) {
                dragging = mod;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
