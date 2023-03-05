package com.jimmy.commands;

import com.google.gson.JsonElement;
import com.jimmy.JimmyAddons;
import com.jimmy.utils.SendChat;
import com.jimmy.utils.Whitelist;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import org.lwjgl.Sys;

public class TickTest extends Command {

    public TickTest() {
        super("ticktest");
    }

    @DefaultHandler
    public void handle() {
        SendChat.chat(String.valueOf(JimmyAddons.location));
//        System.out.println(JimmyAddons.response);
//        System.out.println(JimmyAddons.response.size());
//        System.out.println(Whitelist.whitelisted);
//        for(JsonElement ele : JimmyAddons.response) {
//            System.out.println(ele.getAsJsonObject().get("mcUUID"));
//        }
    }
}
