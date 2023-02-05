package com.jimmy.commands;

import com.jimmy.config.JimmyConfig;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.utils.GuiUtil;

import java.util.Objects;

public class JimmyCommand extends Command {

    public JimmyCommand() {
        super("jimmyaddons");
    }

    @DefaultHandler
    public void handle() {
        GuiUtil.open(Objects.requireNonNull(JimmyConfig.INSTANCE.gui()));
    }

}
