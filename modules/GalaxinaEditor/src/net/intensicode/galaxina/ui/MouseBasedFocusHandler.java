package net.intensicode.galaxina.ui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public final class MouseBasedFocusHandler implements MouseListener, MouseMotionListener
    {
    public MouseBasedFocusHandler( final JComponent aTargetComponent )
        {
        myTargetComponent = aTargetComponent;
        aTargetComponent.addMouseListener( this );
        aTargetComponent.addMouseMotionListener( this );
        }

    // From MouseMotionListener

    public final void mouseMoved( final MouseEvent e )
        {
        requestFocus();
        }

    public final void mouseDragged( final MouseEvent e )
        {
        requestFocus();
        }

    // From MouseListener

    public final void mouseClicked( final MouseEvent e )
        {
        requestFocus();
        }

    public final void mousePressed( final MouseEvent e )
        {
        requestFocus();
        }

    public final void mouseReleased( final MouseEvent e )
        {
        }

    public final void mouseEntered( final MouseEvent e )
        {
        requestFocus();
        }

    public final void mouseExited( final MouseEvent e )
        {
        }

    // Implementation

    private final void requestFocus()
        {
        if ( myTargetComponent.hasFocus() == false ) myTargetComponent.requestFocus();
        }

    private JComponent myTargetComponent;
    }
