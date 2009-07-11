package net.intensicode.galaxina.ui.actions.swarmEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.Identifiers;
import net.intensicode.galaxina.domain.SelectedSwarmMarker;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class ResetSwarm extends RunnableAction implements EditorStateListener
    {
    public ResetSwarm( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        aCoreAPI.state().add( Identifiers.SELECTED_SWARM, this );
        }

    // From Runnable

    public final void run()
        {
        final Swarm swarm = myCoreAPI.state().currentSwarm();
        final SelectedSwarmMarker marker = myCoreAPI.state().getSelectedSwarmMarker();
        if ( marker.swarm == swarm )
            {
            myCoreAPI.state().change( Identifiers.SELECTED_SWARM_MARKER, null );
            }
        swarm.positions().clear();
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }
    }
