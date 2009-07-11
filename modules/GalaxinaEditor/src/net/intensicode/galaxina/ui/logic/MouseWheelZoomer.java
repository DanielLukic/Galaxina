package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.Identifiers;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public final class MouseWheelZoomer implements MouseWheelListener
    {
    public MouseWheelZoomer( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // From MouseWheelListener

    public final void mouseWheelMoved( final MouseWheelEvent e )
        {
        final double zoomFactor = myCoreAPI.state().getZoomFactor();
        final double zoomChange = zoomFactor / 25 * e.getUnitsToScroll();
        final double newZoom = zoomFactor - zoomChange;
        final double limitedZoom = Math.max( Identifiers.MIN_ZOOM_FACTOR, newZoom );
        final double goodZoom = Math.min( Identifiers.MAX_ZOOM_FACTOR, limitedZoom );
        myCoreAPI.state().setZoomFactor( goodZoom );
        myCoreAPI.state().setZoomMode( Identifiers.ZoomMode.ZOOM_FREE );
        }

    private final EditorCoreAPI myCoreAPI;
    }
