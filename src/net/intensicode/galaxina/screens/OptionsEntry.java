package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

final class OptionsEntry extends ScreenBase
    {
    //#if TOUCH_SUPPORTED
    public final TouchableArea touchable = new TouchableArea();
    //#endif

    int id;

    OptionsEntry( final FontGenerator aCharGen, final String aText, final Position aPosition )
        {
        myCharGen = aCharGen;
        myText = aText;
        myTextPosition = aPosition;
        }

    final void setSelected( boolean aSelectedFlag )
        {
        mySelectState = aSelectedFlag;
        }

    final void changeState( boolean aStateFlag )
        {
        myOnOffState = aStateFlag;
        }

    //#if TOUCH_SUPPORTED

    final void updateTouchable()
        {
        final Rectangle rectangle = touchable.rectangle;
        rectangle.width = screen().width() - myTextPosition.x - myTextPosition.x;
        rectangle.height = myCharGen.charHeight();
        rectangle.x = myTextPosition.x;
        rectangle.y = myTextPosition.y - rectangle.height / 2;
        rectangle.applyOutsets( 1 + rectangle.height / 4 );
        }

    //#endif

    // From ScreenBase

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics gc = graphics();

        if ( mySelectState )
            {
            final int x = 0;
            final int y = myTextPosition.y - myCharGen.charHeight() / 2;
            final int width = screen().width();
            final int height = myCharGen.charHeight();
            gc.setColorRGB24( 0x7F0000 );
            gc.fillRect( x, y, width, height );
            }

        myCharGen.blitString( gc, myText, myTextPosition, FontGenerator.LEFT | FontGenerator.VCENTER );

        myStatePosition.x = screen().width() - myTextPosition.x;
        myStatePosition.y = myTextPosition.y;

        final String state = myOnOffState ? I18n._( "ON" ) : I18n._( "OFF" );
        myCharGen.blitString( gc, state, myStatePosition, FontGenerator.RIGHT | FontGenerator.VCENTER );
        }


    private boolean mySelectState;

    private boolean myOnOffState;

    private final String myText;

    private final FontGenerator myCharGen;

    private final Position myTextPosition;

    private final Position myStatePosition = new Position();
    }
