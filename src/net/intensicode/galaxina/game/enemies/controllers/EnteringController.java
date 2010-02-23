package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.objects.Enemy;

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
