package net.intensicode.game;

import net.intensicode.core.*;
import net.intensicode.game.drawers.*;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.World;
import net.intensicode.screens.*;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.Log;

import javax.microedition.lcdui.Image;
import java.io.IOException;


/**
 * TODO: Describe this!
 */
public final class GameController extends AbstractScreen implements GameContext, VisualContext
    {
    public GameController( final Skin aSkin )
        {
        mySkin = aSkin;
        }

    // From VisualContext

    public final Skin skin()
        {
        return mySkin;
        }

    public final AbstractScreen sharedStars()
        {
        return mySharedStars;
        }

    public final AbstractScreen sharedBackground()
        {
        return mySharedBackground;
        }

    public final AbstractScreen sharedGameBackground()
        {
        return mySharedGameBackground;
        }


    public final AbstractScreen sharedGameDrawers()
        {
        return mySharedGameDrawers;
        }

    public final AutoHideSoftkeysScreen sharedSoftkeys()
        {
        return mySharedSoftkeys;
        }

    public BitmapFontGen menuFont() throws IOException
        {
        return mySkin.font( "menufont" );
        }

    public BitmapFontGen softkeysFont() throws IOException
        {
        return mySkin.font( "textfont" );
        }

    public BitmapFontGen textFont() throws IOException
        {
        return mySkin.font( "textfont" );
        }

    public BitmapFontGen titleFont() throws IOException
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
        engine().pushOnce( myMainMenuScreen );
        }

    public final void showHelp() throws Exception
        {
        disposeScreens();
        if ( myHelpScreen == null ) myHelpScreen = new HelpScreen( this );
        engine().pushOnce( myHelpScreen );
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
        engine().pushOnce( myGameScreen );
        myGameModel.startGame();
        }

    public final void pauseGame() throws Exception
        {
        disposeScreens();
        if ( myGamePausedScreen == null ) myGamePausedScreen = new GamePausedScreen( this );
        engine().pushOnce( myGamePausedScreen );
        }

    public final void exit() throws Exception
        {
        engine().shutdownAndExit();
        }

    // From AbstractScreen

    public void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final World world = new World( aScreen.width(), aScreen.height() );
        myGameModel = new GameModel( world );

        myCamera = new Camera( this );
        myHiscore = new Hiscore();

        mySharedStars = SimpleStars.instance();
        mySharedSoftkeys = new AutoHideSoftkeysScreen( softkeysFont() );

        final Image background = mySkin.image( "background" );
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

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        showMainMenu();
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        }

    // Implementation

    private final void disposeScreens()
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

    private AutoHideSoftkeysScreen mySharedSoftkeys;


    private final Skin mySkin;
    }
