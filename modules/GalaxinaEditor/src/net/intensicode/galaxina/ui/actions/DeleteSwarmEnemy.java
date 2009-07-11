package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.domain.SwarmEnemy;

public final class DeleteSwarmEnemy extends RunnableAction implements EditorStateListener
    {
    public DeleteSwarmEnemy( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( EditorState.SELECTED_SWARM_ENEMY, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        final SwarmEnemy enemy = myCoreAPI.state().currentSwarmEnemy();
        myCoreAPI.state().change( EditorState.SELECTED_SWARM_ENEMY, null );
        swarm.enemies().deleteEntry( enemy );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( myCoreAPI.state().currentSwarm() != null && myCoreAPI.state().currentSwarmEnemy() != null );
        }
    }
