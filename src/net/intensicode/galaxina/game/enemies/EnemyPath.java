package net.intensicode.galaxina.game.enemies;

import net.intensicode.galaxina.game.World;
import net.intensicode.path.SmoothPath;
import net.intensicode.util.Position;


public final class EnemyPath extends SmoothPath
    {
    public EnemyPath( final World aWorld )
        {
        myWorld = aWorld;
        }

    public final EnemyPath addRelativePos( final int aX, final int aY )
        {
        addWorldPos( getWorldPos( aX, aY ) );
        return this;
        }

    public final EnemyPath addWorldPos( final Position aWorldPosition )
        {
        add( aWorldPosition );
        return this;
        }

    // Implementation

    private Position getWorldPos( final int aX, final int aY )
        {
        myTempPos.setTo( myWorld.relativeToWorld( aX, aY ) );
        return myTempPos;
        }


    private final World myWorld;

    private final Position myTempPos = new Position();
    }
