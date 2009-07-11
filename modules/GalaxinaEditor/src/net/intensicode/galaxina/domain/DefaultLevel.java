package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorConfiguration;
import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class DefaultLevel extends GroupEntryBase implements Level
    {
    public DefaultLevel( final EditorCoreAPI aCoreAPI )
        {
        myConfiguration = aCoreAPI.configuration();
        mySwarms = new Swarms( aCoreAPI );
        setProperty( CHALLENGING_STAGE, Boolean.FALSE );
        }

    // From Level

    public final void add( final LevelListener aListener )
        {
        throw new RuntimeException( "nyi" );
        }

    public final int levelIndex()
        {
        return 0;
        }

    public final boolean hasChanges()
        {
        return false;
        }

    public final boolean appliesTo( final int aLevelIndex )
        {
        return true;
        }

    public final boolean canChangeRepeatMode()
        {
        return false;
        }

    public final boolean isRepeated()
        {
        return true;
        }

    public final void setRepeated( final boolean aRepeatedFlag )
        {
        if ( !aRepeatedFlag ) throw new IllegalArgumentException();
        }

    public final int repeatInterval()
        {
        return 1;
        }

    public final void setRepeatInterval( final int aRepeatInterval )
        {
        if ( aRepeatInterval != 1 ) throw new IllegalArgumentException();
        }

    public final boolean canChangeLevelInfo()
        {
        return false;
        }

    public final String levelInfo()
        {
        return myConfiguration.string( "Default level. Used if no other level applies." );
        }

    public final void setLevelInfo( final String aLevelInfo )
        {
        throw new RuntimeException();
        }

    public final boolean canBeReplacedByClonedSelf()
        {
        return false;
        }

    public final Swarms swarms()
        {
        return mySwarms;
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        LOG.debug( "Loading default level" );
        if ( aInputStream.readInt() != 0 ) throw new IllegalArgumentException();
        if ( aInputStream.readInt() != 1 ) throw new IllegalArgumentException();
        if ( aInputStream.readBoolean() != true ) throw new IllegalArgumentException();
        setProperty( CHALLENGING_STAGE, aInputStream.readBoolean() );
        mySwarms.load( aInputStream );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        LOG.debug( "Saving default level" );
        aOutputStream.writeInt( 0 );
        aOutputStream.writeInt( 1 );
        aOutputStream.writeBoolean( true );
        aOutputStream.writeBoolean( getBoolean( CHALLENGING_STAGE ) );
        mySwarms.save( aOutputStream );
        }

    // From Object

    public final String toString()
        {
        return myConfiguration.string( "Default Level" );
        }



    private final Swarms mySwarms;

    private final EditorConfiguration myConfiguration;

    private static final Log LOG = Log.get();
    }