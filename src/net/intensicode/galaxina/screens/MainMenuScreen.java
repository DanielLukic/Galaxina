package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.MainContext;
import net.intensicode.screens.*;

public final class MainMenuScreen extends GalaxinaScreen implements MenuHandlerEx
    {
    public MainMenuScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( visuals().sharedBackground() );

        final BasicMenu menu = new BasicMenu( this, visuals().menuFont() );
        menu.setEntryImage( skin().sprite( "menuentry" ) );
        menu.addMenuEntry( START_GAME, "START GAME" );
        menu.addMenuEntry( SHOW_HELP, "SHOW HELP" );
        menu.addMenuEntry( HISCORE, "HISCORE" );
        menu.addMenuEntry( OPTIONS, "OPTIONS" );
        menu.addMenuEntry( CONTROLS, "CONTROLS" );
        menu.addMenuEntry( RESET, "RESET" );
        menu.addMenuEntry( INFO, "INFO" );
        menu.addMenuEntry( EXIT, "EXIT" );
        addScreen( menu );

        mySoftkeys = visuals().sharedSoftkeys();
        addScreen( mySoftkeys );
        }

    public final void onInitEverytime() throws Exception
        {
        final int screenCount = stack().numberOfStackedScreens();
        if ( screenCount <= MAIN_CONTROLLER_AND_MAIN_MENU )
            {
            mySoftkeys.setSoftkeys( "SELECT", "", false );
            myHandleBack = false;
            }
        else if ( screenCount >= MAIN_CONTROLLER_MAIN_MENU_AND_GAME_SCREEN )
            {
            mySoftkeys.setSoftkeys( "SELECT", "BACK", false );
            myHandleBack = true;
            }
        }

    // From MenuHandlerEx

    public final void onLeftSoftKey( final BasicMenuEntry aSelectedEntry ) throws Exception
        {
        onSelected( aSelectedEntry );
        }

    public final void onRightSoftKey( final BasicMenuEntry aSelectedEntry ) throws Exception
        {
        if ( myHandleBack ) stack().popScreen( this );
        }

    // From MenuHandler

    public final void onSelected( final BasicMenuEntry aSelectedEntry ) throws Exception
        {
        switch ( aSelectedEntry.id )
            {
            case START_GAME:
                context().startNewGame();
                break;
            case SHOW_HELP:
                context().screens().showHelp();
                break;
            case HISCORE:
                context().screens().showHiscore();
                break;
            case OPTIONS:
                context().screens().showOptions();
                break;
            case CONTROLS:
                context().screens().showControls();
                break;
            case RESET:
                context().screens().showReset();
                break;
            case INFO:
                context().screens().showInfo();
                break;
            case EXIT:
                system().shutdownAndExit();
                break;
            }
        }


    private boolean myHandleBack;

    private AutohideSoftkeysScreen mySoftkeys;


    private static final int START_GAME = 0;

    private static final int SHOW_HELP = 1;

    private static final int HISCORE = 2;

    private static final int OPTIONS = 3;

    private static final int CONTROLS = 4;

    private static final int RESET = 5;

    private static final int INFO = 6;

    private static final int EXIT = 7;

    private static final int MAIN_CONTROLLER_AND_MAIN_MENU = 2;

    private static final int MAIN_CONTROLLER_MAIN_MENU_AND_GAME_SCREEN = 3;
    }
