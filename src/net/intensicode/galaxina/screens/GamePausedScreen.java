package net.intensicode.galaxina.screens;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.game.*;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;

public final class GamePausedScreen extends MultiScreen
    {
    public GamePausedScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        final VisualContext visualContext = myGameContext.visualContext();

        addScreen( visualContext.sharedGameBackground() );

        final int x = screen().width() / 2;

        final FontGenerator myFont = visualContext.titleFont();

        final int y1 = screen().height() / 2 - myFont.charHeight();
        addScreen( new AlignedTextScreen( myFont, "GAME PAUSED", x, y1, FontGenerator.CENTER ) );
        }

    public void onInitEverytime() throws Exception
        {
        final AutohideSoftkeysScreen softkeys = myGameContext.visualContext().sharedSoftkeys();
        softkeys.setSoftkeys( "MENU", "BACK", false );
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkFireAndConsume() || keys.checkLeftSoftAndConsume() )
            {
            stack().popScreen( this );
            myGameContext.showMainMenu();
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            }
        }



    private final GameContext myGameContext;
    }
