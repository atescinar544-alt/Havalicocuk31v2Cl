package com.havali.client.mixin;

import com.havali.client.module.Module;
import com.havali.client.module.ModuleManager;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeldItemRenderer.class)
public class ItemSwayMixin {
    @WrapWithCondition(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V"))
    private boolean onMultiply(MatrixStack instance, Quaternionf quaternion) {
        Module mod = ModuleManager.INSTANCE.getModule("ItemSway");
        if (mod != null && mod.toggled) {
            return false;
        }
        return true;
    }
}
