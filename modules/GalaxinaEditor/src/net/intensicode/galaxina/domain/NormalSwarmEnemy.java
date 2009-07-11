package net.intensicode.galaxina.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class NormalSwarmEnemy extends GroupEntryBase implements SwarmEnemy
    {
    public NormalSwarmEnemy( final Enemies aEnemies )
        {
        myEnemies = aEnemies;
        mySyncModes = new ArrayList<SyncMode>();
        mySyncModes.add( INDEPENDANT_MODE );
        mySyncModes.add( SYNC_BY_PATH );
        mySyncModes.add( FOLLOW_LEFT_SIDE );
        mySyncModes.add( FOLLOW_RIGHT_SIDE );
        mySyncModes.add( SYNC_SPEED );
        setSyncMode( INDEPENDANT_MODE );
        setZeroDelay( Boolean.FALSE );
        }

    public static final SwarmEnemy loadNew( final Enemies aEnemies, final DataInputStream aInputStream ) throws IOException
        {
        final NormalSwarmEnemy swarmEnemy = new NormalSwarmEnemy( aEnemies );
        swarmEnemy.load( aInputStream );
        return swarmEnemy;
        }

    // From SwarmEnemy

    public final Enemy getEnemy()
        {
        return (Enemy) getProperty( ENEMY );
        }

    public final void setEnemey( final Enemy aEnemy )
        {
        setProperty( ENEMY, aEnemy );
        }

    public final SyncMode getSyncMode()
        {
        return (SyncMode) getProperty( SYNC_MODE );
        }

    public final void setSyncMode( final SyncMode aSyncMode )
        {
        setProperty( SYNC_MODE, aSyncMode );
        }

    public final Boolean getZeroDelay()
        {
        return getBoolean( ZERO_DELAY );
        }

    public final void setZeroDelay( final Boolean aZeroDelay )
        {
        setProperty( ZERO_DELAY, aZeroDelay );
        }

    public final SwarmEnemy clone()
        {
        final NormalSwarmEnemy newSwarm = new NormalSwarmEnemy( myEnemies );
        newSwarm.myProperties.putAll( myProperties );
        return newSwarm;
        }

    // From GroupEntry

    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        final int index = aInputStream.readInt();
        setEnemey( myEnemies.at( index ) );

        final int syncModeIndex = aInputStream.readInt();
        setSyncMode( mySyncModes.get( syncModeIndex ) );
        setZeroDelay( aInputStream.readBoolean() );
        }

    public final void save( final DataOutputStream aOutputStream ) throws IOException
        {
        final int index = myEnemies.indexOf( getEnemy() );
        aOutputStream.writeInt( index );
        aOutputStream.writeInt( getSyncMode().id );
        aOutputStream.writeBoolean( getZeroDelay() );
        }

    // From Object

    public final String toString()
        {
        return getEnemy().toString();
        }



    private final Enemies myEnemies;

    private final ArrayList<SyncMode> mySyncModes;
    }
