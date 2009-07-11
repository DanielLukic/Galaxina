package net.intensicode.screens;

import net.intensicode.core.*;
import net.intensicode.game.GameContext;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;



/**
 * Shows the level to played next.
 */
public final class GameOverScreen extends MultiScreen
{
    public GameOverScreen( final GameContext aGameContext )
    {
        myGameContext = aGameContext;
    }

    // From MultiScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
    {
        addScreen( myGameContext.visualContext().sharedGameBackground() );
        addScreen( myGameContext.visualContext().sharedGameDrawers() );
        myFont = myGameContext.visualContext().textFont();
    }

    public final void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
    {
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "START", "END", false );
    }

    public final void onControlTick( final Engine aEngine ) throws Exception
    {
        myGameContext.gameModel().onControlTick();
        super.onControlTick( aEngine );

        final Keys keys = aEngine.keys;
        if ( keys.checkFireAndConsume() || keys.checkLeftSoftAndConsume() )
        {
            aEngine.popScreen( this );
            myGameContext.gameModel().startGame();
        }
        else if ( keys.checkRightSoftAndConsume() )
        {
            aEngine.popScreen( this );
            myGameContext.pauseGame();
        }
    }

    public final void onDrawFrame( final DirectScreen aScreen )
    {
        super.onDrawFrame( aScreen );

        final Graphics gc = aScreen.graphics();
        myBlitPos.x = aScreen.width() / 2;
        myBlitPos.y = aScreen.height() / 2 + myFont.charHeight();
        myFont.blitString( gc, "GAME OVER", myBlitPos, FontGen.HCENTER | FontGen.TOP );
    }



    private BitmapFontGen myFont;


    private final GameContext myGameContext;

    private final Position myBlitPos = new Position();
}
