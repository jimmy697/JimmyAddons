package com.jimmy;

import com.jimmy.commands.*;
import com.jimmy.events.JimmyEvent;
import com.jimmy.events.TickStartEvent;
import com.jimmy.features.dungeons.AutoClose;
import com.jimmy.features.dungeons.AutoReady;
import com.jimmy.features.dungeons.ShowDungeonMobs;
import com.jimmy.features.nuker.GlowingMushroomNuker;
import com.jimmy.features.player.TpWithAnything;
import com.jimmy.keybinds.AutoJoinSb;
import com.jimmy.macros.ForagingMacro;
import com.jimmy.macros.MacroUtil;
import com.jimmy.utils.Locations;
import com.jimmy.utils.SBInfo;
import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = "jimmyaddons", name = "JimmyAddons", version = "1.0.0", clientSideOnly = true)
@SideOnly(Side.CLIENT)
public class JimmyAddons {

    public static boolean foragingEnabled = false;

    public static Locations location = Locations.NULL;

    private KeyBinding autoJoinSb;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerCommands(
                new JimmyCommand(), new ForagingCommand(), new TickTest()
        );
        registerKeybinds(
                autoJoinSb = new AutoJoinSb()
        );
        registerEvents(
                new MacroUtil(),
                new ForagingMacro(),
                new JimmyEvent(),
                new GlowingMushroomNuker(),
                new AutoClose(),
                new SBInfo(),
                new TickStartEvent(),
                new ShowDungeonMobs(),
                new AutoReady(),
                new TpWithAnything(),
                autoJoinSb
        );

    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        location = Locations.NULL;
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

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if(Minecraft.getMinecraft().theWorld == null)
            return;
        MinecraftForge.EVENT_BUS.post((Event)new TickStartEvent());
    }
}
