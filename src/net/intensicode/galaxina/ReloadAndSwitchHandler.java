package net.intensicode.galaxina;

import net.intensicode.galaxina.game.GameContext;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Log;

public final class ReloadAndSwitchHandler extends ScreenBase
    {
    public final void attach( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    public final void reloadGame() throws Exception
        {
        myTriggerReload = true;
        }

    public final void switchToLevel( final int aLevelIndex ) throws Exception
        {
        //#if DEBUG
        Log.debug( "switchToLevel {}", aLevelIndex );
        //#endif
        myTriggerLevelSwitch = aLevelIndex;
        }

    // From ScreenBase

    public final void onControlTick() throws Exception
        {
        if ( myTriggerReload )
            {
            //#if DEBUG
            Log.debug( "ReloadAndSwitchHandler triggering game reload" );
            //#endif
            myGameContext.startGame();
            myGameContext.gameModel().enemySpawner.reload();
            myTriggerReload = false;
            }

        if ( myTriggerLevelSwitch > 0 )
            {
            //#if DEBUG
            Log.debug( "ReloadAndSwitchHandler triggering level switch to {}", myTriggerLevelSwitch );
            //#endif

            // Start a new game first, then set the level number:
            myGameContext.startGame();
            myGameContext.gameModel().level.numberStartingAt1 = myTriggerLevelSwitch;

            // This will update the level number on the level info screen:
            stack().activeScreen().onInit( system() );

            myTriggerLevelSwitch = 0;
            }
        }

    public final void onDrawFrame()
        {
        }


    private boolean myTriggerReload;

    private int myTriggerLevelSwitch;

    private GameContext myGameContext;
    }
