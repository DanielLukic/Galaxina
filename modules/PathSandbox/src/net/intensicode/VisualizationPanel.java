package net.intensicode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;



/**
 * TODO: Describe this!
 */
final class VisualizationPanel
{
    final JPanel component = new LayeredPanel();



    final void scaleTo( final int aWidth, final int aHeight )
    {
        myScaledWidth = aWidth;
        myScaledHeight = aHeight;
    }

    final void addLayer( final VisualLayer aVisualLayer )
    {
        myLayers.add( aVisualLayer );
    }



    private int myScaledWidth;

    private int myScaledHeight;

    private final ArrayList<VisualLayer> myLayers = new ArrayList<VisualLayer>();



    private final class LayeredPanel extends JPanel
    {
        protected final void paintComponent( final Graphics aGraphics )
        {
            aGraphics.setColor( Color.WHITE );
            aGraphics.fillRect( 0, 0, getWidth(), getHeight() );

            final Graphics2D graphics2D = ( Graphics2D ) aGraphics;
            final AffineTransform oldTransform = graphics2D.getTransform();

            final Dimension size = new Dimension( myScaledWidth, myScaledHeight );
            final double xScale = myScaledWidth * 1.0 / getWidth();
            final double yScale = myScaledHeight * 1.0 / getHeight();
            graphics2D.scale( 1 / xScale, 1 / yScale );

            for ( final VisualLayer layer : myLayers )
            {
                layer.paint( graphics2D, size );
            }

            graphics2D.setTransform( oldTransform );
        }
    }
}
