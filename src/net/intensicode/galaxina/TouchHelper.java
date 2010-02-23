//#condition TOUCH_SUPPORTED

package net.intensicode.galaxina;

import net.intensicode.core.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;
import net.intensicode.galaxina.screens.GalaxinaGameScreen;

import java.io.IOException;

public final class TouchHelper extends ScreenBase
    {
    public TouchHelper( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        createTouchControls();
        }

    public final void onControlTick() throws Exception
        {
        final boolean gameActive = stack().activeScreen() instanceof GalaxinaGameScreen;
        touch().globalControlsActive = gameActive;
        }

    public final void onDrawFrame()
        {
        }

    // Implementation

    private void createTouchControls() throws IOException
        {
        //#if DEBUG
        Log.debug( "creating global touch controls" );
        //#endif

        final VisualConfiguration config = myMainContext.visualContext().configuration();
        if ( config.touchButtonImages )
            {
            myTouchPrimaryFire = makeTouchableImage( "touchPrimaryFire", config.touchPrimaryFire, KeysHandler.FIRE1 );
            myTouchSecondaryFire = makeTouchableImage( "touchSecondaryFire", config.touchSecondaryFire, KeysHandler.FIRE2 );
            if ( config.touchShowArrows )
                {
                myTouchUp = makeTouchableImage( "touchUp", config.touchUp, KeysHandler.UP );
                myTouchDown = makeTouchableImage( "touchDown", config.touchDown, KeysHandler.DOWN );
                myTouchLeft = makeTouchableImage( "touchLeft", config.touchLeft, KeysHandler.LEFT );
                myTouchRight = makeTouchableImage( "touchRight", config.touchRight, KeysHandler.RIGHT );
                }
            }
        else
            {
            final int touchButtonSize = config.touchButtonSize;
            myTouchPrimaryFire = makeTouchableArea( touchButtonSize, config.touchPrimaryFire, KeysHandler.FIRE1 );
            myTouchSecondaryFire = makeTouchableArea( touchButtonSize, config.touchSecondaryFire, KeysHandler.FIRE2 );
            if ( config.touchShowArrows )
                {
                myTouchUp = makeTouchableArea( touchButtonSize, config.touchUp, KeysHandler.UP );
                myTouchDown = makeTouchableArea( touchButtonSize, config.touchDown, KeysHandler.DOWN );
                myTouchLeft = makeTouchableArea( touchButtonSize, config.touchLeft, KeysHandler.LEFT );
                myTouchRight = makeTouchableArea( touchButtonSize, config.touchRight, KeysHandler.RIGHT );
                }
            }

        // Enable key repeat functionality by emulating real key behavior:
        myTouchPrimaryFire.triggerMode = Touchable.TRIGGER_ON_DOWN;
        myTouchPrimaryFire.releaseMode = Touchable.RELEASE_ON_UP;
        myTouchSecondaryFire.triggerMode = Touchable.TRIGGER_ON_DOWN;
        myTouchSecondaryFire.releaseMode = Touchable.RELEASE_ON_UP;

        touch().setGlobalControlsBlending( config.touchButtonAlpha );
        }

    // Implementation

    private TouchableImage makeTouchableImage( final String aImageID, final Position aPosition, final int aKeyID ) throws IOException
        {
        final SkinManager skin = skin();

        final TouchableImage aTouchable = new TouchableImage();
        aTouchable.image = skin.image( aImageID );
        aTouchable.associatedKeyID = aKeyID;
        aTouchable.position.setTo( aPosition );
        aTouchable.imageAlignment = DirectGraphics.ALIGN_TOP_LEFT;
        aTouchable.updateTouchableRect();

        touch().addGlobalControl( aTouchable );

        return aTouchable;
        }

    private TouchableArea makeTouchableArea( final int aSize, final Position aPosition, final int aKeyID )
        {
        final TouchableArea aTouchable = new TouchableArea();
        aTouchable.associatedKeyID = aKeyID;
        aTouchable.rectangle.x = aPosition.x;
        aTouchable.rectangle.y = aPosition.y;
        aTouchable.rectangle.width = aSize;
        aTouchable.rectangle.height = aSize;

        touch().addGlobalControl( aTouchable );

        return aTouchable;
        }


    private Touchable myTouchPrimaryFire;

    private Touchable myTouchSecondaryFire;

    private Touchable myTouchUp;

    private Touchable myTouchLeft;

    private Touchable myTouchRight;

    private Touchable myTouchDown;

    private final MainContext myMainContext;
    }
