package net.intensicode.game.objects;

import net.intensicode.core.Engine;
import net.intensicode.util.Position;
import net.intensicode.util.Size;


/**
 * TODO: Describe this!
 */
public final class Sparks extends GameObject
    {
    public final Spark[] sparks = new Spark[MAX_SPARKS];



    public Sparks()
        {
        for ( int idx = 0; idx < sparks.length; idx++ ) sparks[ idx ] = new Spark();
        }

    public final void add( final Position aWorldPosFixed, final Size aSparkArea )
        {
        final Spark spark = getSpark();
        spark.init( aWorldPosFixed, Engine.ticksPerSecond /3);
        spark.randomize( aSparkArea );
        }

    // From GameObject

    public void onStartGame()
        {
        }

    public void onStartLevel()
        {
        for ( int idx = 0; idx < sparks.length; idx++ )
            {
            sparks[ idx ].active = false;
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < sparks.length; idx++ )
            {
            final Spark spark = sparks[ idx ];
            if ( spark.active == false ) continue;
            spark.onControlTick();
            }
        }

    // Implementation

    private final Spark getSpark()
        {
        int oldestIndex = 0;
        int oldestTicks = 0;

        for ( int idx = 0; idx < sparks.length; idx++ )
            {
            final Spark spark = sparks[ idx ];
            if ( spark.active )
                {
                if ( spark.tick > oldestTicks )
                    {
                    oldestTicks = spark.tick;
                    oldestIndex = idx;
                    }
                continue;
                }

            return spark;
            }

        return sparks[ oldestIndex ];
        }


    private static final int MAX_SPARKS = 32;
    }
