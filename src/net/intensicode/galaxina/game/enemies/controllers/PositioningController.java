package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.*;

public final class PositioningController extends EnemyController
    {
    public PositioningController()
        {
        readyForAction = false;
        }

    public final void onInitialize( final Enemy aEnemy )
        {
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        final PositionF targetPos = aEnemy.getBreathPos( true );
        final PositionF currentPos = aEnemy.worldPos;
        myTempPos.x = targetPos.x - currentPos.x;
        myTempPos.y = targetPos.y - currentPos.y;
        aEnemy.moveByDirection( myTempPos );

        if ( aEnemy.isCloseTo( targetPos ) ) aEnemy.setController( Enemy.BREATHING );
        }

    private final PositionF myTempPos = new PositionF();
    }
