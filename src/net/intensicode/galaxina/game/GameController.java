package net.intensicode.galaxina.game;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.game.drawers.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.galaxina.screens.*;
import net.intensicode.screens.*;
import net.intensicode.util.*;
import net.intensicode.core.Configuration;

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
        final Configuration configuration = loadGameConfiguration();

        final int worldWidth = configuration.readInt( "World.pixelSize.width", screen().width() );
        final int worldHeight = configuration.readInt( "World.pixelSize.height", screen().height() );
        final World world = new World( worldWidth, worldHeight );

        myGameModel = new GameModel( configuration, world );
        myGameModel.onInitialize( system() );

        myCamera = new Camera( this );
        myHiscore = new Hiscore();

        mySharedGameDrawers = new MultiScreen();
        mySharedGameDrawers.addScreen( new ObjectsDrawer( this, "bomb", myGameModel.bombs.bombs ) );
        mySharedGameDrawers.addScreen( new ObjectsDrawer( this, "debris", myGameModel.debrises.debrises, 3 ) );
        mySharedGameDrawers.addScreen( new ObjectsDrawer( this, "gunshot", myGameModel.gunShots.gunShots ) );

        // TODO: Can EnemiesDrawer and MissilesDrawer be unified in the ObjectsDrawer? Like 'WorldObjectWithDirection'?
        // TODO: But what about the EnemyType? How to unify this?
        mySharedGameDrawers.addScreen( new EnemiesDrawer( this ) );
        mySharedGameDrawers.addScreen( new MissilesDrawer( this ) );

        mySharedGameDrawers.addScreen( new PlayerDrawer( this ) );
        mySharedGameDrawers.addScreen( new ObjectsDrawer( this, "satellite", myGameModel.satellites.satellites ) );
        mySharedGameDrawers.addScreen( new ObjectsDrawer( this, "smoke", myGameModel.smokes.smokes ) );
        mySharedGameDrawers.addScreen( new SparksDrawer( this ) );
        mySharedGameDrawers.addScreen( new ExtrasDrawer( this ) );
        mySharedGameDrawers.addScreen( new ObjectsDrawer( this, "explosion", myGameModel.explosions.explosions, 3 ) );
        mySharedGameDrawers.addScreen( new ScoreTagsDrawer( this ) );
        mySharedGameDrawers.addScreen( new InfoFlashDrawer( this ) );

        mySharedSoftkeys = new VerticalSoftkeysScreen( visualContext().textFont() );
        mySharedSoftkeys.setButtonImage( skin().image( "softkeys_vertical" ) );

        mySharedGameBackground = new MultiScreen();
        mySharedGameBackground.addScreen( new ClearScreen() );
        mySharedGameBackground.addScreen( new StarField( 32, skin().charGen( "stars" ) ) );
        mySharedGameBackground.addScreen( myCamera );
        mySharedGameBackground.addScreen( mySharedSoftkeys );
        mySharedGameBackground.addScreen( new ScoreboardDrawer( this ) );
        }

    private Configuration loadGameConfiguration()
        {
        try
            {
            return resources().loadConfiguration( "game.properties" );
            }
        catch ( final Exception e )
            {
            //#if DEBUG
            e.printStackTrace();
            //#endif
            return new Configuration();
            }
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
