package net.intensicode.galaxina.game;

import net.intensicode.galaxina.game.objects.World;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class Camera extends ScreenBase
    {
    public Camera( final GameContext aGameContext )
        {
        myWorld = aGameContext.gameModel().world;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        myScreenWidth = screen().width();
        myScreenHeight = screen().height();

        myScreenCenterX = myScreenWidth / 2;
        myScreenCenterY = myScreenHeight / 2;
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
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
