package net.intensicode.game.drawers;

import net.intensicode.core.DirectGraphics;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.*;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

public final class InfoFlashDrawer extends ScreenBase
    {
    public InfoFlashDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        myFontGen = myGameContext.visualContext().textFont();
        myFlashPosition.x = screen().width() / 2;
        myFlashPosition.y = screen().height() * 3 / 4;
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();

        final GameModel model = myGameContext.gameModel();
        final int intensity = model.screenFlashIntensity;
        if ( intensity > 0 )
            {
            graphics.setColorRGB24( intensity << 16 | intensity << 8 | intensity );
            graphics.fillRect( 0, 0, screen().width(), screen().height() );
            }

        final InfoFlash flash = model.infoFlash;
        if ( !flash.visible || flash.message == null ) return;
        myFontGen.blitString( graphics, flash.message, myFlashPosition, FontGenerator.CENTER );
        }



    private BitmapFontGenerator myFontGen;

    private final GameContext myGameContext;

    private final Position myFlashPosition = new Position();
    }
