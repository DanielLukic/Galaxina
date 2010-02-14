package net.intensicode.galaxina.game.data;

import net.intensicode.galaxina.game.extras.ExtraTypes;
import net.intensicode.util.Position;

import java.io.DataInputStream;
import java.io.IOException;

public final class SwarmConfiguration
    {
    public int size;

    public int delay;

    public int extraID;

    public int[] pathes;

    public EnemyConfiguration[] enemies;

    public Position[] formation;



    public final void load( final DataInputStream aInputStream ) throws IOException
        {
        aInputStream.readUTF(); // skip name
        size = aInputStream.readInt();
        delay = aInputStream.readInt();
        extraID = ExtraTypes.RANDOM; // hardcoded for now
        loadPathes( aInputStream );
        loadEnemies( aInputStream );
        loadPositions( aInputStream );
        }

    public final EnemyConfiguration enemyConfigFor( final int aFormationIndex )
        {
        return enemies[ aFormationIndex % enemies.length ];
        }

    public final int pathFor( final int aFormationIndex )
        {
        return pathes[ aFormationIndex % pathes.length ];
        }

    public final Position formationPositionFor( final int aFormationIndex )
        {
        return formation[ aFormationIndex % formation.length ];
        }

    public final int delayInDeciSecondsFor( final int aFormationIndex )
        {
        final boolean zeroDelay = enemyConfigFor( aFormationIndex ).zeroDelay;
        return zeroDelay ? 0 : delay;
        }

    public final boolean syncSpeedWithPrevious( final int aFormationIndex )
        {
        return enemyConfigFor( aFormationIndex ).syncSpeed;
        }

    public final boolean syncWithPrevious( final int aFormationIndex )
        {
        return enemyConfigFor( aFormationIndex ).syncByPath;
        }

    public final boolean followLeftSide( final int aFormationIndex )
        {
        return enemyConfigFor( aFormationIndex ).followLeftSide;
        }

    public final boolean followRightSide( final int aFormationIndex )
        {
        return enemyConfigFor( aFormationIndex ).followRightSide;
        }

    // Implementation

    private final void loadPositions( final DataInputStream aInputStream ) throws IOException
        {
        final int count = aInputStream.readInt();
        formation = new Position[count];
        for ( int idx = 0; idx < count; idx++ )
            {
            final int x = aInputStream.readInt() * 150 / 240 - 75;
            final int y = aInputStream.readInt() * 150 / 320 - 75;
            formation[ idx ] = new Position( x, y );
            }
        }

    private final void loadPathes( final DataInputStream aInputStream ) throws IOException
        {
        final int size = aInputStream.readInt();
        pathes = new int[size];
        for ( int idx = 0; idx < size; idx++ ) pathes[ idx ] = aInputStream.readInt();
        }

    private final void loadEnemies( final DataInputStream aInputStream ) throws IOException
        {
        final int size = aInputStream.readInt();
        enemies = new EnemyConfiguration[size];
        for ( int idx = 0; idx < size; idx++ )
            {
            final EnemyConfiguration enemyConfig = new EnemyConfiguration();
            enemyConfig.load( aInputStream );
            enemies[ idx ] = enemyConfig;
            }
        }
    }
