package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.Swarm;

public final class DeleteSwarmPath extends RunnableAction implements EditorStateListener
    {
    public DeleteSwarmPath( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( EditorState.SELECTED_SWARM_PATH, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        final Path path = myCoreAPI.state().currentSwarmPath();
        myCoreAPI.state().change( EditorState.SELECTED_SWARM_PATH, null );
        swarm.pathes().deleteEntry( path );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( myCoreAPI.state().currentSwarm() != null && myCoreAPI.state().currentSwarmPath() != null );
        }
    }
