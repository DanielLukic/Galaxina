package net.intensicode;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.DisplayMode;

import javax.swing.JFrame;



/**
 * TODO: Describe this!
 */
final class AdjustableFrame extends JFrame
{
    final ControlPanel controlPanel = new ControlPanel();

    final VisualizationPanel visualizationPanel = new VisualizationPanel();



    AdjustableFrame()
    {
        final Container contentPane = getContentPane();
        contentPane.setLayout( new BorderLayout() );
        contentPane.add( controlPanel.component, BorderLayout.NORTH );
        contentPane.add( visualizationPanel.component, BorderLayout.CENTER );

        setDefaultCloseOperation( EXIT_ON_CLOSE );

        final DisplayMode displayMode = getGraphicsConfiguration().getDevice().getDisplayMode();
        final int displayWidth = displayMode.getWidth();
        final int displayHeight = displayMode.getHeight();
        final int targetHeight = displayHeight * 3 / 4;
        final int targetWidth = targetHeight * 176 / 208;
        final int xPos = ( displayWidth - targetWidth ) / 2;
        final int yPos = ( displayHeight - targetHeight ) / 2;
        setBounds( xPos, yPos, targetWidth, targetHeight );
    }
}
