package net.intensicode.game.objects;

import net.intensicode.util.Position;
import net.intensicode.util.Random;
import net.intensicode.util.Size;



/**
 * TODO: Describe this!
 */
public final class Spark
    {
    public final Position fromPosFixed = new Position();

    public final Position toPosFixed = new Position();

    public final Size sparklingArea = new Size();

    public int durationTicks;

    public int tick;

    public boolean active;



    public final void init( final Position aWorldPosFixed, final int aDurationTicks )
        {
        active = true;
        tick = 0;
        durationTicks = aDurationTicks;
        fromPosFixed.setTo( aWorldPosFixed );
        }

    public final void randomize( final Size aSparkArea )
        {
        sparklingArea.setTo( aSparkArea.width / 6, aSparkArea.height / 6 );
        fromPosFixed.x += theRandom.nextInt( aSparkArea.width / 3 ) - aSparkArea.width / 6;
        fromPosFixed.y += theRandom.nextInt( aSparkArea.height / 3 ) - aSparkArea.height / 6;
        toPosFixed.setTo( fromPosFixed );
        }

    public final void onControlTick()
        {
        if ( tick < durationTicks ) tick++;
        else active = false;

        fromPosFixed.x += theRandom.nextInt( sparklingArea.width ) - sparklingArea.width / 2;
        fromPosFixed.y += theRandom.nextInt( sparklingArea.height ) - sparklingArea.height / 2;
        }



    private static final Random theRandom = new Random();
    }
