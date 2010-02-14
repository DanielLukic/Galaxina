package net.intensicode.galaxina.sandbox;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.screens.*;

import java.io.IOException;

public final class SandboxContext implements GameContext, VisualContext
    {
    public SandboxContext( final SystemContext aSystemContext, final SkinManager aSkin ) throws Exception
        {
        mySystemContext = aSystemContext;
        mySkin = aSkin;

        mySoftkeys = new AutohideSoftkeysScreen( softkeysFont() );
        }

    // From GameContext

    public Camera camera()
        {
        return myCamera;
        }

    public Hiscore hiscore()
        {
        return null;
        }

    public GameModel gameModel()
        {
        return myGameModel;
        }

    public VisualContext visualContext()
        {
        return this;
        }

    public void startGame() throws Exception
        {
        }

    public void pauseGame() throws Exception
        {
        }

    public void showMainMenu() throws Exception
        {
        }

    public void showHelp() throws Exception
        {
        }

    public void showHiscore() throws Exception
        {
        }

    public void showOptions() throws Exception
        {
        }

    public void exit() throws Exception
        {
        mySystemContext.terminateApplication();
        }

    // From VisualContext

    public final SkinManager skinManager()
        {
        return mySkin;
        }

    public final ScreenBase sharedStars()
        {
        return null;
        }

    public final ScreenBase sharedBackground()
        {
        return null;
        }

    public final ScreenBase sharedGameBackground()
        {
        return null;
        }

    public final ScreenBase sharedGameDrawers()
        {
        return null;
        }

    public final AutohideSoftkeysScreen sharedSoftkeys()
        {
        return mySoftkeys;
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



    private AutohideSoftkeysScreen mySoftkeys;

    private final SkinManager mySkin;

    private final SystemContext mySystemContext;

    private final World myWorld = new World( 240, 320 );

    private final GameModel myGameModel = new GameModel( myWorld );

    private final Camera myCamera = new Camera( this );
    }
