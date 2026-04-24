package com.havali.client;

import com.havali.client.gui.ClickGUI;
import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Havalicocuk31v2ClClient implements ClientModInitializer {
    private static KeyBinding clickGuiKey;

    @Override
    public void onInitializeClient() {
        clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.havaliclient.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.havaliclient.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClickGUI());
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            for (Module module : ModuleManager.INSTANCE.getModules()) {
                if (module.isToggled()) {
                    module.onRender(drawContext, tickDelta);
                }
            }
        });
    }
}
