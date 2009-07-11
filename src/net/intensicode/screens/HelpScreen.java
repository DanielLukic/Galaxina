package net.intensicode.screens;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Keys;
import net.intensicode.core.MultiScreen;
import net.intensicode.game.GameContext;
import net.intensicode.game.VisualContext;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;


/**
 * TODO: Describe this!
 */
public final class HelpScreen extends MultiScreen
    {
    public HelpScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final VisualContext visualContext = myGameContext.visualContext();
        myTitleFont = visualContext.titleFont();
        myTextFont = visualContext.textFont();

        addScreen( visualContext.sharedBackground() );
        addScreen( visualContext.sharedSoftkeys() );

        visualContext.sharedSoftkeys().setSoftkeys( "START", "BACK", false );

        extractLines( aEngine.loader.loadString( "/help.txt" ) );

        myHelpPos.x = aScreen.width() / 2;
        myHelpPos.y = myTitleFont.charHeight();
        }

    public final void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Keys keys = aEngine.keys;
        keys.keyRepeatDelayInTicks = Engine.ticksPerSecond * 25 / 100;
        keys.keyRepeatIntervalInTicks = Engine.ticksPerSecond * 10 / 100;
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        final Keys keys = aEngine.keys;
        if ( keys.checkLeftSoftAndConsume() ) myGameContext.startGame();
        else if ( keys.checkFireAndConsume() ) myGameContext.startGame();
        else if ( keys.checkRightSoftAndConsume() ) aEngine.popScreen( this );

        super.onControlTick( aEngine );

        if ( keys.checkUpAndConsume() ) myLineOffset--;
        if ( keys.checkDownAndConsume() ) myLineOffset++;

        final int linesOnScreen = linesOnScreen();
        if ( myLineOffset < 0 ) myLineOffset = 0;
        if ( myLineOffset + linesOnScreen >= myHelpTextLines.size )
            myLineOffset = myHelpTextLines.size - linesOnScreen;
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        super.onDrawFrame( aScreen );

        final Graphics gc = aScreen.graphics();
        myTitleFont.blitString( gc, "HELP", myHelpPos, FontGen.CENTER );

        final int linesOnScreen = linesOnScreen();

        final Object[] lines = myHelpTextLines.objects;
        for ( int idx = 0; idx < linesOnScreen; idx++ )
            {
            if ( myLineOffset + idx >= myHelpTextLines.size ) break;

            final String line = (String) lines[ myLineOffset + idx ];
            myBlitPos.x = aScreen.width() / 2;
            myBlitPos.y = titleHeight() + idx * lineHeight();
            myTextFont.blitString( gc, line, myBlitPos, FontGen.CENTER );
            }
        }

    private final int linesOnScreen()
        {
        final int textHeight = ( screen().height() - titleHeight() );
        return ( textHeight / lineHeight() - 1 );
        }

    private final int lineHeight()
        {
        return myTextFont.charHeight() * 3 / 2;
        }

    private final int titleHeight()
        {
        return myTitleFont.charHeight() * 3;
        }

    // Implementation

    private final void extractLines( final String aText )
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

    private BitmapFontGen myTextFont;

    private BitmapFontGen myTitleFont;

    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();

    private final Position myHelpPos = new Position();

    private final DynamicArray myHelpTextLines = new DynamicArray( 25, 25 );
    }
