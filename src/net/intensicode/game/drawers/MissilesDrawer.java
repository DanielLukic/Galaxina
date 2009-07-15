package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Missile;
import net.intensicode.util.UtilitiesEx;
import net.intensicode.util.FixedMath;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;


/**
 * TODO: Describe this!
 */
public final class MissilesDrawer extends AbstractScreen
    {
    public MissilesDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();

        myGenerators = new Sprite[4];

        for ( int idx = 0; idx < myGenerators.length; idx++ )
            {
            final StringBuffer imageName = new StringBuffer( "missile" );
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

        final Missile[] missiles = myGameContext.gameModel().missiles.missiles;
        for ( int idx = 0; idx < missiles.length; idx++ )
            {
            final Missile missile = missiles[ idx ];
            if ( missile == null || !missile.active || !missile.visible ) continue;
            draw( graphics, missile );
            }
        }

    // Implementation

    private final void draw( final Graphics aGraphics, final Missile aMissile )
        {
        final Camera camera = myGameContext.camera();
        final Position screenPos = camera.toScreen( aMissile.worldPosFixed );

        final Sprite sprite = myGenerators[ aMissile.type ];
        final int directionSteps = sprite.getFrameSequenceLength();
        final int directinFixed = UtilitiesEx.directionToDegrees( aMissile.directionFixed );
        final int direction = FixedMath.toIntRounded( directinFixed );
        final int directionIndex = UtilitiesEx.getDirectionID( direction + 45, directionSteps );
        sprite.setFrame( directionIndex % directionSteps );
        sprite.setRefPixelPosition( screenPos.x, screenPos.y );
        sprite.paint( aGraphics );
        }



    private Sprite[] myGenerators;

    private final GameContext myGameContext;
    }
