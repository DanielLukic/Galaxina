package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorConfiguration;
import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.util.Position;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ListOfPositions extends GroupDomainObject<PositionEx>
    {
    public ListOfPositions( final EditorCoreAPI aCoreAPI )
        {
        this( aCoreAPI.configuration() );
        }

    public static final ListOfPositions loadNew( final EditorCoreAPI aCoreAPI, final DataInputStream aInputStream ) throws IOException
        {
        final ListOfPositions swarm = new ListOfPositions( aCoreAPI );
        swarm.load( aInputStream );
        return swarm;
        }

    public final void move( final int aDeltaX, final int aDeltaY )
        {
        for ( final Position pos : myEntries )
            {
            pos.x += aDeltaX;
            pos.y += aDeltaY;
            }
        fireDataChanged();
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        loadEntries( aInputStream );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        saveEntries( aOutputStream );
        }

    // From GroupDomainObject

    protected final PositionEx loadEntry( final DataInputStream aInputStream ) throws IOException
        {
        final PositionEx position = new PositionEx();
        position.load( aInputStream );
        return position;
        }

    // From Object

    public final ListOfPositions clone()
        {
        final ListOfPositions clone = new ListOfPositions( myConfiguration );
        for ( final Position pos : myEntries ) clone.myEntries.add( new PositionEx( pos ) );
        return clone;
        }

    // Implementation

    private ListOfPositions( final EditorConfiguration aConfiguration )
        {
        myConfiguration = aConfiguration;
        }



    private final EditorConfiguration myConfiguration;
    }
