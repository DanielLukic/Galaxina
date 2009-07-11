package net.intensicode.galaxina.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class NormalEnemy extends GroupEntryBase implements Enemy
    {
    public NormalEnemy( final String aName )
        {
        setProperty( NAME, aName );
        setProperty( FILENAME, "enemy.png" );
        setProperty( HITS, 1 );
        setProperty( BASESCORE, 100 );
        }

    public NormalEnemy( final String aName, final int aHits, final int aBaseScore )
        {
        this( aName );
        setProperty( HITS, aHits );
        setProperty( BASESCORE, aBaseScore );
        }

    public static final Enemy loadNew( final DataInputStream aInputStream ) throws IOException
        {
        final NormalEnemy enemy = new NormalEnemy( "" );
        enemy.load( aInputStream );
        return enemy;
        }

    public final Enemy setFileName( final String aFileName )
        {
        setProperty( FILENAME, aFileName );
        return this;
        }

    // From Enemy

    public final Enemy clone()
        {
        final String name = getString( NAME );
        final int hits = getInt( HITS );
        final int baseScore = getInt( BASESCORE );
        return new NormalEnemy( name, hits, baseScore );
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        setProperty( NAME, aInputStream.readUTF() );
        setProperty( FILENAME, aInputStream.readUTF() );
        setProperty( HITS, aInputStream.readInt() );
        setProperty( BASESCORE, aInputStream.readInt() );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        aOutputStream.writeUTF( getString( NAME ) );
        aOutputStream.writeUTF( getString( FILENAME ) );
        aOutputStream.writeInt( getInt( HITS ) );
        aOutputStream.writeInt( getInt( BASESCORE ) );
        }

    // From Object

    public final String toString()
        {
        return getString( NAME );
        }
    }
