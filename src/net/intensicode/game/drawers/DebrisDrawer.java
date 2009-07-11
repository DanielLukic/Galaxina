package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Debris;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;


/**
 * TODO: Describe this!
 */
public final class DebrisDrawer extends AbstractScreen
    {
    public DebrisDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();

        myGenerators = new Sprite[3];

        for ( int idx = 0; idx < myGenerators.length; idx++ )
            {
            final StringBuffer imageName = new StringBuffer( "debris" );
            imageName.append( idx + 1 );
            myGenerators[ idx ] = skin.sprite( imageName.toString() );
            }
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();

        final Debris[] debrises = myGameContext.gameModel().debrises.debrises;
        for ( int idx = 0; idx < debrises.length; idx++ )
            {
            final Debris debris = debrises[ idx ];
            if ( debris == null || !debris.active ) continue;
            draw( graphics, debris );
            }
        }

    // Implementation

    private final void draw( final Graphics aGraphics, final Debris aDebris )
        {
        final Camera camera = myGameContext.camera();
        final Position screenPos = camera.toScreen( aDebris.worldPosFixed );

        final Sprite sprite = myGenerators[ aDebris.type ];
        final int steps = sprite.getFrameSequenceLength();
        final int step = aDebris.tickCount * ( steps - 1 ) / aDebris.animTicks;
        sprite.setFrame( step );
        sprite.setRefPixelPosition( screenPos.x, screenPos.y );
        sprite.paint( aGraphics );
        }



    private Sprite[] myGenerators;

    private final GameContext myGameContext;
    }
