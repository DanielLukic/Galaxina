package net.intensicode;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.game.GameController;
import net.intensicode.util.Log;

public final class ReloadAndSwitchHandler extends AbstractScreen
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

    // From AbstractScreen

    public final void onControlTick( final Engine aEngine ) throws Exception
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

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        }



    private boolean myTriggerReload;

    private int myTriggerLevelSwitch;

    private final GameController myGameController;
    }
