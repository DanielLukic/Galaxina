package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.SimpleObject;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

public final class ObjectsDrawer extends ScreenBase
    {
    public ObjectsDrawer( final GameContext aGameContext, final String aImageId, final SimpleObject[] aObjects )
        {
        myGameContext = aGameContext;
        myImageId = aImageId;
        myObjects = aObjects;
        }

    public final void drawObjects( final DirectGraphics aGraphics )
        {
        final Camera camera = myGameContext.camera();

        final int animLength = mySpriteGen.getFrameSequenceLength();

        for ( int idx = 0; idx < myObjects.length; idx++ )
            {
            final SimpleObject object = myObjects[ idx ];
            if ( !object.active ) continue;

            final Position screenPos = camera.toScreen( object.worldPosFixed );
            final int frame = object.tickCount * ( animLength - 1 ) / ( object.animTicks - 1 );
            mySpriteGen.paint( aGraphics, screenPos.x, screenPos.y, frame );
            }
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        mySpriteGen = skin.sprite( myImageId );

        final Camera camera = myGameContext.camera();
        final Size sizeInWorld = camera.toWorldSize( mySpriteGen.getWidth(), mySpriteGen.getHeight() );

        for ( int idx = 0; idx < myObjects.length; idx++ )
            {
            final SimpleObject object = myObjects[ idx ];
            object.sizeInWorldFixed.setTo( sizeInWorld );
            }
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        drawObjects( graphics() );
        }


    private SpriteGenerator mySpriteGen;

    private final String myImageId;

    private final SimpleObject[] myObjects;

    private final GameContext myGameContext;
    }
