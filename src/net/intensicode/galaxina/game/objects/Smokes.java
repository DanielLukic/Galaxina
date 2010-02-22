package net.intensicode.galaxina.game.objects;

import net.intensicode.util.Position;

public final class Smokes extends GameObject
    {
    public final Smoke[] smokes = new Smoke[MAX_SMOKES];


    public Smokes()
        {
        for ( int idx = 0; idx < smokes.length; idx++ ) smokes[ idx ] = new Smoke();
        }

    public final void add( final Position aWorldPosFixed )
        {
        final int speedValue = model.world.visibleSizeFixed.height / 4;
        final int fallingSpeedFixed = speedValue / timing.ticksPerSecond;
        add( aWorldPosFixed, 0, fallingSpeedFixed );
        }

    public final void add( final Position aWorldPosFixed, final int aSpeedX, final int aSpeedY )
        {
        final Smoke smoke = getSmoke();
        smoke.init( aWorldPosFixed, timing.ticksPerSecond, aSpeedX, aSpeedY );
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
            final Smoke smoke = smokes[ idx ];
            if ( !smoke.active ) continue;
            smoke.onControlTick();
            }
        }

    // Implementation

    private Smoke getSmoke()
        {
        int oldestSmokeIndex = 0;
        int oldestSmokeTicks = 0;

        for ( int idx = 0; idx < smokes.length; idx++ )
            {
            final Smoke Smoke = smokes[ idx ];
            if ( Smoke.active )
                {
                if ( Smoke.tickCount > oldestSmokeTicks )
                    {
                    oldestSmokeTicks = Smoke.tickCount;
                    oldestSmokeIndex = idx;
                    }
                continue;
                }

            return Smoke;
            }

        return smokes[ oldestSmokeIndex ];
        }


    private static final int MAX_SMOKES = 32;
    }
