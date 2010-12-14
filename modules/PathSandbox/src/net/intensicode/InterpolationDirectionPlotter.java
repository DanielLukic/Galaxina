/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode;

import net.intensicode.path.Interpolation;
import net.intensicode.util.*;

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
        final PositionF from = new PositionF();
        final PositionF to = new PositionF();

        for ( int idx = 1; idx <= InterpolationDirectionPlotter.SEGMENT_STEPS; idx++ )
        {
            final float position = ( idx ) / InterpolationDirectionPlotter.SEGMENT_STEPS;
            from.setTo( myCurveInterpolation.getPosition( aSegmentIndex, position ) );
            to.setTo( myCurveInterpolation.getDirection( aSegmentIndex, position ) );

            final int x1 = (int) from.x;
            final int y1 = (int) from.y;
            final int x2 = (int) to.x;
            final int y2 = (int) to.y;

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

    private static final float SEGMENT_STEPS = 16;
}
