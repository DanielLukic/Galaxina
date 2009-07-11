package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorConfiguration;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class SwarmPathes extends GroupDomainObject<Path>
    {
    public SwarmPathes( final Pathes aPathes, final EditorConfiguration aConfiguration )
        {
        myPathes = aPathes;
        myConfiguration = aConfiguration;
        }

    // From Group

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        if ( myEntries.size() != 0 ) throw new IllegalStateException();

        final int size = aInputStream.readInt();
        LOG.debug( "Loading %d swarm pathes", size );
        for ( int idx = 0; idx < size; idx++ )
            {
            final int index = aInputStream.readInt();
            myEntries.add( myPathes.at( index ) );
            }
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        aOutputStream.writeInt( myEntries.size() );
        LOG.debug( "Saving %d swarm pathes", myEntries.size() );
        for ( final Path path : myEntries )
            {
            final int index = myPathes.indexOf( path );
            aOutputStream.writeInt( index );
            }
        }

    // From Object

    public final SwarmPathes clone()
        {
        final SwarmPathes newSwarmPaths = new SwarmPathes( myPathes, myConfiguration );
        for ( final Path path : myEntries )
            {
            newSwarmPaths.myEntries.add( path );
            }
        return newSwarmPaths;
        }

    // From GroupDomainObject

    protected final Path loadEntry( final DataInputStream aInputStream ) throws IOException
        {
        throw new RuntimeException( "nyi" );
        }



    private final Pathes myPathes;

    private final EditorConfiguration myConfiguration;

    private static final Log LOG = Log.get();
    }
