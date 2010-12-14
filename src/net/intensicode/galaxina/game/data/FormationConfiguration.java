package net.intensicode.galaxina.game.data;

import net.intensicode.core.ResourcesManager;
import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.GameModel;
import net.intensicode.path.PathWithDirection;
import net.intensicode.util.*;

import java.io.*;

public final class FormationConfiguration
    {
    public int numberOfSwarms;

    public int numberOfEnemies;

    public boolean isChallengingState;

    public SwarmConfiguration[] swarms;


    public FormationConfiguration( final ResourcesManager aResourcesManager, final GameModel aGameModel ) throws IOException
        {
        myResourcesManager = aResourcesManager;
        myGameModel = aGameModel;

        loadPathes( openStream( "pathes.dat" ) );
        loadLevels( openStream( "levels.dat" ) );
        }

    public final void switchTo( final int aLevelNumberStartingAt1 )
        {
        // Copy data for the specified level from loaded data
        // into the public accessor fields:
        for ( int idx = myDefinedLevels.length - 1; idx >= 0; idx-- )
            {
            final LevelConfiguration config = myDefinedLevels[ idx ];
            if ( config.appliesTo( aLevelNumberStartingAt1 ) )
                {
                isChallengingState = config.challengingStage;
                config.swarms.applyTo( this );
                return;
                }
            }
        throw new IllegalArgumentException();
        }

    public final int enemiesInSwarm( final int aSwarmIndex )
        {
        return swarms[ aSwarmIndex ].size;
        }

    public final EnemyType enemyTypeFor( final int aSwarmIndex, final int aFormationIndex )
        {
        final int typeID = swarms[ aSwarmIndex ].enemyConfigFor( aFormationIndex ).typeID;
        return myGameModel.enemies.types[ typeID ];
        }

    public final PathWithDirection pathFor( final int aSwarmIndex, final int aFormationIndex )
        {
        final int pathID = swarms[ aSwarmIndex ].pathFor( aFormationIndex );
        return myDefinedPaths[ pathID ];
        }

    // Implementation

    private DataInputStream openStream( final String aFileName ) throws IOException
        {
        return new DataInputStream( myResourcesManager.openStreamChecked( aFileName ) );
        }

    private void loadPathes( final DataInputStream aInputStream ) throws IOException
        {
        final int count = aInputStream.readInt();
        myDefinedPaths = new PathWithDirection[count];

        for ( int idx = 0; idx < count; idx++ )
            {
            myDefinedPaths[ idx ] = loadPath( aInputStream );
            }

        aInputStream.close();
        }

    private EnemyPath loadPath( final DataInputStream aInputStream ) throws IOException
        {
        final EnemyPath path = new EnemyPath( myGameModel.world );

        aInputStream.readUTF(); // skip name

        final int size = aInputStream.readInt();
        for ( int idx = 0; idx < size; idx++ )
            {
            final int x = aInputStream.readInt();
            final int y = aInputStream.readInt();
            final PositionF position = myGameModel.world.editorToWorld( x, y );
            path.addWorldPos( position );
            }
        path.end();

        return path;
        }

    private void loadLevels( final DataInputStream aInputStream ) throws IOException
        {
        final int count = aInputStream.readInt();
        myDefinedLevels = new LevelConfiguration[count];

        for ( int idx = 0; idx < count; idx++ )
            {
            myDefinedLevels[ idx ] = loadLevel( aInputStream );
            }

        aInputStream.close();
        }

    private LevelConfiguration loadLevel( final DataInputStream aInputStream ) throws IOException
        {
        final LevelConfiguration config = new LevelConfiguration();
        config.load( aInputStream );
        return config;
        }


    private PathWithDirection[] myDefinedPaths;

    private LevelConfiguration[] myDefinedLevels;


    private final GameModel myGameModel;

    private final ResourcesManager myResourcesManager;
    }
