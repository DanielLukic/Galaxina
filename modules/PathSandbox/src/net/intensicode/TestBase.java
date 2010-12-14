package net.intensicode;

import net.intensicode.path.Interpolation;
import net.intensicode.path.Path;
import net.intensicode.path.PositionList;
import net.intensicode.util.*;



abstract class TestBase
{
    protected static final void prepare( final PositionList aPositionList )
    {
        aPositionList.add( pos( 88, 52 ) );
        aPositionList.add( pos( 98, 42 ) );
        aPositionList.add( pos( 108, 52 ) );
        aPositionList.add( pos( 88, 104 ) );
        aPositionList.add( pos( 68, 156 ) );
        aPositionList.add( pos( 78, 166 ) );
        aPositionList.add( pos( 88, 156 ) );
        aPositionList.end();
    }

    protected static final void visualize( final Interpolation aInterpolation )
    {
        final AdjustableFrame frame = new AdjustableFrame();

        final VisualizationPanel visualizationPanel = frame.visualizationPanel;
        visualizationPanel.scaleTo( 176, 208 );

        visualizationPanel.addLayer( new InterpolationPlotter( aInterpolation ) );
        visualizationPanel.addLayer( new InterpolationSegmentPlotter( aInterpolation ) );

        frame.setVisible( true );
    }

    protected static final void visualize( final Path aPath )
    {
        final AdjustableFrame frame = new AdjustableFrame();

        final VisualizationPanel visualizationPanel = frame.visualizationPanel;
        visualizationPanel.scaleTo( 176, 208 );

        visualizationPanel.addLayer( new PathPlotter( aPath ) );
        visualizationPanel.addLayer( new PathSegmentPlotter( aPath ) );

        frame.setVisible( true );
    }

    protected static final PositionF pos( final int aX, final int aY )
    {
        return new PositionF( aX, aY );
    }
}
