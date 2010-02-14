package net.intensicode.galaxina.screens;

import net.intensicode.core.DirectGraphics;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Sinus;


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
                final TitleFancyChar fancyChar = myChars[idx];
                if ( fancyChar.isDead() ) continue;
                myChars[idx].tick();
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
            final TitleFancyChar fancyChar = myChars[idx];
            if ( !fancyChar.isVisible() ) continue;
            final int charIndex = idx % MAX_CHARS_PER_ROW;
            final int lineIndex = idx / MAX_CHARS_PER_ROW;
            final int xPos = fancyChar.drawOffsetX + charIndex * myFontGenerator.charHeight();
            final int yPos = fancyChar.drawOffsetY + lineIndex * myFontGenerator.charHeight() + screen().height() / 3;
            final int safeAlpha = Math.max( 0, Math.min( 255, fancyChar.alpha ) );
            myFontGenerator.blendChar( graphics, xPos, yPos, fancyChar.charCode, safeAlpha );
            }
        }

    // Implementation

    private void extractNewLines()
        {
        clearCharBuffer();
        setCharLine( 2, "AN" );
        setCharLine( 3, "INTENSICODE" );
        setCharLine( 4, "PRODUCTION" );
        myVisibleLines = 3;
        }

    private void clearCharBuffer()
        {
        myVisibleLines = 0;
        for ( int idx = 0; idx < myChars.length; idx++ )
            {
            if ( myChars[idx] == null ) myChars[idx] = new TitleFancyChar();
            myChars[idx].reset();
            }
        }

    private void setCharLine( final int aLineIndex, final String aString )
        {
        final int lineOffset = aLineIndex * MAX_CHARS_PER_ROW;
        final int charOffset = ( MAX_CHARS_PER_ROW - aString.length() ) / 2;
        for ( int idx = 0; idx < aString.length(); idx++ )
            {
            myChars[lineOffset + charOffset + idx].charCode = aString.charAt( idx );
            }
        }

    private void setupAnimation()
        {
        final int tps = timing().ticksPerSecond;
        final int overallWaitTicks = tps * myVisibleLines * 2;
        final int incomingTicks = tps * 2;
        final int stayTicks = tps * 5;
        final int outgoingTicks = tps * 1;
        final int moveDistance = myFontGenerator.charHeight() * 3;
        final int numberOfChars = myChars.length;
        for ( int idx = 0; idx < numberOfChars; idx++ )
            {
            myChars[idx].waitTicks = idx * overallWaitTicks / numberOfChars;
            final int charIndex = idx % MAX_CHARS_PER_ROW;
            final int lineIndex = idx / MAX_CHARS_PER_ROW;
            final int sinIndex = charIndex * Sinus.SIN_TABLE_SIZE * 3 / MAX_CHARS_PER_ROW;
            myChars[idx].incomingTicks = incomingTicks;
            myChars[idx].stayTicks = stayTicks;
            myChars[idx].outgoingTicks = outgoingTicks;
            myChars[idx].startOffsetX = mySinus.cos( sinIndex, moveDistance );
            myChars[idx].startOffsetY = mySinus.sin( sinIndex, moveDistance );
            myChars[idx].endOffsetX = mySinus.sin( sinIndex, moveDistance );
            myChars[idx].endOffsetY = mySinus.cos( sinIndex, moveDistance ) * 2;
            }
        }


    private int myVisibleLines;

    private boolean myNeedNewLines;

    private final String myCreditsText;

    private final FontGenerator myFontGenerator;

    private final Sinus mySinus = Sinus.instance();

    private TitleFancyChar[] myChars = new TitleFancyChar[MAX_CHARS_PER_ROW * MAX_LINES];

    private static final int MAX_LINES = 6;

    private static final int MAX_CHARS_PER_ROW = 16;
    }
