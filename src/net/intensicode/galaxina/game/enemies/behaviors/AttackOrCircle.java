package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyBehavior;
import net.intensicode.galaxina.game.enemies.EnemyPath;
import net.intensicode.util.Position;

public final class AttackOrCircle extends EnemyBehavior
    {
    public final boolean isActive( final Enemy aEnemy )
        {
        return aEnemy.path == null;
        }

    public final void onWaiting( final Enemy aEnemy )
        {
        if ( !model.level.anotherAttackerAllowed() ) return;

        {
        final int minDist = model.player.sizeInWorldFixed.width * 2;
        final int maxDist = model.player.sizeInWorldFixed.width * 3;
        final int dx = Math.abs( model.player.worldPosFixed.x - aEnemy.worldPosFixed.x );
        if ( dx > minDist && dx < maxDist ) setAttackPath( aEnemy, false );
        }
        {
        final int minDist = model.player.sizeInWorldFixed.width * 4;
        final int maxDist = model.player.sizeInWorldFixed.width * 6;
        final int dx = Math.abs( model.player.worldPosFixed.x - aEnemy.worldPosFixed.x );
        if ( dx > minDist && dx < maxDist ) setAttackPath( aEnemy, true );
        }
        }

    // Implementation

    private void setAttackPath( final Enemy aEnemy, final boolean aDoCircle )
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

        if ( aDoCircle )
            {
            myTempPos.setTo( playerPos );
            myTempPos.x -= stepWidth;
            myTempPos.y -= stepHeight;
            path.addWorldPos( myTempPos );

            myTempPos.y += stepHeight * 2;
            path.addWorldPos( myTempPos );

            myTempPos.x += stepWidth * 2;
            path.addWorldPos( myTempPos );

            myTempPos.y -= stepHeight * 2;
            path.addWorldPos( myTempPos );

            myTempPos.x -= stepWidth * 2;
            path.addWorldPos( myTempPos );
            }
        else
            {
            myTempPos.setTo( playerPos );
            myTempPos.y -= stepHeight;
            path.addWorldPos( myTempPos );
            }

        myTempPos.x = 0;
        myTempPos.y = model.world.sizeFixed.height;
        path.addWorldPos( myTempPos );

        path.end();

        aEnemy.startAttack( path );
        }



    private final Position myTempPos = new Position();
    }
