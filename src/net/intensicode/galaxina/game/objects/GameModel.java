package net.intensicode.galaxina.game.objects;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.enemies.*;
import net.intensicode.galaxina.game.extras.ExtraTypes;
import net.intensicode.util.*;

public final class GameModel
    {
    public static final int STATE_INITIALIZED = -1;

    public static final int STATE_SHOW_LEVEL_INFO = 0;

    public static final int STATE_PLAY_LEVEL = 1;

    public static final int STATE_SHOW_LEVEL_STATS = 2;

    public static final int STATE_SHOW_GAME_OVER = 3;

    public static final int STATE_PAUSED = 4;


    public final World world;

    public final Level level;

    public final Bombs bombs;

    public final GunShots gunShots;

    public final FallingExtras extras;

    public final Player player;

    public final Smokes smokes;

    public final Breather breather;

    public final Explosions explosions;

    public final ScoreMarkers scoreMarkers;

    public final Missiles missiles;

    public final Debrises debrises;

    public final Enemies enemies;

    public final EnemySpawner enemySpawner;

    public final AttackSpawner attackSpawner;

    public final InfoFlash infoFlash;

    public final Weapons weapons;

    public final Sparks sparks;

    public final ExtraTypes extraTypes = new ExtraTypes();

    public AudioResource soundExtra;

    public AudioResource soundDebris;

    public Configuration configuration;

    public int screenFlashIntensity;

    public int state;


    public GameModel( final World aWorld )
        {
        myGameObjects.add( world = aWorld );
        myGameObjects.add( level = new Level() );
        myGameObjects.add( bombs = new Bombs() );
        myGameObjects.add( gunShots = new GunShots() );
        myGameObjects.add( extras = new FallingExtras() );
        myGameObjects.add( player = new Player() );
        myGameObjects.add( enemies = new Enemies() );
        myGameObjects.add( missiles = new Missiles() );
        myGameObjects.add( debrises = new Debrises() );
        myGameObjects.add( weapons = new Weapons() );
        myGameObjects.add( enemySpawner = new EnemySpawner() );
        myGameObjects.add( attackSpawner = new AttackSpawner() );
        myGameObjects.add( smokes = new Smokes() );
        myGameObjects.add( sparks = new Sparks() );
        myGameObjects.add( explosions = new Explosions() );
        myGameObjects.add( scoreMarkers = new ScoreMarkers() );
        myGameObjects.add( breather = new Breather() );
        myGameObjects.add( infoFlash = new InfoFlash() );
        }

    public final void startGame() throws Exception
        {
        //#if DEBUG
        Log.debug( "startGame" );
        //#endif

        state = STATE_SHOW_LEVEL_INFO;

        for ( int idx = 0; idx < myGameObjects.size; idx++ )
            {
            final GameObject gameObject = (GameObject) myGameObjects.objects[ idx ];
            gameObject.onStartGame();
            }
        }

    public final void pauseGame()
        {
        if ( state == STATE_PAUSED ) return;
        myStateBeforePause = state;
        state = STATE_PAUSED;
        }

    public final void unpauseGame()
        {
        if ( state != STATE_PAUSED ) return;
        state = myStateBeforePause;
        myStateBeforePause = STATE_INITIALIZED;
        }

    public final void startLevel() throws Exception
        {
        state = STATE_PLAY_LEVEL;

        for ( int idx = 0; idx < myGameObjects.size; idx++ )
            {
            final GameObject gameObject = (GameObject) myGameObjects.objects[ idx ];
            gameObject.onStartLevel();
            }
        }

    public final void applySwarmBonus( final Swarm aSwarm )
        {
        //#if DEBUG
        if ( aSwarm.bonusApplies == 0 ) throw new IllegalArgumentException();
        //#endif

        player.score += aSwarm.bonusApplies;
        scoreMarkers.add( aSwarm.lastHitPosition, aSwarm.bonusApplies );

        final boolean removed = enemySpawner.spawnedSwarms.remove( aSwarm );
        //#if DEBUG
        if ( !removed ) throw new IllegalArgumentException();
        //#endif

        aSwarm.bonusApplies = 0;
        }

    public final void triggerSound( final AudioResource aAudioResource )
        {
        // collect these and play at end of tick..
        aAudioResource.start();
        }

    public final void onInitialize( final GameSystem aGameSystem ) throws Exception
        {
        state = STATE_INITIALIZED;

        EnemyBehavior.model = EnemyWeapon.model = Enemy.model = this;
        EnemyBehavior.engine = EnemyWeapon.engine = aGameSystem.engine;
        Enemy.timing = EnemyWeapon.timing = aGameSystem.timing;

        try
            {
            configuration = aGameSystem.resources.loadConfiguration( "/game.properties" );
            }
        catch ( final Exception e )
            {
            //#if DEBUG
            e.printStackTrace();
            //#endif
            configuration = new Configuration();
            }

        for ( int idx = 0; idx < myGameObjects.size; idx++ )
            {
            final GameObject gameObject = (GameObject) myGameObjects.objects[ idx ];
            gameObject.onInitialize( aGameSystem, this );
            }

        soundExtra = aGameSystem.audio.loadSound( "extra" );
        soundDebris = aGameSystem.audio.loadSound( "debris" );
        }

    public final void onControlTick() throws Exception
        {
        for ( int idx = 0; idx < myGameObjects.size; idx++ )
            {
            final GameObject gameObject = (GameObject) myGameObjects.objects[ idx ];
            gameObject.onControlTick();
            }
        }

    public final void onLevelComplete()
        {
        state = STATE_SHOW_LEVEL_STATS;
        }

    public final void onNextLevel()
        {
        level.advance();
        state = STATE_SHOW_LEVEL_INFO;
        }

    public final void onGameOver()
        {
        state = STATE_SHOW_GAME_OVER;
        }


    private int myStateBeforePause;

    private final DynamicArray myGameObjects = new DynamicArray();
    }
