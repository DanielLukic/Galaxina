package net.intensicode.galaxina;

import net.intensicode.screens.ScreenBase;
import net.intensicode.galaxina.game.GameController;
import net.intensicode.util.Log;

public final class ReloadAndSwitchHandler extends ScreenBase
    {
    public final void attach( final GameController aGameController )
        {
        myGameController = aGameController;
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
            myGameController.startGame();
            myGameController.gameModel().enemySpawner.reload();
            myTriggerReload = false;
            }

        if ( myTriggerLevelSwitch > 0 )
            {
            // Start a new game first, then set the level number:
            myGameController.startGame();
            myGameController.gameModel().level.numberStartingAt1 = myTriggerLevelSwitch;

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

    private GameController myGameController;
    }
