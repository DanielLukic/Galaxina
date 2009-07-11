/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.screens;

import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Keys;
import net.intensicode.core.MultiScreen;
import net.intensicode.game.GameContext;
import net.intensicode.game.VisualContext;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Canvas;


/**
 * TODO: Describe this!
 */
public final class MainMenuScreen extends MultiScreen
    {
    public MainMenuScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final VisualContext visualContext = myGameContext.visualContext();
        myFont = visualContext.menuFont();

        addScreen( new ImageScreen( myGameContext.visualContext().skin().image( "title" ), 50, 50, ImageScreen.MODE_RELATIVE ) );

        addMenuEntry( START_GAME, "START GAME" );
        addMenuEntry( SHOW_HELP, "SHOW HELP" );
        addMenuEntry( HISCORE, "HISCORE" );
        addMenuEntry( OPTIONS, "OPTIONS" );
        addMenuEntry( EXIT, "EXIT" );

        addScreen( visualContext.sharedSoftkeys() );
        }

    public final void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final AutoHideSoftkeysScreen sofkeys = myGameContext.visualContext().sharedSoftkeys();
        final String rightKey = aEngine.numberOfStackedScreens() == 2 ? "EXIT" : "BACK";
        sofkeys.setSoftkeys( "SELECT", rightKey, false );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        final Keys keys = aEngine.keys;
        if ( keys.checkUpAndConsume() )
            {
            updateSelectedEntry( mySelectedEntry - 1 );
            }
        else if ( keys.checkDownAndConsume() )
            {
            updateSelectedEntry( mySelectedEntry + 1 );
            }
        else if ( keys.checkLeftSoftAndConsume() || keys.checkStickDownAndConsume() || keys.checkFireAndConsume() )
            {
            onSelected( mySelectedEntry );
            }
        else if ( keys.checkRightSoftAndConsume() )
            {
            if ( aEngine.numberOfStackedScreens() == 2 ) aEngine.shutdownAndExit();
            else aEngine.popScreen( this );
            }

        if ( keys.lastCode == Canvas.KEY_NUM0 )
            {
            final VisualContext visualContext = myGameContext.visualContext();
            myFont = visualContext.menuFont();

            final InfoScreen infoScreen = new InfoScreen( visualContext.menuFont(), visualContext.textFont() );
            infoScreen.shareSoftkeys( visualContext.sharedSoftkeys() );
            aEngine.pushScreen( infoScreen );
            }

        super.onControlTick( aEngine );
        }

    // Implementation

    private final void addMenuEntry( final int aID, final String aText ) throws Exception
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

    private final void updateSelectedEntry( final int aSelectedEntry )
        {
        final int numberOfEntries = myEntries.size;
        mySelectedEntry = ( aSelectedEntry + numberOfEntries ) % numberOfEntries;

        for ( int idx = 0; idx < numberOfEntries; idx++ )
            {
            final SimpleMenuEntry menuEntry = (SimpleMenuEntry) myEntries.get( idx );
            menuEntry.setSelected( idx == mySelectedEntry );
            }
        }

    private final void onSelected( final int aSelectedEntry ) throws Exception
        {
        switch ( aSelectedEntry )
            {
            case START_GAME:
                myGameContext.startGame();
                break;
            case SHOW_HELP:
                myGameContext.showHelp();
                break;
            case HISCORE:
                myGameContext.showHiscore();
                break;
            case OPTIONS:
                myGameContext.showOptions();
                break;
            case EXIT:
                myGameContext.exit();
                break;
            }
        }


    private FontGen myFont;

    private int mySelectedEntry = 0;


    private final GameContext myGameContext;

    private final DynamicArray myEntries = new DynamicArray( 5, 5 );


    private static final int START_GAME = 0;

    private static final int SHOW_HELP = 1;

    private static final int HISCORE = 2;

    private static final int OPTIONS = 3;

    private static final int EXIT = 4;
    }
