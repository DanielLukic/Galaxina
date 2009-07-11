package net.intensicode.game;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.game.objects.World;
import net.intensicode.util.Position;
import net.intensicode.util.Size;



/**
 * TODO: Describe this!
 */
public final class Camera extends AbstractScreen
{
    public Camera( final GameContext aGameContext )
    {
        myWorld = aGameContext.gameModel().world;
    }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
    {
        myScreenWidth = aScreen.width();
        myScreenHeight = aScreen.height();

        myScreenCenterX = myScreenWidth / 2;
        myScreenCenterY = myScreenHeight / 2;
    }

    public final void onControlTick( final Engine aEngine ) throws Exception
    {
    }

    public final void onDrawFrame( final DirectScreen aScreen )
    {
    }

    public final Position toScreen( final Position aWorldPos )
    {
        final int x = aWorldPos.x * myScreenWidth / myWorld.visibleSizeFixed.width;
        final int y = aWorldPos.y * myScreenHeight / myWorld.visibleSizeFixed.height;
        myTempPos.x = x + myScreenCenterX;
        myTempPos.y = y + myScreenCenterY;
        return myTempPos;
    }

    public final Size toWorldSize( final int aWidth, final int aHeight )
    {
        myTempSize.width = aWidth * myWorld.visibleSizeFixed.width / myScreenWidth;
        myTempSize.height = aHeight * myWorld.visibleSizeFixed.height / myScreenHeight;
        return myTempSize;
    }



    private int myScreenWidth;

    private int myScreenHeight;

    private int myScreenCenterX;

    private int myScreenCenterY;

    private final World myWorld;

    private final Size myTempSize = new Size();

    private final Position myTempPos = new Position();
}
