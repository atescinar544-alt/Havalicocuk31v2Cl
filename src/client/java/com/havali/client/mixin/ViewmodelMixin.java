package com.havali.client.mixin;

import com.havali.client.module.ModuleManager;
import com.havali.client.module.impl.ViewmodelModule;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class ViewmodelMixin {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void onRenderItem(float tickDelta, MatrixStack matrices, Object vertexConsumers, Object player, Hand hand, Object stack, CallbackInfo ci) {
        ViewmodelModule mod = (ViewmodelModule) ModuleManager.INSTANCE.getModule("Viewmodel");
        if (mod.toggled) {
            matrices.translate(ViewmodelModule.posX.getValue(), ViewmodelModule.posY.getValue(), ViewmodelModule.posZ.getValue());
            matrices.scale(ViewmodelModule.scale.getValueFloat(), ViewmodelModule.scale.getValueFloat(), ViewmodelModule.scale.getValueFloat());
        }
    }
}
