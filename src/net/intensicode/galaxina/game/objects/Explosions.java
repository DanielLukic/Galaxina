package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;
import net.intensicode.galaxina.game.GameObject;


public final class Explosions extends GameObject
    {
    public final Explosion[] explosions = new Explosion[MAX_EXPLOSIONS];


    public Explosions()
        {
        for ( int idx = 0; idx < explosions.length; idx++ ) explosions[ idx ] = new Explosion();
        }

    public final void addBig( final PositionF aWorldPos )
        {
        final Explosion explosion = getExplosion();

        explosion.init( aWorldPos, timing.ticksPerSecond / 2, 0 );
        explosion.type = Explosion.BIG;
        }

    public final void addNormal( final PositionF aWorldPos )
        {
        final Explosion explosion = getExplosion();

        final float driftSpeed = model.world.visibleSize.height / 10 / timing.ticksPerSecond;
        explosion.init( aWorldPos, timing.ticksPerSecond / 2, driftSpeed );
        explosion.type = Explosion.DEFAULT;
        }

    public final void addSpecial( final PositionF aWorldPos )
        {
        final Explosion explosion = getExplosion();

        final float driftSpeed = model.world.visibleSize.height / 10 / timing.ticksPerSecond;
        explosion.init( aWorldPos, timing.ticksPerSecond / 2, -driftSpeed );
        explosion.type = Explosion.SPECIAL;
        }

    public final void addForPlayer( final PositionF aWorldPos )
        {
        final Explosion explosion = getExplosion();

        final float driftSpeed = model.world.visibleSize.height / 12 / timing.ticksPerSecond;
        explosion.init( aWorldPos, timing.ticksPerSecond / 2, -driftSpeed );
        explosion.type = Explosion.DEFAULT;
        }

    public final void addBigPlayer( final PositionF aWorldPos )
        {
        final Explosion explosion = getExplosion();

        final float driftSpeed = model.world.visibleSize.height / 12 / timing.ticksPerSecond;
        explosion.init( aWorldPos, timing.ticksPerSecond / 2, -driftSpeed );
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
                if ( explosion.tickCount > oldestExplosionTicks )
                    {
                    oldestExplosionTicks = explosion.tickCount;
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
