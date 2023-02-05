package com.jimmy.commands;

import com.jimmy.utils.SendChat;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

import static com.jimmy.JimmyAddons.foragingEnabled;
import static com.jimmy.config.JimmyConfig.configForaging;

public class ForagingCommand extends Command {

    public ForagingCommand() {
        super("foragingmacro");
    }

    @DefaultHandler
    public void handle() {
        if(configForaging) {
            foragingEnabled = !foragingEnabled;
            String str = foragingEnabled  ? "Foraging Macro Enabled" : "Foraging Macro Disabled";
            SendChat.chat(str);
        }
    }

}
