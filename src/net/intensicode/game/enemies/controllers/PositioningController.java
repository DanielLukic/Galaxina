package net.intensicode.game.enemies.controllers;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyController;
import net.intensicode.util.Position;

public final class PositioningController extends EnemyController
    {
    public final boolean isReadyForAction( final Enemy aEnemy )
        {
        return false;
        }

    public final void onInitialize( final Enemy aEnemy )
        {
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        final Position targetPos = aEnemy.getBreathPos( true );
        final Position currentPos = aEnemy.worldPosFixed;
        myTempPos.x = targetPos.x - currentPos.x;
        myTempPos.y = targetPos.y - currentPos.y;
        aEnemy.moveByDirection( myTempPos );

        if ( aEnemy.isCloseTo( targetPos ) ) aEnemy.setController( Enemy.BREATHING );
        }

    private final Position myTempPos = new Position();
    }
