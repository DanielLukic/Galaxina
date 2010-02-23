package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.*;
import net.intensicode.util.Position;

public final class Smokes extends GameObject
    {
    public final WorldObjectWithSpeed[] smokes = new WorldObjectWithSpeed[MAX_INSTANCES];


    public Smokes()
        {
        for ( int idx = 0; idx < smokes.length; idx++ ) smokes[ idx ] = new WorldObjectWithSpeed();
        }

    public final void add( final Position aWorldPosFixed )
        {
        final int speedValue = model.world.visibleSizeFixed.height / 4;
        final int fallingSpeedFixed = speedValue / timing.ticksPerSecond;
        add( aWorldPosFixed, 0, fallingSpeedFixed );
        }

    public final void add( final Position aWorldPosFixed, final int aSpeedX, final int aSpeedY )
        {
        final WorldObjectWithSpeed smoke = getInstance();
        smoke.init( aWorldPosFixed, aSpeedX, aSpeedY );
        smoke.animTicks = timing.ticksPerSecond;
        }

    // From GameObject

    public void onStartGame()
        {
        }

    public void onStartLevel()
        {
        for ( int idx = 0; idx < smokes.length; idx++ )
            {
            smokes[ idx ].active = false;
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < smokes.length; idx++ )
            {
            final WorldObjectWithSpeed smoke = smokes[ idx ];
            if ( !smoke.active ) continue;
            smoke.onControlTick();
            }
        }

    // Implementation

    private WorldObjectWithSpeed getInstance()
        {
        int oldestIndex = 0;
        int oldestTicks = 0;

        for ( int idx = 0; idx < smokes.length; idx++ )
            {
            final WorldObjectWithSpeed instance = smokes[ idx ];
            if ( !instance.active ) return instance;
            if ( instance.tickCount <= oldestTicks ) continue;
            oldestTicks = instance.tickCount;
            oldestIndex = idx;
            }

        return smokes[ oldestIndex ];
        }


    private static final int MAX_INSTANCES = 32;
    }
