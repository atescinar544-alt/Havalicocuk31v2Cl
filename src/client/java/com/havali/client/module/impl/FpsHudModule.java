package com.havali.client.module.impl;

import com.havali.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class FpsHudModule extends Module {
    public FpsHudModule() {
        super("FpsHud");
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.textRenderer != null) {
            String fpsText = "FPS: " + client.getCurrentFps();
            context.drawTextWithShadow(client.textRenderer, fpsText, 5, 5, 0x00FF00);
        }
    }
}
