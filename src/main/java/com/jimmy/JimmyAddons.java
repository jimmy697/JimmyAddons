package com.jimmy;

import com.jimmy.commands.*;
import com.jimmy.features.ChestAura;
import com.jimmy.macros.ForagingMacro;
import com.jimmy.macros.MacroUtil;
import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@Mod(modid = "jimmyaddons", name = "JimmyAddons", version = "1.0.0", clientSideOnly = true)
@SideOnly(Side.CLIENT)
public class JimmyAddons {

    @Mod.Instance("jimmyaddons")
    public static JimmyAddons instance;

    public static File modFile = null;

    public static boolean foragingEnabled = false;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerCommands(
                new JimmyCommand(), new ForagingCommand()
        );
        registerEvents(
                new MacroUtil(), new ForagingMacro(), new ChestAura()
        );
        registerKeybinds(

        );
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modFile = event.getSourceFile();
    }

    private void registerKeybinds(KeyBinding... keybinds) {
        for (KeyBinding keybind : keybinds) {
            ClientRegistry.registerKeyBinding(keybind);
        }
    }

    private void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }

    private void registerCommands(Command... commands) {
        for (Command command : commands) {
            EssentialAPI.getCommandRegistry().registerCommand(command);
        }
    }
}
