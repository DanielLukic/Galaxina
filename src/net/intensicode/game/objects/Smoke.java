package net.intensicode.game.objects;

import net.intensicode.util.Position;



/**
 * TODO: Describe this!
 */
public final class Smoke
{
    public final Position worldPosFixed = new Position();

    public final Position speedFixed = new Position();

    public int smokeTick;

    public int durationTicks;

    public boolean active;



    public final void init( final Position aWorldPosFixed, final int aDurationTicks, final int aSpeedX, final int aSpeedY )
    {
        speedFixed.x = aSpeedX;
        speedFixed.y = aSpeedY;
        active = true;
        smokeTick = 0;
        durationTicks = aDurationTicks;
        worldPosFixed.setTo( aWorldPosFixed );
    }

    public final void onControlTick()
    {
        if ( smokeTick < durationTicks ) smokeTick++;
        else active = false;

        worldPosFixed.translate( speedFixed );
    }
}
