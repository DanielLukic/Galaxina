package net.intensicode;

import net.intensicode.util.FixedMath;
import net.intensicode.path.Interpolation;
import net.intensicode.util.Position;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;



/**
 * TODO: Describe this!
 */
final class InterpolationSegmentPlotter implements VisualLayer
{
    InterpolationSegmentPlotter( final Interpolation aCurveInterpolation )
    {
        myCurveInterpolation = aCurveInterpolation;
    }

    // From VisualLayer

    public final void paint( final Graphics2D aGraphics2D, final Dimension aGraphicsDimension )
    {
        aGraphics2D.setColor( Color.GREEN );

        final int size = myCurveInterpolation.getNumberOfInputPositions();
        for ( int idx = 0; idx < size - 1; idx++ )
        {
            drawSegment( aGraphics2D, idx );
        }
    }

    // Implementation

    private final void drawSegment( final Graphics2D aGraphics2D, final int aSegmentIndex )
    {
        final Position from = new Position();
        final Position to = new Position();

        from.setTo( myCurveInterpolation.getPosition( aSegmentIndex, 0 ) );

        for ( int idx = 1; idx <= SEGMENT_STEPS; idx++ )
        {
            final int segmentPosFixed = FixedMath.toFixed( idx ) / SEGMENT_STEPS;
            to.setTo( myCurveInterpolation.getPosition( aSegmentIndex, segmentPosFixed ) );

            final int x1 = FixedMath.toInt( from.x );
            final int y1 = FixedMath.toInt( from.y );
            final int x2 = FixedMath.toInt( to.x );
            final int y2 = FixedMath.toInt( to.y );
            aGraphics2D.drawLine( x1, y1, x2, y2 );

            from.setTo( to );
        }
    }



    private final Interpolation myCurveInterpolation;

    private static final int SEGMENT_STEPS = 3;
}
