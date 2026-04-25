package com.havali.client.gui;

import com.havali.client.Config;
import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {
    private Module dragging = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public HudEditorScreen() {
        super(Text.literal("HUD Editor"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            if (mod.toggled) {
                float s = mod.scale.getValueFloat();
                int boxW = (int) (60 * s);
                int boxH = (int) (20 * s);

                context.fill(mod.x, mod.y, mod.x + boxW, mod.y + boxH, 0x40FFFFFF);

                context.getMatrices().push();
                context.getMatrices().translate(mod.x, mod.y, 0);
                context.getMatrices().scale(s, s, 1.0f);
                context.drawTextWithShadow(textRenderer, mod.name, 2, 6, 0xFFFFFF);
                context.getMatrices().pop();

                if (dragging == mod) {
                    mod.x = mouseX - dragOffsetX;
                    mod.y = mouseY - dragOffsetY;
                }
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            if (mod.toggled) {
                float s = mod.scale.getValueFloat();
                int boxW = (int) (60 * s);
                int boxH = (int) (20 * s);

                if (mouseX >= mod.x && mouseX <= mod.x + boxW && mouseY >= mod.y && mouseY <= mod.y + boxH) {
                    dragging = mod;
                    dragOffsetX = (int) mouseX - mod.x;
                    dragOffsetY = (int) mouseY - mod.y;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging != null) Config.save();
        dragging = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
