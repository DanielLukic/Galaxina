package net.intensicode.galaxina.game.objects;

import net.intensicode.util.Position;


public final class Smoke extends WorldObject
    {
    public final Position speedFixed = new Position();


    public final void init( final Position aWorldPosFixed, final int aDurationTicks, final int aSpeedX, final int aSpeedY )
        {
        speedFixed.x = aSpeedX;
        speedFixed.y = aSpeedY;
        active = true;
        tickCount = 0;
        animTicks = aDurationTicks;
        worldPosFixed.setTo( aWorldPosFixed );
        }

    public final void onControlTick()
        {
        if ( tickCount < animTicks ) tickCount++;
        else active = false;

        worldPosFixed.translate( speedFixed );
        }
    }
