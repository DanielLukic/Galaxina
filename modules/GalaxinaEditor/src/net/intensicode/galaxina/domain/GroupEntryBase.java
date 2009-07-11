package net.intensicode.galaxina.domain;

import java.util.HashMap;

public abstract class GroupEntryBase implements GroupEntryEx
    {
    public final String getString( final String aName )
        {
        return (String) getProperty( aName );
        }

    public final Integer getInt( final String aName )
        {
        return (Integer) getProperty( aName );
        }

    public final Boolean getBoolean( final String aName )
        {
        return (Boolean) getProperty( aName );
        }

    // From GroupEntryEx

    public final void add( final GroupEntryListener aListener )
        {
        myListeners.add( aListener );
        }

    public final void remove( final GroupEntryListener aListener )
        {
        myListeners.remove( aListener );
        }

    public final Object getProperty( final String aName )
        {
        return myProperties.get( aName );
        }

    public final boolean setProperty( final String aName, final Object aNewValue )
        {
        final Object oldValue = getProperty( aName );
        myProperties.put( aName, aNewValue );

        final boolean changed = ( oldValue != null ) ? !oldValue.equals( aNewValue ) : oldValue != aNewValue;
        if ( changed ) myListeners.fire( "onPropertyChanged", this, aName, oldValue, aNewValue );
        return changed;
        }

    protected final HashMap<String, Object> myProperties = new HashMap<String, Object>();

    private final EventUtilities<GroupEntryListener> myListeners = new EventUtilities<GroupEntryListener>();
    }
