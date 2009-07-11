package net.intensicode.game.enemies.controllers;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyController;

public final class EnteringController extends EnemyController
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
        final boolean endOfPath = aEnemy.moveAlongPath();
        if ( endOfPath ) aEnemy.onEndOfPath();
        }
    }
