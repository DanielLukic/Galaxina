package net.intensicode.galaxina.screens;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.GameModel;

public final class PlayLevelScreen extends GalaxinaGameScreen
    {
    public PlayLevelScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        }

    // From MultiScreen

    public final void onInitOnce() throws Exception
        {
        addScreen( game().sharedGameBackground() );
        addScreen( game().sharedGameDrawers() );
        }

    public final void onInitEverytime() throws Exception
        {
        game().sharedSoftkeys().setSoftkeys( "MENU", "PAUSE" );

        keys().dontRepeatFlags[ KeysHandler.FIRE1 ] = false;
        keys().dontRepeatFlags[ KeysHandler.FIRE2 ] = true;
        }

    public final void onControlTick() throws Exception
        {
        model().onControlTick();

        if ( model().state != GameModel.STATE_PLAY_LEVEL )
            {
            stack().popScreen( this );
            return;
            }

        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkLeftSoftAndConsume() ) context().screens().showMainMenu();
        else if ( keys.checkRightSoftAndConsume() ) model().pauseGame();
        }
    }
