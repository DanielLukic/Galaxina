package net.intensicode;

import net.intensicode.util.*;
import net.intensicode.path.Path;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;



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

        final float pathLengthFixed = myPath.getPathLength();

        final PositionF from = new PositionF();
        from.setTo( myPath.getPosition( 0 ) );

        for ( int idx = 1; idx <= PATH_STEPS; idx++ )
        {
            final float pathPosFixed = idx * pathLengthFixed / PATH_STEPS;

            final PositionF to = myPath.getPosition( pathPosFixed );

            final int x1 = (int) from.x;
            final int y1 = (int) from.y;
            final int x2 = (int) to.x;
            final int y2 = (int) to.y;
            aGraphics2D.drawLine( x1, y1, x2, y2 );

            from.setTo( to );
        }
    }



    private final Path myPath;

    private static final int PATH_STEPS = 64;
}
