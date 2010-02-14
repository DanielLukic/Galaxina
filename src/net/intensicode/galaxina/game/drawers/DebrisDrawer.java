package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.Debris;
import net.intensicode.graphics.SpriteGenerator;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

public final class DebrisDrawer extends ScreenBase
    {
    public DebrisDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();

        myGenerators = new SpriteGenerator[3];

        for ( int idx = 0; idx < myGenerators.length; idx++ )
            {
            final StringBuffer imageName = new StringBuffer( "debris" );
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

        final Debris[] debrises = myGameContext.gameModel().debrises.debrises;
        for ( int idx = 0; idx < debrises.length; idx++ )
            {
            final Debris debris = debrises[ idx ];
            if ( debris == null || !debris.active ) continue;
            draw( graphics, debris );
            }
        }

    // Implementation

    private void draw( final DirectGraphics aGraphics, final Debris aDebris )
        {
        final Camera camera = myGameContext.camera();
        final Position screenPos = camera.toScreen( aDebris.worldPosFixed );

        final SpriteGenerator sprite = myGenerators[ aDebris.type ];
        final int steps = sprite.getFrameSequenceLength();
        final int step = aDebris.tickCount * ( steps - 1 ) / aDebris.animTicks;
        sprite.setFrame( step );
        sprite.paint( aGraphics, screenPos.x, screenPos.y );
        }



    private SpriteGenerator[] myGenerators;

    private final GameContext myGameContext;
    }
