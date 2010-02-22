package net.intensicode.galaxina.game.objects;

import net.intensicode.util.Position;


public final class Explosion extends WorldObjectWithType
    {
    public static final int BIG = 0;

    public static final int DEFAULT = 1;

    public static final int SPECIAL = 2;


    public final void init( final Position aWorldPosFixed, final int aDurationTicks, final int aFallingSpeedFixed )
        {
        myDriftSpeedFixed = aFallingSpeedFixed;
        active = true;
        tickCount = 0;
        animTicks = aDurationTicks;
        worldPosFixed.setTo( aWorldPosFixed );
        }

    public final void onControlTick()
        {
        if ( tickCount < animTicks ) tickCount++;
        else active = false;

        worldPosFixed.y -= myDriftSpeedFixed;
        }

    private int myDriftSpeedFixed;
    }
