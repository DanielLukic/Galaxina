package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.enemies.Enemy;
import net.intensicode.util.Position;
import net.intensicode.util.DynamicArray;



/**
 * TODO: Describe this!
 */
public final class Bomb
    {
    public static final int FROM_ENEMY = 0;

    public static final int FROM_PLAYER = 1;

    public static final int FROM_MISSILE = 2;

    public final Position worldPosFixed = new Position();

    public final Position speedFixed = new Position();

    public boolean active;



    public Bomb()
        {
        }

    public final void start( final int aOwnerID )
        {
        myOwner = aOwnerID;
        }

    public final void init( final Position aWorldPosFixed, final int aDropSpeedFixed )
        {
        init( aWorldPosFixed, 0, aDropSpeedFixed );
        }

    public final void init( final Position aWorldPosFixed, final int aSpeedX, final int aSpeedY )
        {
        active = true;
        myOwner = INVALID;
        speedFixed.x = aSpeedX;
        speedFixed.y = aSpeedY;
        worldPosFixed.setTo( aWorldPosFixed );
        }

    public final void onControlTick( final GameModel aGameModel )
        {
        //#if DEBUG
        if ( myOwner == INVALID ) throw new IllegalStateException();
        //#endif

        if ( !active ) return;

        worldPosFixed.translate( speedFixed );

        final World world = aGameModel.world;
        active = world.isInside( worldPosFixed );
        if (!active) GameObject.model.level.onEnemyBombDone();

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

            final boolean hit = enemy.isHit( worldPosFixed );
            if ( !hit ) continue;

            enemy.hit();

            active = false;
            break;
            }
        }

    private void checkIfPlayerHit( final GameModel aGameModel )
        {
        final Player player = aGameModel.player;
        final int hitKind = player.isHit( worldPosFixed );
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
