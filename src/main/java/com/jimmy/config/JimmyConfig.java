package com.jimmy.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class JimmyConfig extends Vigilant {

    //Foraging Macro
    @Property(
            type = PropertyType.SWITCH, name = "Foraging Macro", description = "Enables whether the /foragingmacro command will enable the macro", category = "Foraging Macro"
    )
    public static boolean configForaging = true;

    @Property(
            type = PropertyType.SWITCH, name = "Rod Swap", description = "Rod swaps for autopet with the macro.", category = "Foraging Macro"
    )
    public static boolean rodSwap = true;

    @Property(
            type = PropertyType.SLIDER, name = "Foraging Macro ACTION Delay", description = "The delay between the macros actions (in ticks). 1 tick = 50ms, eg 80 Ticks = 4 seconds. Use high delay if  high  ping!", category = "Foraging Macro", max = 80
    )
    public static int foragingActionDelay;

    @Property(
            type = PropertyType.SLIDER, name = "Foraging Macro CYCLE Delay", description = "The delay between 1 full cycle of placing saplings, bonemeal, rodswap, then breaking it and another cycle. (in ticks). Recommended amount is 20-30", category = "Foraging Macro", max = 80
    )
    public static int foragingCycleDelay;

    //Glowing Mushroom Nuker
    @Property(
            type = PropertyType.SWITCH, name = "Glowing Mushroom Aura/Nuker", description = "Automatically destroys glowing mushrooms in the player's range. Also highlights them.", category = "Nuker"
    )
    public static boolean shroomNuker = false;

    public static JimmyConfig INSTANCE = new JimmyConfig();

    //Auto Close Chests
    @Property(
            type = PropertyType.SWITCH, name = "Auto Close Secret Chests", description = "does what the title says", category = "Dungeons", subcategory = "Misc"
    )
    public static boolean autoClose = false;

    //Show Hidden Entities
    @Property(
            type = PropertyType.SWITCH, name = "Show Hidden Dungeon Mobs", description = "title.", category = "Dungeons", subcategory = "Render"
    )
    public static boolean showHidden = false;

    //Auto Ready
    @Property(
            type = PropertyType.SWITCH, name = "Auto Ready Up", description = "Automatically ready's you up in a dungeon when opening mort", category = "Dungeons", subcategory = "Misc"
    )
    public static boolean autoReady = false;

    //Tp With Anything
    @Property(
            type = PropertyType.SWITCH,
            name = "Teleport With Anything",
            description = "Allows you to teleport with anything on right click.",
            category = "Player",
            subcategory = "Misc"
    )
    public static boolean tpAnything = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "TP Slot",
            description = "The slot ur item is in u want to tp with. 0 = 1st slot, and so on.",
            category = "Player",
            subcategory = "Misc",
            max = 8
    )
    public static int tpSlot = 0;

    //Auto Join Sb
    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Join Skyblock",
            description = "Joins skyblock on key press, you can change this in ur keybind settings.",
            category = "Player",
            subcategory = "Misc"
    )
    public static boolean autoSB = true;


    public JimmyConfig() {
        super(new File("./config/jimmyaddons.toml"));
        initialize();
    }

}
