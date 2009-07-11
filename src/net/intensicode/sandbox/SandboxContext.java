/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.sandbox;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.Skin;
import net.intensicode.core.SystemContext;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.Hiscore;
import net.intensicode.game.VisualContext;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.World;
import net.intensicode.screens.AutoHideSoftkeysScreen;
import net.intensicode.util.BitmapFontGen;

import java.io.IOException;


/**
 * TODO: Describe this!
 */
public final class SandboxContext implements GameContext, VisualContext
    {
    public SandboxContext( final SystemContext aSystemContext, final Skin aSkin ) throws Exception
        {
        mySystemContext = aSystemContext;
        mySkin = aSkin;

        mySoftkeys = new AutoHideSoftkeysScreen( softkeysFont() );
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
        mySystemContext.exit();
        }

    // From VisualContext

    public final Skin skin()
        {
        return mySkin;
        }

    public final AbstractScreen sharedStars()
        {
        return null;
        }

    public final AbstractScreen sharedBackground()
        {
        return null;
        }

    public final AbstractScreen sharedGameBackground()
        {
        return null;
        }

    public final AbstractScreen sharedGameDrawers()
        {
        return null;
        }

    public final AutoHideSoftkeysScreen sharedSoftkeys()
        {
        return mySoftkeys;
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



    private AutoHideSoftkeysScreen mySoftkeys;

    private final Skin mySkin;

    private final SystemContext mySystemContext;

    private final World myWorld = new World( 240, 320 );

    private final GameModel myGameModel = new GameModel( myWorld );

    private final Camera myCamera = new Camera( this );
    }