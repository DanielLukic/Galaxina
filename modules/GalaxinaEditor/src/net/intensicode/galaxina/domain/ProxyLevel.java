package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorConfiguration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ProxyLevel implements Level
    {
    public ProxyLevel( final EditorConfiguration aConfiguration )
        {
        myConfiguration = aConfiguration;
        }

    public final void setTo( final int aLevelIndex, final Level aLevel )
        {
        myLevelIndex = aLevelIndex;
        myLevel = aLevel;
        }

    // From Level

    public final void add( final LevelListener aListener )
        {
        myLevel.add( aListener );
        }

    public final int levelIndex()
        {
        return myLevelIndex;
        }

    public final boolean hasChanges()
        {
        return myLevel.hasChanges();
        }

    public final boolean appliesTo( final int aLevelIndex )
        {
        return myLevel.appliesTo( aLevelIndex );
        }

    public final boolean canChangeRepeatMode()
        {
        return false;
        }

    public final boolean isRepeated()
        {
        return myLevel.isRepeated();
        }

    public final void setRepeated( final boolean aRepeatedFlag )
        {
        myLevel.setRepeated( aRepeatedFlag );
        }

    public final int repeatInterval()
        {
        return myLevel.repeatInterval();
        }

    public final void setRepeatInterval( final int aRepeatInterval )
        {
        myLevel.setRepeatInterval( aRepeatInterval );
        }

    public final boolean canChangeLevelInfo()
        {
        return false;
        }

    public final String levelInfo()
        {
        return myConfiguration.string( "Proxy for level {0} (using level index {1}).", myLevel.levelIndex(), myLevelIndex );
        }

    public final void setLevelInfo( final String aLevelInfo )
        {
        throw new RuntimeException();
        }

    public final boolean canBeReplacedByClonedSelf()
        {
        return true;
        }

    public final Swarms swarms()
        {
        return myLevel.swarms();
        }

    // From GroupEntryEx

    public final void add( final GroupEntryListener aListener )
        {
        myLevel.add( aListener );
        }

    public final void remove( final GroupEntryListener aListener )
        {
        myLevel.remove( aListener );
        }

    public final Object getProperty( final String aName )
        {
        return myLevel.getProperty( aName );
        }

    public final boolean setProperty( final String aName, final Object aValue )
        {
        return myLevel.setProperty( aName, aValue );
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        throw new RuntimeException( "nyi" );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        throw new RuntimeException( "nyi" );
        }

    // From Object

    public String toString()
        {
        return myConfiguration.string( "Level {0} (Proxy for {1})", myLevelIndex, myLevel.levelIndex() );
        }



    private Level myLevel;

    private int myLevelIndex;

    private final EditorConfiguration myConfiguration;
    }