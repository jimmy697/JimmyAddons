package com.jimmy.features;

import com.jimmy.config.JimmyConfig;
import com.jimmy.utils.BoxRenderer;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

import static com.jimmy.utils.BoxRenderer.drawOutlinedBoundingBox;

public class RubyESP {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final BoxRenderer boxRenderer = new BoxRenderer();
    private BlockPos playerPos;

    public static int renderRange = 20;

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if(!JimmyConfig.gemEsp)
            return;
        World world = mc.theWorld;
        if (playerPos == null) {
            playerPos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);
        }
        for (int x = playerPos.getX() - renderRange; x <= playerPos.getX() + renderRange; x++) {
            for (int y = playerPos.getY() - renderRange; y <= playerPos.getY() + renderRange; y++) {
                for (int z = playerPos.getZ() - renderRange; z <= playerPos.getZ() + renderRange; z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (mc.theWorld.getBlockState(blockPos).getBlock().equals(Blocks.stained_glass)) {
                        if(mc.theWorld.getBlockState(blockPos).getValue(BlockStainedGlass.COLOR) == EnumDyeColor.RED) {
                            int distance = (int) playerPos.distanceSq(blockPos);
                            if (distance < renderRange) {
                                drawOutlinedBoundingBox(blockPos, Color.RED, 3, event.partialTicks);
                            }
                        }
                    }
                }
            }
        }
    }

}
