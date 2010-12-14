package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyBehavior;
import net.intensicode.galaxina.game.enemies.EnemyPath;
import net.intensicode.util.*;
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
        final boolean projectileApproaching = model.gunShots.isProjectileApproaching( aEnemy.boundingBox );
        if ( projectileApproaching ) setAttackPath( aEnemy );
        }

    // Implementation

    private void setAttackPath( final Enemy aEnemy )
        {
        final EnemyPath path = new EnemyPath( model.world );

        myTempPos.setTo( aEnemy.worldPos );
        path.addWorldPos( myTempPos );

        final SizeF size = aEnemy.type.sizeInWorld;

        final float playerDelta = model.player.worldPos.x - aEnemy.worldPos.x;
        final int sign = playerDelta > 0 ? 1 : -1;
        final float xDelta = sign * ( myRandom.nextFloat( size.width * 4 ) - size.width * 2 );
        final float yDelta = myRandom.nextFloat( size.height ) + size.height;

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



    private final PositionF myTempPos = new PositionF();

    private static final Random myRandom = new Random( 1704 );
    }
