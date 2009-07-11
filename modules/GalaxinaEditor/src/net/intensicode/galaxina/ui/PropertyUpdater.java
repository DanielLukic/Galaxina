package net.intensicode.galaxina.ui;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.GroupEntry;
import net.intensicode.galaxina.domain.GroupEntryEx;
import net.intensicode.galaxina.domain.GroupEntryListener;

import javax.swing.*;
import java.util.ArrayList;

public final class PropertyUpdater extends TextFieldUpdater implements EditorStateListener, GroupEntryListener
    {
    public PropertyUpdater( final EditorCoreAPI aCoreAPI, final JTextField aTextField, final String aPropertyID, final String aStateID )
        {
        super( aCoreAPI, aTextField );

        myStateID = aStateID;
        myTextField = aTextField;
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
        if ( aName != myPropertyID ) return;
        setText( aNewValue != null ? aNewValue.toString() : "" );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        detachFrom( (GroupEntryEx) aOldValue );
        attachTo( (GroupEntryEx) aNewValue );
        }

    // From TextFieldUpdater

    protected final String getOldValue()
        {
        final GroupEntryEx entry = (GroupEntryEx) coreAPI().state().get( myStateID );
        if ( entry == null ) return null;
        return entry.getProperty( myPropertyID ).toString();
        }

    protected final void setNewValue( final String aNewValue )
        {
        final GroupEntryEx entry = (GroupEntryEx) coreAPI().state().get( myStateID );
        if ( entry == null ) return;

        final boolean changed = entry.setProperty( myPropertyID, aNewValue );
        if ( changed )
            {
            for ( final JComponent component : myComponents ) component.updateUI();
            }
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
            setText( aNewEntry.getProperty( myPropertyID ).toString() );
            aNewEntry.add( this );
            }
        else
            {
            myTextField.setText( "" );
            }
        myTextField.setEnabled( aNewEntry != null );
        }

    private final void setText( final String aText )
        {
        myTextField.setText( aText );
        if ( aText != null && aText.length() > 0 ) myTextField.selectAll();
        }



    private final String myStateID;

    private final String myPropertyID;

    private final JTextField myTextField;

    private final ArrayList<JComponent> myComponents = new ArrayList<JComponent>();
    }
