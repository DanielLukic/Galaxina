package net.intensicode.galaxina.ui.actions.editors;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class ShowPathes extends RunnableAction implements EditorStateListener
    {
    public ShowPathes( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( PROJECT, this );
        }

    // From Runnable

    public final void run()
        {
        final Boolean align = (Boolean) myCoreAPI.state().get( SHOW_PATHES );
        myCoreAPI.state().change( SHOW_PATHES, !align );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
