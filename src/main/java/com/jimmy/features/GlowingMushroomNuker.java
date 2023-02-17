package com.jimmy.features;

import com.jimmy.JimmyAddons;
import com.jimmy.config.JimmyConfig;
import com.jimmy.events.SpawnParticleEvent;
import com.jimmy.utils.BoxRenderer;
import com.jimmy.utils.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;


/**
 * 95% of ESP Implmemented from AuroraClient's GrassESP
 * Credits: Gabagooooooooooool
 * There's no license but i wanna put this here anyway coz its respectful
 */
public class GlowingMushroomNuker {

    public static final Minecraft mc = Minecraft.getMinecraft();

    private Set<BlockPos> shrooms = ConcurrentHashMap.newKeySet();
    private Set<BlockPos> tempShrooms = ConcurrentHashMap.newKeySet();
    private Set<BlockPos> destroyedBlocks = ConcurrentHashMap.newKeySet();
    private Set<BlockPos> mobBlocks = ConcurrentHashMap.newKeySet();
    boolean readyToScan = true;

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if(JimmyConfig.shroomNuker && readyToScan && mc.theWorld != null) {
            readyToScan = false;
            new Thread(() -> searchForParticles((int)mc.thePlayer.posX, (int)mc.thePlayer.posY, (int)mc.thePlayer.posZ), "SHROOMERSCANNER").start();
            for(BlockPos blockPos : shrooms) {
                if(!destroyedBlocks.contains(blockPos) && canReachBlock(blockPos, 5)) {
                    nuke(blockPos);
                    destroyedBlocks.add(blockPos);
                    mobBlocks.remove(blockPos);
                }
            }
        }
    }

    public void searchForParticles(int x, int y, int z) {
        tempShrooms.clear();
        shrooms.stream().filter(blockPos -> !checkShroom(blockPos)).forEach(blockPos -> tempShrooms.add(blockPos));
        tempShrooms.forEach(blockPos->{tempShrooms.remove(blockPos);});
        IntStream.range(x - 50, x + 50).forEach(x1 -> {
            IntStream.range(y - 50, y + 50).forEach(y1 -> {
                IntStream.range(z - 50, z + 50).filter(z1 -> (checkShroom(new BlockPos(x1, y1, z1)))).forEach(z1 -> {
                    shrooms.add(new BlockPos(x1,y1,z1));
                });
            });
        });
        readyToScan = true;

    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(JimmyConfig.shroomNuker) {
            shrooms.stream().filter(this::checkShroom).forEach(blockPos -> {
                BoxRenderer.drawOutlinedBoundingBox(blockPos, Color.GREEN, 3, event.partialTicks);
            });
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        readyToScan = true;
        shrooms.clear();
    }

    private boolean checkShroom(BlockPos blockPos) {
        if(mobBlocks.contains(blockPos)) {
            return true;
        } else {
            return false;
        }
    }

    @SubscribeEvent
    public void onParticleSpawn(SpawnParticleEvent event) {
        if(!JimmyConfig.shroomNuker)
            return;
        if(event.getParticleTypes() == EnumParticleTypes.SPELL_MOB) {
            if(destroyedBlocks.contains(new BlockPos(event.getXCoord(), event.getYCoord(), event.getZCoord())))  {
                destroyedBlocks.remove(new BlockPos(event.getXCoord(), event.getYCoord(), event.getZCoord()));
            }

            mobBlocks.add(new BlockPos(event.getXCoord(), event.getYCoord(), event.getZCoord()));
        }
    }

    public void nuke(BlockPos pos) {
        mc.playerController.onPlayerDamageBlock(pos, mc.thePlayer.getHorizontalFacing());
        mc.thePlayer.swingItem();
    }

    //All of this code below is from Pizza Client (old public version) to check if the user can  reach the block its nuking!
    public static boolean canReachBlock(BlockPos pos, float range) {
        AxisAlignedBB aabb = getBlockAABB(pos);
        return isInterceptable(PlayerUtil.getPositionEyes(), getMiddleOfAABB(aabb), aabb, range);
    }

    public static AxisAlignedBB getBlockAABB(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        block.setBlockBoundsBasedOnState((IBlockAccess)mc.theWorld, pos);
        return block.getSelectedBoundingBox((World)mc.theWorld, pos);
    }

    public static boolean isInterceptable(Vec3 start, Vec3 goal, AxisAlignedBB aabb, float range) {
        if (start.squareDistanceTo(goal) > (range * range))
            return false;
        return isInterceptable(start, goal, aabb);
    }

    public static boolean isInterceptable(Vec3 start, Vec3 goal, AxisAlignedBB aabb) {
        return (isVecInYZ(start.getIntermediateWithXValue(goal, aabb.minX), aabb) ||
                isVecInYZ(start.getIntermediateWithXValue(goal, aabb.maxX), aabb) ||
                isVecInXZ(start.getIntermediateWithYValue(goal, aabb.minY), aabb) ||
                isVecInXZ(start.getIntermediateWithYValue(goal, aabb.maxY), aabb) ||
                isVecInXY(start.getIntermediateWithZValue(goal, aabb.minZ), aabb) ||
                isVecInXY(start.getIntermediateWithZValue(goal, aabb.maxZ), aabb));
    }

    public static boolean isVecInYZ(Vec3 vec, AxisAlignedBB aabb) {
        return (vec != null && vec.yCoord >= aabb.minY && vec.yCoord <= aabb.maxY && vec.zCoord >= aabb.minZ && vec.zCoord <= aabb.maxZ);
    }

    public static boolean isVecInX(Vec3 vec, AxisAlignedBB aabb) {
        return (vec != null && vec.xCoord >= aabb.minX && vec.xCoord <= aabb.maxX);
    }

    public static boolean isVecInZ(Vec3 vec, AxisAlignedBB aabb) {
        return (vec != null && vec.zCoord >= aabb.minZ && vec.zCoord <= aabb.maxZ);
    }

    public static boolean isVecInXZ(Vec3 vec, AxisAlignedBB aabb) {
        return (vec != null && vec.xCoord >= aabb.minX && vec.xCoord <= aabb.maxX && vec.zCoord >= aabb.minZ && vec.zCoord <= aabb.maxZ);
    }

    public static boolean isVecInXY(Vec3 vec, AxisAlignedBB aabb) {
        return (vec != null && vec.xCoord >= aabb.minX && vec.xCoord <= aabb.maxX && vec.yCoord >= aabb.minY && vec.yCoord <= aabb.maxY);
    }

    public static Vec3 getMiddleOfAABB(AxisAlignedBB aabb) {
        return new Vec3((aabb.maxX + aabb.minX) / 2.0D, (aabb.maxY + aabb.minY) / 2.0D, (aabb.maxZ + aabb.minZ) / 2.0D);
    }

}
