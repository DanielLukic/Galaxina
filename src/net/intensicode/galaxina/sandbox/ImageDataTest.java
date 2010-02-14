package net.intensicode.galaxina.sandbox;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.GameContext;
import net.intensicode.graphics.*;
import net.intensicode.screens.*;

import java.io.IOException;

public final class ImageDataTest extends MultiScreen
    {
    public ImageDataTest( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();

        myGameContext.visualContext().sharedSoftkeys().setSoftkeys( "NEXT", "BACK", false );

        addScreen( new ImageScreen( skin.image( "background" ) ) );
        addScreen( myGameContext.visualContext().sharedSoftkeys() );

        switchImage( myImageIndex = 0 );
        }

    public final void onControlTick() throws Exception
        {
        super.onControlTick();

        final KeysHandler keys = keys();
        if ( keys.checkDownAndConsume() ) myFrameIndex = 0;
        if ( keys.checkUpAndConsume() ) myAutoStep = !myAutoStep;
        if ( keys.checkLeftAndConsume() ) myFrameIndex--;
        if ( keys.checkRightAndConsume() ) myFrameIndex++;
        if ( keys.checkLeftSoftAndConsume() ) nextImage();
        if ( keys.checkRightSoftAndConsume() ) stack().popScreen();

        if ( myAutoStep )
            {
            if ( myTickCounter < timing().ticksPerSecond / 8 ) myTickCounter++;
            else myTickCounter = 0;

            if ( myTickCounter == 0 ) myFrameIndex++;
            }

        final int frameCount = mySprite.getFrameSequenceLength();
        while ( myFrameIndex < 0 ) myFrameIndex += frameCount;
        myFrameIndex %= frameCount;
        }

    public final void onDrawFrame()
        {
        super.onDrawFrame();

        final DirectGraphics gc = graphics();

        final int yBlitPos = screen().height() / 2;
        final int xBlitPos1 = screen().width() / 4;
        final int xBlitPos2 = screen().width() * 3 / 4;

        mySprite.setFrame( myFrameIndex );
        mySprite.paint( gc, xBlitPos1, yBlitPos );

        myCharGen.blit( gc, xBlitPos2, yBlitPos, myFrameIndex );
        }

    // Implementation

    private void nextImage() throws IOException
        {
        final int imageIndex = ( myImageIndex + 1 ) % IMAGES.length;
        switchImage( imageIndex );
        }

    private void switchImage( final int aImageIndex ) throws IOException
        {
        if ( aImageIndex == 0 )
            {
            //final Image image = InMemoryImage.getFourFrames();
            //mySprite = new SpriteGenerator( image, 32, 32 );
            //myCharGen = CharGenerator.fromSize( image, 32, 32 );
            }
        else
            {
            final SkinManager skin = myGameContext.visualContext().skinManager();
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

    private SpriteGenerator mySprite;

    private CharGenerator myCharGen;

    private final GameContext myGameContext;

    private static final String[] IMAGES = { "test", "explosion1", "explosion2", "explosion3",
                                             "debris1", "debris2", "debris3",
                                             "ship", "smoke", "worm", "extras",
                                             "enemy1", "enemy2", "enemy3",
                                             "enemy4", "enemy5", "enemy6",
                                             "missile1", "missile2", "missile3", "missile4" };
    }
