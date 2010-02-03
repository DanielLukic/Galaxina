package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.*;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.util.Position;

import javax.swing.*;
import java.awt.*;

public final class ZoomAwareCoordinateTransformer implements CoordinateTransformer
    {
    public ZoomAwareCoordinateTransformer( final EditorCoreAPI aCoreAPI, final JComponent aComponent )
        {
        myCoreAPI = aCoreAPI;
        myComponent = aComponent;
        }

    public final void setZoomFactor( final double aZoomFactor )
        {
        myZoomFactor = aZoomFactor;
        }

    public final void prepare( final Graphics2D aGraphics2D )
        {
        aGraphics2D.translate( xOffset(), yOffset() );
        aGraphics2D.scale( myZoomFactor, myZoomFactor );
        }

    // From CoordinateTransformer

    public final double scaled( final double aSize )
        {
        return aSize * ( 1 + ( myZoomFactor - 1 ) / 20 );
        }

    public final PositionEx toGame( final Point aPoint )
        {
        final int x = (int) ( ( aPoint.x - xOffset() ) / myZoomFactor );
        final int y = (int) ( ( aPoint.y - yOffset() ) / myZoomFactor );
        if ( myCoreAPI.state().getBoolean( Identifiers.ALIGN_TO_GRID ) )
            {
            final int gridSizeX = myCoreAPI.ui().configuration().gridSizeX;
            final int gridSizeY = myCoreAPI.ui().configuration().gridSizeY;
            final int xOffset = x >= 0 ? gridSizeX / 2 : -gridSizeX / 2;
            final int yOffset = y >= 0 ? gridSizeY / 2 : -gridSizeY / 2;
            final int xAligned = ( x + xOffset ) / gridSizeX * gridSizeX;
            final int yAligned = ( y + yOffset ) / gridSizeY * gridSizeY;
            return new PositionEx( xAligned, yAligned );
            }
        return new PositionEx( x, y );
        }

    // Implementation

    private double xOffset()
        {
        final int width = myComponent.getWidth();
        final int screenWidth = myCoreAPI.gameScreenWidth();

        final double rawOffset = ( width - screenWidth * myZoomFactor ) / 2;

        final Position offset = (Position) myCoreAPI.state().get( Identifiers.PATH_EDITOR_OFFSET );
        if ( offset == null ) return rawOffset;
        return rawOffset - offset.x;
        }

    private double yOffset()
        {
        final int height = myComponent.getHeight();
        final int screenHeight = myCoreAPI.gameScreenHeight();

        final double rawOffset = ( height - screenHeight * myZoomFactor ) / 2;

        final Position offset = (Position) myCoreAPI.state().get( Identifiers.PATH_EDITOR_OFFSET );
        if ( offset == null ) return rawOffset;
        return rawOffset + offset.y;
        }


    private double myZoomFactor;

    private final JComponent myComponent;

    private final EditorCoreAPI myCoreAPI;
    }
