package net.intensicode.galaxina.domain;

public final class SyncMode
    {
    public final int id;

    public final String name;



    public SyncMode( final int aID, final String aName )
        {
        id = aID;
        name = aName;
        }

    // From Object

    public final String toString()
        {
        return name;
        }
    }
