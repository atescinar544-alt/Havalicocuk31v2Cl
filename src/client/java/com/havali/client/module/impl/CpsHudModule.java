package com.havali.client.module.impl;

import com.havali.client.module.Module;
import com.havali.client.module.settings.BooleanSetting;
import com.havali.client.module.settings.ColorSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class CpsHudModule extends Module {
    private final List<Long> leftClicks = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();
    private boolean wasLeftDown = false;
    private boolean wasRightDown = false;

    public BooleanSetting showRight = new BooleanSetting("Sag Tik CPS", false);
    public BooleanSetting background = new BooleanSetting("Arkaplan", true);
    public ColorSetting textColor = new ColorSetting("Yazi Rengi", 0.0f, 0.0f, 1.0f);

    public CpsHudModule() {
        super("CpsHud");
        addSettings(showRight, background, textColor);
        this.y = 30;
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return;

        long time = System.currentTimeMillis();

        boolean isLeftDown = GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
        if (isLeftDown && !wasLeftDown) leftClicks.add(time);
        wasLeftDown = isLeftDown;
        leftClicks.removeIf(t -> time - t > 1000);

        boolean isRightDown = GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS;
        if (isRightDown && !wasRightDown) rightClicks.add(time);
        wasRightDown = isRightDown;
        rightClicks.removeIf(t -> time - t > 1000);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        String text = showRight.isEnabled() ? leftClicks.size() + " | " + rightClicks.size() + " CPS" : leftClicks.size() + " CPS";

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
