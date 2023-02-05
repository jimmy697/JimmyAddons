package com.jimmy.macros;

import cc.polyfrost.oneconfig.utils.TickDelay;
import com.jimmy.config.JimmyConfig;
import com.jimmy.utils.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.jimmy.JimmyAddons.foragingEnabled;
import static com.jimmy.config.JimmyConfig.configForaging;
import static com.jimmy.macros.MacroUtil.*;

public class ForagingMacro {

    public static Minecraft mc = Minecraft.getMinecraft();

    private enum MacroState {
        LOOK, PLACE, PLACE_BONE, BREAK, FIND_ROD, FIND_BONE, THROW_ROD, THROW_BREAK_DELAY, SWITCH
    };

    private static MacroState macroState = MacroState.LOOK;

    private int boneTickCount = 0;
    private int findRodTickCount = 0;
    private int rodTickCound = 0;
    private int throwBreakTickCount = 0;
    private int cycleTickCount = 0;

    public static Vec3 bestDirt;

    public static long foragingDelay;

    //Old version of pizza client function (ages ago when it was open source), creds to them. lmk if u guys want me to remove this!
    private Vec3 getDirt() {
        Vec3 furthest = null;
        Vec3 player = PlayerUtil.getPositionEyes();
        for (BlockPos pos : BlockPos.getAllInBox(new BlockPos(mc.thePlayer.posX + 4.0D, mc.thePlayer.posY, mc.thePlayer.posZ + 4.0D), new BlockPos(mc.thePlayer.posX - 4.0D, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ - 4.0D))) {
            if (mc.theWorld.getBlockState(pos).getBlock() == Blocks.dirt || mc.theWorld.getBlockState(pos).getBlock() == Blocks.grass) {
                Block block = mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock();
                if (!(block instanceof net.minecraft.block.BlockLog) && block != Blocks.sapling) {
                    Vec3 distance = new Vec3(pos.getX() + 0.5D, (pos.getY() + 1), pos.getZ() + 0.5D);
                    if (furthest == null || player.squareDistanceTo(distance) > player.squareDistanceTo(furthest))
                        furthest = distance;
                }
            }
        }
        return furthest;
    }
    //

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(!foragingEnabled) {
            macroState = MacroState.LOOK;
        }

        if(foragingEnabled && configForaging) {
            switch (macroState) {
                case LOOK:
                    PlayerUtil.swapToSlot(Item.getItemFromBlock(Blocks.sapling));
                    bestDirt = getDirt();
                    if(bestDirt!=null) {
                        snapTo(bestDirt);
                        macroState = MacroState.PLACE;
                    } else {
                        macroState = MacroState.FIND_BONE;
                    }
                    return;
                case PLACE:
                    KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
                    macroState = MacroState.LOOK;
                    return;
                case FIND_BONE:
                    PlayerUtil.swapToSlot(Items.dye);
                    boneTickCount = 0;
                    macroState = MacroState.PLACE_BONE;
                    return;
                case PLACE_BONE:
                    boneTickCount++;
                    if(boneTickCount >= JimmyConfig.foragingActionDelay) {
                        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
                        boneTickCount = 0;
                        findRodTickCount = 0;
                        macroState = MacroState.FIND_ROD;
                    }
                    return;
                case FIND_ROD:
                    findRodTickCount++;
                    if(findRodTickCount >= JimmyConfig.foragingActionDelay) {
                        PlayerUtil.swapToSlot(Items.fishing_rod);
                        rodTickCound = 0;
                        macroState = MacroState.THROW_ROD;
                    }
                    return;
                case THROW_ROD:
                    rodTickCound++;
                    if(rodTickCound >= JimmyConfig.foragingActionDelay) {
                        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
                        rodTickCound = 0;
                        throwBreakTickCount = 0;
                        macroState = MacroState.THROW_BREAK_DELAY;
                    }
                    return;
                case THROW_BREAK_DELAY:
                    throwBreakTickCount++;
                    if(throwBreakTickCount >= JimmyConfig.foragingActionDelay) {
                        //null class for extra delay
                        throwBreakTickCount = 0;
                        macroState = MacroState.BREAK;
                    }
                    return;
                case BREAK:
                    PlayerUtil.swapToSlot(Items.golden_axe);
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                    BlockPos logPos = mc.objectMouseOver.getBlockPos();
                    if(logPos != null && !(mc.theWorld.getBlockState(logPos).getBlock() instanceof BlockLog)) {
                        foragingDelay = System.currentTimeMillis();
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                        cycleTickCount = 0;
                        macroState = MacroState.SWITCH;
                    }
                    return;
                case SWITCH:
                    cycleTickCount++;
                    if(cycleTickCount >= JimmyConfig.foragingCycleDelay) {
                        cycleTickCount = 0;
                        macroState = MacroState.LOOK;
                    }
            }
        }

    }

}
