package net.intensicode.galaxina.ui.actions.editors;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class ZoomExact extends RunnableAction implements EditorStateListener
    {
    public ZoomExact( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( PROJECT, this );
        aCoreAPI.state().add( ZOOM_MODE, this );
        }

    // From Runnable

    public final void run()
        {
        myCoreAPI.state().setZoomFactor( 1.0 );
        myCoreAPI.state().setZoomMode( ZoomMode.ZOOM_EXACT );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        if ( aEventID == PROJECT ) setEnabled( aNewValue != null );
        if ( aEventID == ZOOM_MODE ) setEnabled( aNewValue != ZoomMode.ZOOM_EXACT );
        }
    }
