package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class ExtrasDrawer extends ScreenBase
    {
    public ExtrasDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        myExtraGen = skin().sprite( "extras" );

        final Size sizeInWorld = myGameContext.camera().toWorldSize( myExtraGen.getWidth(), myExtraGen.getHeight() );
        myGameContext.gameModel().extras.sizeInWorldFixed.setTo( sizeInWorld );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();
        final GameModel model = myGameContext.gameModel();
        drawExtras( graphics, model.extras.extras );
        }

    // Implementation

    private void drawExtras( final DirectGraphics aGraphics, final Extra[] aExtras )
        {
        for ( int idx = 0; idx < aExtras.length; idx++ )
            {
            final Extra extra = aExtras[ idx ];
            if ( extra == null || !extra.active ) continue;

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( extra.worldPosFixed );

            final int frame = extra.animTickCount * ( FRAMES_PER_EXTRA - 1 ) / ( extra.animTicks - 1 );
            final int maxId = myExtraGen.getFrameSequenceLength() / FRAMES_PER_EXTRA;
            final int id = extra.type.idForDrawer() % maxId;
            final int idOffset = id * FRAMES_PER_EXTRA;
            final int index = idOffset + frame;

            myExtraGen.paint( aGraphics, screenPos.x, screenPos.y, index );

            //#if DEBUG
            aGraphics.setColorARGB32( 0xFF00FFFF );
            aGraphics.fillRect( screenPos.x - 1, screenPos.y - 1, FRAMES_PER_EXTRA, FRAMES_PER_EXTRA );
            //#endif
            }
        }



    private SpriteGenerator myExtraGen;

    private final GameContext myGameContext;

    private static final int FRAMES_PER_EXTRA = 3;
    }
