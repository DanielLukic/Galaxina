package net.intensicode.galaxina.game;

import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.drawers.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.screens.*;
import net.intensicode.screens.*;
import net.intensicode.util.Assert;

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

    public final VerticalSoftkeysScreen sharedSoftkeys()
        {
        return mySharedSoftkeys;
        }

    public final void startGame() throws Exception
        {
        myGameModel.startGame();
        }

    // From ScreenBase

    public void onInitOnce() throws Exception
        {
        final World world = new World( screen().width(), screen().height() );
        myGameModel = new GameModel( world );
        myGameModel.onInitialize( system() );

        myCamera = new Camera( this );
        myHiscore = new Hiscore();

        mySharedGameDrawers = new MultiScreen();
        mySharedGameDrawers.addScreen( new BombsDrawer( this ) );
        mySharedGameDrawers.addScreen( new DebrisDrawer( this ) );
        mySharedGameDrawers.addScreen( new GunShotsDrawer( this ) );
        mySharedGameDrawers.addScreen( new EnemiesDrawer( this ) );
        mySharedGameDrawers.addScreen( new MissilesDrawer( this ) );
        mySharedGameDrawers.addScreen( new PlayerDrawer( this ) );
        mySharedGameDrawers.addScreen( new ObjectsDrawer( this, "satellite", myGameModel.satellites.satellites ) );
        mySharedGameDrawers.addScreen( new SmokesDrawer( this ) );
        mySharedGameDrawers.addScreen( new SparksDrawer( this ) );
        mySharedGameDrawers.addScreen( new ExtrasDrawer( this ) );
        mySharedGameDrawers.addScreen( new ExplosionsDrawer( this ) );
        mySharedGameDrawers.addScreen( new ScoreTagsDrawer( this ) );
        mySharedGameDrawers.addScreen( new InfoFlashDrawer( this ) );

        mySharedSoftkeys = new VerticalSoftkeysScreen( visualContext().textFont() );
        mySharedSoftkeys.setButtonImage( skin().image( "softkeys" ) );

        mySharedGameBackground = new MultiScreen();
        mySharedGameBackground.addScreen( new ClearScreen() );
        mySharedGameBackground.addScreen( new StarField( 32, skin().charGen( "stars" ) ) );
        mySharedGameBackground.addScreen( myCamera );
        mySharedGameBackground.addScreen( mySharedSoftkeys );
        mySharedGameBackground.addScreen( new ScoreboardDrawer( this ) );
        }

    public final void onControlTick() throws Exception
        {
        //#if DEBUG
        Assert.fail( "should not be here" );
        //#endif
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

    private VerticalSoftkeysScreen mySharedSoftkeys;

    private final MainContext myMainContext;
    }
