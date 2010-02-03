package net.intensicode.game.drawers;

import net.intensicode.core.*;
import net.intensicode.game.*;
import net.intensicode.game.objects.Smoke;
import net.intensicode.graphics.*;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;

public final class SmokesDrawer extends ScreenBase
    {
    public SmokesDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        final SkinManager skin = myGameContext.visualContext().skinManager();
        mySmokeGen = skin.charGen( "smoke" );
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();

        final int maxAnimFrame = mySmokeGen.charsPerRow * mySmokeGen.charsPerColumn - 1;

        final Smoke[] smokes = myGameContext.gameModel().smokes.smokes;
        for ( int idx = 0; idx < smokes.length; idx++ )
            {
            final Smoke smoke = smokes[ idx ];
            if ( !smoke.active ) continue;

            final int frame = smoke.smokeTick * maxAnimFrame / ( smoke.durationTicks - 1 );

            final Camera camera = myGameContext.camera();
            final Position screenPos = camera.toScreen( smoke.worldPosFixed );
            final Position aligned = FontGenerator.getAlignedPosition( screenPos, mySmokeGen.charWidth, mySmokeGen.charHeight, SmokesDrawer.ALIGN_CENTER );
            mySmokeGen.blit( graphics, aligned.x, aligned.y, frame );
            }
        }



    private CharGenerator mySmokeGen;

    private final GameContext myGameContext;


    private static final int ALIGN_CENTER = Graphics.HCENTER | Graphics.VCENTER;
    }
