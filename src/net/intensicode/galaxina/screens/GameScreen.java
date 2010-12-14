package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.GameModel;

public final class GameScreen extends GalaxinaGameScreen
    {
    public GameScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        myLevelInfo = new LevelInfoScreen( context() );
        myPlayLevel = new PlayLevelScreen( context() );
        myLevelStats = new LevelStatsScreen( context() );
        myGameOver = new GameOverScreen( game() );
        myPausedScreen = new GamePausedScreen( context() );

        myLevelInfo.onInit( system() );
        myPlayLevel.onInit( system() );
        myLevelStats.onInit( system() );
        myGameOver.onInit( system() );
        }

    public void onInitEverytime() throws Exception
        {
        context().musicController().play( "game" );
        }

    public final void onControlTick() throws Exception
        {
        final float slowMoveFixed = -1f / timing().ticksPerSecond;
        final float warpMoveFixed = -10f / timing().ticksPerSecond;

        switch ( model().state )
            {
            case GameModel.STATE_INITIALIZED:
                // This happens in the editor only.. Just wait for the ReloadAndSwitchHandler..
                break;
            case GameModel.STATE_SHOW_LEVEL_INFO:
                game().starField().changeMovement( 0, slowMoveFixed, 0 );
                stack().pushOnce( myLevelInfo );
                break;
            case GameModel.STATE_PLAY_LEVEL:
                game().starField().changeMovement( 0, slowMoveFixed, 0 );
                stack().pushOnce( myPlayLevel );
                break;
            case GameModel.STATE_SHOW_LEVEL_STATS:
                game().starField().changeMovement( 0, warpMoveFixed, 0 );
                stack().pushOnce( myLevelStats );
                break;
            case GameModel.STATE_SHOW_GAME_OVER:
                game().starField().startTumbling();
                stack().pushOnce( myGameOver );
                break;
            case GameModel.STATE_PAUSED:
                game().starField().changeMovement( 0, 0, slowMoveFixed );
                stack().pushOnce( myPausedScreen );
                break;
            }
        }

    public void onDrawFrame()
        {
        }


    private GameOverScreen myGameOver;

    private LevelInfoScreen myLevelInfo;

    private PlayLevelScreen myPlayLevel;

    private LevelStatsScreen myLevelStats;

    private GamePausedScreen myPausedScreen;
    }
