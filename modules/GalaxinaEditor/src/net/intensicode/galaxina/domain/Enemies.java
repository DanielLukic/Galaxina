package net.intensicode.galaxina.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class Enemies extends GroupDomainObject<Enemy>
    {
    public Enemies()
        {
        }

    public final void open()
        {
        }

    public final void close()
        {
        final ArrayList<Enemy> enemies = new ArrayList<Enemy>( myEntries );
        for ( final Enemy enemy : enemies ) deleteEntry( enemy );
        if ( myEntries.size() != 0 ) throw new IllegalStateException();
        }

    // From Group

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        loadEntries( aInputStream );
        fireDataChanged();
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        saveEntries( aOutputStream );
        }

    // From GroupDomainObject

    protected final Enemy loadEntry( final DataInputStream aInputStream ) throws IOException
        {
        return NormalEnemy.loadNew( aInputStream );
        }
    }
