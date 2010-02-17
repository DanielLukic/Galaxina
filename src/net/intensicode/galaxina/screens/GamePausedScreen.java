package net.intensicode.galaxina.screens;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.*;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;

public final class GamePausedScreen extends MultiScreen
    {
    public GamePausedScreen( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        myGameContext = myMainContext.gameContext();
        myVisualContext = myMainContext.visualContext();
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        addScreen( myGameContext.sharedGameBackground() );

        final int x = screen().width() / 2;

        final FontGenerator titleFont = myVisualContext.titleFont();
        final int y1 = screen().height() / 2 - titleFont.charHeight();
        addScreen( new AlignedTextScreen( titleFont, "GAME PAUSED", x, y1, FontGenerator.CENTER ) );
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
            myMainContext.showMainMenu();
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            myGameContext.gameModel().unpauseGame();
            }
        }


    private final MainContext myMainContext;

    private final GameContext myGameContext;

    private final VisualContext myVisualContext;
    }
