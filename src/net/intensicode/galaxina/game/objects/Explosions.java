package net.intensicode.galaxina.game.objects;

import net.intensicode.util.Position;


/**
 * TODO: Describe this!
 */
public final class Explosions extends GameObject
    {
    public final Explosion[] explosions = new Explosion[MAX_EXPLOSIONS];


    public Explosions()
        {
        for ( int idx = 0; idx < explosions.length; idx++ ) explosions[ idx ] = new Explosion();
        }

    public final void addBig( final Position aWorldPosFixed )
        {
        final Explosion explosion = getExplosion();

        //final int driftSpeedFixed = model.world.visibleSizeFixed.height / 8 / timing.ticksPerSecond;
        explosion.init( aWorldPosFixed, timing.ticksPerSecond / 2, 0 );
        explosion.type = Explosion.BIG;
        }

    public final void addNormal( final Position aWorldPosFixed )
        {
        final Explosion explosion = getExplosion();

        final int driftSpeedFixed = model.world.visibleSizeFixed.height / 10 / timing.ticksPerSecond;
        explosion.init( aWorldPosFixed, timing.ticksPerSecond / 2, driftSpeedFixed );
        explosion.type = Explosion.DEFAULT;
        }

    public final void addSpecial( final Position aWorldPosFixed )
        {
        final Explosion explosion = getExplosion();

        final int driftSpeedFixed = model.world.visibleSizeFixed.height / 10 / timing.ticksPerSecond;
        explosion.init( aWorldPosFixed, timing.ticksPerSecond / 2, -driftSpeedFixed );
        explosion.type = Explosion.SPECIAL;
        }

    public final void addForPlayer( final Position aWorldPosFixed )
        {
        final Explosion explosion = getExplosion();

        final int driftSpeedFixed = model.world.visibleSizeFixed.height / 12 / timing.ticksPerSecond;
        explosion.init( aWorldPosFixed, timing.ticksPerSecond / 2, -driftSpeedFixed );
        explosion.type = Explosion.DEFAULT;
        }

    public final void addBigPlayer( final Position aWorldPosFixed )
        {
        final Explosion explosion = getExplosion();

        final int driftSpeedFixed = model.world.visibleSizeFixed.height / 12 / timing.ticksPerSecond;
        explosion.init( aWorldPosFixed, timing.ticksPerSecond / 2, -driftSpeedFixed );
        explosion.type = Explosion.BIG;
        }

    // From GameObject

    public void onStartGame()
        {
        }

    public void onStartLevel()
        {
        for ( int idx = 0; idx < explosions.length; idx++ )
            {
            explosions[ idx ].active = false;
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < explosions.length; idx++ )
            {
            final Explosion explosion = explosions[ idx ];
            if ( explosion.active == false ) continue;
            explosion.onControlTick();
            }
        }

    // Implementation

    private final Explosion getExplosion()
        {
        int oldestExplosionIndex = 0;
        int oldestExplosionTicks = 0;

        for ( int idx = 0; idx < explosions.length; idx++ )
            {
            final Explosion explosion = explosions[ idx ];
            if ( explosion.active )
                {
                if ( explosion.explodeTick > oldestExplosionTicks )
                    {
                    oldestExplosionTicks = explosion.explodeTick;
                    oldestExplosionIndex = idx;
                    }
                continue;
                }

            return explosion;
            }

        return explosions[ oldestExplosionIndex ];
        }


    private static final int MAX_EXPLOSIONS = 32;
    }
