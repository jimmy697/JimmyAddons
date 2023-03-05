package com.jimmy.features.dungeons;

import com.jimmy.JimmyAddons;
import com.jimmy.config.JimmyConfig;
import com.jimmy.utils.Locations;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.w3c.dom.Entity;

public class ShowDungeonMobs {

    @SubscribeEvent
    public void onEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(event.entity.isInvisible()) {
            if(JimmyConfig.showHidden && JimmyAddons.location == Locations.DUNGEON) {
                if(event.entity instanceof EntityPlayer) {
                    //sa's
                    if(event.entity.getName().contains("Shadow Assassin")) {
                        event.entity.setInvisible(false);
                    }
                } else if (event.entity instanceof EntityEnderman) {
                    //fels
                    event.entity.setInvisible(false);
                }
            }
        }
    }
}
