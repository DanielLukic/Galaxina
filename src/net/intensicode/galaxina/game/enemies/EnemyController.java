package net.intensicode.galaxina.game.enemies;

import net.intensicode.galaxina.game.objects.Enemy;

public abstract class EnemyController
    {
    public boolean isReadyForAction( final Enemy aEnemy )
        {
        return true;
        }

    // Abstract Interface

    public abstract void onInitialize( final Enemy aEnemy );

    public abstract void onControlTick( final Enemy aEnemy );
    }
