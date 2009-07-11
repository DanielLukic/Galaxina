package net.intensicode.galaxina.ui;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel
    {
    public ImagePanel()
        {
        myIcon = null;
        }

    public ImagePanel( final ImageIcon aIcon )
        {
        myIcon = aIcon;
        }

    public final void setIcon( final ImageIcon aIcon )
        {
        myIcon = aIcon;
        updateUI();
        }

    // From ImagePanel

    protected final void paintComponent( final Graphics g )
        {
        super.paintComponent( g );
        if ( myIcon == null ) return;

        final int width = myIcon.getIconWidth();
        final int height = myIcon.getIconHeight();
        final int xOffset = ( getWidth() - width ) / 2;
        final int yOffset = ( getHeight() - height ) / 2;
        g.drawImage( myIcon.getImage(), xOffset, yOffset, null );
        }

    private ImageIcon myIcon;
    }
