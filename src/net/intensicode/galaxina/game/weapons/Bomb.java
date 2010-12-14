package net.intensicode.galaxina.game.weapons;

import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.util.*;


public final class Bomb extends WorldObject
    {
    public static final int FROM_ENEMY = 0;

    public static final int FROM_PLAYER = 1;

    public static final int FROM_MISSILE = 2;

    public final PositionF speed = new PositionF();


    public Bomb()
        {
        }

    public final void start( final int aOwnerID )
        {
        myOwner = aOwnerID;
        }

    public final void init( final PositionF aWorldPos, final float aDropSpeed )
        {
        init( aWorldPos, 0, aDropSpeed );
        }

    public final void init( final PositionF aWorldPos, final float aSpeedX, final float aSpeedY )
        {
        active = true;
        myOwner = INVALID;
        speed.x = aSpeedX;
        speed.y = aSpeedY;
        worldPos.setTo( aWorldPos );
        }

    public final void onControlTick( final GameModel aGameModel )
        {
        //#if DEBUG
        if ( myOwner == INVALID ) throw new IllegalStateException();
        //#endif

        if ( !active ) return;

        worldPos.translate( speed );

        final World world = aGameModel.world;
        active = world.isInside( worldPos );
        if ( !active ) GameObject.model.level.onEnemyBombDone();

        if ( myOwner == FROM_PLAYER || myOwner == FROM_MISSILE )
            {
            checkIfEnemyHit( aGameModel );
            }
        else if ( myOwner == FROM_ENEMY )
            {
            checkIfPlayerHit( aGameModel );
            }
        }

    // Implementation

    private void checkIfEnemyHit( final GameModel aGameModel )
        {
        final DynamicArray enemies = aGameModel.level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( !enemy.active ) continue;

            final boolean hit = enemy.isHit( worldPos );
            if ( !hit ) continue;

            enemy.hit();

            active = false;
            break;
            }
        }

    private void checkIfPlayerHit( final GameModel aGameModel )
        {
        final Player player = aGameModel.player;
        final int hitKind = player.isHit( worldPos );
        if ( hitKind != Player.NOT_HIT )
            {
            player.hit( hitKind );
            GameObject.model.level.onEnemyBombDone();
            active = false;
            }
        }

    private int myOwner;

    private static final int INVALID = -1;
    }
