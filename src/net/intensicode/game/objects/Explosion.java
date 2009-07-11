package net.intensicode.game.objects;

import net.intensicode.util.Position;



/**
 * TODO: Describe this!
 */
public final class Explosion
    {
    public static final int BIG = 0;

    public static final int DEFAULT = 1;

    public static final int SPECIAL = 2;

    public final Position worldPosFixed = new Position();

    public int explodeTick;

    public int durationTicks;

    public boolean active;

    public int type;
    


    public final void init( final Position aWorldPosFixed, final int aDurationTicks, final int aFallingSpeedFixed )
        {
        myDriftSpeedFixed = aFallingSpeedFixed;
        active = true;
        explodeTick = 0;
        durationTicks = aDurationTicks;
        worldPosFixed.setTo( aWorldPosFixed );
        }

    public final void onControlTick()
        {
        if ( explodeTick < durationTicks ) explodeTick++;
        else active = false;

        worldPosFixed.y -= myDriftSpeedFixed;
        }

    private int myDriftSpeedFixed;
    }
