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


    //Chest aura
    @Property(
            type = PropertyType.SWITCH, name = "Chest Aura/Secret Aura", description = "does what the title says, but kinda shitty and USE AT YOUR OWN RISK!", category = "Dungeons"
    )
    public static boolean chestAura = false;

    //gemstone esp
//    @Property(
//            type = PropertyType.SWITCH, name = "Gemstone ESP", category = "Gemstone ESP"
//    )
//    public static boolean gemEsp = false;
//
//    @Property(
//            type = PropertyType.SWITCH, name = "Ruby", category = "Gemstone ESP"
//    )
//    public static boolean rubyESP = false;
//
//    @Property(
//            type = PropertyType.SWITCH, name = "Jade", category = "Gemstone ESP"
//    )
//    public static boolean jadeESP = false;
//
//    @Property(
//            type = PropertyType.SWITCH, name = "Amber", category = "Gemstone ESP"
//    )
//    public static boolean amberESP = false;
//
//    @Property(
//            type = PropertyType.SWITCH, name = "Topaz", category = "Gemstone ESP"
//    )
//    public static boolean topazESP = false;
//
//    @Property(
//            type = PropertyType.SWITCH, name = "Sapphire", category = "Gemstone ESP"
//    )
//    public static boolean sapphireESP = false;
//
//    @Property(
//            type = PropertyType.SWITCH, name = "Amethyst", category = "Gemstone ESP"
//    )
//    public static boolean amethystESP = false;

    public static JimmyConfig INSTANCE = new JimmyConfig();

    public JimmyConfig() {
        super(new File("./config/jimmyaddons.toml"));
        initialize();
    }

}
