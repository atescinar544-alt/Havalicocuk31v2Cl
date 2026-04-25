package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.BooleanSetting;
import com.havali.client.module.settings.ColorSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class FpsHudModule extends Module {
    public BooleanSetting background = new BooleanSetting("Arkaplan", true);
    public ColorSetting textColor = new ColorSetting("Yazi Rengi", 0.0f, 0.0f, 1.0f);

    public FpsHudModule() {
        super("FpsHud");
        addSettings(background, textColor);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.textRenderer != null) {
            String text = client.getCurrentFps() + " FPS";
            
            context.getMatrices().push();
            context.getMatrices().translate(x, y, 0);
            context.getMatrices().scale(scale.getValueFloat(), scale.getValueFloat(), 1.0f);
            
            if (background.isEnabled()) {
                int width = client.textRenderer.getWidth(text) + 8;
                context.fill(0, 0, width, 14, 0x90000000);
            }
            
            context.drawTextWithShadow(client.textRenderer, text, 4, 3, textColor.getColor());
            context.getMatrices().pop();
        }
    }
}
