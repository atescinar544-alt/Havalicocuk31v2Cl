package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.BooleanSetting;
import com.havali.client.module.settings.ColorSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;

public class KeystrokesModule extends Module {
    public ColorSetting bgNormal = new ColorSetting("Arkaplan", 0.0f, 0.0f, 0.0f);
    public ColorSetting bgPressed = new ColorSetting("Basili Arkaplan", 0.0f, 0.0f, 1.0f);
    public ColorSetting textNormal = new ColorSetting("Yazi Rengi", 0.0f, 0.0f, 1.0f);
    public ColorSetting textPressed = new ColorSetting("Basili Yazi", 0.0f, 0.0f, 0.0f);
    public BooleanSetting outline = new BooleanSetting("Cizgi", false);
    public ColorSetting outlineColor = new ColorSetting("Cizgi Rengi", 0.0f, 0.0f, 1.0f);

    public KeystrokesModule() {
        super("Keystrokes");
        addSettings(bgNormal, bgPressed, textNormal, textPressed, outline, outlineColor);
        this.x = 50;
        this.y = 50;
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0);
        context.getMatrices().scale(scale.getValueFloat(), scale.getValueFloat(), 1.0f);

        int bgN = (100 << 24) | (bgNormal.getColor() & 0xFFFFFF);
        int bgP = (160 << 24) | (bgPressed.getColor() & 0xFFFFFF);

        drawKey(context, client.options.forwardKey, "W", 18, 0, 17, 17, bgN, bgP);
        drawKey(context, client.options.leftKey, "A", 0, 18, 17, 17, bgN, bgP);
        drawKey(context, client.options.backKey, "S", 18, 18, 17, 17, bgN, bgP);
        drawKey(context, client.options.rightKey, "D", 36, 18, 17, 17, bgN, bgP);

        drawKey(context, client.options.attackKey, "LMB", 0, 36, 26, 17, bgN, bgP);
        drawKey(context, client.options.useKey, "RMB", 27, 36, 26, 17, bgN, bgP);

        drawKey(context, client.options.jumpKey, "-", 0, 54, 53, 9, bgN, bgP);

        context.getMatrices().pop();
    }

    private void drawKey(DrawContext context, KeyBinding key, String name, int kX, int kY, int kW, int kH, int bgN, int bgP) {
        boolean pressed = key.isPressed();
        int bgColor = pressed ? bgP : bgN;
        int txtColor = pressed ? textPressed.getColor() : textNormal.getColor();

        context.fill(kX, kY, kX + kW, kY + kH, bgColor);

        if (outline.isEnabled()) {
            int oc = (255 << 24) | (outlineColor.getColor() & 0xFFFFFF);
            context.fill(kX, kY, kX + kW, kY + 1, oc);
            context.fill(kX, kY + kH - 1, kX + kW, kY + kH, oc);
            context.fill(kX, kY, kX + 1, kY + kH, oc);
            context.fill(kX + kW - 1, kY, kX + kW, kY + kH, oc);
        }

        MinecraftClient client = MinecraftClient.getInstance();
        int textWidth = client.textRenderer.getWidth(name);
        context.drawText(client.textRenderer, name, kX + (kW - textWidth) / 2, kY + (kH - 8) / 2, txtColor, false);
    }
}
