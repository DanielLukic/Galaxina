package net.intensicode.galaxina.game.particles;

import net.intensicode.galaxina.game.*;
import net.intensicode.util.*;

public final class SimpleParticles extends GameObject
    {
    public final WorldObjectWithSpeed[] instances;


    public SimpleParticles()
        {
        this( DEFAULT_MAX_INSTANCES, FixedMath.FIXED_1, false );
        }

    public SimpleParticles( final int aMaxCount, final int aAnimDurationInSecondsFixed, final boolean aRepeatAnimation )
        {
        instances = new WorldObjectWithSpeed[DEFAULT_MAX_INSTANCES];
        for ( int idx = 0; idx < DEFAULT_MAX_INSTANCES; idx++ )
            {
            instances[ idx ] = new WorldObjectWithSpeed();
            instances[ idx ].repeatAnimation = aRepeatAnimation;
            }
        myAnimDurationInSecondsFixed = aAnimDurationInSecondsFixed;
        }

    public final void add( final Position aWorldPosFixed, final int aSpeedX, final int aSpeedY )
        {
        final WorldObjectWithSpeed warp = getInstance();
        warp.init( aWorldPosFixed, aSpeedX, aSpeedY );
        warp.animTicks = FixedMath.toInt( timing.ticksPerSecond * myAnimDurationInSecondsFixed );
        }

    // From GameObject

    public void onStartGame()
        {
        }

    public void onStartLevel()
        {
        for ( int idx = 0; idx < instances.length; idx++ )
            {
            instances[ idx ].active = false;
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < instances.length; idx++ )
            {
            final WorldObjectWithSpeed warp = instances[ idx ];
            if ( !warp.active ) continue;
            warp.onControlTick();
            }
        }

    // Implementation

    private WorldObjectWithSpeed getInstance()
        {
        int oldestIndex = 0;
        int oldestTicks = 0;

        for ( int idx = 0; idx < instances.length; idx++ )
            {
            final WorldObjectWithSpeed warp = instances[ idx ];
            if ( !warp.active ) return warp;
            if ( warp.tickCount <= oldestTicks ) continue;

            oldestTicks = warp.tickCount;
            oldestIndex = idx;
            }

        return instances[ oldestIndex ];
        }


    private final int myAnimDurationInSecondsFixed;

    private static final int DEFAULT_MAX_INSTANCES = 32;
    }
