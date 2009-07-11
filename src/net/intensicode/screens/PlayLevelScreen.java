package net.intensicode.screens;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Keys;
import net.intensicode.core.MultiScreen;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.GameModel;



/**
 * Shows the level to played next.
 */
public final class PlayLevelScreen extends MultiScreen
    {
    public PlayLevelScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        myGameModel = aGameContext.gameModel();
        }

    // From MultiScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        addScreen( myGameContext.visualContext().sharedGameBackground() );
        addScreen( myGameContext.visualContext().sharedGameDrawers() );
        }

    public final void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "MENU", "PAUSE", true );

        aEngine.keys.dontRepeatFlags[ Keys.FIRE1 ] = false;
        aEngine.keys.dontRepeatFlags[ Keys.FIRE2 ] = true;
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        myGameModel.onControlTick();

        if ( myGameModel.state != GameModel.STATE_PLAY_LEVEL )
            {
            aEngine.popScreen( this );
            return;
            }

        super.onControlTick( aEngine );

        final Keys keys = aEngine.keys;
        if ( keys.checkLeftSoftAndConsume() ) myGameContext.showMainMenu();
        else if ( keys.checkRightSoftAndConsume() ) myGameContext.pauseGame();
        }



    private final GameModel myGameModel;

    private final GameContext myGameContext;
    }
