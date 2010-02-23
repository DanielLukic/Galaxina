package net.intensicode.galaxina.game;

import net.intensicode.util.*;


public final class World extends GameObject
    {
    public static final int EDITOR_WORLD_WIDTH = 240;

    public static final int EDITOR_WORLD_HEIGHT = 320;

    public final Size pixelSize = new Size();

    public final Size sizeFixed = new Size();

    public final Size visibleSizeFixed = new Size();


    public World( final int aScreenWidth, final int aScreenHeight )
        {
        pixelSize.setTo( aScreenWidth, aScreenHeight );
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
        myTempPos.x = aRelativeX * sizeFixed.width / ONE_HUNDRED_PERCENT_ON_BOTH_SIDES;
        myTempPos.y = aRelativeY * sizeFixed.height / ONE_HUNDRED_PERCENT_ON_BOTH_SIDES;
        return myTempPos;
        }

    public final Position editorToWorld( final int aEditorX, final int aEditorY )
        {
        myTempPos.x = aEditorX * visibleSizeFixed.width / EDITOR_WORLD_WIDTH - visibleSizeFixed.width / 2;
        myTempPos.y = aEditorY * visibleSizeFixed.height / EDITOR_WORLD_HEIGHT - visibleSizeFixed.height / 2;
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

    private static final int ONE_HUNDRED_PERCENT_ON_BOTH_SIDES = 200;
    }
