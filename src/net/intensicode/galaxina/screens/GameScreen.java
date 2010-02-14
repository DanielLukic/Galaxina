package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.game.GameContext;
import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.screens.ScreenBase;

public final class GameScreen extends ScreenBase
    {
    public GameScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        myGameModel = aGameContext.gameModel();
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        myGameModel.onInitialize( system() );

        myLevelInfo = new LevelInfoScreen( myGameContext );
        myPlayLevel = new PlayLevelScreen( myGameContext );
        myLevelStats = new LevelStatsScreen( myGameContext );
        myGameOver = new GameOverScreen( myGameContext );

        myLevelInfo.onInit( system() );
        myPlayLevel.onInit( system() );
        myLevelStats.onInit( system() );
        myGameOver.onInit( system() );
        }

    public void onInitEverytime() throws Exception
        {
        //audio().stopMusic();
        }

    public final void onControlTick() throws Exception
        {
        switch ( myGameModel.state )
            {
            case GameModel.STATE_INITIALIZED:
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
    }
