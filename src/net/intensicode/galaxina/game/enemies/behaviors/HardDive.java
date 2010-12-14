package net.intensicode.galaxina.game.enemies.behaviors;

import net.intensicode.galaxina.game.enemies.EnemyBehavior;
import net.intensicode.galaxina.game.enemies.EnemyPath;
import net.intensicode.galaxina.game.objects.Enemy;
import net.intensicode.util.*;

public final class HardDive extends EnemyBehavior
    {
    public final boolean isActive( final Enemy aEnemy )
        {
        return aEnemy.path == null;
        }

    public final void onWaiting( final Enemy aEnemy )
        {
        if ( !model.level.anotherAttackerAllowed() ) return;

        setAttackPath( aEnemy );
        }

    // Implementation

    private void setAttackPath( final Enemy aEnemy )
        {
        final EnemyPath path = new EnemyPath( model.world );
        myTempPos.setTo( aEnemy.worldPos );
        path.addWorldPos( myTempPos );

        final float stepWidth = aEnemy.type.sizeInWorld.width;
        final float stepHeight = aEnemy.type.sizeInWorld.height;
        myTempPos.x += stepWidth;
        myTempPos.y -= stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x += stepWidth;
        myTempPos.y += stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x -= stepWidth;
        myTempPos.y = model.world.size.height / 3;
        path.addWorldPos( myTempPos );

        myTempPos.x -= stepWidth;
        myTempPos.y = model.world.size.height;
        path.addWorldPos( myTempPos );

        path.end();

        aEnemy.startAttack( path );
        }



    private final PositionF myTempPos = new PositionF();
    }
