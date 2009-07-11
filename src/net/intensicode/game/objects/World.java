package net.intensicode.game.objects;

import net.intensicode.util.FixedMath;
import net.intensicode.util.Position;
import net.intensicode.util.Size;



/**
 * TODO: Describe this!
 */
public final class World extends GameObject
{
    public final Size sizeFixed = new Size();

    public final Size visibleSizeFixed = new Size();



    public World( final int aScreenWidth, final int aScreenHeight )
    {
        sizeFixed.width = FixedMath.toFixed( aScreenWidth );
        sizeFixed.height = FixedMath.toFixed( aScreenHeight );
        visibleSizeFixed.width = sizeFixed.width * 3 / 4;
        visibleSizeFixed.height = sizeFixed.height * 3 / 4;
    }

    public boolean isInside( final Position aWorldPosFixed )
    {
        if ( Math.abs( aWorldPosFixed.x ) > sizeFixed.width / 2 ) return false;
        if ( Math.abs( aWorldPosFixed.y ) > sizeFixed.height / 2 ) return false;
        return true;
    }

    public final Position relativeToWorld( final Position aRelativePosition )
    {
        return relativeToWorld( aRelativePosition.x, aRelativePosition.y );
    }

    public final Position relativeToWorld( final int aRelativeX, final int aRelativeY )
    {
        myTempPos.x = aRelativeX * sizeFixed.width / 200;
        myTempPos.y = aRelativeY * sizeFixed.height / 200;
        return myTempPos;
    }

    // From GameObject

    public final void onStartGame()
    {
    }

    public final void onStartLevel()
    {
    }

    public final void onControlTick()
    {
    }



    private final Position myTempPos = new Position();
}
