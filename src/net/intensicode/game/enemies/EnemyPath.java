/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.game.enemies;

import net.intensicode.game.objects.World;
import net.intensicode.path.SmoothPath;
import net.intensicode.util.Position;



/**
 * TODO: Describe this!
 */
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

    private final Position getWorldPos( final int aX, final int aY )
    {
        myTempPos.setTo( myWorld.relativeToWorld( aX, aY ) );
        return myTempPos;
    }



    private final World myWorld;

    private final Position myTempPos = new Position();
}
