package net.intensicode.game;

import net.intensicode.core.ImageResource;
import net.intensicode.core.SkinManager;
import net.intensicode.game.drawers.*;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.World;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.screens.*;
import net.intensicode.util.Log;

import java.io.IOException;

public final class GameController extends ScreenBase implements GameContext, VisualContext
    {
    public GameController( final SkinManager aSkin )
        {
        mySkin = aSkin;
        }

    // From VisualContext

    public final SkinManager skinManager()
        {
        return mySkin;
        }

    public final ScreenBase sharedStars()
        {
        return mySharedStars;
        }

    public final ScreenBase sharedBackground()
        {
        return mySharedBackground;
        }

    public final ScreenBase sharedGameBackground()
        {
        return mySharedGameBackground;
        }


    public final ScreenBase sharedGameDrawers()
        {
        return mySharedGameDrawers;
        }

    public final AutohideSoftkeysScreen sharedSoftkeys()
        {
        return mySharedSoftkeys;
        }

    public BitmapFontGenerator menuFont() throws IOException
        {
        return mySkin.font( "menufont" );
        }

    public BitmapFontGenerator softkeysFont() throws IOException
        {
        return mySkin.font( "textfont" );
        }

    public BitmapFontGenerator textFont() throws IOException
        {
        return mySkin.font( "textfont" );
        }

    public BitmapFontGenerator titleFont() throws IOException
        {
        return mySkin.font( "menufont" );
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
        return this;
        }

    public final void showMainMenu() throws Exception
        {
        disposeScreens();
        if ( myMainMenuScreen == null ) myMainMenuScreen = new MainMenuScreen( this );
        stack().pushOnce( myMainMenuScreen );
        }

    public final void showHelp() throws Exception
        {
        disposeScreens();
        if ( myHelpScreen == null ) myHelpScreen = new HelpScreen( this );
        stack().pushOnce( myHelpScreen );
        }

    public final void showHiscore() throws Exception
        {
        throw new RuntimeException( "NYI" );
        }

    public final void showOptions() throws Exception
        {
        throw new RuntimeException( "NYI" );
        }

    public final void startGame() throws Exception
        {
        disposeScreens();
        if ( myGameScreen == null ) myGameScreen = new GameScreen( this );
        stack().pushOnce( myGameScreen );
        myGameModel.startGame();
        }

    public final void pauseGame() throws Exception
        {
        disposeScreens();
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

        mySharedStars = SimpleStars.instance();
        mySharedSoftkeys = new AutohideSoftkeysScreen( softkeysFont() );

        final ImageResource background = mySkin.image( "background" );
        mySharedBackground = new ImageScreen( background, 50, 50, ImageScreen.MODE_RELATIVE );
        mySharedBackground.clearOutside = true;

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
        mySharedGameBackground.addScreen( mySharedBackground );
        mySharedGameBackground.addScreen( new SimpleStars( 32 ) );
        mySharedGameBackground.addScreen( myCamera );
        mySharedGameBackground.addScreen( mySharedSoftkeys );
        mySharedGameBackground.addScreen( new ScoreboardDrawer( this ) );
        }

    public final void onControlTick() throws Exception
        {
        showMainMenu();
        }

    public final void onDrawFrame()
        {
        }

    // Implementation

    private void disposeScreens()
        {
        //#if DEBUG
        Log.debug( "TODO: DISPOSE SCREENS IF MEM LOW" );
        //#endif
        }



    private HelpScreen myHelpScreen;

    private GameScreen myGameScreen;

    private MainMenuScreen myMainMenuScreen;

    private GamePausedScreen myGamePausedScreen;


    private Camera myCamera;

    private Hiscore myHiscore;

    private GameModel myGameModel;

    private SimpleStars mySharedStars;

    private ImageScreen mySharedBackground;

    private MultiScreen mySharedGameDrawers;

    private MultiScreen mySharedGameBackground;

    private AutohideSoftkeysScreen mySharedSoftkeys;


    private final SkinManager mySkin;
    }
