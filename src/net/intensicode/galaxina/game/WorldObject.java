package net.intensicode.galaxina.game;

import net.intensicode.util.Position;

public abstract class WorldObject
    {
    public boolean active;

    public final Position worldPosFixed = new Position();

    public boolean repeatAnimation;

    public int animTicks;

    public int tickCount;


    public final void tickAnimation()
        {
        if ( tickCount < animTicks ) tickCount++;
        else if ( repeatAnimation ) tickCount = 0;
        else active = false;
        }
    }
