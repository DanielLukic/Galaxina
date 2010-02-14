package net.intensicode.galaxina;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.VisualContext;
import net.intensicode.galaxina.screens.*;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.screens.SoftkeysScreen;

public final class ScreensBuilder
    {
    public ScreensBuilder( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        myGameSystem = aMainContext.gameSystem();
        myVisualContext = aMainContext.visualContext();
        }

    public final void showLoadingScreen( final LoadingCallback aLoadingCallback ) throws Exception
        {
        final BitmapFontGenerator textfont = skin().font( "menufont" );
        final LoadingScreen loadingScreen = new LoadingScreen( textfont );
        loadingScreen.shareSoftkeys( softkeys() );
        loadingScreen.setLateInit( aLoadingCallback );
        loadingScreen.setLogoAnim( skin().sprite( "logo_anim" ) );
        //loadingScreen.setStartSound( audio().loadSound( "psychocell" ) );
        stack().pushOnce( loadingScreen );
        }

    public final void showAudioMenu() throws Exception
        {
        final BitmapFontGenerator menufont = skin().font( "menufont" );
        final AudioMenu audioMenu = new AudioMenu( menufont );
        audioMenu.insertScreen( myVisualContext.sharedBackground() );
        audioMenu.shareSoftkeys( softkeys() );
        stack().pushOnce( audioMenu );
        }

    public final void showTitleScreen() throws Exception
        {
        if ( myTitleScreen == null ) myTitleScreen = new TitleScreen( myMainContext );
        stack().pushOnce( myTitleScreen );
        }

    public final void showMainMenu() throws Exception
        {
        //if ( myMainMenuScreen == null ) myMainMenuScreen = new MainMenuScreen( myMainContext );
        //stack().pushOnce( myMainMenuScreen );
        throw new RuntimeException( "nyi" );
        }

    public final void showHelp() throws Exception
        {
        //if ( myHelpScreen == null ) myHelpScreen = new HelpScreen( myMainContext );
        //stack().pushOnce( myHelpScreen );
        throw new RuntimeException( "nyi" );
        }

    public final void showHiscore() throws Exception
        {
        throw new RuntimeException( "nyi" );
        }

    public final void showOptions() throws Exception
        {
        throw new RuntimeException( "nyi" );
        }

    // Implementation

    private SoftkeysScreen softkeys()
        {
        return myVisualContext.sharedSoftkeys();
        }

    private AudioManager audio()
        {
        return myGameSystem.audio;
        }

    private SkinManager skin()
        {
        return myGameSystem.skin;
        }

    private ScreenStack stack()
        {
        return myGameSystem.stack;
        }


    private TitleScreen myTitleScreen;

    private final GameSystem myGameSystem;

    private final MainContext myMainContext;

    private final VisualContext myVisualContext;
    }
