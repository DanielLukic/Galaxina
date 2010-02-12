package net.intensicode.galaxina.domain;

import net.intensicode.*;
import net.intensicode.core.GameSystem;
import net.intensicode.game.GameController;
import net.intensicode.screens.ScreenBase;


public final class EmbeddedGalaxina extends IntensiME
    {
    public EmbeddedGalaxina()
        {
        }

    public final void reloadGame() throws Exception
        {
        waitForHandler();
        myReloadHandler.reloadGame();
        }

    public final void switchToLevel( final int aLevelIndex ) throws Exception
        {
        waitForHandler();
        myReloadHandler.switchToLevel( aLevelIndex );
        }

    public final GameController controller() throws Exception
        {
        //#if DEBUG
        if ( myGameController == null ) throw new IllegalStateException();
        //#endif
        return myGameController;
        }

    // From SystemContext

    public final boolean useOpenglIfPossible()
        {
        return false;
        }

    public final ScreenBase createMainScreen( final GameSystem aGameSystem ) throws Exception
        {
        if ( myGameController == null )
            {
            myGameController = new GameController( aGameSystem.skin );

            myReloadHandler = new ReloadAndSwitchHandler( myGameController );
            aGameSystem.stack.addGlobalHandler( myReloadHandler );

            synchronized ( this )
                {
                notifyAll();
                }
            }
        return myGameController;
        }

    // Implementation

    private void waitForHandler() throws InterruptedException
        {
        synchronized ( this )
            {
            while ( myReloadHandler == null ) wait();
            }
        }



    private GameController myGameController;

    private ReloadAndSwitchHandler myReloadHandler;
    }
