package net.intensicode.galaxina.ui.logic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public final class EventDelegator implements MouseListener, MouseMotionListener
    {
    public EventDelegator( final List<InputHandler> aHandlers )
        {
        myHandlers = aHandlers;
        }

    // From MouseMotionListener

    public final void mouseDragged( final MouseEvent e )
        {
        for ( final InputHandler handler : myHandlers )
            {
            if ( handler.isActive() && handler.mouseDragged( e ) ) break;
            }
        }

    public final void mouseMoved( final MouseEvent e )
        {
        }

    // From MouseListener

    public final void mouseClicked( final MouseEvent e )
        {
        for ( final InputHandler handler : myHandlers )
            {
            if ( handler.isActive() && handler.mouseClicked( e ) ) break;
            }
        }

    public final void mousePressed( final MouseEvent e )
        {
        for ( final InputHandler handler : myHandlers )
            {
            if ( handler.isActive() && handler.mousePressed( e ) ) break;
            }
        }

    public final void mouseReleased( final MouseEvent e )
        {
        for ( final InputHandler handler : myHandlers )
            {
            if ( handler.isActive() && handler.mouseReleased( e ) ) break;
            }
        }

    public final void mouseEntered( final MouseEvent e )
        {
        }

    public final void mouseExited( final MouseEvent e )
        {
        }

    private final List<InputHandler> myHandlers;
    }
