package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.WorldObject;


public final class Spark extends WorldObject
    {
    public final PositionF toPos = new PositionF();

    public final SizeF sparklingArea = new SizeF();


    public final void init( final PositionF aWorldPos, final int aDurationTicks )
        {
        active = true;
        tickCount = 0;
        animTicks = aDurationTicks;
        worldPos.setTo( aWorldPos );
        }

    public final void randomize( final SizeF aSparkArea )
        {
        sparklingArea.setTo( aSparkArea.width / 6, aSparkArea.height / 6 );
        worldPos.x += theRandom.nextFloat( aSparkArea.width / 3 ) - aSparkArea.width / 6;
        worldPos.y += theRandom.nextFloat( aSparkArea.height / 3 ) - aSparkArea.height / 6;
        toPos.setTo( worldPos );
        }

    public final void onControlTick()
        {
        if ( tickCount < animTicks ) tickCount++;
        else active = false;

        worldPos.x += theRandom.nextFloat( sparklingArea.width ) - sparklingArea.width / 2;
        worldPos.y += theRandom.nextFloat( sparklingArea.height ) - sparklingArea.height / 2;
        }


    private static final Random theRandom = new Random();
    }
