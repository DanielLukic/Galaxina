package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Level;
import net.intensicode.galaxina.domain.Swarm;

public final class CloneSwarm extends RunnableAction implements EditorStateListener
    {
    public CloneSwarm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( false );
        myCoreAPI.state().add( SELECTED_SWARM, this );
        }

    // From Runnable

    public final void run()
        {
        final Level level = myCoreAPI.state().currentLevel();
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        level.swarms().addEntry( swarm.clone() );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }