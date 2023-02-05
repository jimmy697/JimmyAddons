package com.jimmy.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class PlayerUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static float fastEyeHeight() {
        return mc.thePlayer.isSneaking() ? 1.54F : 1.62F;
    }

    public static Vec3 getPositionEyes() {
        return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + fastEyeHeight(), mc.thePlayer.posZ);
    }

    public static void swapToSlot(Item item) {
        for (int i = 0; i < 8; i++) {
            ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
            if (stack != null && stack.getItem() == item) {
                mc.thePlayer.inventory.currentItem = i;
                break;
            }
        }
    }

    public static void sleep(int sleeptime) {
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
