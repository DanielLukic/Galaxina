package net.intensicode.galaxina.game.drawers;

import net.intensicode.core.*;
import net.intensicode.galaxina.game.*;
import net.intensicode.galaxina.game.objects.Explosion;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

public final class ExplosionsDrawer extends ScreenBase
    {
    public ExplosionsDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        myCharGens = new SpriteGenerator[3];
        myCharGens[ Explosion.BIG ] = skin.sprite( "explosion1" );
        myCharGens[ Explosion.DEFAULT ] = skin.sprite( "explosion2" );
        myCharGens[ Explosion.SPECIAL ] = skin.sprite( "explosion3" );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();

        final Explosion[] explosions = myGameContext.gameModel().explosions.explosions;
        for ( int idx = 0; idx < explosions.length; idx++ )
            {
            final Explosion explosion = explosions[ idx ];
            if ( !explosion.active ) continue;

            final SpriteGenerator gen = myCharGens[ explosion.type ];
            final int numberOfFrames = gen.getFrameSequenceLength();
            final int frame = explosion.explodeTick * ( numberOfFrames - 1 ) / ( explosion.durationTicks - 1 );

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( explosion.worldPosFixed );
            gen.paint( graphics, screenPos.x, screenPos.y, frame );

            //#if DEBUG
            graphics.setColorARGB32( 0xFF00FFFF );
            graphics.fillRect( screenPos.x - 1, screenPos.y - 1, 3, 3 );
            //#endif
            }
        }



    private SpriteGenerator[] myCharGens;

    private final GameContext myGameContext;
    }
