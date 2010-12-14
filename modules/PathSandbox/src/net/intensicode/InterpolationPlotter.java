package net.intensicode;

import net.intensicode.path.Interpolation;
import net.intensicode.util.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;



final class InterpolationPlotter implements VisualLayer
{
    InterpolationPlotter( final Interpolation aCurveInterpolation )
    {
        myCurveInterpolation = aCurveInterpolation;
    }

    // From VisualLayer

    public final void paint( final Graphics2D aGraphics2D, final Dimension aGraphicsDimension )
    {
        final int pointWidth = getPointWidth( aGraphicsDimension );
        final int pointHeight = getPointHeight( aGraphicsDimension );
        final int pointSize = Math.max( pointWidth, pointHeight );
        final int halfSize = pointSize / 2;

        aGraphics2D.setColor( Color.BLUE );

        final int size = myCurveInterpolation.getNumberOfInputPositions();
        for ( int idx = 0; idx < size; idx++ )
        {
            final PositionF point = myCurveInterpolation.getInputPosition( idx );
            final int x = (int) point.x;
            final int y = (int) point.y;
            aGraphics2D.drawRect( x - halfSize, y - halfSize, pointSize, pointSize );
        }
    }

    // Implementation

    private final int getPointWidth( final Dimension aGraphicsDimension )
    {
        return aGraphicsDimension.width * InterpolationPlotter.POINT_WIDTH_IN_PERCENT / 100;
    }

    private final int getPointHeight( final Dimension aGraphicsDimension )
    {
        return aGraphicsDimension.height * InterpolationPlotter.POINT_HEIGHT_IN_PERCENT / 100;
    }



    private final Interpolation myCurveInterpolation;

    private static final int POINT_WIDTH_IN_PERCENT = 1;

    private static final int POINT_HEIGHT_IN_PERCENT = 1;
}
