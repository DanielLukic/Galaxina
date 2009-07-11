package net.intensicode.galaxina.ui.actions.editors;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class ShowGrid extends RunnableAction implements EditorStateListener
    {
    public ShowGrid( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( PROJECT, this );
        }

    // From Runnable

    public final void run()
        {
        final Boolean align = (Boolean) myCoreAPI.state().get( SHOW_GRID );
        myCoreAPI.state().change( SHOW_GRID, !align );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
