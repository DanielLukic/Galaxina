package net.intensicode.galaxina.game;

import net.intensicode.util.Position;
import net.intensicode.galaxina.game.WorldObject;


public final class WorldObjectWithSpeed extends WorldObject
    {
    public final Position speedFixed = new Position();


    public final void init( final Position aWorldPosFixed, final int aSpeedX, final int aSpeedY )
        {
        worldPosFixed.setTo( aWorldPosFixed );
        speedFixed.x = aSpeedX;
        speedFixed.y = aSpeedY;

        tickCount = animTicks = 0;
        active = true;
        }

    public final void onControlTick()
        {
        tickAnimation();

        worldPosFixed.translate( speedFixed );
        }
    }
