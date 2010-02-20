package net.intensicode.galaxina.domain;

import net.intensicode.IntensiME;
import net.intensicode.util.Log;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.core.*;
import net.intensicode.galaxina.*;
import net.intensicode.galaxina.screens.GameScreen;
import net.intensicode.galaxina.game.*;
import net.intensicode.screens.*;


public final class EmbeddedGalaxina extends IntensiME implements MainContext
    {
    public EmbeddedGalaxina()
        {
        }

    public final void reloadGame() throws Exception
        {
        myReloadHandler.reloadGame();
        }

    public final void switchToLevel( final int aLevelIndex ) throws Exception
        {
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
            final FontGenerator font = aGameSystem.skin.font( "textfont" );
            mySoftkeysScreen = new AutohideSoftkeysScreen( font );

            final SkinManager skin = getGameSystem().skin;
            myVisualContext = new ConfigurableVisualContext( skin, mySoftkeysScreen );
            myVisualContext.initialize();

            myGameController = new GameController( this );
            myGameController.onInit( aGameSystem );
            myGameController.startGame();

            myReloadHandler.attach( myGameController );
            aGameSystem.stack.addGlobalHandler( myReloadHandler );

            synchronized ( this )
                {
                notifyAll();
                }
            }
        return new GameScreen( this );
        }

    // From MainContext

    public GameSystem gameSystem()
        {
        return getGameSystem();
        }

    public GameContext gameContext()
        {
        return myGameController;
        }

    public VisualContext visualContext()
        {
        return myVisualContext;
        }

    public MusicController musicController()
        {
        return new NoMusicController();
        }

    public void showMainMenu() throws Exception
        {
        myGameController.startGame();
        }

    public void showHelp() throws Exception
        {
        }

    public void showHiscore() throws Exception
        {
        }

    public void showOptions() throws Exception
        {
        }

    public void startNewGame() throws Exception
        {
        myGameController.startGame();
        }


    private GameController myGameController;

    private AutohideSoftkeysScreen mySoftkeysScreen;

    private ConfigurableVisualContext myVisualContext;

    private final ReloadAndSwitchHandler myReloadHandler = new ReloadAndSwitchHandler();
    }
