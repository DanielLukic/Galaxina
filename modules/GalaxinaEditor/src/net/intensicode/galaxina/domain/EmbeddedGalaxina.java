/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.galaxina.domain;

import net.intensicode.ReloadAndSwitchHandler;
import net.intensicode.core.*;
import net.intensicode.game.GameController;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.util.Log;
import net.intensicode.util.Size;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import java.io.IOException;


/**
 * TODO: Describe this!
 */
public final class EmbeddedGalaxina extends MIDlet implements SystemContext
    {
    public EmbeddedGalaxina() throws Exception
        {
        myLoader = new ResourceLoader( getClass() );
        }

    public final void reloadGame() throws Exception
        {
        waitForHandler();
        myReloadHandler.reloadGame();
        }

    public final void switchToLevel( final int aLevelIndex ) throws Exception
        {
        waitForHandler();
        myReloadHandler.switchToLevel( aLevelIndex );
        }

    public final Engine engine() throws Exception
        {
        if ( myEngine == null ) init();
        return myEngine;
        }

    public final GameController controller() throws Exception
        {
        //#if DEBUG
        if ( myGameController == null ) throw new IllegalStateException();
        //#endif
        return myGameController;
        }

    public final void init() throws Exception
        {
        initConfiguration();
        initEngine();
        }

    // From SystemContext

    public final AbstractScreen initMainController() throws Exception
        {
        if ( myGameController == null )
            {
            final ResourceLoader loader = myEngine.loader;
            final Configuration skinConfig = loader.loadConfiguration( "/skin.properties" );
            final Skin skin = new Skin( loader, skinConfig );
            myGameController = new GameController( skin );

            myReloadHandler = new ReloadAndSwitchHandler( myGameController );
            myEngine.addGlobalHandler( myReloadHandler );

            synchronized ( this )
                {
                notifyAll();
                }
            }

        return myGameController;
        }

    // From MIDlet

    public final void startApp() throws MIDletStateChangeException
        {
        try
            {
            if ( myEngine == null ) init();
            myEngine.start();
            }
        catch ( final Exception e )
            {
            //#if DEBUG
            e.printStackTrace();
            //#endif

            throw new MIDletStateChangeException( e.toString() );
            }
        }

    public final void pauseApp()
        {
        myEngine.stop();
        notifyPaused();
        }

    public final void destroyApp( final boolean unconditional )
        {
        myEngine.stop();
        notifyDestroyed();
        }

    // From SystemContext

    public final void exit()
        {
        throw new RuntimeException( "nyi" );
        }

    public final void pause()
        {
        pauseApp();
        }

    public final Display getDisplay()
        {
        return Display.getDisplay( this );
        }

    public final ResourceLoader getResourceLoader()
        {
        return myLoader;
        }

    // Protected Interface

    protected void initConfiguration()
        {
        try
            {
            final byte[] data = myLoader.loadData( "/engine.properties" );
            myConfiguration = new Configuration( new String( data ) );
            }
        catch ( final IOException e )
            {
            //#if DEBUG
            Log.debug( "Failed loading '/engine.properties': {}", e );
            //#endif

            myConfiguration = new Configuration();
            }
        }

    protected void initEngine() throws Exception
        {
        Engine.limitFps = myConfiguration.readInt( "Engine.limitFps", Engine.limitFps );
        Engine.ticksPerSecond = myConfiguration.readInt( "Engine.ticksPerSecond", Engine.ticksPerSecond );
        Engine.limitTpsPerLoop = myConfiguration.readInt( "Engine.limitTpsPerLoop", Engine.limitTpsPerLoop );
        DirectScreen.scaleToFitPixelMargin = myConfiguration.readInt( "DirectScreen.scaleToFitPixelMargin", DirectScreen.scaleToFitPixelMargin );

        Engine.showStats = myConfiguration.readBoolean( "Engine.showStats", Engine.showStats );
        BitmapFontGen.buffered = myConfiguration.readBoolean( "BitmapFontGen.buffered", BitmapFontGen.buffered );

        final int width = myConfiguration.readInt( "DirectScreen.width", 176 );
        final int height = myConfiguration.readInt( "DirectScreen.height", 208 );
        //#if DEBUG
        Log.debug( "DirectScreen: {}x{}", width, height );
        //#endif

        myEngine = new Engine( this, new Size( width, height ) );
        }

    // Implementation

    private final void waitForHandler() throws InterruptedException
        {
        synchronized ( this )
            {
            while ( myReloadHandler == null ) wait();
            }
        }



    protected Engine myEngine;

    protected Configuration myConfiguration;

    private GameController myGameController;

    private ReloadAndSwitchHandler myReloadHandler;


    protected final ResourceLoader myLoader;
    }
