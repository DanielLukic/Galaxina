package net.intensicode.galaxina.game.enemies;

import net.intensicode.galaxina.game.objects.Enemy;

public abstract class EnemyController
    {
    public boolean readyForAction = true;

    public boolean readyUsesSyncSource = false;

    // Abstract Interface

    public abstract void onInitialize( final Enemy aEnemy );

    public abstract void onControlTick( final Enemy aEnemy );
    }
