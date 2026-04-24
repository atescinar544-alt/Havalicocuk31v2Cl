package com.havali.client.mixin;

import com.havali.client.module.ModuleManager;
import com.havali.client.module.impl.FogModule;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(BackgroundRenderer.class)
public class FogMixin {
    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void onApplyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        FogModule mod = (FogModule) ModuleManager.INSTANCE.getModule("Fog");
        if (mod.toggled) {
            RenderSystem.setShaderFogStart(0f);
            RenderSystem.setShaderFogEnd(viewDistance * (1.1f - FogModule.density.getValueFloat()));
            RenderSystem.setShaderFogColor(FogModule.red.getValueFloat(), FogModule.green.getValueFloat(), FogModule.blue.getValueFloat());
        }
    }
}
