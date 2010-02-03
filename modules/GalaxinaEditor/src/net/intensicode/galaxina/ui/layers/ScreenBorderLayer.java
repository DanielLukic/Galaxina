package net.intensicode.galaxina.ui.layers;

import net.intensicode.galaxina.*;

import java.awt.*;

public final class ScreenBorderLayer implements VisualLayer, Identifiers
    {
    public ScreenBorderLayer( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // From VisualLayer

    public final void paintInto( final Graphics2D aGraphics2D )
        {
        final int screenWidth = myCoreAPI.gameScreenWidth();
        final int screenHeight = myCoreAPI.gameScreenHeight();

        aGraphics2D.setColor( myCoreAPI.ui().configuration().guideColor );
        aGraphics2D.setStroke( myCoreAPI.ui().configuration().guideStroke );
        aGraphics2D.drawLine( screenWidth / 2, 0, screenWidth / 2, screenHeight );
        aGraphics2D.drawLine( screenWidth / 4, 0, screenWidth / 4, screenHeight / 2 );
        aGraphics2D.drawLine( screenWidth * 3 / 4, 0, screenWidth * 3 / 4, screenHeight / 2 );
        aGraphics2D.drawLine( 0, screenHeight / 4, screenWidth, screenHeight / 4 );
        aGraphics2D.drawLine( 0, screenHeight / 2, screenWidth, screenHeight / 2 );

        aGraphics2D.setColor( myCoreAPI.ui().configuration().borderColor );
        aGraphics2D.setStroke( myCoreAPI.ui().configuration().borderStroke );
        aGraphics2D.drawRect( 0, 0, screenWidth, screenHeight );
        }

    private final EditorCoreAPI myCoreAPI;
    }
