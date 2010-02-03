package net.intensicode;

import net.intensicode.core.GameSystem;
import net.intensicode.core.ImageResource;
import net.intensicode.core.SkinManager;
import net.intensicode.game.GameController;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.screens.*;

public final class MainController extends ScreenBase implements LoadingCallback
    {
    public MainController()
        {
        }

    // From LoadingCallback

    public final void onLoadingDone( final GameSystem aGameSystem ) throws Exception
        {
        if ( myLateInitFlag ) return;
        myLateInitFlag = true;

        myGameController = new GameController( mySkin );
        myGameController.onInit( aGameSystem );

        //#if DEBUG
        stack().addGlobalHandler( new SkinErrorHandler( mySkin ) );
        //#endif

        //#if CHEAT
        stack().addGlobalHandler( new CheatHandler( myGameController ) );
        //#endif

        //#if CONSOLE
        stack().addGlobalHandler( new ConsoleOverlay( mySkin.font( "textfont" ) ) );
        //#endif
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        mySkin = skin();

        mySharedSoftkeys = new AutohideSoftkeysScreen( mySkin.font( "textfont" ) );
        }

    public final void onControlTick() throws Exception
        {
        if ( myState == STATE_LOADING ) showLoadingScreen();
        if ( myState == STATE_GAME ) showGameController();
        if ( myState != STATE_GAME ) myState++;
        }

    public final void onDrawFrame()
        {
        }

    // Implementation

    private void showLoadingScreen() throws Exception
        {
        final BitmapFontGenerator textFont = mySkin.font( "textfont" );
        final LoadingScreen loadingScreen = new LoadingScreen( mySkin, textFont );
        loadingScreen.shareSoftkeys( mySharedSoftkeys );
        loadingScreen.setLateInit( this );

        final ImageResource logoImage = mySkin.image( "logo" );
        final ImageScreen logo = new ImageScreen( logoImage );
        logo.position.x = 50;
        logo.position.y = 50;
        loadingScreen.setLogo( logo );

        stack().pushOnce( loadingScreen );
        }

    private void showGameController() throws Exception
        {
        stack().pushOnce( myGameController );
        }



    private boolean myLateInitFlag;

    private int myState = STATE_LOADING;


    private SkinManager mySkin;

    private GameController myGameController;

    private AutohideSoftkeysScreen mySharedSoftkeys;


    private static final int STATE_LOADING = 0;

    private static final int STATE_GAME = 1;
    }
