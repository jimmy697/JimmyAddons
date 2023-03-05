package com.jimmy.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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

    public static void swapToSlot(int slot) {
        mc.thePlayer.inventory.currentItem = slot;
    }

    public static void sleep(int sleeptime) {
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDecimalPartApproximately(double fullValue, double expectedDecimalPart) {
        return (Math.abs(Math.abs(fullValue % 1.0D) - expectedDecimalPart) < 0.01D);
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

    public static void clickWindow(int windowID, int slotID, int mouseButtonClicked, int mode) {
        if (mc.thePlayer.openContainer instanceof ContainerChest || mc.currentScreen instanceof GuiInventory) {
            mc.playerController.windowClick(windowID, slotID, mouseButtonClicked, mode, mc.thePlayer);
        } else {
            return;
        }
    }

    public static String getSkyBlockID(ItemStack item) {
        if(item != null) {
            NBTTagCompound extraAttributes = item.getSubCompound("ExtraAttributes", false);
            if(extraAttributes != null && extraAttributes.hasKey("id")) {
                return extraAttributes.getString("id");
            }
        }
        return "";
    }

}
