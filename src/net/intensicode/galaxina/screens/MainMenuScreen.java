package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.VisualContext;
import net.intensicode.screens.*;
import net.intensicode.util.Log;

public final class MainMenuScreen extends MenuBase
    {
    public MainMenuScreen( final MainContext aMainContext )
        {
        super( aMainContext.visualContext().menuFont() );
        myMainContext = aMainContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( visuals().sharedBackground() );

        addMenuEntry( START_GAME, "START GAME" );
        addMenuEntry( SHOW_HELP, "SHOW HELP" );
        addMenuEntry( HISCORE, "HISCORE" );
        addMenuEntry( OPTIONS, "OPTIONS" );
        addMenuEntry( EXIT, "EXIT" );

        mySoftkeys = visuals().sharedSoftkeys();
        addScreen( mySoftkeys );
        }

    protected void afterInitEverytime() throws Exception
        {
        final int screenCount = stack().numberOfStackedScreens();
        Log.debug( "screen count: {}", screenCount );
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

    // From MenuBase

    protected void onRightSoftKey( final MenuEntry aSelectedEntry ) throws Exception
        {
        if ( myHandleBack ) stack().popScreen( this );
        }

    protected final void onSelected( final MenuEntry aSelectedEntry ) throws Exception
        {
        stack().popScreen( this );
        switch ( aSelectedEntry.id )
            {
            case START_GAME:
                context().startNewGame();
                break;
            case SHOW_HELP:
                context().showHelp();
                break;
            case HISCORE:
                context().showHiscore();
                break;
            case OPTIONS:
                context().showOptions();
                break;
            case EXIT:
                system().shutdownAndExit();
                break;
            }
        }

    // Implementation

    private MainContext context()
        {
        return myMainContext;
        }

    private VisualContext visuals()
        {
        return myMainContext.visualContext();
        }


    private boolean myHandleBack;

    private AutohideSoftkeysScreen mySoftkeys;

    private final MainContext myMainContext;


    private static final int START_GAME = 0;

    private static final int SHOW_HELP = 1;

    private static final int HISCORE = 2;

    private static final int OPTIONS = 3;

    private static final int EXIT = 4;

    private static final int MAIN_CONTROLLER_AND_MAIN_MENU = 2;

    private static final int MAIN_CONTROLLER_MAIN_MENU_AND_GAME_SCREEN = 3;
    }
