package net.intensicode.screens;

import net.intensicode.core.*;
import net.intensicode.graphics.FontGenerator;

import java.io.IOException;

public final class LoadingScreen extends MultiScreen
    {
    public LoadingScreen( final SkinManager aSkin, final FontGenerator aTextFont ) throws IOException
        {
        mySkin = aSkin;
        myTextFont = aTextFont;
        }

    public final LoadingScreen setLogo( final ScreenBase aLogoScreen )
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

    // From ScreenBase

    public void onInitEverytime() throws Exception
        {
        myWaitTicks = timing().ticksPerSecond * SECONDS_TO_WAIT;
        }

    public final void onInitOnce() throws Exception
        {
        addScreen( new ClearScreen() );

        if ( myLogoScreen != null ) addScreen( myLogoScreen );

        final AlignedTextScreen loading = new AlignedTextScreen( myTextFont, I18n._( "LOADING" ) );
        loading.position.x = screen().width() / 2;
        loading.position.y = screen().height() / 8;
        addScreen( loading );

        addScreen( mySoftkeys );
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        if ( myWaitTicks > 0 ) myWaitTicks--;

        if ( mySkin.allImagesLoaded() )
            {
            myCallback.onLoadingDone( system() );
            if ( myWaitTicks == 0 ) stack().popScreen( this );
            }

        if ( keys().checkRightSoftAndConsume() )
            {
            system().shutdownAndExit();
            }

        mySoftkeys.setSoftkeys( "", I18n._( "EXIT" ) );
        }



    private int myWaitTicks;

    private SoftkeysScreen mySoftkeys;

    private LoadingCallback myCallback;

    private ScreenBase myLogoScreen;

    private final SkinManager mySkin;

    private final FontGenerator myTextFont;

    private static final int SECONDS_TO_WAIT = 2;
    }
