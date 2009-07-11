package net.intensicode.galaxina.ui.layers;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.UiConfiguration;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.ListOfPositions;
import net.intensicode.galaxina.ui.logic.CoordinateTransformer;
import net.intensicode.path.SmoothPath;
import net.intensicode.util.FixedMath;
import net.intensicode.util.Position;

import java.awt.*;

public class PathLayer implements VisualLayer, Identifiers
    {
    public boolean showMarkers = true;

    public boolean skipIfSelected = true;

    public PathLayer( final EditorCoreAPI aCoreAPI, final CoordinateTransformer aTransformer )
        {
        myCoreAPI = aCoreAPI;
        myTransformer = aTransformer;

        final UiConfiguration config = aCoreAPI.ui().configuration();
        setStroke( config.pathStroke );
        setSmoothPathColor( config.smoothPathColor );
        setPathColor( config.pathColor );
        setMarkerColor( config.markerColor );
        }

    public final void attach( final Path aPath )
        {
        myPath = aPath;
        }

    public final void setStroke( final Stroke aStroke )
        {
        myStroke = aStroke;
        }

    public final void setSmoothPathColor( final Color aColor )
        {
        mySmoothPathColor = aColor;
        }

    public final void setPathColor( final Color aColor )
        {
        myPathColor = aColor;
        }

    public final void setMarkerColor( final Color aColor )
        {
        myMarkerColor = aColor;
        }

    // From VisualLayer

    public final void paintInto( final Graphics2D aGraphics2D )
        {
        if ( !isVisible() ) return;

        aGraphics2D.setStroke( myStroke );

        if ( myPath.positions().size() > 2 && myCoreAPI.state().getBoolean( SMOOTH_PATHES ) )
            {
            drawSmoothPath( aGraphics2D );
            }

        if ( myCoreAPI.state().getBoolean( SHOW_PATHES ) )
            {
            drawPath( aGraphics2D );
            }

        if ( showMarkers ) drawMarkers( aGraphics2D );
        }

    // Protected Interface

    protected boolean isVisible()
        {
        if ( skipIfSelected && myCoreAPI.state().currentPath() == myPath ) return false;
        return myPath != null;
        }

    // Implementation

    private final void drawSmoothPath( final Graphics2D aGraphics2D )
        {
        mySmoothPath.clear();
        for ( final Position position : myPath.positions() )
            {
            myPosition.setTo( position );
            FixedMath.toFixed( myPosition );
            mySmoothPath.add( myPosition );
            }
        mySmoothPath.end();

        final int pathLength = mySmoothPath.getPathLength();

        aGraphics2D.setColor( mySmoothPathColor );
        final int pathSteps = myPath.positions().size() * 7;
        for ( int idx = 1; idx <= pathSteps; idx++ )
            {
            myPosition.setTo( mySmoothPath.getPosition( pathLength * ( idx - 1 ) / pathSteps ) );
            final Position to = mySmoothPath.getPosition( pathLength * idx / pathSteps );
            FixedMath.toInt( myPosition );
            FixedMath.toInt( to );
            aGraphics2D.drawLine( myPosition.x, myPosition.y, to.x, to.y );
            }
        }

    private final void drawPath( final Graphics2D aGraphics2D )
        {
        aGraphics2D.setColor( myPathColor );
        final ListOfPositions positions = myPath.positions();
        for ( int idx = 1; idx < positions.size(); idx++ )
            {
            final int x1 = positions.at( idx - 1 ).x;
            final int y1 = positions.at( idx - 1 ).y;
            final int x2 = positions.at( idx ).x;
            final int y2 = positions.at( idx ).y;
            aGraphics2D.drawLine( x1, y1, x2, y2 );
            }
        }

    private final void drawMarkers( final Graphics2D aGraphics2D )
        {
        final int rawMarkerSize = myCoreAPI.ui().configuration().markerSize;
        final int markerSize = (int) ( myTransformer.scaled( rawMarkerSize ) + 1 );
        final int halfMarkerSize = markerSize / 2;

        aGraphics2D.setColor( myMarkerColor );
        for ( final Position position : myPath.positions() )
            {
            final int x = position.x - halfMarkerSize;
            final int y = position.y - halfMarkerSize;
            aGraphics2D.fillRect( x, y, markerSize, markerSize );
            }
        }



    private Path myPath;

    private Stroke myStroke;

    private Color myPathColor;

    private Color myMarkerColor;

    private Color mySmoothPathColor;

    protected final EditorCoreAPI myCoreAPI;

    protected final CoordinateTransformer myTransformer;

    private final Position myPosition = new Position();

    private final SmoothPath mySmoothPath = new SmoothPath();
    }
