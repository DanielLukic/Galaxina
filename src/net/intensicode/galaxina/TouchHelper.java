//#condition TOUCH_SUPPORTED

package net.intensicode.galaxina;

import net.intensicode.core.*;
import net.intensicode.util.Position;

import java.io.IOException;

public final class TouchHelper
    {
    public TouchHelper( final GameSystem aGameSystem, final VisualConfiguration aVisualConfiguration )
        {
        myGameSystem = aGameSystem;
        myVisualConfiguration = aVisualConfiguration;
        }

    public void createTouchControls() throws IOException
        {
        touch().globalControlsActive = false;

        final VisualConfiguration config = myVisualConfiguration;
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

    private SkinManager skin()
        {
        if ( myGameSystem.skin == null ) throw new IllegalStateException();
        return myGameSystem.skin;
        }

    private DirectScreen screen()
        {
        if ( myGameSystem.screen == null ) throw new IllegalStateException();
        return myGameSystem.screen;
        }

    private TouchHandler touch()
        {
        if ( myGameSystem.touch == null ) throw new IllegalStateException();
        return myGameSystem.touch;
        }


    private Touchable myTouchPrimaryFire;

    private Touchable myTouchSecondaryFire;

    private Touchable myTouchUp;

    private Touchable myTouchLeft;

    private Touchable myTouchRight;

    private Touchable myTouchDown;

    private final GameSystem myGameSystem;

    private final VisualConfiguration myVisualConfiguration;
    }
