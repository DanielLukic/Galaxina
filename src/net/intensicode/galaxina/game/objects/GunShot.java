package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.objects.Weapon;
import net.intensicode.galaxina.game.*;
import net.intensicode.util.*;


public final class GunShot extends WorldObject
    {
    public int speedFixed;

    public Weapon source;


    public final GunShot init( final Position aStartPosition, final int aFixedSpeed )
        {
        worldPosFixed.setTo( aStartPosition );
        speedFixed = aFixedSpeed;
        active = true;
        myCollisionCheckSteps = GameObject.model.configuration.readInt( "GunShot.CollisionCheckSteps", DEFAULT_COLLISION_CHECK_STEPS );
        return this;
        }

    public final void onControlTick( final GameModel aGameModel )
        {
        if ( !active ) return;

        final int speedStepFixed = speedFixed / myCollisionCheckSteps;
        for ( int step = 0; step < myCollisionCheckSteps; step++ )
            {
            worldPosFixed.y -= speedStepFixed;

            final World world = aGameModel.world;
            active = world.isInside( worldPosFixed );
            if ( !active )
                {
                if ( source != null ) source.onProjectileDone( this );
                return;
                }

            final DynamicArray enemies = aGameModel.level.activeEnemies;
            for ( int idx = 0; idx < enemies.size; idx++ )
                {
                final Enemy enemy = (Enemy) enemies.objects[ idx ];
                if ( !enemy.active ) continue;

                final boolean hit = enemy.isHit( worldPosFixed );
                if ( !hit ) continue;

                enemy.hit();

                active = false;
                if ( source != null ) source.onProjectileDone( this );
                return;
                }
            }
        }


    private int myCollisionCheckSteps;

    private static final int DEFAULT_COLLISION_CHECK_STEPS = 3;
    }
