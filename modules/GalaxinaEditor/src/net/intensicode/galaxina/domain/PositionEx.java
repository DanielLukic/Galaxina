package net.intensicode.galaxina.domain;

import net.intensicode.util.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PositionEx extends PositionF implements GroupEntry
    {
    public PositionEx()
        {
        }

    public PositionEx( final float aX, final float aY )
        {
        x = aX;
        y = aY;
        }

    public PositionEx( final PositionF aPosition )
        {
        super( aPosition );
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        x = aInputStream.readFloat();
        y = aInputStream.readFloat();
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        aOutputStream.writeFloat( x );
        aOutputStream.writeFloat( y );
        }
    }
