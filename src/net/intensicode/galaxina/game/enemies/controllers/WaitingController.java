package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.enemies.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyController;
import net.intensicode.galaxina.game.objects.GameObject;

public class WaitingController extends EnemyController
    {
    public final void onInitialize( final Enemy aEnemy )
        {
        aEnemy.inSyncWithBreath = aEnemy.isCloseTo( aEnemy.getBreathPos( true ) );
        }

    public void onControlTick( final Enemy aEnemy )
        {
        if ( !aEnemy.inSyncWithBreath )
            {
            aEnemy.inSyncWithBreath = GameObject.model.breather.breathPercent <= 1;
            }
        aEnemy.updateToBreathPosition();
        }
    }
