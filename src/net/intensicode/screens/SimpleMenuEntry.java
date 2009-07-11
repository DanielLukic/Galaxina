/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.screens;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;



final class SimpleMenuEntry extends AbstractScreen
{
    SimpleMenuEntry( final FontGen aFontGen, final String aText, final Position aPosition )
    {
        myFontGen = aFontGen;
        myText = aText;
        myPosition = aPosition;
    }

    final void setSelected( boolean aSelectedFlag )
    {
        mySelectState = aSelectedFlag;
    }

    // From AbstractScreen

    public final void onControlTick( final Engine aEngine ) throws Exception
    {
    }

    public final void onDrawFrame( final DirectScreen aScreen )
    {
        final Graphics gc = aScreen.graphics();

        if ( mySelectState )
        {
            final int x = 0;
            final int y = myPosition.y - myFontGen.charHeight() / 2;
            final int width = aScreen.width();
            final int height = myFontGen.charHeight();
            gc.setColor( 0xFF7F0000 );
            gc.fillRect( x, y, width, height );
        }

        myFontGen.blitString( gc, myText, myPosition, FontGen.CENTER );
    }



    private boolean mySelectState;

    private final Position myPosition;

    private final String myText;

    private final FontGen myFontGen;
}
