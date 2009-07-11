package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class Levels extends GroupDomainObject<Level>
    {
    public Levels( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    public final void open()
        {
        myEntries.add( new DefaultLevel( myCoreAPI ) );
        }

    public final void close()
        {
        final ArrayList<Level> levels = new ArrayList<Level>( myEntries );
        for ( final Level level : levels ) deleteEntry( level );
        if ( myEntries.size() != 0 ) throw new IllegalStateException();
        }

    public final void replaceByClone( final Level aLevel )
        {
        final NormalLevel clonedLevel = new NormalLevel( myCoreAPI, aLevel );

        final int highestIndex = myEntries.size() - 1;
        for ( int idx = highestIndex; idx >= 0; idx-- )
            {
            final Level level = myEntries.get( idx );
            if ( level == null ) continue;
            if ( level.levelIndex() > clonedLevel.levelIndex() ) continue;
            myEntries.add( idx + 1, clonedLevel );
            LOG.debug( "insert clone at %d", idx );
            }

        myListeners.fire( "onReplaced", this, aLevel, clonedLevel, clonedLevel.levelIndex() );
        }

    public final int findLevelIndexOf( final Level aLevel )
        {
        return aLevel.levelIndex();
        }

    // From Group

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        loadEntries( aInputStream );
        fireDataChanged();
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        saveEntries( aOutputStream );
        }

    public final Level at( final int aLevelIndex )
        {
        final int highestIndex = myEntries.size() - 1;
        for ( int idx = highestIndex; idx >= 0; idx-- )
            {
            final Level level = myEntries.get( idx );
            if ( level == null ) continue;
            if ( level.levelIndex() > aLevelIndex ) continue;
            if ( level.appliesTo( aLevelIndex ) == false ) continue;

            if ( aLevelIndex == level.levelIndex() ) return level;

            final ProxyLevel proxyLevel = new ProxyLevel( myCoreAPI.configuration() );
            proxyLevel.setTo( aLevelIndex, level );
            return proxyLevel;
            }

        throw new IllegalArgumentException();
        }

    // From GroupDomainObject

    protected final Level loadEntry( final DataInputStream aInputStream ) throws IOException
        {
        if ( myEntries.size() == 0 )
            {
            final DefaultLevel defaultLevel = new DefaultLevel( myCoreAPI );
            defaultLevel.load( aInputStream );
            return defaultLevel;
            }
        return NormalLevel.loadNew( myCoreAPI, aInputStream );
        }



    private final EditorCoreAPI myCoreAPI;

    private static final Log LOG = Log.get();
    }
