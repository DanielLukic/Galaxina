package net.intensicode.galaxina;

import net.intensicode.core.*;
import net.intensicode.galaxina.VisualContext;
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
        loadingScreen.setStartSound( audio().loadSound( "psychocell" ) );
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
        stack().pushOnce( new TitleScreen( myMainContext ) );
        }

    public final void showMainMenu() throws Exception
        {
        stack().pushOnce( new MainMenuScreen( myMainContext ) );
        }

    public final void showGameScreen() throws Exception
        {
        if ( myGameScreen == null ) myGameScreen = new GameScreen( myMainContext );
        stack().pushOnce( myGameScreen );
        }

    public final void showHelp() throws Exception
        {
        stack().pushOnce( new HelpScreen( myMainContext ) );
        }

    public final void showHiscore() throws Exception
        {
        stack().pushOnce( new HiscoreScreen( myMainContext ) );
        }

    public final void showOptions() throws Exception
        {
        stack().pushOnce( new OptionsScreen( myMainContext ) );
        }

    public final void showControls() throws Exception
        {
        stack().pushOnce( new ControlsScreen( myMainContext ) );
        }

    public final void showReset() throws Exception
        {
        stack().pushOnce( new ResetScreen( myMainContext ) );
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


    private GameScreen myGameScreen;

    private final GameSystem myGameSystem;

    private final MainContext myMainContext;

    private final VisualContext myVisualContext;
    }
