package com.havali.client.mixin;

import com.havali.client.module.ModuleManager;
import com.havali.client.module.impl.ViewmodelModule;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class ViewmodelMixin {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private void onRenderItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        ViewmodelModule mod = (ViewmodelModule) ModuleManager.INSTANCE.getModule("Viewmodel");
        if (mod != null && mod.toggled) {
            matrices.translate(ViewmodelModule.posX.getValue(), ViewmodelModule.posY.getValue(), ViewmodelModule.posZ.getValue());
            float s = ViewmodelModule.scale.getValueFloat();
            matrices.scale(s, s, s);
        }
    }
}
