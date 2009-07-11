package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class Pathes extends GroupDomainObject<Path>
    {
    public Pathes( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    public final void open()
        {
        }

    public final void close()
        {
        final ArrayList<Path> pathes = new ArrayList<Path>( myEntries );
        for ( final Path path : pathes ) deleteEntry( path );
        if ( myEntries.size() != 0 ) throw new IllegalStateException();
        }

    // From Group

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        LOG.debug( "Loading pathes" );
        loadEntries( aInputStream );
        fireDataChanged();
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        LOG.debug( "Saving pathes" );
        saveEntries( aOutputStream );
        }

    // From GroupDomainObject

    protected final Path loadEntry( final DataInputStream aInputStream ) throws IOException
        {
        return NormalPath.loadNew( myCoreAPI, aInputStream );
        }



    private final EditorCoreAPI myCoreAPI;

    private static final Log LOG = Log.get();
    }
