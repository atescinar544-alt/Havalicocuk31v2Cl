package com.havali.client.mixin;

import com.havali.client.module.ModuleManager;
import com.havali.client.module.impl.HitColorModule;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class HitColorMixin {
    @Inject(method = "getOverlay", at = @At("HEAD"), cancellable = true)
    private static void onGetOverlay(LivingEntity entity, float whiteIntensity, CallbackInfoReturnable<Integer> cir) {
        HitColorModule mod = (HitColorModule) ModuleManager.INSTANCE.getModule("HitColor");
        if (mod.toggled && entity.hurtTime > 0) {
            int r = (int)(HitColorModule.red.getValue() * 255);
            int g = (int)(HitColorModule.green.getValue() * 255);
            int b = (int)(HitColorModule.blue.getValue() * 255);
            cir.setReturnValue((10 << 24) | (b << 16) | (g << 8) | r);
        }
    }
}
