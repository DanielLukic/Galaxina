package net.intensicode.galaxina.ui.models;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Group;
import net.intensicode.galaxina.domain.GroupListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public final class ListGroupModel<T> implements ListModel, ListSelectionListener, GroupListener<T>, EditorStateListener
    {
    public ListGroupModel( final EditorCoreAPI aCoreAPI, final JList aSource, final String aStateID )
        {
        myCoreAPI = aCoreAPI;
        mySource = aSource;
        myStateID = aStateID;
        mySource.setModel( this );
        mySource.addListSelectionListener( this );
        mySource.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        myCoreAPI.state().add( aStateID, this );
        }

    public final void attach( final Group<T> aGroup )
        {
        myGroup = aGroup;
        myGroup.add( this );
        }

    public final void detach( final Group<T> aGroup )
        {
        if ( myGroup != aGroup ) throw new IllegalArgumentException();
        myGroup.remove( this );
        myGroup = null;
        }

    public final void initSelection()
        {
        if ( mySource.getModel().getSize() > 0 )
            {
            mySource.setSelectedIndex( 0 );
            valueChanged( new ListSelectionEvent( mySource, 0, 0, false ) );
            }
        else
            {
            myCoreAPI.state().change( myStateID, null );
            }
        }

    public final int findIndexOf( final Object aObject )
        {
        for ( int idx = 0; idx < myGroup.size(); idx++ )
            {
            if ( myGroup.at( idx ) == aObject ) return idx;
            }
        throw new IllegalArgumentException();
        }

    public final void notifyListeners()
        {
        final ListDataEvent event = new ListDataEvent( this, ListDataEvent.CONTENTS_CHANGED, 0, getSize() );
        for ( int idx = 0; idx < myListeners.size(); idx++ )
            {
            myListeners.get( idx ).contentsChanged( event );
            }
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aNewValue == null ) return;
        mySource.setSelectedIndex( findIndexOf( aNewValue ) );
        }

    // From GroupListener

    public final void onAdded( final Group<T> aGroup, final T aAddedEntry )
        {
        final int index = mySource.getModel().getSize() - 1;
        mySource.setSelectedIndex( index );
        valueChanged( new ListSelectionEvent( mySource, index, index, false ) );

        notifyListeners();
        }

    public final void onRemoved( final Group<T> aGroup, final T aRemovedEntry )
        {
        notifyListeners();

        if ( mySource.getModel().getSize() > 0 )
            {
            final int oldIndex = mySource.getSelectedIndex();
            final int newIndex = Math.max( 0, Math.min( getSize() - 1, oldIndex ) );
            mySource.setSelectedIndex( newIndex );
            valueChanged( new ListSelectionEvent( mySource, newIndex, newIndex, false ) );
            }
        }

    public final void onReplaced( final Group<T> aGroup, final T aOldEntry, final T aEntry, final Integer aIndex )
        {
        throw new RuntimeException( "nyi" );
        }

    public final void onDataChanged( final Group<T> aGroup )
        {
        notifyListeners();
        }

    public final void onPropertyChanged( final Group<T> aGroup, final String aKey, final Object aValue )
        {
        throw new RuntimeException( "nyi" );
        }

    // From ListSelectionListener

    public final void valueChanged( final ListSelectionEvent aEvent )
        {
        if ( aEvent.getValueIsAdjusting() ) return;

        final JList source = (JList) aEvent.getSource();
        final int selectedIndex = source.getSelectedIndex();
        if ( selectedIndex == -1 )
            {
            myCoreAPI.state().change( myStateID, null );
            }
        else
            {
            final int goodIndex = Math.max( 0, Math.min( myGroup.size() - 1, selectedIndex ) );
            source.setSelectedIndex( goodIndex );
            myCoreAPI.state().change( myStateID, getElementAt( goodIndex ) );
            }
        }

    // From ListModel

    public final int getSize()
        {
        if ( myGroup == null ) return 0;
        return myGroup.size();
        }

    public final Object getElementAt( final int aIndex )
        {
        return myGroup.at( aIndex );
        }

    public final void addListDataListener( final ListDataListener aListener )
        {
        removeListDataListener( aListener );
        myListeners.add( aListener );
        }

    public final void removeListDataListener( final ListDataListener aListener )
        {
        while ( myListeners.remove( aListener ) ) ;
        }



    private Group<T> myGroup;

    private final JList mySource;

    private final String myStateID;

    private final EditorCoreAPI myCoreAPI;

    private final ArrayList<ListDataListener> myListeners = new ArrayList<ListDataListener>();
    }
