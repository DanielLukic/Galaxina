package net.intensicode.galaxina.game.enemies.controllers;

import net.intensicode.galaxina.game.enemies.*;

public final class VanishedController extends EnemyController
    {
    public final boolean isReadyForAction( final Enemy aEnemy )
        {
        // This is what we want because 'vanished' means 'not destroyed'..
        return true;
        }

    public final void onInitialize( final Enemy aEnemy )
        {
        aEnemy.active = false;
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        }
    }
