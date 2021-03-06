package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyController;
import net.intensicode.galaxina.game.GameObject;
import net.intensicode.galaxina.game.World;
import net.intensicode.util.*;

public final class ReturningController extends EnemyController
    {
    public final void onInitialize( final Enemy aEnemy )
        {
        final World world = GameObject.model.world;
        if ( aEnemy.worldPos.y > world.visibleSize.height )
            {
            myTempPos.x = aEnemy.formationPosition.x;
            myTempPos.y = -GameObject.model.world.size.height;
            aEnemy.worldPos.setTo( myTempPos );
            }
        aEnemy.deployExtraIfDestroyed = true;
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        final PositionF targetPos = aEnemy.getBreathPos( true );
        final PositionF currentPos = aEnemy.worldPos;
        myTempPos.x = targetPos.x - currentPos.x;
        myTempPos.y = targetPos.y - currentPos.y;
        aEnemy.moveByDirection( myTempPos );

        if ( aEnemy.isCloseTo( targetPos ) )
            {
            aEnemy.deployExtraIfDestroyed = false;
            aEnemy.setController( Enemy.BREATHING );
            }
        }

    private final PositionF myTempPos = new PositionF();
    }
