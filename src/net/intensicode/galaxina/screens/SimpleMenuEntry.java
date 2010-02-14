package net.intensicode.galaxina.screens;

import net.intensicode.core.DirectGraphics;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.util.Position;
import net.intensicode.screens.ScreenBase;

final class SimpleMenuEntry extends ScreenBase
    {
    SimpleMenuEntry( final FontGenerator aFontGen, final String aText, final Position aPosition )
        {
        myFontGen = aFontGen;
        myText = aText;
        myPosition = aPosition;
        }

    final void setSelected( boolean aSelectedFlag )
        {
        mySelectState = aSelectedFlag;
        }

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
            final int y = myPosition.y - myFontGen.charHeight() / 2;
            final int width = screen().width();
            final int height = myFontGen.charHeight();
            gc.setColorRGB24( 0xFF7F0000 );
            gc.fillRect( x, y, width, height );
            }

        myFontGen.blitString( gc, myText, myPosition, FontGenerator.CENTER );
        }



    private boolean mySelectState;

    private final Position myPosition;

    private final String myText;

    private final FontGenerator myFontGen;
    }
