package net.intensicode.game.enemies.controllers;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyController;
import net.intensicode.game.objects.GameObject;

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
