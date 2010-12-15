package net.intensicode;

import net.intensicode.util.*;
import net.intensicode.path.Path;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;



final class PathPlotter implements VisualLayer
{
    PathPlotter( final Path aPath )
    {
        myPath = aPath;
    }

    // From VisualLayer

    public final void paint( final Graphics2D aGraphics2D, final Dimension aGraphicsDimension )
    {
        final int pointWidth = getPointWidth( aGraphicsDimension );
        final int pointHeight = getPointHeight( aGraphicsDimension );
        final int pointSize = Math.max( pointWidth, pointHeight );
        final int halfSize = pointSize / 2;

        aGraphics2D.setColor( Color.BLUE );

        final float pathLengthFixed = myPath.getPathLength();
        for ( int idx = 0; idx <= PATH_STEPS; idx++ )
        {
            final float pathPosFixed = pathLengthFixed * idx / PATH_STEPS;
            final PositionF point = myPath.getPosition( pathPosFixed );
            final int x = (int) point.x;
            final int y = (int) point.y;
            aGraphics2D.drawRect( x - halfSize, y - halfSize, pointSize, pointSize );
        }
    }

    // Implementation

    private final int getPointWidth( final Dimension aGraphicsDimension )
    {
        return aGraphicsDimension.width * PathPlotter.POINT_WIDTH_IN_PERCENT / 100;
    }

    private final int getPointHeight( final Dimension aGraphicsDimension )
    {
        return aGraphicsDimension.height * PathPlotter.POINT_HEIGHT_IN_PERCENT / 100;
    }



    private final Path myPath;

    private static final int POINT_WIDTH_IN_PERCENT = 1;

    private static final int POINT_HEIGHT_IN_PERCENT = 1;

    private static final int PATH_STEPS = 16;
}
