package com.jimmy.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jimmy.JimmyAddons;
import net.minecraft.client.Minecraft;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Whitelist {
//
//    public static Minecraft mc = Minecraft.getMinecraft();
//
//    public static boolean whitelisted = false;
//
//    public static JsonElement getJson(String jsonUrl) {
//        return (new JsonParser()).parse(getInputStream(jsonUrl));
//    }
//
//    public static InputStreamReader getInputStream(String url) {
//        try {
//            URLConnection conn = (new URL(url)).openConnection();
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//            return new InputStreamReader(conn.getInputStream());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void checkForWhitelist() {
//        String uuid = mc.getSession().getPlayerID();
//
//        for(JsonElement usr : JimmyAddons.response) {
//            if(usr.getAsJsonObject().get("mcUUID").equals(uuid)) {
//                whitelisted = true;
//            }
//        }
//    }

}
