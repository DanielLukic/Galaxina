package net.intensicode.game.enemies.controllers;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyController;
import net.intensicode.util.Position;

public final class FollowBySideController extends EnemyController
    {
    public final void onInitialize( final Enemy aEnemy )
        {
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        final boolean endOfPath = aEnemy.moveAlongPath();

        final Position direction = aEnemy.path.getDirection( aEnemy.pathPos );
        myTempDirection.setTo( direction );
        myTempDirection.normalizeFixed();

        if ( aEnemy.syncMode == Enemy.SYNC_LEFT )
            {
            final int newValue = myTempDirection.x;
            myTempDirection.x = +myTempDirection.y;
            myTempDirection.y = -newValue;
            }
        if ( aEnemy.syncMode == Enemy.SYNC_RIGHT )
            {
            final int newValue = myTempDirection.x;
            myTempDirection.x = -myTempDirection.y;
            myTempDirection.y = +newValue;
            }
        myTempDirection.scaleFixed( aEnemy.type.sizeInWorldFixed.width );

        aEnemy.worldPosFixed.translate( myTempDirection );

        if ( endOfPath ) aEnemy.onEndOfPath();
        }

    private final Position myTempDirection = new Position();
    }
