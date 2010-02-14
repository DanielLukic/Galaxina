/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode;

import net.intensicode.path.Interpolation;
import net.intensicode.util.Position;
import net.intensicode.util.FixedMath;

import java.awt.*;



/**
 * TODO: Describe this!
 */
final class InterpolationDirectionPlotter implements VisualLayer
{
    InterpolationDirectionPlotter( final Interpolation aCurveInterpolation )
    {
        myCurveInterpolation = aCurveInterpolation;
    }

    // From VisualLayer

    public final void paint( final Graphics2D aGraphics2D, final Dimension aGraphicsDimension )
    {
        final int size = myCurveInterpolation.getNumberOfInputPositions();
        for ( int idx = 0; idx < size; idx++ )
        {
            drawSegment( aGraphics2D, idx );
        }
    }

    // Implementation

    private final void drawSegment( final Graphics2D aGraphics2D, final int aSegmentIndex )
    {
        final Position from = new Position();
        final Position to = new Position();

        for ( int idx = 1; idx <= InterpolationDirectionPlotter.SEGMENT_STEPS; idx++ )
        {
            final int positionFixed = FixedMath.toFixed( idx ) / InterpolationDirectionPlotter.SEGMENT_STEPS;
            from.setTo( myCurveInterpolation.getPosition( aSegmentIndex, positionFixed ) );
            to.setTo( myCurveInterpolation.getDirection( aSegmentIndex, positionFixed ) );

            final int x1 = FixedMath.toInt( from.x );
            final int y1 = FixedMath.toInt( from.y );
            final int x2 = FixedMath.toInt( to.x );
            final int y2 = FixedMath.toInt( to.y );

            final Stroke oldStroke = aGraphics2D.getStroke();
            aGraphics2D.setStroke( new BasicStroke( 0.25f ) );
            aGraphics2D.setColor( Color.YELLOW );
            aGraphics2D.drawLine( x1, y1, x1 + x2, y1 + y2 );
            aGraphics2D.setStroke( oldStroke );
            aGraphics2D.setColor( Color.BLACK );
            aGraphics2D.drawLine( x1 + x2, y1 + y2, x1 + x2, y1 + y2 );
        }
    }



    private final Interpolation myCurveInterpolation;

    private static final int SEGMENT_STEPS = 16;
}
