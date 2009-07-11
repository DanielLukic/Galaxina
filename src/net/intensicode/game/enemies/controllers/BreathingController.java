package net.intensicode.game.enemies.controllers;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.objects.GameObject;

public final class BreathingController extends WaitingController
    {
    public final void onControlTick( final Enemy aEnemy )
        {
        super.onControlTick( aEnemy );

        if ( GameObject.model.enemySpawner.done() ) aEnemy.setController( Enemy.WAITING );
        }
    }
