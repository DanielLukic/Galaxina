package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyController;

public final class AttackingController extends EnemyController
    {
    public final void onInitialize( final Enemy aEnemy )
        {
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        aEnemy.type.tickAttackingBehaviors( aEnemy );

        final boolean endOfPath = aEnemy.moveAlongPath();
        if ( !endOfPath ) return;

        aEnemy.setPath( null );
        aEnemy.setController( Enemy.RETURNING );
        }
    }
