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

    public final Position toScreen( final Position aWorldPos )
        {
        final int x = aWorldPos.x * myWorld.pixelSize.width / myWorld.visibleSizeFixed.width;
        final int y = aWorldPos.y * myWorld.pixelSize.height / myWorld.visibleSizeFixed.height;
        myTempPos.x = x + myScreenCenterX;
        myTempPos.y = y + myScreenCenterY;
        return myTempPos;
        }

    public final Size toWorldSize( final int aWidth, final int aHeight )
        {
        myTempSize.width = aWidth * myWorld.visibleSizeFixed.width / myWorld.pixelSize.width;
        myTempSize.height = aHeight * myWorld.visibleSizeFixed.height / myWorld.pixelSize.height;
        return myTempSize;
        }


    private int myScreenCenterX;

    private int myScreenCenterY;

    private final World myWorld;

    private final Size myTempSize = new Size();

    private final Position myTempPos = new Position();

    private final GameContext myGameContext;
    }
