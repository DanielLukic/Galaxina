package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.InfoFlash;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;



/**
 * TODO: Describe this!
 */
public final class InfoFlashDrawer extends AbstractScreen
    {
    public InfoFlashDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        myFontGen = myGameContext.visualContext().textFont();
        myFlashPosition.x = aScreen.width() / 2;
        myFlashPosition.y = aScreen.height() * 3 / 4;
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();

        final GameModel model = myGameContext.gameModel();
        final int intensity = model.screenFlashIntensity;
        if ( intensity > 0 )
            {
            graphics.setColor( intensity << 16 | intensity << 8 | intensity );
            graphics.fillRect( 0, 0, aScreen.width(), aScreen.height() );
            }

        final InfoFlash flash = model.infoFlash;
        if ( !flash.visible || flash.message == null ) return;
        myFontGen.blitString( graphics, flash.message, myFlashPosition, FontGen.CENTER );
        }



    private BitmapFontGen myFontGen;

    private final GameContext myGameContext;

    private final Position myFlashPosition = new Position();
    }
