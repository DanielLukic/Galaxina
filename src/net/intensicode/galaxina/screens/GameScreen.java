package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.game.GameContext;
import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.galaxina.MainContext;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class GameScreen extends ScreenBase
    {
    public GameScreen( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        myGameContext = myMainContext.gameContext();
        myGameModel = myGameContext.gameModel();
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        myGameModel.onInitialize( system() );

        myLevelInfo = new LevelInfoScreen( myMainContext );
        myPlayLevel = new PlayLevelScreen( myMainContext );
        myLevelStats = new LevelStatsScreen( myGameContext );
        myGameOver = new GameOverScreen( myGameContext );
        myPausedScreen = new GamePausedScreen( myMainContext );

        myLevelInfo.onInit( system() );
        myPlayLevel.onInit( system() );
        myLevelStats.onInit( system() );
        myGameOver.onInit( system() );
        }

    public void onInitEverytime() throws Exception
        {
        myMainContext.musicController().play( "game" );
        }

    public final void onControlTick() throws Exception
        {
        switch ( myGameModel.state )
            {
            case GameModel.STATE_INITIALIZED:
                //#if DEBUG
                Assert.fail( "should not be here" );
                //#endif
                myGameModel.startGame();
                break;
            case GameModel.STATE_SHOW_LEVEL_INFO:
                stack().pushOnce( myLevelInfo );
                break;
            case GameModel.STATE_PLAY_LEVEL:
                stack().pushOnce( myPlayLevel );
                break;
            case GameModel.STATE_SHOW_LEVEL_STATS:
                stack().pushOnce( myLevelStats );
                break;
            case GameModel.STATE_SHOW_GAME_OVER:
                stack().pushOnce( myGameOver );
                break;
            case GameModel.STATE_PAUSED:
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


    private final GameModel myGameModel;

    private final GameContext myGameContext;

    private final MainContext myMainContext;

    private GamePausedScreen myPausedScreen;
    }
