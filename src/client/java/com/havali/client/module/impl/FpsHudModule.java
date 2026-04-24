package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.BooleanSetting;
import com.havali.client.module.settings.ColorSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class FpsHudModule extends Module {
    public BooleanSetting background = new BooleanSetting("Arkaplan", true);
    public ColorSetting color = new ColorSetting("Tema Rengi", 0, 255, 204);

    public FpsHudModule() {
        super("FpsHud");
        addSettings(background, color);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.textRenderer != null) {
            String text = "FPS: " + client.getCurrentFps();
            if (background.isEnabled()) {
                context.fill(x, y, x + 60, y + 16, 0x90101010); 
                context.fill(x, y, x + 2, y + 16, color.getColor()); // Kenar Çizgisi
            }
            context.drawTextWithShadow(client.textRenderer, text, x + 6, y + 4, 0xFFFFFF);
        }
    }
}
