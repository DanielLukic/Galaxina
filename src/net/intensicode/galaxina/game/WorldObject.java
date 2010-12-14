package net.intensicode.galaxina.game;

import net.intensicode.util.*;

public abstract class WorldObject
    {
    public boolean active;

    public final PositionF worldPos = new PositionF();

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
