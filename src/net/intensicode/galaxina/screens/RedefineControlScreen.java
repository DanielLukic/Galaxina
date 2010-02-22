package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.VisualContext;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.MultiScreen;
import net.intensicode.util.*;

public final class RedefineControlScreen extends MultiScreen
    {
    public RedefineControlScreen( final VisualContext aVisualContext, final DynamicArray aControlkeys )
        {
        myVisualContext = aVisualContext;
        myControlKeys = aControlkeys;

        myTitleFont = myVisualContext.menuFont();
        myTextFont = myVisualContext.textFont();
        }

    public void reset( final ControlKeyEntry aControlKeyEntry )
        {
        myTarget = aControlKeyEntry;
        myCaptureFlag = false;
        myBlinkFlag = false;
        myWaitTicks = timing().ticksPerSecond / 3;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( myVisualContext.sharedBackground() );
        addScreen( myVisualContext.sharedSoftkeys() );

        myTitlePos.x = screen().width() / 2;
        myTitlePos.y = myTitleFont.charHeight() * 2;

        myLinePos1.x = screen().width() / 2;
        myLinePos1.y = myTitleFont.charHeight() * 4;
        myLinePos2.x = screen().width() / 2;
        myLinePos2.y = myLinePos1.y + myTextFont.charHeight() * 3 / 2;
        myLinePos3.x = screen().width() / 2;
        myLinePos3.y = myLinePos2.y + myTextFont.charHeight() * 3 / 2;
        }

    public final void onInitEverytime() throws Exception
        {
        myVisualContext.sharedSoftkeys().setSoftkeys( "", "" );
        myCaptureFlag = false;
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        myBlinkFlag = false;

        if ( myTickCounter < timing().ticksPerSecond ) myTickCounter++;
        else myTickCounter -= timing().ticksPerSecond;

        final KeysHandler keys = keys();
        if ( myWaitTicks > 0 ) myWaitTicks--;
        if ( myWaitTicks > 0 ) return;

        myBlinkFlag = myTickCounter > timing().ticksPerSecond / 5;

        if ( !keys.someKeyPressed() && !myCaptureFlag )
            {
            keys.lastAction = 0;
            keys.lastCode = 0;
            myCaptureFlag = true;
            }

        final int code = keys.lastAction != 0 ? keys.lastAction : keys.lastCode;
        if ( myCaptureFlag && code != 0 )
            {
            swapKeyCodes( myTarget.keyCode, code );
            stack().popScreen( this );
            }
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();
        myTitleFont.blitString( gc, I18n._( "CONTROLS" ), myTitlePos, FontGenerator.CENTER );
        myTextFont.blitString( gc, I18n._( "REDEFINING" ), myLinePos1, FontGenerator.CENTER );
        myTextFont.blitString( gc, myTarget.text, myLinePos2, FontGenerator.CENTER );

        if ( myBlinkFlag ) myTextFont.blitString( gc, I18n._( "PRESS A KEY" ), myLinePos3, FontGenerator.CENTER );
        }

    // Implementation

    private void swapKeyCodes( final int aOldCode, final int aNewCode )
        {
        for ( int idx = 0; idx < myControlKeys.size; idx++ )
            {
            final ControlKeyEntry entry = (ControlKeyEntry) myControlKeys.get( idx );
            if ( entry.keyCode == aOldCode ) entry.keyCode = aNewCode;
            else if ( entry.keyCode == aNewCode ) entry.keyCode = aOldCode;
            }
        }


    private int myWaitTicks;

    private int myTickCounter;

    private boolean myBlinkFlag;

    private boolean myCaptureFlag;

    private ControlKeyEntry myTarget;

    private final FontGenerator myTextFont;

    private final FontGenerator myTitleFont;

    private final DynamicArray myControlKeys;

    private final VisualContext myVisualContext;

    private final Position myTitlePos = new Position();

    private final Position myLinePos1 = new Position();

    private final Position myLinePos2 = new Position();

    private final Position myLinePos3 = new Position();
    }
