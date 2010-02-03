package net.intensicode;

import net.intensicode.screens.ScreenBase;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.GameEngine;
import net.intensicode.game.GameController;
import net.intensicode.util.Log;

public final class ReloadAndSwitchHandler extends ScreenBase
    {
    public ReloadAndSwitchHandler( final GameController aGameController )
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
            myGameController.startGame();
            myGameController.gameModel().level.numberStartingAt1 = myTriggerLevelSwitch;
            myTriggerLevelSwitch = 0;
            }
        }

    public final void onDrawFrame()
        {
        }



    private boolean myTriggerReload;

    private int myTriggerLevelSwitch;

    private final GameController myGameController;
    }
