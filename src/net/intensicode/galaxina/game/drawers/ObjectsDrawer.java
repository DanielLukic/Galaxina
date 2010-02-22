package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.*;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.*;

import java.io.IOException;

public final class ObjectsDrawer extends ScreenBase
    {
    public ObjectsDrawer( final GameContext aGameContext, final String aImageId, final WorldObject[] aObjects )
        {
        myGameContext = aGameContext;
        myImageId = aImageId;
        myObjects = aObjects;
        }

    public ObjectsDrawer( final GameContext aGameContext, final String aImageId, final WorldObject[] aObjects, final int aNumberOfTypes )
        {
        myGameContext = aGameContext;
        myImageId = aImageId;
        myObjects = aObjects;
        myNumberOfTypes = aNumberOfTypes;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();

        if ( myNumberOfTypes > 0 ) createSpriteGenerators();
        else myGenerator = skin.sprite( myImageId );

        final Camera camera = myGameContext.camera();
        final Size sizeInWorld = camera.toWorldSize( myGenerator.getWidth(), myGenerator.getHeight() );

        for ( int idx = 0; idx < myObjects.length; idx++ )
            {
            if ( !( myObjects[ idx ] instanceof WorldObjectWithSize ) ) continue;
            final WorldObjectWithSize object = (WorldObjectWithSize) myObjects[ idx ];
            object.sizeInWorldFixed.setTo( sizeInWorld );
            }

        myCamera = myGameContext.camera();
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        myGraphics = graphics();

        if ( myGeneratorsByType != null )
            {
            for ( int idx = 0; idx < myObjects.length; idx++ )
                {
                final WorldObjectWithType object = (WorldObjectWithType) myObjects[ idx ];
                if ( object == null || !object.active ) continue;
                drawObject( object, myGeneratorsByType[ object.type ] );
                }
            }
        else
            {
            for ( int idx = 0; idx < myObjects.length; idx++ )
                {
                final WorldObject object = myObjects[ idx ];
                if ( object == null || !object.active ) continue;
                drawObject( object, myGenerator );
                }
            }
        }

    // Implementation

    private void createSpriteGenerators() throws IOException
        {
        myGeneratorsByType = new SpriteGenerator[myNumberOfTypes];

        final StringBuffer imageName = new StringBuffer();
        for ( int idx = 0; idx < myNumberOfTypes; idx++ )
            {
            imageName.setLength( 0 );
            imageName.append( myImageId );
            imageName.append( idx + 1 );
            myGeneratorsByType[ idx ] = skin().sprite( imageName.toString() );
            }

        myGenerator = myGeneratorsByType[ 0 ];
        }

    private void drawObject( final WorldObject aObject, final SpriteGenerator aGenerator )
        {
        final int animLength = myGenerator.getFrameSequenceLength();

        final Position screenPos = myCamera.toScreen( aObject.worldPosFixed );
        if ( aGenerator.getRawFrameCount() > 1 )
            {
            final int frame = aObject.tickCount * ( animLength - 1 ) / ( aObject.animTicks - 1 );
            aGenerator.paint( myGraphics, screenPos.x, screenPos.y, frame );
            }
        else
            {
            aGenerator.paint( myGraphics, screenPos.x, screenPos.y );
            }

        //#if DEBUG
        myGraphics.setColorARGB32( 0x8000FFFF );
        myGraphics.fillRect( screenPos.x - 1, screenPos.y - 1, 3, 3 );
        //#endif
        }


    private Camera myCamera;

    private int myNumberOfTypes;

    private DirectGraphics myGraphics;

    private SpriteGenerator myGenerator;

    private SpriteGenerator[] myGeneratorsByType;

    private final String myImageId;

    private final WorldObject[] myObjects;

    private final GameContext myGameContext;
    }
