package com.jimmy.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jimmy.JimmyAddons;
import com.jimmy.config.JimmyConfig;
import com.jimmy.events.TickStartEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;
import scala.collection.parallel.ParIterableLike;

public class SBInfo {

    //PIZZA CLIENT LOCATION IMPLEMENTATION

    public static Minecraft mc = Minecraft.getMinecraft();
    private int ticks;

    private long lastManualLocRaw;

    private long lastLocRaw;

    private long joinedWorld;

    private String mode;

    public static boolean bossSpawned;

    public static boolean inSkyblock;

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (this.mode == null && System.currentTimeMillis() - this.joinedWorld >= 1500L && System.currentTimeMillis() - this.lastLocRaw >= 5000L && isOnHypixel()) {
            this.lastLocRaw = System.currentTimeMillis();
            mc.thePlayer.sendChatMessage("/locraw");
        }
        if (this.ticks % 30 == 0) {
            if (isOnHypixel()) {
                isInSkyblock();
//                getLocation();
            }
        }
    }

    public static boolean isOnHypixel() {
        if (mc.thePlayer.getClientBrand() != null &&
                mc.thePlayer.getClientBrand().toLowerCase().contains("hypixel"))
            return true;
        return (mc.getCurrentServerData() != null && (mc.getCurrentServerData()).serverIP.toLowerCase().contains("hypixel"));
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onRecieveChat(ClientChatReceivedEvent event) {
        if (event.type == 2)
            return;
        String unformatted = event.message.getUnformattedText();
        if (unformatted.startsWith("{") && unformatted.endsWith("}"))
            try {
                JsonObject obj = (JsonObject)(new Gson()).fromJson(unformatted, JsonObject.class);
                if (obj.has("server")) {
                    this.mode = "notnull";
                    if (System.currentTimeMillis() - this.lastManualLocRaw >= 2000L)
                        event.setCanceled(true);
                    if (obj.has("gametype") && obj.get("gametype").getAsString().equals("SKYBLOCK")) {
                        inSkyblock = true;
                        String mode = obj.get("mode").getAsString();
                        this.mode = mode;
                        if (mode != null) {
                            switch (mode) {
                                case "dynamic":
                                    JimmyAddons.location = Locations.PRIVATEISLAND;
                                    return;
                                case "crystal_hollows":
                                    JimmyAddons.location = Locations.CHOLLOWS;
                                    return;
                                case "mining_3":
                                    JimmyAddons.location = Locations.DWARVENMINES;
                                    return;
                                case "dungeon":
                                    JimmyAddons.location = Locations.DUNGEON;
                                    return;
                                case "foraging_1":
                                    JimmyAddons.location = Locations.PARK;
                                    return;
                                case "combat_3":
                                    JimmyAddons.location = Locations.END;
                                    return;
                                case "hub":
                                    JimmyAddons.location = Locations.HUB;
                                    return;
                                case "crimson_isle":
                                    JimmyAddons.location = Locations.CRIMSON_ISLE;
                                    return;
                            }
                            JimmyAddons.location = Locations.NOTNULL;
                        } else {
                            JimmyAddons.location = Locations.NULL;
                        }
                    } else {
                        inSkyblock = false;
                    }
                }
            } catch (Exception e) {
                inSkyblock = false;
                JimmyAddons.location = Locations.NULL;
                e.printStackTrace();
            }
    }

    public static boolean isInSkyblock() {
        ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
        if (scoreboardObj != null) {
            if (ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName()).startsWith("SKYBLOCK")) {
                inSkyblock = true;
                return true;
            }
            inSkyblock = false;
            JimmyAddons.location = Locations.NULL;
            return false;
        }
        inSkyblock = false;
        return false;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        this.ticks = 1;
        inSkyblock = false;
        this.lastLocRaw = 0L;
        this.mode = null;
        this.joinedWorld = System.currentTimeMillis();
    }

//    public static void getLocation() {
//        if(inSkyblock) {
//            for (String s : ScoreboardHandler.getSidebarLines()) {
//                if (s.startsWith(" §7⏣")) {
//                    String sCleaned = ScoreboardHandler.cleanSB(s);
//                    switch (sCleaned.substring(sCleaned.indexOf("") + 2)) {
//                        case "Void Sepulture":
//                            JimmyAddons.location = Locations.END;
//                            break;
//                        case "The Catacombs":
//                            JimmyAddons.location = Locations.DUNGEON;
//                            break;
//                        case "Your Island":
//                            JimmyAddons.location = Locations.PRIVATEISLAND;
//                            break;
//                        case "Dark Thicket":
//                            JimmyAddons.location = Locations.PARK;
//                            break;
//                        case "Mithril Deposits":
//                            JimmyAddons.location = Locations.CHOLLOWS;
//                            break;
//                        case "Jungle":
//                            JimmyAddons.location = Locations.CHOLLOWS;
//                            break;
//                        case "Goblin Holdout":
//                            JimmyAddons.location = Locations.CHOLLOWS;
//                            break;
//                        case "Precursor Remnants":
//                            JimmyAddons.location = Locations.CHOLLOWS;
//                            break;
//                        case "Divan's Gateway":
//                            JimmyAddons.location = Locations.DWARVENMINES;
//                            break;
//                    }
//                    return;
//                }
//        }
//    }
//}

}
