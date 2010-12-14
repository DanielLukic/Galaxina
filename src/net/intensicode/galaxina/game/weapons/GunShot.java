package net.intensicode.galaxina.game.weapons;

import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.weapons.Weapon;
import net.intensicode.util.*;


public final class GunShot extends WorldObject
    {
    public float speed;

    public Weapon source;


    public final GunShot init( final PositionF aStartPosition, final float aSpeed )
        {
        worldPos.setTo( aStartPosition );
        speed = aSpeed;
        active = true;
        myCollisionCheckSteps = GameObject.model.configuration.readInt( "GunShot.CollisionCheckSteps", DEFAULT_COLLISION_CHECK_STEPS );
        return this;
        }

    public final void onControlTick( final GameModel aGameModel )
        {
        if ( !active ) return;

        final float speedStep = speed / myCollisionCheckSteps;
        for ( int step = 0; step < myCollisionCheckSteps; step++ )
            {
            worldPos.y -= speedStep;

            final World world = aGameModel.world;
            active = world.isInside( worldPos );
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

                final boolean hit = enemy.isHit( worldPos );
                if ( !hit ) continue;

                enemy.hit();

                active = false;
                if ( source != null ) source.onProjectileDone( this );
                return;
                }
            }
        }


    private int myCollisionCheckSteps;

    private static final int DEFAULT_COLLISION_CHECK_STEPS = 2;
    }
