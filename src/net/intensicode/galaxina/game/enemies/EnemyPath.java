package net.intensicode.galaxina.game.enemies;

import net.intensicode.galaxina.game.World;
import net.intensicode.path.SmoothPath;
import net.intensicode.util.*;


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

    public final EnemyPath addWorldPos( final PositionF aWorldPosition )
        {
        add( aWorldPosition );
        return this;
        }

    // Implementation

    private PositionF getWorldPos( final int aX, final int aY )
        {
        myTempPos.setTo( myWorld.relativeToWorld( aX, aY ) );
        return myTempPos;
        }


    private final World myWorld;

    private final PositionF myTempPos = new PositionF();
    }
