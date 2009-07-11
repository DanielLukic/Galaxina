package net.intensicode.screens;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Keys;
import net.intensicode.core.MultiScreen;
import net.intensicode.game.GameContext;
import net.intensicode.game.VisualContext;
import net.intensicode.util.FontGen;



/**
 * TODO: Describe this!
 */
public final class GamePausedScreen extends MultiScreen
{
    public GamePausedScreen( final GameContext aGameContext )
    {
        myGameContext = aGameContext;
    }

    // From AbstractScreen

    public void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
    {
        final VisualContext visualContext = myGameContext.visualContext();

        addScreen( visualContext.sharedGameBackground() );

        final int x = aScreen.width() / 2;

        final FontGen myFont = visualContext.titleFont();

        final int y1 = aScreen.height() / 2 - myFont.charHeight();
        addScreen( new AlignedTextScreen( myFont, "GAME PAUSED", x, y1, FontGen.CENTER ) );
    }

    public void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
    {
        final AutoHideSoftkeysScreen softkeys = myGameContext.visualContext().sharedSoftkeys();
        softkeys.setSoftkeys( "MENU", "BACK", false );
    }

    public final void onControlTick( final Engine aEngine ) throws Exception
    {
        super.onControlTick( aEngine );

        final Keys keys = aEngine.keys;
        if ( keys.checkFireAndConsume() || keys.checkLeftSoftAndConsume() )
        {
            aEngine.popScreen( this );
            myGameContext.showMainMenu();
        }
        else if ( keys.checkRightSoftAndConsume() )
        {
            aEngine.popScreen( this );
        }
    }



    private final GameContext myGameContext;
}
