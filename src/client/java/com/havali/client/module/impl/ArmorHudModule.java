package com.havali.client.module.impl;

import com.havali.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class ArmorHudModule extends Module {
    public ArmorHudModule() {
        super("ArmorHud");
        this.x = 300;
        this.y = 200;
    }

    @Override
    public void onRender(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        int renderY = this.y;
        DefaultedList<ItemStack> armor = client.player.getInventory().armor;
        
        for (int i = 3; i >= 0; i--) {
            ItemStack stack = armor.get(i);
            if (!stack.isEmpty()) {
                context.drawItem(stack, this.x, renderY);
                context.drawItemInSlot(client.textRenderer, stack, this.x, renderY);
                renderY += 18;
            }
        }
    }
}
