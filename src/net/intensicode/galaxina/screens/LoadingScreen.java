package net.intensicode.galaxina.screens;

import net.intensicode.core.*;
import net.intensicode.graphics.*;
import net.intensicode.screens.*;

public final class LoadingScreen extends MultiScreen
    {
    public LoadingScreen( final BitmapFontGenerator aFontGenerator )
        {
        myFontGenerator = aFontGenerator;
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

    public final void setLogoAnim( final SpriteGenerator aLogoAnim )
        {
        myLogoAnim = aLogoAnim;
        }

    public final void setStartSound( final AudioResource aStartSound )
        {
        myStartSound = aStartSound;
        }

    public void setDelayInTicks( final int aDelayInTicks )
        {
        if ( aDelayInTicks < 0 ) throw new IllegalArgumentException();
        myDelayInTicks = aDelayInTicks;
        }

    // From ScreenBase

    public void onInitEverytime() throws Exception
        {
        myWaitTicks = myDelayInTicks == -1 ? timing().ticksPerSecond * 5 : myDelayInTicks;
        myAnimFrame = -1;
        myAnimTick = 0;
        }

    public final void onInitOnce() throws Exception
        {
        addScreen( new ClearScreen() );

        final AlignedTextScreen loading = new AlignedTextScreen( myFontGenerator, I18n._( "LOADING" ) );
        loading.position.x = screen().width() / 2;
        loading.position.y = screen().height() * 6 / 8;
        addScreen( loading );

        addScreen( mySoftkeys );
        }

    public void onPop()
        {
        if ( myLogoAnim == null ) return;

        final SpriteGenerator logoAnim = myLogoAnim;
        myLogoAnim = null;
        skin().purgeImage( logoAnim, true );
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        if ( myWaitTicks > 0 ) myWaitTicks--;

        switch ( myState )
            {
            case ANIMATING_LOGO:
                if ( myAnimTick == 0 && myStartSound != null ) myStartSound.start();
                if ( myAnimTick < timing().ticksPerSecond )
                    {
                    myAnimTick++;
                    }
                else
                    {
                    myState = LOADING_IMAGES;
                    }
                if ( myLogoAnim != null )
                    {
                    final int animSteps = myLogoAnim.getFrameSequenceLength();
                    myAnimFrame = myAnimTick * ( animSteps - 1 ) / timing().ticksPerSecond;
                    }
                break;
            case LOADING_IMAGES:
                if ( skin().allImagesLoaded() ) myState = LATE_INIT;
                Thread.yield();
                break;
            case LATE_INIT:
                myCallback.onLoadingDone( system() );
                myState = POP_SCREEN;
                break;
            default:
            case POP_SCREEN:
                if ( myWaitTicks == 0 ) stack().popScreen( this );

                if ( keys().checkLeftSoftAndConsume() )
                    {
                    if ( myStartSound != null ) myStartSound.stop();
                    stack().popScreen( this );
                    }
                break;
            }

        if ( myState == POP_SCREEN )
            {
            mySoftkeys.setSoftkeys( I18n._( "SKIP" ), I18n._( "EXIT" ) );
            }
        else
            {
            mySoftkeys.setSoftkeys( "", I18n._( "EXIT" ) );
            }

        if ( keys().checkRightSoftAndConsume() )
            {
            system().shutdownAndExit();
            }
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        if ( myLogoAnim == null || myAnimFrame == -1 ) return;

        final int x = screen().width() / 2;
        final int y = screen().height() / 3;
        myLogoAnim.setFrame( myAnimFrame );
        myLogoAnim.paint( graphics(), x, y );
        }


    private int myState = ANIMATING_LOGO;


    private int myAnimTick;

    private int myAnimFrame;

    private int myWaitTicks;

    private int myDelayInTicks = -1;

    private SpriteGenerator myLogoAnim;

    private AudioResource myStartSound;

    private SoftkeysScreen mySoftkeys;

    private LoadingCallback myCallback;

    private final BitmapFontGenerator myFontGenerator;


    private static final int ANIMATING_LOGO = 0;

    private static final int LOADING_IMAGES = 1;

    private static final int LATE_INIT = 2;

    private static final int POP_SCREEN = 3;
    }
