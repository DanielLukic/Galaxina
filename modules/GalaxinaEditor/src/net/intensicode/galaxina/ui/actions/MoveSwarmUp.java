package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.*;

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
        final Level level = myCoreAPI.state().currentLevel();
        final net.intensicode.galaxina.domain.Swarm swarm = myCoreAPI.state().currentSwarm();
        level.swarms().moveUp( swarm );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }