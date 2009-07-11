package net.intensicode.game.enemies.behaviors;

import net.intensicode.game.enemies.Enemy;
import net.intensicode.game.enemies.EnemyBehavior;
import net.intensicode.game.enemies.EnemyPath;
import net.intensicode.util.Position;
import net.intensicode.util.Random;
import net.intensicode.util.Size;

public final class AvoidBeingHit extends EnemyBehavior
    {
    public final boolean isActive( final Enemy aEnemy )
        {
        return aEnemy.path == null;
        }

    public final void onWaiting( final Enemy aEnemy )
        {
        final boolean projectileApproaching = model.gunShots.isProjectileApproaching( aEnemy.bbox );
        if ( projectileApproaching ) setAttackPath( aEnemy );
        }

    // Implementation

    private void setAttackPath( final Enemy aEnemy )
        {
        final EnemyPath path = new EnemyPath( model.world );

        myTempPos.setTo( aEnemy.worldPosFixed );
        path.addWorldPos( myTempPos );

        final Size size = aEnemy.type.sizeInWorldFixed;

        final int playerDelta = model.player.worldPosFixed.x - aEnemy.worldPosFixed.x;
        final int sign = playerDelta > 0 ? 1 : -1;
        final int xDelta = sign * ( myRandom.nextInt( size.width * 4 ) - size.width * 2 );
        final int yDelta = myRandom.nextInt( size.height ) + size.height;

        myTempPos.x += xDelta;
        myTempPos.y -= yDelta;
        path.addWorldPos( myTempPos );

        myTempPos.x += size.width;
        path.addWorldPos( myTempPos );

        myTempPos.y += size.height * 2;
        path.addWorldPos( myTempPos );

        myTempPos.x -= size.width;
        myTempPos.y += size.height;
        path.addWorldPos( myTempPos );

        path.end();

        aEnemy.startAttack( path );
        }



    private final Position myTempPos = new Position();

    private static final Random myRandom = new Random( 1704 );
    }
