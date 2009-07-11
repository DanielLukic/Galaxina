/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode;

import net.intensicode.util.FixedMath;
import net.intensicode.path.Path;
import net.intensicode.util.Position;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;



/**
 * TODO: Describe this!
 */
final class PathSegmentPlotter implements VisualLayer
{
    PathSegmentPlotter( final Path aCurveInterpolation )
    {
        myPath = aCurveInterpolation;
    }

    // From VisualLayer

    public final void paint( final Graphics2D aGraphics2D, final Dimension aGraphicsDimension )
    {
        aGraphics2D.setColor( Color.GREEN );

        final int pathLengthFixed = myPath.getPathLength();

        final Position from = new Position();
        from.setTo( myPath.getPosition( 0 ) );

        for ( int idx = 1; idx <= PATH_STEPS; idx++ )
        {
            final int pathPosFixed = idx * pathLengthFixed / PATH_STEPS;

            final Position to = myPath.getPosition( pathPosFixed );

            final int x1 = FixedMath.toInt( from.x );
            final int y1 = FixedMath.toInt( from.y );
            final int x2 = FixedMath.toInt( to.x );
            final int y2 = FixedMath.toInt( to.y );
            aGraphics2D.drawLine( x1, y1, x2, y2 );

            from.setTo( to );
        }
    }



    private final Path myPath;

    private static final int PATH_STEPS = 64;
}
