package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorConfiguration;
import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class NormalPath extends GroupEntryBase implements Path
    {
    public NormalPath( final EditorCoreAPI aCoreAPI )
        {
        this( aCoreAPI.configuration() );
        setProperty( NAME, myConfiguration.string( "Unnamed path" ) );
        myPositions = new ListOfPositions( aCoreAPI );
        }

    public static final Path loadNew( final EditorCoreAPI aCoreAPI, final DataInputStream aInputStream ) throws IOException
        {
        final NormalPath path = new NormalPath( aCoreAPI );
        path.load( aInputStream );
        return path;
        }

    // From Path

    public final void move( final int aDeltaX, final int aDeltaY )
        {
        myPositions.move( aDeltaX, aDeltaY );
        }

    public final ListOfPositions positions()
        {
        return myPositions;
        }

    public final Path clone()
        {
        final NormalPath newPath = new NormalPath( myConfiguration );
        newPath.setProperty( NAME, getProperty( NAME ) );
        newPath.myPositions = myPositions.clone();
        return newPath;
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        LOG.debug( "Loading path" );
        setProperty( NAME, aInputStream.readUTF() );
        myPositions.load( aInputStream );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        LOG.debug( "Saving path %s", toString() );
        aOutputStream.writeUTF( getString( NAME ) );
        myPositions.save( aOutputStream );
        }

    // From Object

    public final String toString()
        {
        return getString( NAME );
        }

    // Implementation

    private NormalPath( final EditorConfiguration aConfiguration )
        {
        myConfiguration = aConfiguration;
        }



    private ListOfPositions myPositions;

    private final EditorConfiguration myConfiguration;

    private static final Log LOG = Log.get();
    }
