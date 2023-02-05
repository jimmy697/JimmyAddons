package com.jimmy.features;

import com.jimmy.JimmyAddons;
import com.jimmy.config.JimmyConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class ChestAura {
    public static Minecraft mc = Minecraft.getMinecraft();
    private int range = 5; // range in which to search for chests
    private List<TileEntityChest> openedChests = new ArrayList<>(); // list to keep track of opened chests

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if(!JimmyConfig.chestAura)
            return;
        openChests();
    }

    public void openChests() {
        List<TileEntityChest> chests = getChestsInRange();
        for (TileEntityChest chest : chests) {
            if (!openedChests.contains(chest)) { // only open chest if it hasn't been opened yet
                openChest(chest);
                openedChests.add(chest);
                closeGUI();
            }
        }
    }

    private List<TileEntityChest> getChestsInRange() {
        List<TileEntityChest> chests = new ArrayList<>();
        int x = (int) mc.thePlayer.posX;
        int y = (int) mc.thePlayer.posY;
        int z = (int) mc.thePlayer.posZ;
        for (int i = x - range; i <= x + range; i++) {
            for (int j = y - range; j <= y + range; j++) {
                for (int k = z - range; k <= z + range; k++) {
                    BlockPos pos = new BlockPos(i, j, k);
                    TileEntity tileEntity = mc.theWorld.getTileEntity(pos);
                    if (tileEntity instanceof TileEntityChest) {
                        chests.add((TileEntityChest) tileEntity);
                    }
                }
            }
        }
        return chests;
    }

    private void openChest(TileEntityChest chest) {
        mc.thePlayer.displayGUIChest(chest);
    }

    private void closeGUI() {
        mc.thePlayer.closeScreen();
    }
}
