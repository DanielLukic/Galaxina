package net.intensicode.galaxina;

import net.intensicode.core.GameSystem;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.screens.LoadingCallback;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.io.StorageIO;
import net.intensicode.screens.*;
import net.intensicode.util.*;

import java.io.IOException;

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

    public final Controls controls()
        {
        return myControls;
        }

    public final Options options()
        {
        return myOptions;
        }

    public final Hiscore hiscore()
        {
        return myHiscore;
        }

    public final void saveHiscore() throws Exception
        {
        storage().save( myHiscore );
        }

    //#if ONLINE_HISCORE
    public final void updateHiscore() throws Exception
        {
        throw new RuntimeException( "nyi" );
        }
    //#endif

    public final void showMainMenu() throws Exception
        {
        myScreenBuilder.showMainMenu();
        }

    public final void showHelp() throws Exception
        {
        myScreenBuilder.showHelp();
        }

    public final void showHiscore() throws Exception
        {
        myScreenBuilder.showHiscore();
        }

    public final void showOptions() throws Exception
        {
        myScreenBuilder.showOptions();
        }

    public final void showControls() throws Exception
        {
        myScreenBuilder.showControls();
        }

    public final void showReset() throws Exception
        {
        myScreenBuilder.showReset();
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

        myVisualContext.initialize( resources() );

        myGameController = new GameController( this );
        myGameController.onInit( aGameSystem );

        addGlobalHandlers();

        myControls = new Controls();
        myControls.initFrom( keys() );
        tryLoading( myControls );
        myControls.activate( keys() );

        myOptions = new Options( myVisualContext.configuration(), audio() );
        tryLoading( myOptions );

        myHiscore = new Hiscore();
        tryLoading( myHiscore );
        }

    private void addGlobalHandlers() throws Exception
        {
        //#if DEBUG
        stack().addGlobalHandler( new SkinErrorHandler( skin() ) );
        //#endif

        ////#if CHEAT
        //stack().addGlobalHandler( new CheatHandler( myGameController ) );
        ////#endif

        //#if CONSOLE
        stack().addGlobalHandler( new ConsoleOverlay( skin().font( "textfont" ) ) );
        //#endif

        stack().addGlobalHandler( new EngineStats( skin().font( "textfont" ) ) );

        myMusicController = new MusicController();
        stack().addGlobalHandler( myMusicController );
        }

    private void tryLoading( final StorageIO aStorageIO )
        {
        try
            {
            if ( storage().hasData( aStorageIO ) ) storage().load( aStorageIO );
            }
        catch ( final IOException e )
            {
            Log.error( "Failed loading data for storage object {}", aStorageIO, e );
            }
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        final BitmapFontGenerator textfont = skin().font( "textfont" );
        final AutohideSoftkeysScreen softkeys = new AutohideSoftkeysScreen( textfont );
        softkeys.setButtonImage( skin().image( "softkeys" ) );
        myVisualContext = new ConfigurableVisualContext( skin(), softkeys );
        myScreenBuilder = new ScreensBuilder( this );
        }

    public final void onControlTick() throws Exception
        {
        //#if FALSE
        if ( myState < STATE_TITLE )
            {
            myState = STATE_TITLE;
            onLoadingDone( system() );
            }
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


    private Hiscore myHiscore;

    private Options myOptions;

    private Controls myControls;

    private boolean myLateInitFlag;

    private int myState = STATE_LOADING;

    private GameController myGameController;

    private ScreensBuilder myScreenBuilder;

    private MusicController myMusicController;

    private ConfigurableVisualContext myVisualContext;

    private static final int STATE_LOADING = 0;

    private static final int STATE_AUDIO = 1;

    private static final int STATE_TITLE = 2;

    private static final int STATE_MAIN_MENU = 3;
    }
