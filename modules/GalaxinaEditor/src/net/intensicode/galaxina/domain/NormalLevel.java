package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class NormalLevel extends GroupEntryBase implements Level
    {
    public NormalLevel( final EditorCoreAPI aCoreAPI, final Level aLevel )
        {
        this( aCoreAPI, aLevel.swarms().clone() );
        myLevelIndex = aLevel.levelIndex();
        myLevelInfo = myCoreAPI.configuration().string( "Level {0}", myLevelIndex );
        myRepeatedFlag = aLevel.isRepeated();
        myRepeatInterval = aLevel.repeatInterval();
        }

    public NormalLevel( final EditorCoreAPI aCoreAPI, final int aLevelIndex )
        {
        this( aCoreAPI );
        myLevelIndex = aLevelIndex;
        myLevelInfo = myCoreAPI.configuration().string( "Level {0}", myLevelIndex );
        myRepeatedFlag = false;
        myRepeatInterval = 1;
        }

    public static final Level loadNew( final EditorCoreAPI aCoreAPI, final DataInputStream aInputStream ) throws IOException
        {
        final NormalLevel level = new NormalLevel( aCoreAPI );
        level.load( aInputStream );
        return level;
        }

    // From Level

    public final void add( final LevelListener aListener )
        {
        myListeners.add( aListener );
        }

    public final int levelIndex()
        {
        return myLevelIndex;
        }

    public final boolean hasChanges()
        {
        return false;
        }

    public final boolean appliesTo( final int aLevelIndex )
        {
        if ( isRepeated() )
            {
            return ( ( aLevelIndex - myLevelIndex ) % repeatInterval() ) == 0;
            }
        else
            {
            return myLevelIndex == aLevelIndex;
            }
        }

    public final boolean canChangeRepeatMode()
        {
        return true;
        }

    public final boolean isRepeated()
        {
        return myRepeatedFlag;
        }

    public final void setRepeated( final boolean aRepeatedFlag )
        {
        myRepeatedFlag = aRepeatedFlag;
        myListeners.fire( "onSetRepeated", this, aRepeatedFlag );
        }

    public final int repeatInterval()
        {
        return myRepeatInterval;
        }

    public final void setRepeatInterval( final int aRepeatInterval )
        {
        if ( aRepeatInterval < 1 ) throw new IllegalArgumentException();
        myRepeatInterval = aRepeatInterval;
        myListeners.fire( "onSetRepeatInterval", this, aRepeatInterval );
        }

    public final boolean canChangeLevelInfo()
        {
        return true;
        }

    public final String levelInfo()
        {
        return myLevelInfo;
        }

    public final void setLevelInfo( final String aLevelInfo )
        {
        if ( aLevelInfo == null ) throw new NullPointerException();
        myLevelInfo = aLevelInfo;
        myListeners.fire( "onSetLevelInfo", this, aLevelInfo );
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
        LOG.debug( "Loading level" );
        myLevelIndex = aInputStream.readInt();
        myRepeatInterval = aInputStream.readInt();
        myRepeatedFlag = aInputStream.readBoolean();
        setProperty( CHALLENGING_STAGE, aInputStream.readBoolean() );
        mySwarms.load( aInputStream );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        LOG.debug( "Saving level %d", myLevelIndex );
        aOutputStream.writeInt( myLevelIndex );
        aOutputStream.writeInt( myRepeatInterval );
        aOutputStream.writeBoolean( myRepeatedFlag );
        aOutputStream.writeBoolean( getBoolean( CHALLENGING_STAGE ) );
        mySwarms.save( aOutputStream );
        }

    // From Object

    public final String toString()
        {
        return myCoreAPI.configuration().string( "Level {0}", myLevelIndex );
        }

    // Implementation

    private NormalLevel( final EditorCoreAPI aCoreAPI )
        {
        this( aCoreAPI, new Swarms( aCoreAPI ) );
        }

    private NormalLevel( final EditorCoreAPI aCoreAPI, final Swarms aSwarms )
        {
        myCoreAPI = aCoreAPI;
        mySwarms = aSwarms;
        setProperty( CHALLENGING_STAGE, Boolean.FALSE );
        }



    private int myLevelIndex;

    private String myLevelInfo;

    private int myRepeatInterval;

    private boolean myRepeatedFlag;


    private final Swarms mySwarms;

    private final EditorCoreAPI myCoreAPI;

    private final EventUtilities<LevelListener> myListeners = new EventUtilities<LevelListener>();

    private static final Log LOG = Log.get();
    }
