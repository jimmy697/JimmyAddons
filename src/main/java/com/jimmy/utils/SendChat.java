package com.jimmy.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class SendChat {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void chat(String msg) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("§6Jimmy§2Addons §e>§d " + msg));
    }

}
