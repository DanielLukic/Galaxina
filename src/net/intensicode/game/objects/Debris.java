package net.intensicode.game.objects;

import net.intensicode.core.Engine;
import net.intensicode.game.enemies.Enemy;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.Position;



/**
 * TODO: Describe this!
 */
public final class Debris
    {
    public static final int TYPE_BIG = 0;

    public static final int TYPE_SMALL = 1;

    public static final int TYPE_MEDIUM = 2;

    public final Position worldPosFixed = new Position();

    public final Position speedFixed = new Position();

    public boolean active;

    public int tickCount;

    public int animTicks;

    public int timeOut;

    public int type;



    public Debris()
        {
        }

    public final void init( final Position aWorldPosFixed, final int aSpeedX, final int aSpeedY )
        {
        timeOut = 0;
        type = TYPE_MEDIUM;
        active = true;
        speedFixed.x = aSpeedX;
        speedFixed.y = aSpeedY;
        worldPosFixed.setTo( aWorldPosFixed );
        tickCount = 0;
        animTicks = Engine.ticksPerSecond / 2;
        }

    public final void onControlTick()
        {
        if ( timeOut > 0 )
            {
            timeOut--;
            if ( timeOut == 0 ) active = false;
            }
        if ( active == false ) return;

        if ( tickCount < animTicks ) tickCount++;
        else tickCount = 0;
        
        worldPosFixed.translate( speedFixed );

        final GameModel model = GameObject.model;
        active = model.world.isInside( worldPosFixed );

        checkIfEnemyHit( model );
        checkIfPlayerHit( model );
        }

    // Implementation

    private final void checkIfEnemyHit( final GameModel aGameModel )
        {
        final DynamicArray enemies = aGameModel.level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( enemy.active == false ) continue;

            final boolean hit = enemy.isHit( worldPosFixed );
            if ( hit == false ) continue;

            enemy.hit();

            active = false;
            break;
            }
        }

    private final void checkIfPlayerHit( final GameModel aGameModel )
        {
        final Player player = aGameModel.player;
        final int hitKind = player.isHit( worldPosFixed );
        if ( hitKind != Player.NOT_HIT )
            {
            player.hit( hitKind );
            active = false;
            }
        }
    }
