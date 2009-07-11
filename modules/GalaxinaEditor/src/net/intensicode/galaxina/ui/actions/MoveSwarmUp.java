package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;

public final class MoveSwarmUp extends RunnableAction implements EditorStateListener
    {
    public MoveSwarmUp( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( false );
        myCoreAPI.state().add( EditorState.SELECTED_SWARM, this );
        }

    // From Runnable

    public final void run()
        {
        throw new RuntimeException( "nyi" );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }