package net.intensicode.galaxina.screens;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.game.GameContext;
import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.screens.MultiScreen;

public final class PlayLevelScreen extends MultiScreen
    {
    public PlayLevelScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        myGameModel = aGameContext.gameModel();
        }

    // From MultiScreen

    public final void onInitOnce() throws Exception
        {
        addScreen( myGameContext.sharedGameBackground() );
        addScreen( myGameContext.sharedGameDrawers() );
        }

    public final void onInitEverytime() throws Exception
        {
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "MENU", "PAUSE", true );

        keys().dontRepeatFlags[ KeysHandler.FIRE1 ] = false;
        keys().dontRepeatFlags[ KeysHandler.FIRE2 ] = true;
        }

    public final void onControlTick() throws Exception
        {
        myGameModel.onControlTick();

        if ( myGameModel.state != GameModel.STATE_PLAY_LEVEL )
            {
            stack().popScreen( this );
            return;
            }

        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkLeftSoftAndConsume() ) myGameContext.showMainMenu();
        else
        if ( keys.checkRightSoftAndConsume() ) myGameContext.pauseGame();
        }


    private final GameModel myGameModel;

    private final GameContext myGameContext;
    }
