package com.jimmy.keybinds;

import com.jimmy.config.JimmyConfig;
import com.jimmy.utils.SendChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class AutoJoinSb extends KeyBinding {
    private final Minecraft mc = Minecraft.getMinecraft();

    public AutoJoinSb() {
        super("Auto Join Skyblock", Keyboard.KEY_L, "JimmyAddons");
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event)  {
        if(GameSettings.isKeyDown(this) && JimmyConfig.autoSB) {
            SendChat.chat("Joining skyblock...");
            mc.thePlayer.sendChatMessage("/play sb");
        }
    }
}
