package net.intensicode.game.enemies.behaviors;

import net.intensicode.game.enemies.EnemyBehavior;
import net.intensicode.game.enemies.EnemyPath;
import net.intensicode.game.enemies.Enemy;
import net.intensicode.util.Position;

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
        myTempPos.setTo( aEnemy.worldPosFixed );
        path.addWorldPos( myTempPos );

        final int stepWidth = aEnemy.type.sizeInWorldFixed.width;
        final int stepHeight = aEnemy.type.sizeInWorldFixed.height;
        myTempPos.x += stepWidth;
        myTempPos.y -= stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x += stepWidth;
        myTempPos.y += stepHeight;
        path.addWorldPos( myTempPos );

        myTempPos.x -= stepWidth;
        myTempPos.y = model.world.sizeFixed.height / 3;
        path.addWorldPos( myTempPos );

        myTempPos.x -= stepWidth;
        myTempPos.y = model.world.sizeFixed.height;
        path.addWorldPos( myTempPos );

        path.end();

        aEnemy.startAttack( path );
        }



    private final Position myTempPos = new Position();
    }
