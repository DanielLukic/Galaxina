package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.*;
import net.intensicode.util.*;

public final class Debris extends WorldObjectWithType
    {
    public static final int TYPE_BIG = 0;

    public static final int TYPE_SMALL = 1;

    public static final int TYPE_MEDIUM = 2;

    public final PositionF speed = new PositionF();

    public int timeOut;


    public final void init( final PositionF aWorldPos, final float aSpeedX, final float aSpeedY )
        {
        timeOut = 0;
        type = TYPE_MEDIUM;
        active = true;
        speed.x = aSpeedX;
        speed.y = aSpeedY;
        worldPos.setTo( aWorldPos );
        tickCount = 0;
        animTicks = GameObject.timing.ticksPerSecond / 2;
        repeatAnimation = true;
        }

    public final void onControlTick()
        {
        if ( timeOut > 0 )
            {
            timeOut--;
            if ( timeOut == 0 ) active = false;
            }
        if ( !active ) return;

        tickAnimation();

        worldPos.translate( speed );

        final GameModel model = GameObject.model;
        active = model.world.isInside( worldPos );

        checkIfEnemyHit( model );
        checkIfPlayerHit( model );
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
            active = false;
            }
        }
    }
