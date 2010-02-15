package net.intensicode.galaxina.screens;

import net.intensicode.core.KeysHandler;
import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.VisualContext;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.*;
import net.intensicode.util.*;

import javax.microedition.lcdui.Canvas;

public final class MainMenuScreen extends MultiScreen
    {
    public MainMenuScreen( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final VisualContext visualContext = myMainContext.visualContext();
        myFont = visualContext.menuFont();

        addScreen( new ImageScreen( skin().image( "title" ), 50, 50, ImageScreen.MODE_RELATIVE ) );

        addMenuEntry( START_GAME, "START GAME" );
        addMenuEntry( SHOW_HELP, "SHOW HELP" );
        addMenuEntry( HISCORE, "HISCORE" );
        addMenuEntry( OPTIONS, "OPTIONS" );
        addMenuEntry( EXIT, "EXIT" );

        addScreen( visualContext.sharedSoftkeys() );

        audio().loadMusic( "theme" ).start();
        }

    public final void onInitEverytime() throws Exception
        {
        final AutohideSoftkeysScreen sofkeys = myMainContext.visualContext().sharedSoftkeys();
        final String rightKey = stack().numberOfStackedScreens() == 2 ? "EXIT" : "BACK";
        sofkeys.setSoftkeys( "SELECT", rightKey, false );
        }

    public final void onControlTick() throws Exception
        {
        final KeysHandler keys = keys();
        if ( keys.checkUpAndConsume() )
            {
            updateSelectedEntry( mySelectedEntry - 1 );
            }
        else
        if ( keys.checkDownAndConsume() )
            {
            updateSelectedEntry( mySelectedEntry + 1 );
            }
        else
        if ( keys.checkLeftSoftAndConsume() || keys.checkStickDownAndConsume() || keys.checkFireAndConsume() )
            {
            onSelected( mySelectedEntry );
            }
        else
        if ( keys.checkRightSoftAndConsume() )
            {
            if ( stack().numberOfStackedScreens() == 2 ) system().shutdownAndExit();
            else stack().popScreen( this );
            }

        if ( keys.lastCode == Canvas.KEY_NUM0 )
            {
            final VisualContext visualContext = myMainContext.visualContext();
            myFont = visualContext.menuFont();

            //final InfoScreen infoScreen = new InfoScreen( visualContext.menuFont(), visualContext.textFont() );
            //infoScreen.shareSoftkeys( visualContext.sharedSoftkeys() );
            //aEngine.pushScreen( infoScreen );
            }

        super.onControlTick();
        }

    // Implementation

    private void addMenuEntry( final int aID, final String aText ) throws Exception
        {
        if ( aID != myEntries.size ) throw new IllegalArgumentException();

        final int x = screen().width() / 2;
        final int y = screen().height() / 5 + ( myEntries.size + 2 ) * ( myFont.charHeight() * 5 / 4 );

        final Position position = new Position( x, y );
        final SimpleMenuEntry newEntry = new SimpleMenuEntry( myFont, aText, position );
        addScreen( newEntry );

        if ( myEntries.size == 0 )
            {
            mySelectedEntry = 0;
            newEntry.setSelected( true );
            }
        else
            {
            newEntry.setSelected( false );
            }

        myEntries.add( newEntry );
        }

    private void updateSelectedEntry( final int aSelectedEntry )
        {
        final int numberOfEntries = myEntries.size;
        mySelectedEntry = ( aSelectedEntry + numberOfEntries ) % numberOfEntries;

        for ( int idx = 0; idx < numberOfEntries; idx++ )
            {
            final SimpleMenuEntry menuEntry = (SimpleMenuEntry) myEntries.get( idx );
            menuEntry.setSelected( idx == mySelectedEntry );
            }
        }

    private void onSelected( final int aSelectedEntry ) throws Exception
        {
//        switch ( aSelectedEntry )
//            {
//            case START_GAME:
//                myGameContext.startGame();
//                break;
//            case SHOW_HELP:
//                myGameContext.showHelp();
//                break;
//            case HISCORE:
//                myGameContext.showHiscore();
//                break;
//            case OPTIONS:
//                myGameContext.showOptions();
//                break;
//            case EXIT:
//                myGameContext.exit();
//                break;
//            }
        }


    private FontGenerator myFont;

    private int mySelectedEntry = 0;

    private final MainContext myMainContext;

    private final DynamicArray myEntries = new DynamicArray( 5, 5 );


    private static final int START_GAME = 0;

    private static final int SHOW_HELP = 1;

    private static final int HISCORE = 2;

    private static final int OPTIONS = 3;

    private static final int EXIT = 4;
    }
