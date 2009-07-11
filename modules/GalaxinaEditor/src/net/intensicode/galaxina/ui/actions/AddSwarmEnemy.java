package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Enemy;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.domain.NormalSwarmEnemy;

public final class AddSwarmEnemy extends RunnableAction implements EditorStateListener
    {
    public AddSwarmEnemy( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.state().add( EditorState.SELECTED_SWARM, this );
        myCoreAPI.state().add( EditorState.SELECTED_ENEMY, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        final Enemy enemy = myCoreAPI.state().currentEnemy();
        swarm.enemies().addEntry( enemy );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( myCoreAPI.state().currentSwarm() != null && myCoreAPI.state().currentEnemy() != null );
        }
    }
