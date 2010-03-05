package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.Position;

public final class AttackWhenClose extends EnemyBehavior
    {
    public final boolean isActive( final Enemy aEnemy )
        {
        return aEnemy.path == null;
        }

    public final void onWaiting( final Enemy aEnemy )
        {
        if ( !model.level.anotherAttackerAllowed() ) return;

        final int minDist = model.player.sizeInWorldFixed.width * 2 / 3;
        final int maxDist = model.player.sizeInWorldFixed.width * 3 / 2;
        final int dx = Math.abs( model.player.worldPosFixed.x - aEnemy.worldPosFixed.x );
        if ( dx > minDist && dx < maxDist ) setAttackPath( aEnemy );
        }

    // Implementation

    private void setAttackPath( final Enemy aEnemy )
        {
        final EnemyPath path = new EnemyPath( model.world );
        myTempPos.setTo( aEnemy.worldPosFixed );
        path.addWorldPos( myTempPos );

        final int stepWidth = aEnemy.type.sizeInWorldFixed.width * 2;
        final int stepHeight = aEnemy.type.sizeInWorldFixed.height * 2;
        myTempPos.x += stepWidth;
        myTempPos.y -= stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x += stepWidth;
        myTempPos.y += stepHeight;
        path.addWorldPos( myTempPos );

        final Position playerPos = model.player.worldPosFixed;

        myTempPos.setTo( playerPos );
        myTempPos.y -= stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x = 0;
        myTempPos.y = model.world.sizeFixed.height;
        path.addWorldPos( myTempPos );

        path.end();

        aEnemy.startAttack( path );
        }


    private final Position myTempPos = new Position();
    }
