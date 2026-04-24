package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.BooleanSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class FpsHudModule extends Module {
    public BooleanSetting background = new BooleanSetting("Background", true);

    public FpsHudModule() {
        super("FpsHud");
        addSettings(background);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.textRenderer != null) {
            String fpsText = "FPS: " + client.getCurrentFps();
            if (background.isEnabled()) {
                context.fill(x, y, x + 45, y + 15, 0x90000000);
            }
            context.drawTextWithShadow(client.textRenderer, fpsText, x + 5, y + 4, 0x00FF00);
        }
    }
}
