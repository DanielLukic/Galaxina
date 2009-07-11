package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorState;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.Level;
import net.intensicode.galaxina.domain.Swarm;

public final class DeleteSwarm extends RunnableAction implements EditorStateListener
    {
    public DeleteSwarm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        setEnabled( false );

        myCoreAPI.state().add( EditorState.SELECTED_SWARM, this );
        }

    // From Runnable

    public final void run()
        {
        final Level level = myCoreAPI.state().currentLevel();
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        myCoreAPI.state().change( EditorState.SELECTED_SWARM, null );
        level.swarms().deleteEntry( swarm );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }