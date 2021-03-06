package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.GameObject;

public final class Sparks extends GameObject
    {
    public final Spark[] sparks = new Spark[MAX_SPARKS];



    public Sparks()
        {
        for ( int idx = 0; idx < sparks.length; idx++ ) sparks[ idx ] = new Spark();
        }

    public final void add( final PositionF aWorldPos, final SizeF aSparkArea )
        {
        final Spark spark = getSpark();
        spark.init( aWorldPos, timing.ticksPerSecond /3);
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
            if ( !spark.active ) continue;
            spark.onControlTick();
            }
        }

    // Implementation

    private Spark getSpark()
        {
        int oldestIndex = 0;
        int oldestTicks = 0;

        for ( int idx = 0; idx < sparks.length; idx++ )
            {
            final Spark spark = sparks[ idx ];
            if ( spark.active )
                {
                if ( spark.tickCount > oldestTicks )
                    {
                    oldestTicks = spark.tickCount;
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
