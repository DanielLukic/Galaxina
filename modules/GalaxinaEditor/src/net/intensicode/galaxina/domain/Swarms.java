package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Swarms extends GroupDomainObject<Swarm>
    {
    public Swarms( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // From Group

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        LOG.debug( "Loading swarms" );
        loadEntries( aInputStream );
        fireDataChanged();
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        LOG.debug( "Saving swarms" );
        saveEntries( aOutputStream );
        }

    // From Object

    public final Swarms clone()
        {
        final Swarms newSwarms = new Swarms( myCoreAPI );
        for ( final Swarm swarm : myEntries )
            {
            newSwarms.myEntries.add( swarm.clone() );
            }
        return newSwarms;
        }

    // From GroupDomainObject

    protected final Swarm loadEntry( final DataInputStream aInputStream ) throws IOException
        {
        return NormalSwarm.loadNew( myCoreAPI, aInputStream );
        }


    private final EditorCoreAPI myCoreAPI;

    private static final Log LOG = Log.get();
    }
