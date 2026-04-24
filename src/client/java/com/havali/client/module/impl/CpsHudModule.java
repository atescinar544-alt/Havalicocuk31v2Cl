package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.BooleanSetting;
import com.havali.client.module.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

public class CpsHudModule extends Module {
    private final List<Long> clicks = new ArrayList<>();
    public BooleanSetting background = new BooleanSetting("Background", true);
    public NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.1);

    public CpsHudModule() {
        super("CpsHud");
        addSettings(background, scale);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.attackKey.wasPressed()) clicks.add(System.currentTimeMillis());
        clicks.removeIf(time -> System.currentTimeMillis() - time > 1000);
    }

    @Override
    public void onRender(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        String text = clicks.size() + " CPS";
        if (background.isEnabled()) {
            context.fill(x, y, x + 40, y + 15, 0x90000000);
        }
        context.drawTextWithShadow(client.textRenderer, text, x + 5, y + 4, 0xFFFFFF);
    }
}
