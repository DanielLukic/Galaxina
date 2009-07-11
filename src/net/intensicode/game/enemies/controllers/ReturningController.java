package net.intensicode.game.enemies.controllers;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyController;
import net.intensicode.game.objects.GameObject;
import net.intensicode.game.objects.World;
import net.intensicode.util.Position;

public final class ReturningController extends EnemyController
    {
    public final void onInitialize( final Enemy aEnemy )
        {
        final World world = GameObject.model.world;
        if ( aEnemy.worldPosFixed.y > world.visibleSizeFixed.height )
            {
            myTempPos.x = aEnemy.formationPositionRelative.x;
            myTempPos.y = -100;
            aEnemy.worldPosFixed.setTo( world.relativeToWorld( myTempPos ) );
            }
        aEnemy.deployExtraIfDestroyed = true;
        }

    public final void onControlTick( final Enemy aEnemy )
        {
        final Position targetPos = aEnemy.getBreathPos( true );
        final Position currentPos = aEnemy.worldPosFixed;
        myTempPos.x = targetPos.x - currentPos.x;
        myTempPos.y = targetPos.y - currentPos.y;
        aEnemy.moveByDirection( myTempPos );

        if ( aEnemy.isCloseTo( targetPos ) )
            {
            aEnemy.deployExtraIfDestroyed = false;
            aEnemy.setController( Enemy.BREATHING );
            }
        }

    private final Position myTempPos = new Position();
    }
