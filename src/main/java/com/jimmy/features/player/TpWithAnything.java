package com.jimmy.features.player;

import com.jimmy.JimmyAddons;
import com.jimmy.config.JimmyConfig;
import com.jimmy.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TpWithAnything {

    public static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void interact(PlayerInteractEvent event) {
        if(JimmyConfig.tpAnything && mc.thePlayer.inventory.currentItem == JimmyConfig.tpSlot && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            for(int i = 0; i < 9; i++) {
                ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);
                String itemID = PlayerUtil.getSkyBlockID(item);

                if((itemID.equals("ASPECT_OF_THE_END") || itemID.equals("ASPECT_OF_THE_VOID"))) {
                    event.setCanceled(true);
                    mc.thePlayer.inventory.currentItem = i;
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, item);
                    mc.thePlayer.inventory.currentItem = JimmyConfig.tpSlot;
                    break;
                }
            }
        }
    }

}
