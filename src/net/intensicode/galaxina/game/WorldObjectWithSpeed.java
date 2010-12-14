package net.intensicode.galaxina.game;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.WorldObject;


public final class WorldObjectWithSpeed extends WorldObject
    {
    public final PositionF speed = new PositionF();


    public final void init( final PositionF aWorldPosFixed, final float aSpeedX, final float aSpeedY )
        {
        worldPos.setTo( aWorldPosFixed );
        speed.x = aSpeedX;
        speed.y = aSpeedY;

        tickCount = animTicks = 0;
        active = true;
        }

    public final void onControlTick()
        {
        tickAnimation();
        worldPos.translate( speed );
        }
    }
