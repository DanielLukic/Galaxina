package net.intensicode.galaxina.ui.actions.editors;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class ZoomOut extends RunnableAction implements EditorStateListener
    {
    public ZoomOut( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( PROJECT, this );
        }

    // From Runnable

    public final void run()
        {
        final double newZoom = myCoreAPI.state().getZoomFactor() * 0.75;
        myCoreAPI.state().setZoomFactor( newZoom );
        myCoreAPI.state().setZoomMode( ZoomMode.ZOOM_FREE );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == PROJECT ) setEnabled( aNewValue != null );
        }
    }
