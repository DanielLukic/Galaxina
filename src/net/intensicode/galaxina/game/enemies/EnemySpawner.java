package net.intensicode.galaxina.game.enemies;

import net.intensicode.galaxina.game.data.FormationConfiguration;
import net.intensicode.galaxina.game.data.SwarmConfiguration;
import net.intensicode.galaxina.game.objects.GameObject;
import net.intensicode.path.PathWithDirection;
import net.intensicode.util.DynamicArray;
import net.intensicode.util.Log;
import net.intensicode.util.Position;
import net.intensicode.screens.ConsoleOverlay;

import java.io.IOException;

public final class EnemySpawner extends GameObject
    {
    public final DynamicArray spawnedSwarms = new DynamicArray();



    public EnemySpawner()
        {
        }

    public final void reload() throws IOException
        {
        //#if DEBUG
        Log.debug( "Reloading formation" );
        //#endif
        //#if CONSOLE
        ConsoleOverlay.addMessage( "Reloading formation" );
        //#endif
        myConfiguration = new FormationConfiguration( system.resources, model );
        }

    public final boolean isChallengingStage()
        {
        return myConfiguration.isChallengingState;
        }

    public final int numberOfEnemies()
        {
        return myConfiguration.numberOfEnemies;
        }

    public final boolean done()
        {
        if ( myDoneFlag ) return true;
        if ( myState != STATE_IDLE ) return false;

        final DynamicArray enemies = model.level.activeEnemies;
        for ( int idx = 0; idx < enemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) enemies.objects[ idx ];
            if ( !enemy.active ) continue;
            if ( !enemy.isReady() ) return false;
            }

        return myDoneFlag = true;
        }

    // From GameObject

    public final void onStartGame() throws Exception
        {
        myState = STATE_INITIALIZING;
        if ( myConfiguration == null ) reload();
        }

    public final void onStartLevel() throws Exception
        {
        myState = STATE_INITIALIZING;
        myDoneFlag = false;
        myCurrentSwarm = null;
        spawnedSwarms.clear();

        if ( myConfiguration == null ) reload();
        myConfiguration.switchTo( model.level.numberStartingAt1 );
        }

    public final void onControlTick()
        {
        switch ( myState )
            {
            case STATE_INITIALIZING:
                onInitializing();
                break;
            case STATE_WAITING_FOR_NEXT_SWARM:
                onWaiting();
                break;
            case STATE_SPAWNING_SWARM:
                onSpawning();
                break;
            case STATE_IDLE:
                break;
            default:
                throw new IllegalStateException();
            }

        checkSwarms();
        }

    // Implementation

    private void changeState( final int aNewState )
        {
        myState = aNewState;
        mySpawnTicks = 0;
        myCurrentEnemyIndex = 0;
        }

    private void onInitializing()
        {
        mySpawningTicks = timing.ticksPerSecond / 3;
        myCurrentSwarmIndex = 0;
        changeState( STATE_SPAWNING_SWARM );
        }

    private void onSpawning()
        {
        final SwarmConfiguration config = myConfiguration.swarms[ myCurrentSwarmIndex ];
        if ( myCurrentEnemyIndex < config.size )
            {
            final int delayInDeciSecs = config.delayInDeciSecondsFor( myCurrentEnemyIndex );
            mySpawningTicks = timing.ticksPerSecond * delayInDeciSecs / 10;

            if ( mySpawnTicks < mySpawningTicks )
                {
                mySpawnTicks++;
                }
            else
                {
                if ( myCurrentSwarm == null )
                    {
                    myCurrentSwarm = new Swarm();
                    myCurrentSwarm.extraID = config.extraID;
                    spawnedSwarms.add( myCurrentSwarm );
                    }

                spawnNextEnemy();
                mySpawnTicks = 0;

                myCurrentSwarm.isComplete = ( myCurrentEnemyIndex == config.size );

                // This makes sure an enemy delay == 0 works:
                onSpawning();
                }
            }
        else
            {
            myCurrentSwarm = null;
            myCurrentSwarmIndex++;
            if ( myCurrentSwarmIndex == myConfiguration.numberOfSwarms ) changeState( STATE_IDLE );
            else changeState( STATE_WAITING_FOR_NEXT_SWARM );
            }
        }

    private void onWaiting()
        {
        boolean stillWaiting = false;

        final DynamicArray activeEnemies = model.level.activeEnemies;
        for ( int idx = 0; idx < activeEnemies.size; idx++ )
            {
            final Enemy enemy = (Enemy) activeEnemies.objects[ idx ];
            if ( !enemy.active ) continue;
            stillWaiting |= !enemy.isReady();
            }

        if ( !stillWaiting ) changeState( STATE_SPAWNING_SWARM );
        }

    private void checkSwarms()
        {
        for ( int idx = spawnedSwarms.size - 1; idx >= 0; idx-- )
            {
            final Swarm swarm = (Swarm) spawnedSwarms.objects[ idx ];
            swarm.onControlTick();

            if ( swarm.bonusApplies > 0 ) model.applySwarmBonus( swarm );
            }
        }

    private void spawnNextEnemy()
        {
        final Enemy enemy = myCurrentSwarm.spawnEnemy();
        final FormationConfiguration configuration = myConfiguration;
        final EnemyType type = configuration.enemyTypeFor( myCurrentSwarmIndex, myCurrentEnemyIndex );
        final PathWithDirection path = configuration.pathFor( myCurrentSwarmIndex, myCurrentEnemyIndex );
        final SwarmConfiguration config = configuration.swarms[ myCurrentSwarmIndex ];

        final boolean normalLevel = !configuration.isChallengingState;
        if ( normalLevel )
            {
            final Position position = config.formationPositionFor( myCurrentEnemyIndex );
            enemy.init( type, path, position );
            }
        else
            {
            enemy.init( type, path, null );
            }

        if ( config.syncSpeedWithPrevious( myCurrentEnemyIndex ) )
            {
            final Enemy previous = (Enemy) model.level.activeEnemies.last();
            if ( previous == null ) throw new IllegalStateException();
            enemy.syncSpeedWith( previous );
            }
        if ( config.syncWithPrevious( myCurrentEnemyIndex ) )
            {
            final Enemy previous = (Enemy) model.level.activeEnemies.last();
            if ( previous == null ) throw new IllegalStateException();
            enemy.syncPathWith( previous );
            }
        if ( config.followLeftSide( myCurrentEnemyIndex ) )
            {
            final Enemy previous = (Enemy) model.level.activeEnemies.last();
            if ( previous == null ) throw new IllegalStateException();
            enemy.followAtLeftSideOf( previous );
            }
        if ( config.followRightSide( myCurrentEnemyIndex ) )
            {
            final Enemy previous = (Enemy) model.level.activeEnemies.last();
            if ( previous == null ) throw new IllegalStateException();
            enemy.followAtRightSideOf( previous );
            }

        model.level.activeEnemies.add( enemy );

        myCurrentEnemyIndex++;
        }



    private int mySpawnTicks;

    private boolean myDoneFlag;

    private int mySpawningTicks;

    private Swarm myCurrentSwarm;

    private int myCurrentEnemyIndex;

    private int myCurrentSwarmIndex;

    private int myState = STATE_INITIALIZING;

    private FormationConfiguration myConfiguration;


    private static final int STATE_INITIALIZING = 0;

    private static final int STATE_WAITING_FOR_NEXT_SWARM = 1;

    private static final int STATE_SPAWNING_SWARM = 2;

    private static final int STATE_IDLE = 3;
    }
