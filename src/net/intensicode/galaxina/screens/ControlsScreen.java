package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.galaxina.*;
import net.intensicode.graphics.FontGenerator;
import net.intensicode.screens.AlignedTextScreen;
import net.intensicode.util.*;

public final class ControlsScreen extends GalaxinaScreen implements TouchableHandler
    {
    public ControlsScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        myTitleFont = visuals().menuFont();
        myTextFont = visuals().textFont();
        myRedefineScreen = new RedefineControlScreen( visuals(), myEntries );
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
        addScreen( visuals().sharedBackground() );
        addScreen( visuals().sharedSoftkeys() );

        addScreen( new AlignedTextScreen( myTitleFont, I18n._( "CONTROLS" ), 50, 0, FontGenerator.TOP ) );

        addControlKey( Controls.MOVE_UP, I18n._( "MOVE UP" ) );
        addControlKey( Controls.MOVE_LEFT, I18n._( "MOVE LEFT" ) );
        addControlKey( Controls.MOVE_RIGHT, I18n._( "MOVE RIGHT" ) );
        addControlKey( Controls.MOVE_DOWN, I18n._( "MOVE DOWN" ) );
        addControlKey( Controls.PRIMARY_FIRE, I18n._( "PRIMARY FIRE" ) );
        addControlKey( Controls.SECONDARY_FIRE, I18n._( "SECONDARY FIRE" ) );
        addControlKey( Controls.PRIMARY_FIRE_ALT, I18n._( "PRIMARY FIRE ALT" ) );
        addControlKey( Controls.SECONDARY_FIRE_ALT, I18n._( "SECONDARY FIRE ALT" ) );
        addControlKey( Controls.LEFT_SOFTKEY, I18n._( "LEFT MENUKEY" ) );
        addControlKey( Controls.RIGHT_SOFTKEY, I18n._( "RIGHT MENUKEY" ) );

        updateSelectedEntry( 0 );

        updateEntriesFromControls();
        }

    public final void onInitEverytime() throws Exception
        {
        myRedefineScreen.onInit( system() );
        visuals().sharedSoftkeys().setSoftkeys( I18n._( "ACCEPT" ), I18n._( "CANCEL" ) );
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
        else if ( keys.checkStickDownAndConsume() || keys.checkFireAndConsume() )
                {
                onSelected( mySelectedEntry );
                }
            else if ( keys.checkLeftSoftAndConsume() )
                    {
                    stack().popScreen( this );
                    updateControlsFromEntries();
                    storage().save( context().controls() );
                    }
                else if ( keys.checkRightSoftAndConsume() )
                        {
                        updateEntriesFromControls();
                        stack().popScreen( this );
                        }

        super.onControlTick();
        }

    // Implementation

    private void updateEntriesFromControls()
        {
        final Controls controls = context().controls();
        getEntry( Controls.MOVE_UP ).keyCode = controls.upCode;
        getEntry( Controls.MOVE_LEFT ).keyCode = controls.leftCode;
        getEntry( Controls.MOVE_RIGHT ).keyCode = controls.rightCode;
        getEntry( Controls.MOVE_DOWN ).keyCode = controls.downCode;
        getEntry( Controls.PRIMARY_FIRE ).keyCode = controls.starCode;
        getEntry( Controls.SECONDARY_FIRE ).keyCode = controls.poundCode;
        getEntry( Controls.PRIMARY_FIRE_ALT ).keyCode = controls.fireCodeA;
        getEntry( Controls.SECONDARY_FIRE_ALT ).keyCode = controls.fireCodeB;
        getEntry( Controls.LEFT_SOFTKEY ).keyCode = controls.leftSoftCode;
        getEntry( Controls.RIGHT_SOFTKEY ).keyCode = controls.rightSoftCode;
        }

    private void updateControlsFromEntries()
        {
        final Controls controls = context().controls();
        controls.upCode = getEntry( Controls.MOVE_UP ).keyCode;
        controls.leftCode = getEntry( Controls.MOVE_LEFT ).keyCode;
        controls.rightCode = getEntry( Controls.MOVE_RIGHT ).keyCode;
        controls.downCode = getEntry( Controls.MOVE_DOWN ).keyCode;
        controls.starCode = getEntry( Controls.PRIMARY_FIRE ).keyCode;
        controls.poundCode = getEntry( Controls.SECONDARY_FIRE ).keyCode;
        controls.fireCodeA = getEntry( Controls.PRIMARY_FIRE_ALT ).keyCode;
        controls.fireCodeB = getEntry( Controls.SECONDARY_FIRE_ALT ).keyCode;
        controls.leftSoftCode = getEntry( Controls.LEFT_SOFTKEY ).keyCode;
        controls.rightSoftCode = getEntry( Controls.RIGHT_SOFTKEY ).keyCode;
        }

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

    private void addControlKey( final int aID, final String aText ) throws Exception
        {
        if ( myEntries.size == 0 ) mySelectedEntry = 0;

        final int x = myTextFont.charHeight();
        final int y = mySpacing + myTitleFont.charHeight() * 4 + myEntries.size * ( myTextFont.charHeight() * 3 / 2 );

        final ControlKeyEntry newEntry = new ControlKeyEntry( myTextFont, aText, new Position( x, y ) );
        addScreen( newEntry );
        newEntry.id = aID;

        //#if TOUCH_SUPPORTED
        newEntry.touchable.associatedHandler = this;
        newEntry.updateTouchable();
        //#endif

        myEntries.add( newEntry );
        }

    private void updateSelectedEntry( final int aSelectedEntry )
        {
        final int numberOfEntries = myEntries.size;
        mySelectedEntry = ( aSelectedEntry + numberOfEntries ) % numberOfEntries;

        for ( int idx = 0; idx < numberOfEntries; idx++ )
            {
            final ControlKeyEntry entry = (ControlKeyEntry) myEntries.get( idx );
            entry.selected = ( idx == mySelectedEntry );
            }
        }

    //#if TOUCH_SUPPORTED

    private int getEntryIndexByTouchable( final Touchable aTouchable )
        {
        for ( int idx = 0; idx < myEntries.size; idx++ )
            {
            final ControlKeyEntry entry = (ControlKeyEntry) myEntries.get( idx );
            if ( entry.touchable == aTouchable ) return idx;
            }
        throw new IllegalArgumentException();
        }

    //#endif

    private ControlKeyEntry getEntry( final int aID )
        {
        final int numberOfEntries = myEntries.size;
        for ( int idx = 0; idx < numberOfEntries; idx++ )
            {
            final ControlKeyEntry entry = (ControlKeyEntry) myEntries.get( idx );
            if ( entry.id == aID ) return entry;
            }
        throw new IllegalArgumentException( Integer.toString( aID ) );
        }

    private void onSelected( final int aSelectedEntry ) throws Exception
        {
        myRedefineScreen.reset( (ControlKeyEntry) myEntries.get( aSelectedEntry ) );
        stack().pushOnce( myRedefineScreen );
        }


    private int mySpacing = 0;

    private int mySelectedEntry = 0;

    private final FontGenerator myTextFont;

    private final FontGenerator myTitleFont;

    private final DynamicArray myEntries = new DynamicArray( 5, 5 );

    private final RedefineControlScreen myRedefineScreen;
    }
