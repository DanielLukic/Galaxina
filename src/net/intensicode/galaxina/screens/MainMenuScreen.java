package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.VisualContext;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;
import net.intensicode.util.DynamicArray;

public final class MainMenuScreen extends MenuBase
    {
    public MainMenuScreen( final MainContext aMainContext )
        {
        super( aMainContext.visualContext().menuFont() );
        myMainContext = aMainContext;
        myVisualContext = aMainContext.visualContext();
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        addScreen( myVisualContext.sharedBackground() );

        addMenuEntry( START_GAME, "START GAME" );
        addMenuEntry( SHOW_HELP, "SHOW HELP" );
        addMenuEntry( HISCORE, "HISCORE" );
        addMenuEntry( OPTIONS, "OPTIONS" );
        addMenuEntry( EXIT, "EXIT" );
        }

    // From MenuBase

    protected final void onSelected( final MenuEntry aSelectedEntry ) throws Exception
        {
        switch ( aSelectedEntry.id )
            {
            case START_GAME:
                myMainContext.startNewGame();
                break;
//            case SHOW_HELP:
//                myGameContext.showHelp();
//                break;
//            case HISCORE:
//                myGameContext.showHiscore();
//                break;
//            case OPTIONS:
//                myGameContext.showOptions();
//                break;
            case EXIT:
                system().shutdownAndExit();
                break;
            }
        }


    private FontGenerator myFont;

    private int mySelectedEntry = 0;

    private final MainContext myMainContext;

    private final VisualContext myVisualContext;

    private final DynamicArray myEntries = new DynamicArray( 5, 5 );


    private static final int START_GAME = 0;

    private static final int SHOW_HELP = 1;

    private static final int HISCORE = 2;

    private static final int OPTIONS = 3;

    private static final int EXIT = 4;
    }
