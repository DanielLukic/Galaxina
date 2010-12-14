package net.intensicode.galaxina.game;

import net.intensicode.util.*;


public final class World extends GameObject
    {
    public static final int EDITOR_WORLD_WIDTH = 240;

    public static final int EDITOR_WORLD_HEIGHT = 320;

    public final Size pixelSize = new Size();

    public final SizeF size = new SizeF();

    public final SizeF visibleSize = new SizeF();


    public World( final int aScreenWidth, final int aScreenHeight )
        {
        pixelSize.setTo( aScreenWidth, aScreenHeight );
        size.width = aScreenWidth;
        size.height = aScreenHeight;
        visibleSize.width = size.width * 3 / 4;
        visibleSize.height = size.height * 3 / 4;
        }

    public boolean isInside( final Position aWorldPos )
        {
        if ( Math.abs( aWorldPos.x ) > size.width / 2 ) return false;
        if ( Math.abs( aWorldPos.y ) > size.height / 2 ) return false;
        return true;
        }

    public boolean isInside( final PositionF aWorldPos )
        {
        if ( Math.abs( aWorldPos.x ) > size.width / 2 ) return false;
        if ( Math.abs( aWorldPos.y ) > size.height / 2 ) return false;
        return true;
        }

    public final PositionF relativeToWorld( final Position aRelativePosition )
        {
        return relativeToWorld( aRelativePosition.x, aRelativePosition.y );
        }

    public final PositionF relativeToWorld( final int aRelativeX, final int aRelativeY )
        {
        myTempPos.x = aRelativeX * size.width / ONE_HUNDRED_PERCENT_ON_BOTH_SIDES;
        myTempPos.y = aRelativeY * size.height / ONE_HUNDRED_PERCENT_ON_BOTH_SIDES;
        return myTempPos;
        }

    public final PositionF editorToWorld( final int aEditorX, final int aEditorY )
        {
        myTempPos.x = aEditorX * visibleSize.width / EDITOR_WORLD_WIDTH - visibleSize.width / 2;
        myTempPos.y = aEditorY * visibleSize.height / EDITOR_WORLD_HEIGHT - visibleSize.height / 2;
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


    private final PositionF myTempPos = new PositionF();

    private static final float ONE_HUNDRED_PERCENT_ON_BOTH_SIDES = 200;
    }
