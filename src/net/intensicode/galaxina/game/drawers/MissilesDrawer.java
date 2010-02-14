package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.Missile;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.galaxina.util.*;
import net.intensicode.util.*;

public final class MissilesDrawer extends ScreenBase
    {
    public MissilesDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();

        myGenerators = new SpriteGenerator[4];

        for ( int idx = 0; idx < myGenerators.length; idx++ )
            {
            final StringBuffer imageName = new StringBuffer( "missile" );
            imageName.append( idx + 1 );
            myGenerators[ idx ] = skin.sprite( imageName.toString() );
            }
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();

        final Missile[] missiles = myGameContext.gameModel().missiles.missiles;
        for ( int idx = 0; idx < missiles.length; idx++ )
            {
            final Missile missile = missiles[ idx ];
            if ( missile == null || !missile.active || !missile.visible ) continue;
            draw( graphics, missile );
            }
        }

    // Implementation

    private void draw( final DirectGraphics aGraphics, final Missile aMissile )
        {
        final Camera camera = myGameContext.camera();
        final Position screenPos = camera.toScreen( aMissile.worldPosFixed );

        final SpriteGenerator sprite = myGenerators[ aMissile.type ];
        final int directionSteps = sprite.getFrameSequenceLength();
        final int directinFixed = UtilitiesEx.directionToDegrees( aMissile.directionFixed );
        final int direction = FixedMath.toIntRounded( directinFixed );
        final int directionIndex = UtilitiesEx.getDirectionID( direction + 45, directionSteps );
        sprite.setFrame( directionIndex % directionSteps );
        sprite.paint( aGraphics, screenPos.x, screenPos.y );
        }

    private SpriteGenerator[] myGenerators;

    private final GameContext myGameContext;
    }
