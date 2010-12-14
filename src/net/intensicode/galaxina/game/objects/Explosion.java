package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.WorldObjectWithType;


public final class Explosion extends WorldObjectWithType
    {
    public static final int BIG = 0;

    public static final int DEFAULT = 1;

    public static final int SPECIAL = 2;


    public final void init( final PositionF aWorldPos, final int aDurationTicks, final float aFallingSpeed )
        {
        myDriftSpeed = aFallingSpeed;
        active = true;
        tickCount = 0;
        animTicks = aDurationTicks;
        worldPos.setTo( aWorldPos );
        }

    public final void onControlTick()
        {
        if ( tickCount < animTicks ) tickCount++;
        else active = false;

        worldPos.y -= myDriftSpeed;
        }

    private float myDriftSpeed;
    }
