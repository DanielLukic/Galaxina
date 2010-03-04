package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.GameObject;
import net.intensicode.galaxina.game.objects.Enemy;

public class WaitingController extends EnemyController
    {
    public final void onInitialize( final Enemy aEnemy )
        {
        aEnemy.inSyncWithBreath = aEnemy.isCloseTo( aEnemy.getBreathPos( true ) );
        }

    public void onControlTick( final Enemy aEnemy )
        {
        if ( GameObject.model.enemySpawner.done() )
            {
            aEnemy.type.tickWaitingBehaviors( aEnemy );
            }

        if ( !aEnemy.inSyncWithBreath )
            {
            aEnemy.inSyncWithBreath = GameObject.model.breather.breathPercent <= 1;
            }
        aEnemy.updateToBreathPosition();
        }
    }
