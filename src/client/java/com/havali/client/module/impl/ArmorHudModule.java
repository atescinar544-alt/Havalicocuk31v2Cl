package com.havali.client.module.impl;

import com.havali.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class ArmorHudModule extends Module {

    public ArmorHudModule() {
        super("ArmorHud");
        this.x = 20;
        this.y = 100;
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0);
        context.getMatrices().scale(scale.getValueFloat(), scale.getValueFloat(), 1.0f);

        int currentY = 82;
        ItemStack mainHand = client.player.getInventory().getMainHandStack();
        renderItem(context, mainHand, 0, currentY);

        currentY -= 20;
        for (int i = 0; i <= 3; i++) {
            ItemStack item = client.player.getInventory().armor.get(i);
            renderItem(context, item, 0, currentY);
            currentY -= 20;
        }
        context.getMatrices().pop();
    }

    private void renderItem(DrawContext context, ItemStack stack, int localX, int localY) {
        MinecraftClient client = MinecraftClient.getInstance();
        context.drawItem(stack, localX, localY);
        String countText = stack.getCount() > 1 ? String.valueOf(stack.getCount()) : null;
        context.drawItemInSlot(client.textRenderer, stack, localX, localY, countText);
    }
}
