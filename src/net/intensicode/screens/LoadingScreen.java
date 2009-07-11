package net.intensicode.screens;

import net.intensicode.core.*;
import net.intensicode.util.FontGen;

import java.io.IOException;

/**
 * TODO: Describe this!
 */
public final class LoadingScreen extends MultiScreen
    {
    public LoadingScreen( final Skin aSkin, final FontGen aTextFont ) throws IOException
        {
        mySkin = aSkin;
        myTextFont = aTextFont;
        }

    public final LoadingScreen setLogo( final AbstractScreen aLogoScreen )
        {
        myLogoScreen = aLogoScreen;
        return this;
        }

    public final LoadingScreen shareSoftkeys( final SoftkeysScreen aSoftkeys )
        {
        mySoftkeys = aSoftkeys;
        return this;
        }

    public final LoadingScreen setLateInit( final LoadingCallback aCallback )
        {
        myCallback = aCallback;
        return this;
        }

    // From AbstractScreen

    public void onInitEverytime( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        myWaitTicks = Engine.ticksPerSecond * SECONDS_TO_WAIT;
        }

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        addScreen( new ColorScreen( 0 ) );

        if ( myLogoScreen != null ) addScreen( myLogoScreen );

        final AlignedTextScreen loading = new AlignedTextScreen( myTextFont, I18n._( "LOADING" ) );
        loading.position.x = aScreen.width() / 2;
        loading.position.y = aScreen.height() / 8;
        addScreen( loading );

        addScreen( mySoftkeys );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        super.onControlTick( aEngine );

        if ( myWaitTicks > 0 ) myWaitTicks--;

        if ( mySkin.allImagesLoaded() )
            {
            myCallback.onLoadingDone( engine(), screen() );
            if ( myWaitTicks == 0 ) aEngine.popScreen( this );
            }

        if ( aEngine.keys.checkRightSoftAndConsume() )
            {
            aEngine.shutdownAndExit();
            }

        mySoftkeys.setSoftkeys( "", I18n._( "EXIT" ) );
        }



    private int myWaitTicks;

    private SoftkeysScreen mySoftkeys;

    private LoadingCallback myCallback;

    private AbstractScreen myLogoScreen;

    private final Skin mySkin;

    private final FontGen myTextFont;

    private static final int SECONDS_TO_WAIT = 2;
    }
