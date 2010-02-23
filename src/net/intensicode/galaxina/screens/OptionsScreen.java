package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.*;
import net.intensicode.galaxina.game.GameModel;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.AlignedTextScreen;
import net.intensicode.util.*;

public final class OptionsScreen extends GalaxinaScreen implements TouchableHandler
    {
    public OptionsScreen( final MainContext aMainLogic )
        {
        super( aMainLogic );
        myTitleFont = visuals().menuFont();
        myTextFont = visuals().textFont();
        }

    //#ifdef TOUCH_SUPPORTED

    // From TouchableHandler

    public final void onPressed( final Object aTouchable )
        {
        try
            {
            final Touchable touchable = (Touchable) aTouchable;
            final int indexOfEntry = getEntryIndexByTouchable( touchable );
            onSelected( indexOfEntry );
            }
        catch ( final Exception e )
            {
            GameSystem.showException( e );
            }
        }

    public final void onReleased( final Object aTouchable )
        {
        }

    //#endif

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        myGameModel = context().gameContext().gameModel();

        addScreen( visuals().sharedBackground() );
        addScreen( visuals().sharedSoftkeys() );

        addScreen( new AlignedTextScreen( myTitleFont, I18n._( "OPTIONS" ), 50, 0, FontGenerator.TOP ) );

        addOption( Options.SHOW_ENGINE_STATS, I18n._( "SHOW ENGINE STATS" ) );
        addOption( Options.BUFFERED_FONTGEN, I18n._( "BUFFERED FONTGEN" ) );
        //#if CONSOLE
        addOption( Options.SHOW_CONSOLE, I18n._( "SHOW CONSOLE" ) );
        //#endif

        addOption( Options.SHOW_STARS, I18n._( "SHOW STARS" ) );

        addOption( Options.PLAY_MUSIC, I18n._( "PLAY MUSIC" ) );
        addOption( Options.PLAY_SOUND, I18n._( "PLAY SOUND" ) );

        updateSelectedEntry( 0 );
        }

    public final void onInitEverytime() throws Exception
        {
        visuals().sharedSoftkeys().setSoftkeys( I18n._( "SELECT" ), I18n._( "BACK" ) );
        }

    public final void onControlTick() throws Exception
        {
        //#if TOUCH_SUPPORTED
        addTouchableAreas();
        //#endif

        final KeysHandler keys = keys();
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
            else if ( keys.checkLeftAndConsume() || keys.checkRightAndConsume() )
                    {
                    onSelected( mySelectedEntry );
                    }
                else if ( keys.checkRightSoftAndConsume() )
                        {
                        stack().popScreen( this );
                        storage().save( context().options() );
                        }

        final VisualConfiguration config = visuals().configuration();
        updateEntry( Options.SHOW_ENGINE_STATS );
        updateEntry( Options.BUFFERED_FONTGEN );
        //#if CONSOLE
        updateEntry( Options.SHOW_CONSOLE );
        //#endif
        updateEntry( Options.SHOW_STARS );
        updateEntry( Options.PLAY_MUSIC );
        updateEntry( Options.PLAY_SOUND );

        super.onControlTick();
        }

    // Implementation

    //#if TOUCH_SUPPORTED

    private void addTouchableAreas()
        {
        final TouchHandler touch = touch();
        final int numberOfMenuEntries = myEntries.size;
        for ( int idx = 0; idx < numberOfMenuEntries; idx++ )
            {
            touch.addLocalControl( getEntry( idx ).touchable );
            }
        }

    //#endif

    private void addOption( final int aID, final String aText ) throws Exception
        {
        final int x = myTextFont.charHeight();
        final int y = myTitleFont.charHeight() * 4 + myEntries.size * ( myTextFont.charHeight() * 3 / 2 );

        final Position position = new Position( x, y );
        final OptionsEntry newEntry = new OptionsEntry( myTextFont, aText, position );
        newEntry.id = aID;
        addScreen( newEntry );
        //#if TOUCH_SUPPORTED
        newEntry.touchable.associatedHandler = this;
        newEntry.updateTouchable();
        //#endif
        myEntries.add( newEntry );

        if ( myEntries.size == 0 ) mySelectedEntry = 0;
        }

    private void updateSelectedEntry( final int aSelectedEntry )
        {
        final int numberOfEntries = myEntries.size;
        mySelectedEntry = ( aSelectedEntry + numberOfEntries ) % numberOfEntries;

        for ( int idx = 0; idx < numberOfEntries; idx++ )
            {
            final OptionsEntry entry = (OptionsEntry) myEntries.get( idx );
            entry.setSelected( idx == mySelectedEntry );
            }
        }

    private OptionsEntry getEntry( final int aIndex )
        {
        return (OptionsEntry) myEntries.objects[ aIndex ];
        }

    private OptionsEntry getEntryByID( final int aID )
        {
        for ( int idx = 0; idx < myEntries.size; idx++ )
            {
            final OptionsEntry entry = (OptionsEntry) myEntries.get( idx );
            if ( entry.id == aID ) return entry;
            }
        throw new IllegalArgumentException();
        }

    //#if TOUCH_SUPPORTED

    private int getEntryIndexByTouchable( final Touchable aTouchable )
        {
        for ( int idx = 0; idx < myEntries.size; idx++ )
            {
            final OptionsEntry entry = (OptionsEntry) myEntries.get( idx );
            if ( entry.touchable == aTouchable ) return idx;
            }
        throw new IllegalArgumentException();
        }

    //#endif

    private void updateEntry( final int aOptionId )
        {
        getEntryByID( aOptionId ).changeState( context().options().getOption( aOptionId ) );
        }

    private void onSelected( final int aSelectedEntry ) throws Exception
        {
        final VisualConfiguration config = visuals().configuration();
        final OptionsEntry entry = (OptionsEntry) myEntries.get( aSelectedEntry );
        context().options().toggleOption( entry.id );
        updateSelectedEntry( mySelectedEntry );
        }


    private int mySelectedEntry = 0;

    private GameModel myGameModel;

    private final FontGenerator myTextFont;

    private final FontGenerator myTitleFont;

    private final DynamicArray myEntries = new DynamicArray( 5, 5 );
    }
