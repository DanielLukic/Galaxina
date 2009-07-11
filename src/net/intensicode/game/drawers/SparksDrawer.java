package net.intensicode.game.drawers;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.DirectScreen;
import net.intensicode.core.Engine;
import net.intensicode.game.Camera;
import net.intensicode.game.GameContext;
import net.intensicode.game.objects.Spark;
import net.intensicode.util.Position;

import javax.microedition.lcdui.Graphics;



/**
 * TODO: Describe this!
 */
public final class SparksDrawer extends AbstractScreen
    {
    public SparksDrawer( final GameContext aGameContext )
        {
        myGameContext = aGameContext;
        }

    // From AbstractScreen

    public final void onInitOnce( final Engine aEngine, final DirectScreen aScreen ) throws Exception
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

    public final void onControlTick( final Engine aEngine ) throws Exception
        {
        }

    public final void onDrawFrame( final DirectScreen aScreen )
        {
        final Graphics graphics = aScreen.graphics();

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

            graphics.setColor( gradientColors[ gradientIndex ] );
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
