package net.intensicode.galaxina;

import net.intensicode.core.GameSystem;
import net.intensicode.galaxina.game.VisualContext;
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

    public final VisualContext visualContext()
        {
        return myVisualContext;
        }

    public final void showMainMenu() throws Exception
        {
        myScreenBuilder.showMainMenu();
        }

    // From LoadingCallback

    public final void onLoadingDone( final GameSystem aGameSystem ) throws Exception
        {
        if ( myLateInitFlag ) return;
        myLateInitFlag = true;

        myVisualContext.initialize();

        //myGameController = new GameController( mySkin );
        //myGameController.onInit( aGameSystem );

        //#if DEBUG
        stack().addGlobalHandler( new SkinErrorHandler( skin() ) );
        //#endif

        ////#if CHEAT
        //stack().addGlobalHandler( new CheatHandler( myGameController ) );
        ////#endif

        //#if CONSOLE
        stack().addGlobalHandler( new ConsoleOverlay( skin().font( "textfont" ) ) );
        //#endif
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
        if ( myState < STATE_TITLE ) myState++;
        }

    public final void onDrawFrame()
        {
        }



    private boolean myLateInitFlag;

    private int myState = STATE_LOADING;


    private ScreensBuilder myScreenBuilder;

    private ConfigurableVisualContext myVisualContext;


    private static final int STATE_LOADING = 0;

    private static final int STATE_AUDIO = 1;

    private static final int STATE_TITLE = 2;
    }
