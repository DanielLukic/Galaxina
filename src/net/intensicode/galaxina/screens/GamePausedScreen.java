package net.intensicode.galaxina.screens;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.MainContext;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;

public final class GamePausedScreen extends GalaxinaGameScreen
    {
    public GamePausedScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        addScreen( game().sharedGameBackground() );

        final int x = screen().width() / 2;

        final FontGenerator titleFont = visuals().titleFont();
        final int y1 = screen().height() / 2 - titleFont.charHeight();
        addScreen( new AlignedTextScreen( titleFont, "GAME PAUSED", x, y1, FontGenerator.CENTER ) );
        }

    public void onInitEverytime() throws Exception
        {
        game().sharedSoftkeys().setSoftkeys( "MENU", "BACK" );
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkFireAndConsume() || keys.checkLeftSoftAndConsume() )
            {
            stack().popScreen( this );
            context().screens().showMainMenu();
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            stack().popScreen( this );
            model().unpauseGame();
            }
        }
    }
