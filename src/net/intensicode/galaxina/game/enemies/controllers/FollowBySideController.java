package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.*;

public final class FollowBySideController extends EnemyController
    {
    public FollowBySideController()
        {
        readyUsesSyncSource = true;
        }

    public final void onInitialize( final Enemy aEnemy )
        {
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        final boolean endOfPath = aEnemy.moveAlongPath();

        final PositionF direction = aEnemy.path.getDirection( aEnemy.pathPos );
        myTempDirection.setTo( direction );
        myTempDirection.normalize();

        if ( aEnemy.syncMode == Enemy.SYNC_LEFT )
            {
            final float newValue = myTempDirection.x;
            myTempDirection.x = +myTempDirection.y;
            myTempDirection.y = -newValue;
            }
        if ( aEnemy.syncMode == Enemy.SYNC_RIGHT )
            {
            final float newValue = myTempDirection.x;
            myTempDirection.x = -myTempDirection.y;
            myTempDirection.y = +newValue;
            }
        myTempDirection.scale( aEnemy.type.sizeInWorld.width );

        aEnemy.worldPos.translate( myTempDirection );

        if ( endOfPath ) aEnemy.onEndOfPath();
        }

    private final PositionF myTempDirection = new PositionF();
    }
