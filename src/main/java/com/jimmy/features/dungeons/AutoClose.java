package com.jimmy.features.dungeons;

import com.jimmy.JimmyAddons;
import com.jimmy.config.JimmyConfig;
import com.jimmy.utils.Locations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoClose {

    public Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onGui(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(event.gui instanceof GuiChest && JimmyAddons.location == Locations.DUNGEON)
            if(((ContainerChest) ((GuiChest) event.gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText().equals("Chest") && JimmyConfig.autoClose) {
                mc.thePlayer.closeScreen();
            }
    }

}
