package net.intensicode.game.drawers;

import net.intensicode.core.DirectGraphics;
import net.intensicode.game.*;
import net.intensicode.game.objects.Spark;
import net.intensicode.screens.ScreenBase;
import net.intensicode.util.Position;

public final class SparksDrawer extends ScreenBase
    {
    public SparksDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From ScreenBase

    public final void onInitOnce() throws Exception
        {
        for ( int color = 0; color < GRADIENT_COLORS.length - 1; color++ )
            {
            final int fromRGB = GRADIENT_COLORS[ color ];
            final int fromR = ( fromRGB >> 16 ) & 0xFF;
            final int fromG = ( fromRGB >> 8 ) & 0xFF;
            final int fromB = ( fromRGB >> 0 ) & 0xFF;

            final int toRGB = GRADIENT_COLORS[ color + 1 ];
            final int toR = ( toRGB >> 16 ) & 0xFF;
            final int toG = ( toRGB >> 8 ) & 0xFF;
            final int toB = ( toRGB >> 0 ) & 0xFF;

            for ( int idx = 0; idx < GRADIENT_STEPS; idx++ )
                {
                final int r = ( fromR * ( GRADIENT_STEPS - idx ) + toR * idx ) / GRADIENT_STEPS;
                final int g = ( fromG * ( GRADIENT_STEPS - idx ) + toG * idx ) / GRADIENT_STEPS;
                final int b = ( fromB * ( GRADIENT_STEPS - idx ) + toB * idx ) / GRADIENT_STEPS;
                final int rgb = 0xFF000000 | r << 16 | g << 8 | b;
                gradientColors[ color * GRADIENT_STEPS + idx ] = rgb;
                }
            }
        }

    public final void onControlTick() throws Exception
        {
        }

    public final void onDrawFrame()
        {
        final DirectGraphics graphics = graphics();

        final int maxIndex = gradientColors.length;

        final Spark[] sparks = myGameContext.gameModel().sparks.sparks;
        for ( int idx = 0; idx < sparks.length; idx++ )
            {
            final Spark spark = sparks[ idx ];
            if ( !spark.active ) continue;

            final int frame = spark.tick * ( maxIndex - 1 ) / ( spark.durationTicks );
            final int gradientIndex = frame * ( gradientColors.length - 1 ) / ( maxIndex - 1 );

            final Camera camera = myGameContext.camera();
            myFromPos.setTo( camera.toScreen( spark.fromPosFixed ) );
            myToPos.setTo( camera.toScreen( spark.toPosFixed ) );

            graphics.setColorRGB24( gradientColors[ gradientIndex ] );
            graphics.drawLine( myFromPos.x, myFromPos.y, myToPos.x, myToPos.y );
            }
        }



    private final GameContext myGameContext;

    private final Position myToPos = new Position();

    private final Position myFromPos = new Position();

    private final int[] gradientColors = new int[GRADIENT_STEPS * GRADIENT_COLORS.length];


    private static final int GRADIENT_STEPS = 5;

    private static final int[] GRADIENT_COLORS = { 0xFF0000, 0xFFFF00, 0xFFFFFF, 0xFF0000, 0x000000 };
    }
