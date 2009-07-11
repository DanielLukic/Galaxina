/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.game.data;

import net.intensicode.core.ResourceLoader;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.enemies.EnemyType;
import net.intensicode.game.enemies.EnemyPath;
import net.intensicode.path.PathWithDirection;

import java.io.DataInputStream;
import java.io.IOException;


/**
 * TODO: Describe this!
 */
public final class FormationConfiguration
    {
    public int numberOfSwarms;

    public int numberOfEnemies;

    public boolean isChallengingState;

    public SwarmConfiguration[] swarms;



    public FormationConfiguration( final ResourceLoader aLoader, final GameModel aGameModel ) throws IOException
        {
        myLoader = aLoader;
        myGameModel = aGameModel;

        loadPathes( openStream( "/pathes.dat" ) );
        loadLevels( openStream( "/levels.dat" ) );
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

    private final DataInputStream openStream( final String aFileName ) throws IOException
        {
        return new DataInputStream( myLoader.openChecked( aFileName ) );
        }

    private final void loadPathes( final DataInputStream aInputStream ) throws IOException
        {
        final int count = aInputStream.readInt();
        myDefinedPaths = new PathWithDirection[count];

        for ( int idx = 0; idx < count; idx++ )
            {
            myDefinedPaths[ idx ] = loadPath( aInputStream );
            }

        aInputStream.close();
        }

    private final EnemyPath loadPath( final DataInputStream aInputStream ) throws IOException
        {
        final EnemyPath path = new EnemyPath( myGameModel.world );

        aInputStream.readUTF(); // skip name

        final int size = aInputStream.readInt();
        for ( int idx = 0; idx < size; idx++ )
            {
            final int x = aInputStream.readInt() * 150 / 240 - 75;
            final int y = aInputStream.readInt() * 150 / 320 - 75;
            path.addRelativePos( x, y );
            }
        path.end();

        return path;
        }

    private final void loadLevels( final DataInputStream aInputStream ) throws IOException
        {
        final int count = aInputStream.readInt();
        myDefinedLevels = new LevelConfiguration[count];

        for ( int idx = 0; idx < count; idx++ )
            {
            myDefinedLevels[ idx ] = loadLevel( aInputStream );
            }

        aInputStream.close();
        }

    private final LevelConfiguration loadLevel( final DataInputStream aInputStream ) throws IOException
        {
        final LevelConfiguration config = new LevelConfiguration();
        config.load( aInputStream );
        return config;
        }



    private PathWithDirection[] myDefinedPaths;

    private LevelConfiguration[] myDefinedLevels;


    private final GameModel myGameModel;

    private final ResourceLoader myLoader;
    }
