package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Extra;
import net.intensicode.game.objects.GameModel;
import net.intensicode.util.Position;
import net.intensicode.util.Size;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;



/**
 * TODO: Describe this!
 */
public final class ExtrasDrawer extends AbstractScreen
    {
    public ExtrasDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();
        myExtraGen = skin.sprite( "extras" );

        final Size sizeInWorld = myGameContext.camera().toWorldSize( myExtraGen.getWidth(), myExtraGen.getHeight() );
        myGameContext.gameModel().extras.sizeInWorldFixed.setTo( sizeInWorld );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();
        final GameModel model = myGameContext.gameModel();
        drawExtras( graphics, model.extras.extras );
        }

    // Implementation

    private final void drawExtras( final Graphics aGraphics, final Extra[] aExtras )
        {
        for ( int idx = 0; idx < aExtras.length; idx++ )
            {
            final Extra extra = aExtras[ idx ];
            if ( extra == null || extra.active == false ) continue;

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( extra.worldPosFixed );
            myExtraGen.setRefPixelPosition( screenPos.x, screenPos.y );
            myExtraGen.paint( aGraphics );

            final int frame = extra.animTickCount * 3 / extra.animTicks;
            final int maxID = myExtraGen.getRawFrameCount() / 3;
            final int id = extra.type.id % maxID;
            myExtraGen.setFrame( id * 3 + frame );
            }
        }



    private Sprite myExtraGen;

    private final GameContext myGameContext;
    }
