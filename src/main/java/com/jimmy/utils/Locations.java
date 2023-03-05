package com.jimmy.utils;

import com.jimmy.JimmyAddons;
import net.minecraft.client.Minecraft;

public enum Locations {

    //PIZZA CLIENT LOCATION IMPLEMENTATION

    PRIVATEISLAND("Private Island"),
    CHOLLOWS("Crystal Hollows"),
    DWARVENMINES("Dwarven Mines"),
    PARK("Park"),
    END("The End"),
    DUNGEON("Dungeons"),
    HUB("The Hub"),
    NULL("None"),
    NOTNULL("Unknown"),
    CRIMSON_ISLE("Crimson Isle");

    private final String name;

    private final Runnable warpBack;

    Locations(String name) {
        this.name = name;
        this.warpBack = null;
    }

    Locations(String name, Runnable warpBack) {
        this.name = name;
        this.warpBack = warpBack;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}

