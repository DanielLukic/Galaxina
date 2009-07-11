package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.util.Log;
import net.intensicode.galaxina.domain.GroupEntry;
import net.intensicode.galaxina.domain.GroupEntryEx;
import net.intensicode.galaxina.domain.GroupEntryListener;

import javax.swing.*;
import java.util.ArrayList;

public final class BooleanPropertyUpdater extends ToggleButtonUpdater implements EditorStateListener, GroupEntryListener
    {
    public BooleanPropertyUpdater( final EditorCoreAPI aCoreAPI, final JToggleButton aCheckBox, final String aPropertyID, final String aStateID )
        {
        super( aCoreAPI, aCheckBox );

        myStateID = aStateID;
        myPropertyID = aPropertyID;

        aCoreAPI.state().add( aStateID, this );
        }

    public final void attach( final JComponent aComponent )
        {
        if ( myComponents.contains( aComponent ) ) throw new IllegalStateException();
        myComponents.add( aComponent );
        }

    // From GroupEntryListener

    public final void onPropertyChanged( final GroupEntry aEntry, final String aName, final Object aOldValue, final Object aNewValue )
        {
        Log.get().debug( "-> %s %s", aName, aNewValue );
        if ( aName != myPropertyID ) return;
        updateState( (Boolean) aNewValue );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        detachFrom( (GroupEntryEx) aOldValue );
        attachTo( (GroupEntryEx) aNewValue );
        }

    // From TextFieldUpdater

    protected final Boolean getOldValue()
        {
        final GroupEntryEx entry = (GroupEntryEx) myCoreAPI.state().get( myStateID );
        if ( entry == null ) return null;
        return (Boolean) entry.getProperty( myPropertyID );
        }

    protected final void setNewValue( final Boolean aNewValue )
        {
        final GroupEntryEx entry = (GroupEntryEx) myCoreAPI.state().get( myStateID );
        if ( entry == null ) return;

        final boolean changed = entry.setProperty( myPropertyID, aNewValue );
        if ( !changed ) return;

        for ( final JComponent component : myComponents ) component.updateUI();
        }

    // Implementation

    private final void detachFrom( final GroupEntryEx aOldEntry )
        {
        if ( aOldEntry == null ) return;
        aOldEntry.remove( this );
        }

    private final void attachTo( final GroupEntryEx aNewEntry )
        {
        if ( aNewEntry != null )
            {
            updateState( (Boolean) aNewEntry.getProperty( myPropertyID ) );
            aNewEntry.add( this );
            }
        else
            {
            updateState( null );
            }
        }

    private final void updateState( final Boolean aState )
        {
        if ( aState != null ) myToggleButton.setSelected( aState );
        myToggleButton.setEnabled( aState != null );
        }



    private final String myStateID;

    private final String myPropertyID;

    private final ArrayList<JComponent> myComponents = new ArrayList<JComponent>();
    }
