package net.intensicode.game.drawers;

import net.intensicode.core.*;
import net.intensicode.game.*;
import net.intensicode.game.objects.*;
import net.intensicode.graphics.SpriteGenerator;
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
        final SkinManager skin = myGameContext.visualContext().skinManager();
        myExtraGen = skin.sprite( "extras" );

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
            myExtraGen.paint( aGraphics, screenPos.x, screenPos.y );

            final int frame = extra.animTickCount * 3 / extra.animTicks;
            final int maxID = myExtraGen.getRawFrameCount() / 3;
            final int id = extra.type.id % maxID;
            myExtraGen.setFrame( id * 3 + frame );
            }
        }



    private SpriteGenerator myExtraGen;

    private final GameContext myGameContext;
    }
