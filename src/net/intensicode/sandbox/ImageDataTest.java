/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.sandbox;

import net.intensicode.core.*;
import net.intensicode.game.GameContext;
import net.intensicode.screens.ImageScreen;
import net.intensicode.util.CharGen;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import java.io.IOException;


/**
 * TODO: Describe this!
 */
public final class ImageDataTest extends MultiScreen
    {
    public ImageDataTest( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();

        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "NEXT", "BACK", false );

        addScreen( new ImageScreen( skin.image( "background" ) ) );
        addScreen( myGameContext.visualContext().sharedSoftkeys() );

        switchImage( myImageIndex = 0 );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        super.onControlTick( aEngine );

        final Keys keys = aEngine.keys;
        if ( keys.checkDownAndConsume() ) myFrameIndex = 0;
        if ( keys.checkUpAndConsume() ) myAutoStep = !myAutoStep;
        if ( keys.checkLeftAndConsume() ) myFrameIndex--;
        if ( keys.checkRightAndConsume() ) myFrameIndex++;
        if ( keys.checkLeftSoftAndConsume() ) nextImage();
        if ( keys.checkRightSoftAndConsume() ) aEngine.popScreen();

        if ( myAutoStep )
            {
            if ( myTickCounter < Engine.ticksPerSecond / 8 ) myTickCounter++;
            else myTickCounter = 0;

            if ( myTickCounter == 0 ) myFrameIndex++;
            }

        final int frameCount = mySprite.getFrameSequenceLength();
        while ( myFrameIndex < 0 ) myFrameIndex += frameCount;
        myFrameIndex %= frameCount;
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        super.onDrawFrame( aScreen );

        final Graphics gc = aScreen.graphics();

        final int yBlitPos = aScreen.height() / 2;
        final int xBlitPos1 = aScreen.width() / 4;
        final int xBlitPos2 = aScreen.width() * 3 / 4;

        mySprite.setFrame( myFrameIndex );
        mySprite.setRefPixelPosition( xBlitPos1, yBlitPos );
        mySprite.paint( gc );

        myCharGen.blit( gc, xBlitPos2, yBlitPos, myFrameIndex );
        }

    // Implementation

    private final void nextImage() throws IOException
        {
        final int imageIndex = ( myImageIndex + 1 ) % IMAGES.length;
        switchImage( imageIndex );
        }

    private final void switchImage( final int aImageIndex ) throws IOException
        {
        if ( aImageIndex == 0 )
            {
            final Image image = InMemoryImage.getFourFrames();
            mySprite = new Sprite( image, 32, 32 );
            myCharGen = CharGen.fromSize( image, 32, 32 );
            }
        else
            {
            final Skin skin = myGameContext.visualContext().skin();
            final String image = IMAGES[ aImageIndex ];
            mySprite = skin.sprite( image );
            myCharGen = skin.charGen( image );
            myImageIndex = aImageIndex;
            }
        }



    private int myFrameIndex;

    private int myImageIndex;

    private int myTickCounter;

    private boolean myAutoStep;

    private Sprite mySprite;

    private CharGen myCharGen;

    private final GameContext myGameContext;

    private static final String[] IMAGES = { "test", "explosion1", "explosion2", "explosion3",
                                             "debris1", "debris2", "debris3",
                                             "ship", "smoke", "worm", "extras",
                                             "enemy1", "enemy2", "enemy3",
                                             "enemy4", "enemy5", "enemy6",
                                             "missile1", "missile2", "missile3", "missile4" };
    }
