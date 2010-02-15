package net.intensicode.galaxina.screens;

import net.intensicode.core.DirectGraphics;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;


public final class TitleCreditsScreen extends ScreenBase
    {
    public TitleCreditsScreen( final FontGenerator aFontGenerator, final String aCreditsText )
        {
        myFontGenerator = aFontGenerator;
        myCreditsText = aCreditsText;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        super.onInitOnce();
        myNeedNewLines = true;
        clearCharBuffer();
        }

    public final void onControlTick()
        {
        if ( myNeedNewLines )
            {
            extractNewLines();
            setupAnimation();
            myNeedNewLines = false;
            }
        else
            {
            boolean somethingAlive = false;
            for ( int idx = 0; idx < myChars.length; idx++ )
                {
                final TitleFancyChar fancyChar = myChars[ idx ];
                if ( fancyChar.isDead() ) continue;
                myChars[ idx ].tick();
                somethingAlive = true;
                }
            if ( !somethingAlive ) myNeedNewLines = true;
            }
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();
        for ( int idx = 0; idx < myChars.length; idx++ )
            {
            final TitleFancyChar fancyChar = myChars[ idx ];
            if ( fancyChar == null || !fancyChar.isVisible() ) continue;
            final int safeAlpha = Math.max( 0, Math.min( 255, fancyChar.alpha ) );
            myFontGenerator.blendChar( graphics, fancyChar.drawOffsetX, fancyChar.drawOffsetY, fancyChar.charCode, safeAlpha );
            }
        }

    // Implementation

    private void extractNewLines()
        {
        clearCharBuffer();

        final int nextScreenBreak = myCreditsText.indexOf( '~', myCreditsTextOffset );
        final int nextScreenBreakOrEnd = nextScreenBreak == -1 ? myCreditsText.length() : nextScreenBreak;

        int lineIndex = 0;
        while ( true )
            {
            final int nextLineBreak = myCreditsText.indexOf( '\n', myCreditsTextOffset );
            final int nextLineBreakOrEnd = nextLineBreak == -1 ? myCreditsText.length() : nextLineBreak;
            if ( nextLineBreakOrEnd >= nextScreenBreakOrEnd ) break;
            setCharLine( lineIndex, myCreditsText, myCreditsTextOffset, nextLineBreakOrEnd );

            myCreditsTextOffset = nextLineBreakOrEnd + 1;
            lineIndex++;
            }

        myVisibleLines = lineIndex;
        myCreditsTextOffset = nextScreenBreak + 1;
        }

    private void clearCharBuffer()
        {
        myVisibleLines = 0;
        for ( int idx = 0; idx < myChars.length; idx++ )
            {
            if ( myChars[ idx ] == null ) myChars[ idx ] = new TitleFancyChar();
            myChars[ idx ].reset();
            }
        for ( int idx = 0; idx < MAX_LINES; idx++ )
            {
            myLineUsed[ idx ] = false;
            }
        }

    private void setCharLine( final int aLineIndex, final String aString, final int aStartIndex, final int aEndIndex )
        {
        final int lineLength = aEndIndex - aStartIndex;
        final int lineOffset = aLineIndex * MAX_CHARS_PER_ROW;
        final int charOffset = ( MAX_CHARS_PER_ROW - lineLength ) / 2;
        for ( int idx = 0; idx < lineLength; idx++ )
            {
            myChars[ lineOffset + charOffset + idx ].charCode = aString.charAt( aStartIndex + idx );
            }
        myLineUsed[ aLineIndex ] = lineLength > 0;
        myLineWidthInPixels[ aLineIndex ] = myFontGenerator.substringWidth( aString, aStartIndex, lineLength );
        }

    private void setupAnimation()
        {
        final int tps = timing().ticksPerSecond;
        final int overallWaitTicks = tps * myVisibleLines * 2;
        final int incomingTicks = tps * 1;
        final int stayTicks = tps * 5;
        final int outgoingTicks = tps * 1;
        final int moveDistance = myFontGenerator.charHeight() * 3;
        final int numberOfChars = myChars.length;
        for ( int lineIndex = 0; lineIndex < MAX_LINES; lineIndex++ )
            {
            int xPos = ( screen().width() - myLineWidthInPixels[ lineIndex ] ) / 2;
            final int yStart = ( screen().height() - myFontGenerator.charHeight() * myVisibleLines ) / 2;
            final int yPos = yStart + lineIndex * myFontGenerator.charHeight();
            for ( int charIndex = 0; charIndex < MAX_CHARS_PER_ROW; charIndex++ )
                {
                final int idx = charIndex + lineIndex * MAX_CHARS_PER_ROW;
                final TitleFancyChar fancyChar = myChars[ idx ];
                fancyChar.waitTicks = idx * overallWaitTicks / numberOfChars;
                final int sinIndex1 = charIndex * Sinus.SIN_TABLE_SIZE * 3 / MAX_CHARS_PER_ROW;
                final int sinIndex2 = lineIndex * Sinus.SIN_TABLE_SIZE * 3 / MAX_CHARS_PER_ROW;
                final int sinIndex = sinIndex1 + sinIndex2;
                fancyChar.incomingTicks = incomingTicks;
                fancyChar.stayTicks = stayTicks;
                fancyChar.outgoingTicks = outgoingTicks;
                fancyChar.startOffsetX = mySinus.cos( sinIndex, moveDistance );
                fancyChar.startOffsetY = mySinus.sin( sinIndex, moveDistance * 2 );
                fancyChar.endOffsetX = mySinus.sin( sinIndex, moveDistance );
                fancyChar.endOffsetY = mySinus.cos( sinIndex, moveDistance ) * 2;
                fancyChar.posX = xPos;
                fancyChar.posY = yPos;
                xPos += myFontGenerator.charWidth( fancyChar.charCode );
                }
            }
        }


    private int myVisibleLines;

    private boolean myNeedNewLines;

    private int myCreditsTextOffset;

    private final String myCreditsText;

    private final FontGenerator myFontGenerator;

    private final Sinus mySinus = Sinus.instance();

    private final boolean[] myLineUsed = new boolean[MAX_LINES];

    private final int[] myLineWidthInPixels = new int[MAX_LINES];

    private TitleFancyChar[] myChars = new TitleFancyChar[MAX_CHARS_PER_ROW * MAX_LINES];

    private static final int MAX_LINES = 6;

    private static final int MAX_CHARS_PER_ROW = 16;
    }
