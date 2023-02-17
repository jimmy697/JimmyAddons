package com.jimmy.events;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.awt.*;

public class JimmyEvent extends Event {
    public boolean post() {
        MinecraftForge.EVENT_BUS.post(this);
        return (isCancelable() && isCanceled());
    }

    public void cancel() {
        setCanceled(true);
    }

}
