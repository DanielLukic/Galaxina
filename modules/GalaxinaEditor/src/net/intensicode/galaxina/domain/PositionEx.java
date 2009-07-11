package net.intensicode.galaxina.domain;

import net.intensicode.util.Position;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PositionEx extends Position implements GroupEntry
    {
    public PositionEx()
        {
        }

    public PositionEx( int aX, int aY )
        {
        x = aX;
        y = aY;
        }

    public PositionEx( final Position aPosition )
        {
        super( aPosition );
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        x = aInputStream.readInt();
        y = aInputStream.readInt();
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        aOutputStream.writeInt( x );
        aOutputStream.writeInt( y );
        }
    }
