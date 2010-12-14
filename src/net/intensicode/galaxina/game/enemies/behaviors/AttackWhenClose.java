package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.*;

public final class AttackWhenClose extends EnemyBehavior
    {
    public final boolean isActive( final Enemy aEnemy )
        {
        return aEnemy.path == null;
        }

    public final void onWaiting( final Enemy aEnemy )
        {
        if ( !model.level.anotherAttackerAllowed() ) return;

        final float minDist = model.player.sizeInWorld.width * 2 / 3;
        final float maxDist = model.player.sizeInWorld.width * 3 / 2;
        final float dx = Math.abs( model.player.worldPos.x - aEnemy.worldPos.x );
        if ( dx > minDist && dx < maxDist ) setAttackPath( aEnemy );
        }

    // Implementation

    private void setAttackPath( final Enemy aEnemy )
        {
        final EnemyPath path = new EnemyPath( model.world );
        myTempPos.setTo( aEnemy.worldPos );
        path.addWorldPos( myTempPos );

        final float stepWidth = aEnemy.type.sizeInWorld.width * 2;
        final float stepHeight = aEnemy.type.sizeInWorld.height * 2;
        myTempPos.x += stepWidth;
        myTempPos.y -= stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x += stepWidth;
        myTempPos.y += stepHeight;
        path.addWorldPos( myTempPos );

        final PositionF playerPos = model.player.worldPos;

        myTempPos.setTo( playerPos );
        myTempPos.y -= stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x = 0;
        myTempPos.y = model.world.size.height;
        path.addWorldPos( myTempPos );

        path.end();

        aEnemy.startAttack( path );
        }


    private final PositionF myTempPos = new PositionF();
    }
