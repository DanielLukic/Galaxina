package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.enemies.EnemyBehavior;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.galaxina.game.enemies.EnemyPath;
import net.intensicode.util.*;

public final class StrikeWhenAttacked extends EnemyBehavior
    {
    public final boolean isActive( final Enemy aEnemy )
        {
        return aEnemy.path == null;
        }

    public final void onWaiting( final Enemy aEnemy )
        {
        if ( !model.level.anotherAttackerAllowed() ) return;

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
        myTempPos.x += size.width;
        myTempPos.y -= size.height;
        path.addWorldPos( myTempPos );

        myTempPos.x += size.width;
        myTempPos.y += size.height;
        path.addWorldPos( myTempPos );

        final int xStop = myRandom.nextInt( 100 ) - 50;
        final int yStop = myRandom.nextInt( 50 ) - 25;
        myTempPos.setTo( model.world.relativeToWorld( xStop, yStop ) );
        path.addWorldPos( myTempPos );

        myTempPos.setTo( model.player.worldPos );
        myTempPos.y -= size.height;
        path.addWorldPos( myTempPos );

        path.addWorldPos( aEnemy.worldPos );
        path.end();

        aEnemy.startAttack( path );
        }



    private final PositionF myTempPos = new PositionF();

    private static final Random myRandom = new Random();
    }
