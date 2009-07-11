package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorConfiguration;
import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class NormalSwarm extends GroupEntryBase implements Swarm
    {
    public NormalSwarm( final EditorCoreAPI aCoreAPI )
        {
        this( aCoreAPI.configuration() );
        setProperty( NAME, myConfiguration.string( "Unnamed swarm" ) );
        setProperty( SIZE, new Integer( 0 ) );
        setProperty( DELAY, new Integer( 4 ) );
        myPathes = new SwarmPathes( aCoreAPI.project().pathes(), myConfiguration );
        myEnemies = new SwarmEnemies( aCoreAPI.project().enemies(), myConfiguration );
        myFormation = new ListOfPositions( aCoreAPI );
        }

    public static final Swarm loadNew( final EditorCoreAPI aCoreAPI, final DataInputStream aInputStream ) throws IOException
        {
        final NormalSwarm swarm = new NormalSwarm( aCoreAPI );
        swarm.load( aInputStream );
        return swarm;
        }

    // From Swarm

    public final Integer getSize()
        {
        return (Integer) getProperty( SIZE );
        }

    public final void setSize( final int aNewSize )
        {
        setProperty( SIZE, aNewSize );
        }

    public final SwarmPathes pathes()
        {
        return myPathes;
        }

    public final SwarmEnemies enemies()
        {
        return myEnemies;
        }

    public final ListOfPositions positions()
        {
        return myFormation;
        }

    public final void move( final int aDeltaX, final int aDeltaY )
        {
        myFormation.move( aDeltaX, aDeltaY );
        }

    public final Swarm clone()
        {
        final NormalSwarm newSwarm = new NormalSwarm( myConfiguration );
        newSwarm.myProperties.putAll( myProperties );
        newSwarm.myPathes = myPathes.clone();
        newSwarm.myEnemies = myEnemies.clone();
        newSwarm.myFormation = myFormation.clone();
        return newSwarm;
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        LOG.debug( "Loading swarm" );
        setProperty( NAME, aInputStream.readUTF() );
        setProperty( SIZE, aInputStream.readInt() );
        setProperty( DELAY, aInputStream.readInt() );
        myPathes.load( aInputStream );
        myEnemies.load( aInputStream );
        myFormation.load( aInputStream );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        LOG.debug( "Saving swarm %s", toString() );
        aOutputStream.writeUTF( getString( NAME ) );
        aOutputStream.writeInt( getInt( SIZE ) );
        aOutputStream.writeInt( getInt( DELAY ) );
        myPathes.save( aOutputStream );
        myEnemies.save( aOutputStream );
        myFormation.save( aOutputStream );
        }

    // From Object

    public final String toString()
        {
        return getString( NAME );
        }

    // Implementation

    private NormalSwarm( final EditorConfiguration aConfiguration )
        {
        myConfiguration = aConfiguration;
        }



    private SwarmPathes myPathes;

    private SwarmEnemies myEnemies;

    private ListOfPositions myFormation;

    private final EditorConfiguration myConfiguration;

    private static final Log LOG = Log.get();
    }
