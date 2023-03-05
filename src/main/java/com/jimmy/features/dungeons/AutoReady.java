package com.jimmy.features.dungeons;

import com.jimmy.JimmyAddons;
import com.jimmy.config.JimmyConfig;
import com.jimmy.utils.Locations;
import com.jimmy.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoReady {

    public static Minecraft mc = Minecraft.getMinecraft();
    private Thread thread;

    public static boolean isReady = false;
    
    private enum ReadyState  {
      START, READY      
    };
    
    private static ReadyState readyState;

    @SubscribeEvent
    public void onGui(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(!JimmyConfig.autoReady)
            return;
        if(thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                try {
                    if(JimmyAddons.location == Locations.DUNGEON && mc.thePlayer.openContainer != null) {
                        String invName = mc.thePlayer.openContainer.inventorySlots.get(0).inventory.getName();
                        if(invName != null) {
                            if(invName.startsWith("Start")) {
                                readyState = ReadyState.START;
                            } else if (invName.startsWith("Catacombs")) {
                                readyState = ReadyState.READY;
                            }
                        }

                        switch (readyState) {
                            case START:
                                PlayerUtil.clickWindow(mc.thePlayer.openContainer.windowId, 13, 0, 0);
                                Thread.sleep(250);
                                readyState = ReadyState.READY;
                                return;
                            case READY:
                                for(Slot slot : mc.thePlayer.openContainer.inventorySlots) {
                                    if(slot.getStack() != null && slot.getStack().getDisplayName().contains(mc.thePlayer.getName())) {
                                        PlayerUtil.clickWindow(mc.thePlayer.openContainer.windowId, slot.slotNumber, 0, 0);
                                        isReady = true;
                                        break;
                                    }
                                }
                        }

                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            if(!isReady)
                thread.start();
        }
    }

    @SubscribeEvent
    public void onWorld(WorldEvent.Load event) {
        isReady = false;
        readyState = null;
    }



}
