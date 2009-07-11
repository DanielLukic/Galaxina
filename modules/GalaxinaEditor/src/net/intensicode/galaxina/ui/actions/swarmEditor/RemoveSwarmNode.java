package net.intensicode.galaxina.ui.actions.swarmEditor;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.EditorStateListener;
import net.intensicode.galaxina.domain.PositionEx;
import net.intensicode.galaxina.domain.SelectedSwarmMarker;
import net.intensicode.galaxina.domain.Swarm;
import net.intensicode.galaxina.ui.actions.RunnableAction;

public final class RemoveSwarmNode extends RunnableAction implements EditorStateListener
    {
    public RemoveSwarmNode( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        aCoreAPI.state().add( SELECTED_SWARM_MARKER, this );
        }

    // From EditorStateListener

    public final void onStateChanged( final String aEventID, final Object aOldValue, final Object aNewValue )
        {
        setEnabled( aNewValue != null );
        }

    // From Runnable

    public final void run()
        {
        final SelectedSwarmMarker marker = (SelectedSwarmMarker) myCoreAPI.state().get( SELECTED_SWARM_MARKER );
        if ( marker == null ) throw new IllegalStateException();

        final Swarm selectedSwarm = marker.swarm;
        final int deletedIndex = selectedSwarm.positions().indexOf( marker.marker );
        if ( selectedSwarm.positions().size() == selectedSwarm.getSize() )
            {
            selectedSwarm.setSize( selectedSwarm.getSize() - 1 );
            }
        selectedSwarm.positions().deleteEntry( marker.marker );

        if ( selectedSwarm.positions().size() == 0 )
            {
            myCoreAPI.state().change( SELECTED_SWARM_MARKER, null );
            }
        else
            {
            final int newIndex = Math.min( deletedIndex, selectedSwarm.positions().size() - 1 );
            final PositionEx lastPosition = selectedSwarm.positions().at( newIndex );
            myCoreAPI.state().change( SELECTED_SWARM_MARKER, new SelectedSwarmMarker( selectedSwarm, lastPosition ) );
            }
        }
    }