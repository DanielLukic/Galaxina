package net.intensicode;

import net.intensicode.core.*;
import net.intensicode.game.GameController;
import net.intensicode.screens.AutoHideSoftkeysScreen;
import net.intensicode.screens.ImageScreen;
import net.intensicode.screens.LoadingCallback;
import net.intensicode.screens.LoadingScreen;
import net.intensicode.util.BitmapFontGen;

import javax.microedition.lcdui.Image;


/**
 * TODO: Describe this!
 */
public final class MainController extends AbstractScreen implements LoadingCallback
    {
    public MainController()
        {
        }

    // From LoadingCallback

    public final void onLoadingDone( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        if ( myLateInitFlag ) return;
        myLateInitFlag = true;

        myGameController = new GameController( mySkin );
        myGameController.onInit( aEngine, screen() );

        //#if DEBUG
        aEngine.addGlobalHandler( new SkinErrorHandler( mySkin ) );
        //#endif

        //#if CHEAT
        aEngine.addGlobalHandler( new CheatHandler( myGameController ) );
        //#endif

        //#if CONSOLE
        aEngine.addGlobalHandler( new ConsoleOverlay( mySkin.font( "textfont" ) ) );
        //#endif
        }

    // From AbstractScreen

    public void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final ResourceLoader loader = aEngine.loader;
        final Configuration skinConfig = loader.loadConfiguration( "/skin.properties" );
        mySkin = new Skin( loader, skinConfig );

        mySharedSoftkeys = new AutoHideSoftkeysScreen( mySkin.font( "textfont" ) );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        if ( myState == STATE_LOADING ) showLoadingScreen( aEngine );
        if ( myState == STATE_GAME ) showGameController();
        if ( myState != STATE_GAME ) myState++;
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        }

    // Implementation

    private final void showLoadingScreen( final Engine aEngine ) throws Exception
        {
        final BitmapFontGen textFont = mySkin.font( "textfont" );
        final LoadingScreen loadingScreen = new LoadingScreen( mySkin, textFont );
        loadingScreen.shareSoftkeys( mySharedSoftkeys );
        loadingScreen.setLateInit( this );

        final Image logoImage = mySkin.image( "logo" );
        final ImageScreen logo = new ImageScreen( logoImage );
        logo.position.x = 50;
        logo.position.y = 50;
        loadingScreen.setLogo( logo );

        aEngine.pushOnce( loadingScreen );
        }

    private final void showGameController() throws Exception
        {
        engine().pushOnce( myGameController );
        }



    private boolean myLateInitFlag;

    private int myState = STATE_LOADING;


    private Skin mySkin;

    private GameController myGameController;

    private AutoHideSoftkeysScreen mySharedSoftkeys;


    private static final int STATE_LOADING = 0;

    private static final int STATE_GAME = 1;
    }
