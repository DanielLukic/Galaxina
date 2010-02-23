package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.GameObject;

public final class BreathingController extends WaitingController
    {
    public final void onControlTick( final Enemy aEnemy )
        {
        super.onControlTick( aEnemy );

        if ( GameObject.model.enemySpawner.done() ) aEnemy.setController( Enemy.WAITING );
        }
    }
