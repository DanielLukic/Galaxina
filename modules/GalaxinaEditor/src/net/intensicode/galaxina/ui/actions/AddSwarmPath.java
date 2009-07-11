package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Path;
import net.intensicode.galaxina.domain.Swarm;

public final class AddSwarmPath extends RunnableAction implements EditorStateListener
    {
    public AddSwarmPath( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( EditorState.SELECTED_SWARM, this );
        myCoreAPI.state().add( EditorState.SELECTED_PATH, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        final Path path = myCoreAPI.state().currentPath();
        swarm.pathes().addEntry( path );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( myCoreAPI.state().currentSwarm() != null && myCoreAPI.state().currentPath() != null );
        }
    }
