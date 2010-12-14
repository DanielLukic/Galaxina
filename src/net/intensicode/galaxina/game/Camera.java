package net.intensicode.galaxina.game;

import net.intensicode.galaxina.game.World;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;
import net.intensicode.core.Configuration;

public final class Camera extends ScreenBase
    {
    public Camera( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        myWorld = aGameContext.gameModel().world;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        // By default align world-to-screen-projection at top and horizontally centered:
        final Configuration configuration = myGameContext.gameModel().configuration;
        myScreenCenterX = configuration.readInt( "Camera.screenCenter.x", screen().width() / 2 );
        myScreenCenterY = configuration.readInt( "Camera.screenCenter.y", myWorld.pixelSize.height / 2 );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        }

    public final Position toScreen( final PositionF aWorldPos )
        {
        final float x = aWorldPos.x * myWorld.pixelSize.width / myWorld.visibleSize.width;
        final float y = aWorldPos.y * myWorld.pixelSize.height / myWorld.visibleSize.height;
        myTempPos.x = (int) (x + myScreenCenterX);
        myTempPos.y = (int) (y + myScreenCenterY);
        return myTempPos;
        }

    public final SizeF toWorldSize( final int aWidth, final int aHeight )
        {
        myTempSize.width = aWidth * myWorld.visibleSize.width / myWorld.pixelSize.width;
        myTempSize.height = aHeight * myWorld.visibleSize.height / myWorld.pixelSize.height;
        return myTempSize;
        }


    private int myScreenCenterX;

    private int myScreenCenterY;

    private final World myWorld;

    private final SizeF myTempSize = new SizeF();

    private final Position myTempPos = new Position();

    private final GameContext myGameContext;
    }
