package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.core.Skin;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Smoke;
import net.intensicode.util.CharGen;
import net.intensicode.util.FontGen;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;



/**
 * TODO: Describe this!
 */
public final class SmokesDrawer extends AbstractScreen
    {
    public SmokesDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
        {
        final Skin skin = myGameContext.visualContext().skin();
        mySmokeGen = skin.charGen( "smoke" );
        }

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();

        final int maxAnimFrame = mySmokeGen.charsPerRow * mySmokeGen.charsPerColumn - 1;

        final Smoke[] smokes = myGameContext.gameModel().smokes.smokes;
        for ( int idx = 0; idx < smokes.length; idx++ )
            {
            final Smoke smoke = smokes[ idx ];
            if ( !smoke.active ) continue;

            final int frame = smoke.smokeTick * maxAnimFrame / ( smoke.durationTicks - 1 );

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( smoke.worldPosFixed );
            final Position aligned = FontGen.getAlignedPosition( screenPos, mySmokeGen.charWidth, mySmokeGen.charHeight, SmokesDrawer.ALIGN_CENTER );
            mySmokeGen.blit( graphics, aligned.x, aligned.y, frame );
            }
        }



    private CharGen mySmokeGen;

    private final GameContext myGameContext;


    private static final int ALIGN_CENTER = Graphics.HCENTER | Graphics.VCENTER;
    }
