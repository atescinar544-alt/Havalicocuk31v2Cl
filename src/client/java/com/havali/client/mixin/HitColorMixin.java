package com.havali.client.mixin;

import com.havali.client.module.ModuleManager;
import com.havali.client.module.impl.HitColorModule;
import com.havali.client.module.settings.ColorSetting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class HitColorMixin {
    
    @Inject(method = "render*", at = @At("HEAD"))
    private void onRenderHead(LivingEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        HitColorModule mod = (HitColorModule) ModuleManager.INSTANCE.getModule("HitColor");
        if (mod != null && mod.toggled && entity.hurtTime > 0) {
            ColorSetting c = HitColorModule.color;
            int color = c.getColor();
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float green = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;
            com.mojang.blaze3d.systems.RenderSystem.setShaderColor(r, green, b, 1.0f);
        }
    }

    @Inject(method = "render*", at = @At("TAIL"))
    private void onRenderTail(LivingEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        com.mojang.blaze3d.systems.RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
