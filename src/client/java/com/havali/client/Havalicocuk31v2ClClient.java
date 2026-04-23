package com.havali.client;

import com.havali.client.gui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Havalicocuk31v2ClClient implements ClientModInitializer {
    private static KeyBinding clickGuiKey;

    @Override
    public void onInitializeClient() {
        // Right Shift Tuş Ataması
        clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.havaliclient.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.havaliclient.general"
        ));

        // Her oyun tick'inde tuşa basılıp basılmadığını kontrol et
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClickGUI());
                }
            }
        });

        Havalicocuk31v2Cl.LOGGER.info("Havalicocuk31v2 GUI ve Moduller Yuklendi!");
    }
}
