package net.intensicode.galaxina.domain;

public interface GroupEntryEx extends GroupEntry
    {
    void add( GroupEntryListener aListener );

    void remove( GroupEntryListener aListener );

    Object getProperty( String aName );

    boolean setProperty( String aName, Object aValue );
    }
