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
        this.renderBackground(context);
        int y = 40;
        context.drawTextWithShadow(this.textRenderer, "Havalicocuk31v2 Client", 100, y - 20, 0xFFFFFF);
        for (Module mod : ModuleManager.INSTANCE.getModules()) {
            int color = mod.isToggled() ? 0x00FF00 : 0xFF0000;
            context.drawTextWithShadow(this.textRenderer, mod.getName(), 100, y, color);
            y += 15;
        }
        super.render(context, mouseX, mouseY, delta);
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int y = 40;
            for (Module mod : ModuleManager.INSTANCE.getModules()) {
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