package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.enemies.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyController;

public final class FollowInSyncController extends EnemyController
    {
    public final void onInitialize( final Enemy aEnemy )
        {
        aEnemy.syncPathTo( aEnemy.syncSource );
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        final Enemy source = aEnemy.syncSource;
        if ( !source.active )
            {
            aEnemy.setController( Enemy.ENTERING );
            }
        else if ( source.path != null )
            {
            aEnemy.syncPathTo( source );
            }
        else
            {
            aEnemy.onEndOfPath();
            }
        }
    }
