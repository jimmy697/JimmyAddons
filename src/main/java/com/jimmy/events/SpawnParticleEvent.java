package com.jimmy.events;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
@Cancelable
public class SpawnParticleEvent extends JimmyEvent {

    EnumParticleTypes particleTypes;

    boolean isLongDistance;

    double xCoord;

    double yCoord;

    double zCoord;

    double xOffset;

    double yOffset;

    double zOffset;

    int[] params;

    public SpawnParticleEvent(EnumParticleTypes particleTypes, boolean isLongDistance, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] params) {
        this.particleTypes = particleTypes;
        this.isLongDistance = isLongDistance;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.params = params;
    }

    public EnumParticleTypes getParticleTypes() {
        return this.particleTypes;
    }

    public boolean isLongDistance() {
        return this.isLongDistance;
    }

    public double getXCoord() {
        return this.xCoord;
    }

    public double getYCoord() {
        return this.yCoord;
    }

    public double getZCoord() {
        return this.zCoord;
    }

    public double getXOffset() {
        return this.xOffset;
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public double getZOffset() {
        return this.zOffset;
    }

    public int[] getParams() {
        return this.params;
    }

}
