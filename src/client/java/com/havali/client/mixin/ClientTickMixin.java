package com.havali.client.mixin;

import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ClientTickMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo info) {
        for (Module module : ModuleManager.INSTANCE.getModules()) {
            if (module.isToggled()) {
                module.onTick();
            }
        }
    }
}
