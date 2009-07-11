package net.intensicode.screens;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.GameModel;


/**
 * TODO: Describe this!
 */
public final class GameScreen extends AbstractScreen
    {
    public GameScreen( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        myGameModel = aGameContext.gameModel();
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        myGameModel.onInitialize( aEngine );

        myLevelInfo = new LevelInfoScreen( myGameContext );
        myPlayLevel = new PlayLevelScreen( myGameContext );
        myLevelStats = new LevelStatsScreen( myGameContext );
        myGameOver = new GameOverScreen( myGameContext );

        myLevelInfo.onInit( aEngine, aScreen );
        myPlayLevel.onInit( aEngine, aScreen );
        myLevelStats.onInit( aEngine, aScreen );
        myGameOver.onInit( aEngine, aScreen );
        }

    public void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        aEngine.sound.stopMusic();
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        switch ( myGameModel.state )
            {
            case GameModel.STATE_INITIALIZED:
                myGameModel.startGame();
                break;
            case GameModel.STATE_SHOW_LEVEL_INFO:
                aEngine.pushOnce( myLevelInfo );
                break;
            case GameModel.STATE_PLAY_LEVEL:
                aEngine.pushOnce( myPlayLevel );
                break;
            case GameModel.STATE_SHOW_LEVEL_STATS:
                aEngine.pushOnce( myLevelStats );
                break;
            case GameModel.STATE_SHOW_GAME_OVER:
                aEngine.pushOnce( myGameOver );
                break;
            }
        }

    public void onDrawFrame( final DirectScreen aScreen )
        {
        }


    private GameOverScreen myGameOver;

    private LevelInfoScreen myLevelInfo;

    private PlayLevelScreen myPlayLevel;

    private LevelStatsScreen myLevelStats;


    private final GameModel myGameModel;

    private final GameContext myGameContext;
    }
