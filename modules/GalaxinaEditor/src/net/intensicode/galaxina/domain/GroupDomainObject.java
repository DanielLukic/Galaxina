package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class GroupDomainObject<T extends GroupEntry> implements Group<T>
    {
    public final void add( final GroupListener<T> aGroupListener )
        {
        myListeners.add( aGroupListener );
        }

    public final void remove( final GroupListener<T> aGroupListener )
        {
        myListeners.remove( aGroupListener );
        }

    public final void fireDataChanged()
        {
        myListeners.fire( "onDataChanged", this );
        }

    public final void clear()
        {
        while ( myEntries.size() > 0 ) deleteEntry( at( 0 ) );
        }

    public final void moveUp( final T aEntry )
        {
        final int index = myEntries.indexOf( aEntry );
        if ( index == -1 ) throw new IllegalArgumentException();
        if ( index == 0 ) return;
        final T removed = myEntries.remove( index );
        myEntries.add( index - 1, removed );
        myListeners.fire( "onDataChanged", this );
        }

    public final void addEntry( final T aEntry )
        {
        myEntries.add( aEntry );
        myListeners.fire( "onAdded", this, aEntry );
        }

    public final void deleteEntry( final T aEntry )
        {
        if ( !myEntries.contains( aEntry ) )
            {
            throw new IllegalArgumentException( aEntry.toString() );
            }
        myEntries.remove( aEntry );
        myListeners.fire( "onRemoved", this, aEntry );

        fireDataChanged();
        }

    public final int indexOf( final T aLevel )
        {
        final int index = myEntries.indexOf( aLevel );
        if ( index == -1 ) throw new IllegalArgumentException();
        return index;
        }

    public T at( final int aIndex )
        {
        return myEntries.get( aIndex );
        }

    public final int size()
        {
        return myEntries.size();
        }

    public final Iterator<T> iterator()
        {
        return myEntries.iterator();
        }

    // Protected Interface

    protected abstract T loadEntry( final DataInputStream aInputStream ) throws IOException;

    protected final void loadEntries( final DataInputStream aInputStream ) throws IOException
        {
        if ( myEntries.size() != 0 ) throw new IllegalStateException();

        final int size = aInputStream.readInt();
        LOG.debug( "Loading %d entries for %s", size, getClass().getSimpleName() );
        for ( int idx = 0; idx < size; idx++ )
            {
            myEntries.add( loadEntry( aInputStream ) );
            }
        }

    protected final void saveEntries( final DataOutputStream aOutputStream ) throws IOException
        {
        aOutputStream.writeInt( myEntries.size() );
        LOG.debug( "Saving %d entries for %s", myEntries.size(), getClass().getSimpleName() );
        for ( final T entry : myEntries )
            {
            entry.save( aOutputStream );
            }
        }



    protected final ArrayList<T> myEntries = new ArrayList<T>();

    protected final EventUtilities<GroupListener<T>> myListeners = new EventUtilities<GroupListener<T>>();

    private static final Log LOG = Log.get();
    }
