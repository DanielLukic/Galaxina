package net.intensicode.galaxina.ui.actions.editors;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class ZoomIn extends RunnableAction implements EditorStateListener
    {
    public ZoomIn( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( PROJECT, this );
        }

    // From Runnable

    public final void run()
        {
        final double newZoom = myCoreAPI.state().getZoomFactor() * 1.25;
        myCoreAPI.state().setZoomFactor( newZoom );
        myCoreAPI.state().setZoomMode( ZoomMode.ZOOM_FREE );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == PROJECT ) setEnabled( aNewValue != null );
        }
    }
