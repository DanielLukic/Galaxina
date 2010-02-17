package net.intensicode.galaxina;

import net.intensicode.core.GameSystem;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.screens.*;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.screens.*;

public final class MainController extends ScreenBase implements LoadingCallback, MainContext
    {
    public MainController()
        {
        }

    // From MainContext

    public final GameSystem gameSystem()
        {
        return system();
        }

    public final GameContext gameContext()
        {
        return myGameController;
        }

    public final VisualContext visualContext()
        {
        return myVisualContext;
        }

    public final MusicController musicController()
        {
        return myMusicController;
        }

    public final void showMainMenu() throws Exception
        {
        myScreenBuilder.showMainMenu();
        }

    public final void startNewGame() throws Exception
        {
        myScreenBuilder.showGameScreen();
        myGameController.startGame();
        }

    // From LoadingCallback

    public final void onLoadingDone( final GameSystem aGameSystem ) throws Exception
        {
        if ( myLateInitFlag ) return;
        myLateInitFlag = true;

        myVisualContext.initialize();

        myGameController = new GameController( this );
        myGameController.onInit( aGameSystem );

        //#if DEBUG
        stack().addGlobalHandler( new SkinErrorHandler( skin() ) );
        //#endif

        ////#if CHEAT
        //stack().addGlobalHandler( new CheatHandler( myGameController ) );
        ////#endif

        //#if CONSOLE
        stack().addGlobalHandler( new ConsoleOverlay( skin().font( "textfont" ) ) );
        //#endif

        stack().addGlobalHandler( myMusicController );
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        final BitmapFontGenerator textfont = skin().font( "textfont" );
        final AutohideSoftkeysScreen softkeys = new AutohideSoftkeysScreen( textfont );
        myVisualContext = new ConfigurableVisualContext( skin(), softkeys );
        myScreenBuilder = new ScreensBuilder( this );
        }

    public final void onControlTick() throws Exception
        {
        //#if FALSE
        myState = STATE_TITLE;
        onLoadingDone( system() );
        //#endif
        if ( myState == STATE_LOADING ) myScreenBuilder.showLoadingScreen( this );
        if ( myState == STATE_AUDIO ) myScreenBuilder.showAudioMenu();
        if ( myState == STATE_TITLE ) myScreenBuilder.showTitleScreen();
        if ( myState == STATE_MAIN_MENU ) myScreenBuilder.showMainMenu();
        if ( myState < STATE_MAIN_MENU ) myState++;
        }

    public final void onDrawFrame()
        {
        }



    private boolean myLateInitFlag;

    private int myState = STATE_LOADING;

    private GameController myGameController;

    private ScreensBuilder myScreenBuilder;

    private ConfigurableVisualContext myVisualContext;


    private static final int STATE_LOADING = 0;

    private static final int STATE_AUDIO = 1;

    private static final int STATE_TITLE = 2;

    private static final int STATE_MAIN_MENU = 3;

    private final MusicController myMusicController = new MusicController();
    }
