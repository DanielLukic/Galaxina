package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;


public final class TitleLogoAnimScreen extends ScreenBase
    {
    public TitleLogoAnimScreen( final ImageResource aImage, final int aX, final int aY )
        {
        myImage = aImage;
        myImagePosition.x = aX;
        myImagePosition.y = aY;
        myImageWidth = aImage.getWidth();
        myImageHeight = aImage.getHeight();
        myLineData = new int[myImageWidth];
        }

    // From ScreenBase

    public void onControlTick()
        {
        final int animTicks = timing().ticksPerSecond * 4;
        if ( myAnimIndex < animTicks ) myAnimIndex++;
        else myAnimIndex = 0;

        myPhaseOneIndex = myAnimIndex * Sinus.SIN_TABLE_SIZE / animTicks;
        myPhaseTwoIndex = myAnimIndex * 3 * Sinus.SIN_TABLE_SIZE / animTicks + mySinus.sin( myPhaseOneIndex, Sinus.SIN_TABLE_SIZE );
        }

    public void onDrawFrame()
        {
        final DirectGraphics gc = graphics();
        for ( int lineIndex = -myImageHeight / 2; lineIndex < myImageHeight * 3 / 2; lineIndex++ )
            {
            final int sinOffset = lineIndex * Sinus.SIN_TABLE_SIZE / 4 / myImageHeight;
            final int xOffset = mySinus.sin( myPhaseTwoIndex + sinOffset, myImageHeight / 8 );
            final int yOffset = mySinus.sin( myPhaseOneIndex + sinOffset, myImageHeight / 4 );
            final int[] lineData = extractImageLine( lineIndex + yOffset );
            if ( lineData == null) continue;
            final int xPos = myImagePosition.x + xOffset;
            final int yPos = myImagePosition.y + lineIndex;
            gc.drawRGB( lineData, 0, myImageWidth, xPos, yPos, myImageWidth, 1, true );
            }
        }

    // Implementation

    private int[] extractImageLine( final int aLineIndex )
        {
        if ( aLineIndex < 0 || aLineIndex >= myImageHeight ) return null;
        myImage.getRGB( myLineData, 0, myImageWidth, 0, aLineIndex, myImageWidth, 1 );
        return myLineData;
        }


    private int myAnimIndex;

    private int myPhaseOneIndex;

    private int myPhaseTwoIndex;

    private final int myImageWidth;

    private final int myImageHeight;

    private final int[] myLineData;

    private final ImageResource myImage;

    private final Sinus mySinus = Sinus.instance();

    private final Position myImagePosition = new Position();
    }
