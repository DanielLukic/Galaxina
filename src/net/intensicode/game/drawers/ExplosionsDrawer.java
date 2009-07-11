package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Explosion;
import net.intensicode.util.CharGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;



/**
 * TODO: Describe this!
 */
public final class ExplosionsDrawer extends AbstractScreen
    {
    public ExplosionsDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();
        myCharGens = new CharGen[3];
        myCharGens[ Explosion.BIG ] = skin.charGen( "explosion1" );
        myCharGens[ Explosion.DEFAULT ] = skin.charGen( "explosion2" );
        myCharGens[ Explosion.SPECIAL ] = skin.charGen( "explosion3" );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();


        final Explosion[] explosions = myGameContext.gameModel().explosions.explosions;
        for ( int idx = 0; idx < explosions.length; idx++ )
            {
            final Explosion explosion = explosions[ idx ];
            if ( explosion.active == false ) continue;

            final CharGen gen = myCharGens[ explosion.type ];
            final int maxAnimFrame = gen.charsPerRow * gen.charsPerColumn - 1;
            final int frame = explosion.explodeTick * maxAnimFrame / ( explosion.durationTicks - 1 );

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( explosion.worldPosFixed );
            final Position aligned = FontGen.getAlignedPosition( screenPos, gen.charWidth, gen.charHeight, ExplosionsDrawer.ALIGN_CENTER );
            gen.blit( graphics, aligned.x, aligned.y, frame );
            }
        }



    private CharGen[] myCharGens;

    private final GameContext myGameContext;


    private static final int ALIGN_CENTER = Graphics.HCENTER | Graphics.VCENTER;
    }
