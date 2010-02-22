package net.intensicode.galaxina.domain;

import net.intensicode.IntensiME;
import net.intensicode.core.*;
import net.intensicode.galaxina.*;
import net.intensicode.galaxina.game.GameContext;
import net.intensicode.galaxina.screens.GameScreen;
import net.intensicode.screens.*;
import net.intensicode.util.*;


public final class EmbeddedGalaxina extends IntensiME
    {
    public final void reloadGame() throws Exception
        {
        myReloadHandler.reloadGame();
        }

    public final void switchToLevel( final int aLevelIndex ) throws Exception
        {
        myReloadHandler.switchToLevel( aLevelIndex );
        }

    public final GameContext controller() throws Exception
        {
        //#if DEBUG
        Assert.notNull( "game context initialized", myGameContext );
        //#endif
        return myGameContext;
        }

    // From SystemContext

    public final boolean useOpenglIfPossible()
        {
        return false;
        }

    public final ScreenBase createMainScreen( final GameSystem aGameSystem ) throws Exception
        {
        final MainController controller = new MainController();
        controller.onInit( aGameSystem );
        controller.onLoadingDone( aGameSystem );

        myGameContext = controller.gameContext();

        myReloadHandler.attach( myGameContext );
        aGameSystem.stack.addGlobalHandler( myReloadHandler );

        //#if CONSOLE
        ConsoleOverlay.show = false;
        //#endif

        getGameSystem().audio.disable();

        myGameScreen = new GameScreen( controller );
        return myGameScreen;
        }

    public final void terminateApplication()
        {
        try
            {
            myGameContext.startGame();
            getGameSystem().stack.pushOnce( myGameScreen );
            }
        catch ( Exception e )
            {
            Log.error( "failed (re)starting game - ignored", e );
            }
        }

    private GameContext myGameContext;

    private final ReloadAndSwitchHandler myReloadHandler = new ReloadAndSwitchHandler();

    private GameScreen myGameScreen;
    }
