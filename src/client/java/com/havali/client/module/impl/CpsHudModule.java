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
    private final List<Long> clicks = new ArrayList<>();
    private boolean wasDown = false;
    
    public BooleanSetting background = new BooleanSetting("Arkaplan", true);
    public ColorSetting color = new ColorSetting("Tema Rengi", 0, 255, 204);

    public CpsHudModule() {
        super("CpsHud");
        addSettings(background, color);
        this.y = 30; // Fps hudun biraz altında başlasın
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return;
        
        // Vuruşu engellememek için farenin fiziksel tuşunu direkt okuyoruz
        boolean isDown = GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
        if (isDown && !wasDown) clicks.add(System.currentTimeMillis());
        wasDown = isDown;
        
        clicks.removeIf(time -> System.currentTimeMillis() - time > 1000);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        String text = clicks.size() + " CPS";
        
        if (background.isEnabled()) {
            context.fill(x, y, x + 55, y + 16, 0x90101010); // Premium Koyu Arkaplan
            context.fill(x, y, x + 2, y + 16, color.getColor()); // Sol Tema Çizgisi
        }
        context.drawTextWithShadow(client.textRenderer, text, x + 6, y + 4, 0xFFFFFF);
    }
}
