package net.intensicode.galaxina.game;

import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.drawers.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.screens.*;
import net.intensicode.screens.*;

public final class GameController extends ScreenBase implements GameContext
    {
    public GameController( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        }

    // From GameContext

    public final Camera camera()
        {
        return myCamera;
        }

    public final Hiscore hiscore()
        {
        return myHiscore;
        }

    public final GameModel gameModel()
        {
        return myGameModel;
        }

    public final VisualContext visualContext()
        {
        return myMainContext.visualContext();
        }

    public final ScreenBase sharedGameBackground()
        {
        return mySharedGameBackground;
        }

    public final ScreenBase sharedGameDrawers()
        {
        return mySharedGameDrawers;
        }

    public final void showMainMenu() throws Exception
        {
        myMainContext.showMainMenu();
        }

    public final void showHelp() throws Exception
        {
        }

    public final void showHiscore() throws Exception
        {
        }

    public final void showOptions() throws Exception
        {
        }

    public final void startGame() throws Exception
        {
        if ( myGameScreen == null ) myGameScreen = new GameScreen( this );
        stack().pushOnce( myGameScreen );
        myGameModel.startGame();
        }

    public final void pauseGame() throws Exception
        {
        if ( myGamePausedScreen == null ) myGamePausedScreen = new GamePausedScreen( this );
        stack().pushOnce( myGamePausedScreen );
        }

    public final void exit() throws Exception
        {
        system().shutdownAndExit();
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        final World world = new World( screen().width(), screen().height() );
        myGameModel = new GameModel( world );

        myCamera = new Camera( this );
        myHiscore = new Hiscore();

        mySharedGameDrawers = new MultiScreen();
        mySharedGameDrawers.addScreen( new BombsDrawer( this ) );
        mySharedGameDrawers.addScreen( new DebrisDrawer( this ) );
        mySharedGameDrawers.addScreen( new GunShotsDrawer( this ) );
        mySharedGameDrawers.addScreen( new EnemiesDrawer( this ) );
        mySharedGameDrawers.addScreen( new MissilesDrawer( this ) );
        mySharedGameDrawers.addScreen( new PlayerDrawer( this ) );
        mySharedGameDrawers.addScreen( new SmokesDrawer( this ) );
        mySharedGameDrawers.addScreen( new SparksDrawer( this ) );
        mySharedGameDrawers.addScreen( new ExtrasDrawer( this ) );
        mySharedGameDrawers.addScreen( new ExplosionsDrawer( this ) );
        mySharedGameDrawers.addScreen( new ScoreTagsDrawer( this ) );
        mySharedGameDrawers.addScreen( new InfoFlashDrawer( this ) );

        mySharedGameBackground = new MultiScreen();
        mySharedGameBackground.addScreen( visualContext().sharedBackground() );
        mySharedGameBackground.addScreen( new SimpleStars( 32 ) );
        mySharedGameBackground.addScreen( myCamera );
        mySharedGameBackground.addScreen( visualContext().sharedSoftkeys() );
        mySharedGameBackground.addScreen( new ScoreboardDrawer( this ) );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        }


    private Camera myCamera;

    private Hiscore myHiscore;

    private GameModel myGameModel;

    private GameScreen myGameScreen;

    private MultiScreen mySharedGameDrawers;

    private MultiScreen mySharedGameBackground;

    private GamePausedScreen myGamePausedScreen;

    private final MainContext myMainContext;
    }
