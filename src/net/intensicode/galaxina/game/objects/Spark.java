package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.WorldObject;


public final class Spark extends WorldObject
    {
    public final Position toPosFixed = new Position();

    public final Size sparklingArea = new Size();


    public final void init( final Position aWorldPosFixed, final int aDurationTicks )
        {
        active = true;
        tickCount = 0;
        animTicks = aDurationTicks;
        worldPosFixed.setTo( aWorldPosFixed );
        }

    public final void randomize( final Size aSparkArea )
        {
        sparklingArea.setTo( aSparkArea.width / 6, aSparkArea.height / 6 );
        worldPosFixed.x += theRandom.nextInt( aSparkArea.width / 3 ) - aSparkArea.width / 6;
        worldPosFixed.y += theRandom.nextInt( aSparkArea.height / 3 ) - aSparkArea.height / 6;
        toPosFixed.setTo( worldPosFixed );
        }

    public final void onControlTick()
        {
        if ( tickCount < animTicks ) tickCount++;
        else active = false;

        worldPosFixed.x += theRandom.nextInt( sparklingArea.width ) - sparklingArea.width / 2;
        worldPosFixed.y += theRandom.nextInt( sparklingArea.height ) - sparklingArea.height / 2;
        }


    private static final Random theRandom = new Random();
    }
