package net.intensicode.galaxina.game.objects;

import net.intensicode.util.Position;

public abstract class WorldObject
    {
    public boolean active;

    public final Position worldPosFixed = new Position();

    public int animTicks;

    public int tickCount;


    public final void tickAnimation()
        {
        if ( tickCount < animTicks ) tickCount++;
        else tickCount = 0;
        }
    }
