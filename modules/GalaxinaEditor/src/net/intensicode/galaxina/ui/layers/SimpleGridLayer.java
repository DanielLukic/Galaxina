package net.intensicode.galaxina.ui.layers;

import net.intensicode.galaxina.*;

import java.awt.*;

public final class SimpleGridLayer implements VisualLayer, Identifiers
    {
    public SimpleGridLayer( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // From VisualLayer

    public final void paintInto( final Graphics2D aGraphics2D )
        {
        final Boolean showGrid = (Boolean) myCoreAPI.state().get( SHOW_GRID );
        if ( !showGrid ) return;

        aGraphics2D.setColor( myCoreAPI.ui().configuration().gridColor );
        aGraphics2D.setStroke( myCoreAPI.ui().configuration().gridStroke );

        int gridSizeX = myCoreAPI.ui().configuration().gridSizeX;
        if ( myCoreAPI.state().getZoomFactor() < 0.66 ) gridSizeX *= 2;
        if ( myCoreAPI.state().getZoomFactor() < 0.33 ) gridSizeX *= 2;

        int gridSizeY = myCoreAPI.ui().configuration().gridSizeY;
        if ( myCoreAPI.state().getZoomFactor() < 0.66 ) gridSizeY *= 2;
        if ( myCoreAPI.state().getZoomFactor() < 0.33 ) gridSizeY *= 2;

        final int width = myCoreAPI.gameScreenWidth();
        final int height = myCoreAPI.gameScreenHeight();

        final int xOffset = width / gridSizeX * gridSizeX;
        final int xStart = -xOffset;
        final int xEnd = width + xOffset;

        final int yOffset = height / gridSizeY * gridSizeY;
        final int yStart = -yOffset;
        final int yEnd = height + yOffset;

        for ( int x = xStart; x <= xEnd; x += gridSizeX )
            {
            aGraphics2D.drawLine( x, yStart, x, yEnd );
            }
        for ( int y = yStart; y <= yEnd; y += gridSizeY )
            {
            aGraphics2D.drawLine( xStart, y, xEnd, y );
            }
        }

    private final EditorCoreAPI myCoreAPI;
    }
