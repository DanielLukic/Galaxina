package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;

public final class ZoomGameToFit extends RunnableAction implements EditorStateListener
    {
    public ZoomGameToFit( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( PROJECT, this );
        aCoreAPI.state().add( MIDLET_ZOOM, this );
        }

    // From Runnable

    public final void run()
        {
        myCoreAPI.state().change( MIDLET_ZOOM, ZoomMode.ZOOM_TO_FIT );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        final boolean project = myCoreAPI.state().get( PROJECT ) != null;
        final boolean otherZoom = myCoreAPI.state().get( MIDLET_ZOOM ) != ZoomMode.ZOOM_TO_FIT;
        setEnabled( project && otherZoom );
        }
    }
