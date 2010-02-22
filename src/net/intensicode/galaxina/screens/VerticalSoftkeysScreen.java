package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public class VerticalSoftkeysScreen extends ScreenBase
    {
    public VerticalSoftkeysScreen( final FontGenerator aFont )
        {
        myFontGen = aFont;
        //#if TOUCH_SUPPORTED
        myLeftTouchRect.associatedKeyID = KeysHandler.LEFT_SOFT;
        myRightTouchRect.associatedKeyID = KeysHandler.RIGHT_SOFT;
        //#endif
        setPositionInPercent( 25 );
        }

    public final void setButtonImage( final ImageResource aButtonImage )
        {
        myButtonImage = aButtonImage;
        mySomethingChanged = true;
        }

    public final void setSoftkeys( final String aLeftText, final String aRightText )
        {
        myLeftText = aLeftText;
        myRightText = aRightText;
        mySomethingChanged = true;
        }

    public final void setInsets( final int aHorizontalPixels, final int aVerticalPixels )
        {
        myInsetX = aHorizontalPixels;
        myInsetY = aVerticalPixels;
        mySomethingChanged = true;
        }

    public final void setPositionInPercent( final int aVerticalPositionInPercent )
        {
        myVerticalPositionMode = MODE_VERTICAL_POSITION_PERCENT;
        myVerticalPositionValue = aVerticalPositionInPercent;
        mySomethingChanged = true;
        }

    public final void setVerticalPosition( final int aVerticalPositionInPixels )
        {
        myVerticalPositionMode = MODE_VERTICAL_POSITION_ABSOLUTE;
        myVerticalPositionValue = aVerticalPositionInPixels;
        mySomethingChanged = true;
        }

    // From ScreenBase

    public void onTop()
        {
        super.onTop();

        //#if TOUCH_SUPPORTED
        updateTouchableAreas();
        //#endif
        }

    public void onPop()
        {
        super.onPop();

        //#if TOUCH_SUPPORTED
        removeTouchableAreas();
        //#endif
        }

    public final void onControlTick() throws Exception
        {
        if ( mySomethingChanged ) updateTouchableAreas();
        //else tickTouchableAreas();
        mySomethingChanged = false;
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();

        if ( hasLeftText() )
            {
            setPosition( 0, myLeftText );
            if ( myButtonImage != null )
                {
                graphics.drawImage( myButtonImage, myPosition.x, myPosition.y, ALIGN_TOP_LEFT );
                }
            blitVerticalString( myLeftText, ALIGN_TOP_LEFT );
            }

        if ( hasRightText() )
            {
            setPosition( screen().width(), myRightText );
            if ( myButtonImage != null )
                {
                graphics.drawImage( myButtonImage, myPosition.x, myPosition.y, ALIGN_TOP_RIGHT );
                }
            blitVerticalString( myRightText, ALIGN_TOP_RIGHT );
            }
        }

    // Implementation

    private boolean hasLeftText()
        {
        return myLeftText != null && myLeftText.length() > 0;
        }

    private boolean hasRightText()
        {
        return myRightText != null && myRightText.length() > 0;
        }

    private void setPosition( final int aHorizontalPosition, final String aText )
        {
        final int height = getVerticalButtonHeight( aText );
        myPosition.x = aHorizontalPosition;
        myPosition.y = calcVerticalPosition( height );
        }

    private int getVerticalButtonHeight( final String aText )
        {
        if ( myButtonImage != null ) return myButtonImage.getHeight();
        else return getTextHeight( aText ) + myInsetY * 2;
        }

    private int getTextHeight( final String aText )
        {
        return myFontGen.charHeight() * aText.length();
        }

    private int calcVerticalPosition( final int aObjectHeight )
        {
        if ( myVerticalPositionMode == MODE_VERTICAL_POSITION_ABSOLUTE )
            {
            return myVerticalPositionValue;
            }
        final int availableHeight = screen().height() - aObjectHeight;
        return availableHeight * myVerticalPositionValue / ONE_HUNDRED_PERCENT;
        }

    private void blitVerticalString( final String aText, final int aAlignment )
        {
        final int alignWidth = getAlignWidthForVerticalText( aText );
        final int charHeight = myFontGen.charHeight();
        final int x = myPosition.x;
        final int y = myPosition.y;
        final Position aligned = DirectGraphics.getAlignedPosition( x, y, alignWidth, charHeight, aAlignment );

        final DirectGraphics graphics = graphics();
        for ( int idx = 0; idx < aText.length(); idx++ )
            {
            final int yPos = aligned.y + myInsetY + charHeight * idx;

            final char charCode = aText.charAt( idx );

            final int xCentered = aligned.x + ( alignWidth - myFontGen.charWidth( charCode ) ) / 2;
            myFontGen.blitChar( graphics, xCentered, yPos, charCode );
            }
        }

    private int getAlignWidthForVerticalText( final String aText )
        {
        final int maxCharWidth = myFontGen.maxCharWidth( aText );
        return maxCharWidth + myInsetX * 2;
        }

    //#if TOUCH_SUPPORTED

    private void updateTouchableAreas()
        {
        updateTouchableArea( myLeftTouchRect, myLeftText, 0 );
        final int alignWidth = getAlignWidthForVerticalText( myRightText );
        updateTouchableArea( myRightTouchRect, myRightText, screen().width() - alignWidth );
        }

    private void updateTouchableArea( final TouchableArea aTouchableArea, final String aText, final int aHorizontalPosition )
        {
        if ( aText == null || aText.length() == 0 )
            {
            touch().removeLocalControl( aTouchableArea );
            }
        else
            {
            setPosition( aHorizontalPosition, aText );
            aTouchableArea.rectangle.x = myPosition.x;
            aTouchableArea.rectangle.y = myPosition.y;
            aTouchableArea.rectangle.width = getAlignWidthForVerticalText( aText );
            aTouchableArea.rectangle.height = getVerticalButtonHeight( aText );
            touch().addLocalControl( aTouchableArea );
            }
        }

    private void removeTouchableAreas()
        {
        touch().removeLocalControl( myLeftTouchRect );
        touch().removeLocalControl( myRightTouchRect );
        }

    //#endif


    private int myInsetX = 4;

    private int myInsetY = 2;

    private String myLeftText;

    private String myRightText;

    private ImageResource myButtonImage;

    private int myVerticalPositionMode;

    private int myVerticalPositionValue;

    private boolean mySomethingChanged;


    private final FontGenerator myFontGen;

    private final Position myPosition = new Position();

    //#if TOUCH_SUPPORTED

    private final TouchableArea myLeftTouchRect = new TouchableArea();

    private final TouchableArea myRightTouchRect = new TouchableArea();

    //#endif


    private static final int ONE_HUNDRED_PERCENT = 100;

    private static final int MODE_VERTICAL_POSITION_PERCENT = 0;

    private static final int MODE_VERTICAL_POSITION_ABSOLUTE = 1;

    private static final int ALIGN_TOP_LEFT = DirectGraphics.ALIGN_LEFT;

    private static final int ALIGN_TOP_RIGHT = DirectGraphics.ALIGN_RIGHT;
    }
