package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.MainContext;
import net.intensicode.graphics.*;
import net.intensicode.util.*;
import net.intensicode.screens.MultiScreen;

public final class HelpScreen extends MultiScreen
    {
    public HelpScreen( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final VisualContext visualContext = myMainContext.visualContext();
        myTitleFont = visualContext.titleFont();
        myTextFont = visualContext.textFont();

        addScreen( visualContext.sharedBackground() );
        addScreen( visualContext.sharedSoftkeys() );

        visualContext.sharedSoftkeys().setSoftkeys( "START", "BACK", false );

        extractLines( resources().loadString( "help.txt" ) );

        myHelpPos.x = screen().width() / 2;
        myHelpPos.y = myTitleFont.charHeight();
        }

    public final void onInitEverytime() throws Exception
        {
        final KeysHandler keys = keys();
        keys.keyRepeatDelayInTicks = timing().ticksPerSecond * 25 / 100;
        keys.keyRepeatIntervalInTicks = timing().ticksPerSecond * 10 / 100;
        }

    public final void onControlTick() throws Exception
        {
        final KeysHandler keys = keys();
        if ( keys.checkLeftSoftAndConsume() || keys.checkFireAndConsume() || keys.checkStickDownAndConsume() )
            {
            stack().popScreen( this );
            myMainContext.startNewGame();
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            }

        super.onControlTick();

        if ( keys.checkUpAndConsume() ) myLineOffset--;
        if ( keys.checkDownAndConsume() ) myLineOffset++;

        final int linesOnScreen = linesOnScreen();
        if ( myLineOffset < 0 ) myLineOffset = 0;
        if ( myLineOffset + linesOnScreen >= myHelpTextLines.size )
            myLineOffset = myHelpTextLines.size - linesOnScreen;
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();
        myTitleFont.blitString( gc, "HELP", myHelpPos, FontGenerator.CENTER );

        final int linesOnScreen = linesOnScreen();

        final Object[] lines = myHelpTextLines.objects;
        for ( int idx = 0; idx < linesOnScreen; idx++ )
            {
            if ( myLineOffset + idx >= myHelpTextLines.size ) break;

            final String line = (String) lines[ myLineOffset + idx ];
            myBlitPos.x = screen().width() / 2;
            myBlitPos.y = titleHeight() + idx * lineHeight();
            myTextFont.blitString( gc, line, myBlitPos, FontGenerator.CENTER );
            }
        }

    private int linesOnScreen()
        {
        final int textHeight = ( screen().height() - titleHeight() );
        return ( textHeight / lineHeight() - 1 );
        }

    private int lineHeight()
        {
        return myTextFont.charHeight() * 3 / 2;
        }

    private int titleHeight()
        {
        return myTitleFont.charHeight() * 3;
        }

    // Implementation

    private void extractLines( final String aText )
        {
        final int textLength = aText.length();

        int start = 0;
        int end;
        do
            {
            end = textLength;

            final char firstChar = aText.charAt( start );
            if ( firstChar == '\n' || firstChar == '\r' ) start++;

            for ( int idx = start; idx < textLength; idx++ )
                {
                final char charCode = aText.charAt( idx );
                if ( charCode == '\n' || charCode == '\r' )
                    {
                    end = idx + 1;
                    break;
                    }
                }

            myHelpTextLines.add( aText.substring( start, end ) );

            start = end;
            }
        while ( start < textLength );
        }


    private int myLineOffset = 0;

    private BitmapFontGenerator myTextFont;

    private BitmapFontGenerator myTitleFont;

    private final MainContext myMainContext;

    private final Position myBlitPos = new Position();

    private final Position myHelpPos = new Position();

    private final DynamicArray myHelpTextLines = new DynamicArray( 25, 25 );
    }
